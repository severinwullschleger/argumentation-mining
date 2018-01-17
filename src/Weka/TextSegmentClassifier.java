package Weka;

import GUI.GUI;
import Main.MicroText;
import Main.TextSegment;
import StandfordParserManager.POSType;
import StandfordParserManager.POSTypeDecider;
import StandfordParserManager.StanfordNLP;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public abstract class TextSegmentClassifier extends Classifier{
    protected final String SENTIMENTSCORE = "sentimentScore";
    protected final String POSTYPE = "posType";


    protected List<TextSegment> alltextSegments;
    protected List<TextSegment> textSegments;
    protected List<TextSegment> trainingTextSegments;
    protected List<TextSegment> testTextSegments;

    protected Attribute classAttribute;
    protected List<HashMap> attributes;
    protected ArrayList<Attribute> attributeVector;

    protected Instances trainingSet;
    protected Instances testingSet;

    protected weka.classifiers.Classifier cModel;
    protected StanfordNLP stanfordNLP;

    protected abstract Attribute defineClassAttribute();

    protected abstract String getClassValue(TextSegment textSegment);

    public final void run(List<MicroText> microTexts, int testDataPercentage) {
        stanfordNLP = stanfordNLP.getInstance();

        createFullTextSegmentList(microTexts);
        createTextSegmentList(microTexts);

        defineClassAttribute();
        createAttributes();
        createAttributeVector();

        splitTextSegmentsIntoTrainingAndTestSet(testDataPercentage);
        createTrainingSet();
        createTestingSet();

        learnModel();
        evaluateModel();
    }

    private final List<TextSegment> createFullTextSegmentList(List<MicroText> microTexts) {
        //create list with all textsegments
        alltextSegments = new ArrayList<>();
        for (MicroText microText : microTexts) {
            alltextSegments.addAll(microText.getTextSegments());
        }
        return alltextSegments;
    }

    protected List<TextSegment> createTextSegmentList(List<MicroText> microTexts) {
        textSegments = new ArrayList<>(alltextSegments);
        return textSegments;
    }

    protected void createAttributes() {
        attributes = new ArrayList<>();
        // define different attribute sets
        attributes.add(getAllLemmaUnigramsAsAttributes());
        attributes.add(getAllLemmaBigramsAsAttributes());
//        attributes.add(generatePOSTypeAttributeHash());
//      attributes.add(generateSentimentScoreAttribute());
    }

    private HashMap generateSentimentScoreAttribute() {
        HashMap hash = new HashMap<String, Attribute>();

        ArrayList<String> sentimentScoreValues = new ArrayList<>(5);
        sentimentScoreValues.add("0");
        sentimentScoreValues.add("1");
        sentimentScoreValues.add("2");
        sentimentScoreValues.add("3");
        sentimentScoreValues.add("4");
        hash.put(SENTIMENTSCORE, new Attribute(SENTIMENTSCORE, sentimentScoreValues));
        return hash;
    }

    private HashMap generatePOSTypeAttributeHash() {
        HashMap hash = new HashMap<String, Attribute>();
        hash.put(POSTYPE, generatePOSTypeAttribute());
        return hash;
    }

    private Attribute generatePOSTypeAttribute() {
        ArrayList<String> posTypeValues = new ArrayList<>(16);
        for (POSType posType: POSType.values()) {
            posTypeValues.add(posType.toString());
        }
        Attribute posTypeAttribute = new Attribute(POSTYPE, posTypeValues);
        return posTypeAttribute;
    }

    private HashMap getAllLemmaUnigramsAsAttributes() {
        HashMap attributes = new HashMap<String, Attribute>();
        for (TextSegment textSegment : alltextSegments) {
            addStringListAsAttributes(attributes, textSegment.getLemmaUnigrams());
        }
        return attributes;
    }

    private HashMap getAllLemmaBigramsAsAttributes() {
        HashMap attributes = new HashMap<String, Attribute>();
        for (TextSegment textSegment : alltextSegments) {
            addStringListAsAttributes(attributes, textSegment.getLemmaBigrams());
        }
        return attributes;
    }

    private List<Attribute> createAttributeVector() {
        attributeVector = new ArrayList<>();
        attributeVector.add(classAttribute);

        // add different attribute sets
        for (HashMap hm : attributes)
            attributeVector.addAll(hm.values());

        return attributeVector;
    }

    protected void splitTextSegmentsIntoTrainingAndTestSet(int testDataPercentage) {
        Collections.shuffle(textSegments);
        trainingTextSegments = splitTextSegmentList(testDataPercentage, false);
        testTextSegments = splitTextSegmentList(testDataPercentage, true);
    }

    private ArrayList<TextSegment> splitTextSegmentList(int takeEveryXCorpus, Boolean isTestData) {
        ArrayList<TextSegment> partData = new ArrayList<>();

        for (int index = 0; index < textSegments.size(); index++) {
            if (isTestData) {
                if (index % takeEveryXCorpus == 0)
                    partData.add(textSegments.get(index));
            } else {
                if (index % takeEveryXCorpus != 0)
                    partData.add(textSegments.get(index));
            }
        }
        return partData;
    }

    private void createTrainingSet() {
        trainingSet = new Instances("trainingSet", attributeVector, trainingTextSegments.size() + 1);
        trainingSet.setClass(classAttribute);

        trainingSet.addAll(createDefaultInstancesList(trainingTextSegments.size(), attributeVector));

        addValuesToInstances(trainingSet, trainingTextSegments);
    }

    private void createTestingSet() {
        testingSet = new Instances("testingSet", attributeVector, testTextSegments.size() + 1);
        testingSet.setClass(classAttribute);

        testingSet.addAll(createDefaultInstancesList(testTextSegments.size(), attributeVector));

        addValuesToInstances(testingSet, testTextSegments);
    }

    protected final void addValuesToInstances(Instances trainingSet, List<TextSegment> trainingTextSegments) {
        for (int i = 0; i < trainingTextSegments.size(); i++) {
            addClassValueToInstance(trainingSet.get(i), trainingTextSegments.get(i));
            addValuesToInstance(trainingSet.get(i), trainingTextSegments.get(i));
        }
    }

    protected final void addClassValueToInstance(Instance instance, TextSegment textSegment) {
        setStringValue(instance, getClassValue(textSegment), classAttribute);
    }

    protected void addValuesToInstance(Instance instance, TextSegment textSegment) {
        //Set Unigrams
        setStringValuesToOne(instance, textSegment.getLemmaUnigrams(), attributes.get(0));
        setStringValuesToOne(instance, textSegment.getLemmaUnigramsFromPrecedingSegment(), attributes.get(0));
        setStringValuesToOne(instance, textSegment.getLemmaUnigramsFromSubsequentSegment(), attributes.get(0));
        //Set Bigrams
        setStringValuesToOne(instance, textSegment.getLemmaBigrams(), attributes.get(1));

        // set additional Values
//        //Set POStype
//        POSType postType = POSTypeDecider.getInstance().getPOSType(textSegment.getSentence().toString());
//        setStringValue(instance, postType.toString(), attributes.get(2), POSTYPE);
//        //Set Sentimentscore
//        int sentimentScore = stanfordNLP.getSentimentScore(textSegment.toString());
//        setNumericValue(instance, sentimentScore, attributes.get(3), SENTIMENTSCORE);
    }

    protected void learnModel() {
        try {
            // Create a naïve bayes classifier
            cModel = (weka.classifiers.Classifier)new NaiveBayes();
//            cModel = (weka.classifiers.Classifier)new J48();
            cModel.buildClassifier(trainingSet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void evaluateModel() {
        try {
            // Test the model
            Evaluation eTest = new Evaluation(testingSet);
            eTest.evaluateModel(cModel, testingSet);

            // Print the result à la Weka explorer:
            String strSummary = eTest.toSummaryString();
            System.out.println(strSummary);


            showResultOnGUI(strSummary);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showResultOnGUI(String strSummary) {
        String temp[] = strSummary.split("\n");
        GUI.getGUI().correctInstancesLabel.setText(temp[0]);
        GUI.getGUI().incorrectInstancesLabel.setText(temp[1]);
        GUI.getGUI().runClassifierInfoLabel.setText("");

        List<String> notif = new ArrayList<>();
        notif.add(strSummary);
        GUI.showNotification(notif);
    }

    public final void useClassifier(MicroText myMicroText) {
        List<Instance> instances = createInstances(myMicroText);
        setDataSetFor(instances);
        makeDecisionsFor(instances, myMicroText);
    }

    private List<Instance> createInstances(MicroText myMicroText) {
        List<Instance> instances = createDefaultInstancesList(myMicroText.getTextSegments().size(), attributeVector);
        for (int i = 0; i < instances.size(); i++)
            addValuesToInstance(instances.get(i), myMicroText.getTextSegment(i));
        return instances;
    }

    private final void setDataSetFor(List<Instance> instances) {
        for (Instance instance : instances)
            instance.setDataset(trainingSet);
    }

    protected abstract MicroText makeDecisionsFor(List<Instance> instances, MicroText myMicroText);

    protected abstract void handleDecisionDistribution(double[] distribution, TextSegment textSegment);

    protected final double[] getDistributionFor(Instance instance) {
        try {
            double[] fDistribution = cModel.distributionForInstance(instance);
            return fDistribution;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
