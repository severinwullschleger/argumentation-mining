package Weka;

import Main.MicroText;
import Main.TextSegment;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.evaluation.Evaluation;
import weka.core.Attribute;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class TextSegmentClassifier extends Classifier{

    protected List<TextSegment> textSegments;
    protected List<TextSegment> trainingTextSegments;
    protected List<TextSegment> testTextSegments;

    protected Attribute classAttribute;
    protected List<HashMap> attributes;
    protected ArrayList<Attribute> attributeVector;

    protected Instances trainingSet;
    protected Instances testingSet;

    protected weka.classifiers.Classifier cModel;

    protected abstract Attribute defineClassAttribute();
    protected abstract String getClassValue(TextSegment textSegment);

    public final void run(List<MicroText> microTexts, int testDataPercentage) {

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

    protected List<TextSegment> createTextSegmentList(List<MicroText> microTexts) {
        //create list with all textsegments
        textSegments = new ArrayList<>();
        for(MicroText microText : microTexts) {
            textSegments.addAll(microText.getTextSegments());
        }
        return textSegments;
    }

    protected void createAttributes() {
        attributes = new ArrayList<>();
        // define different attribute sets
        attributes.add(getAllLemmaUnigramsAsAttributes());
        attributes.add(getAllLemmaBigramsAsAttributes());
    }

    private HashMap getAllLemmaUnigramsAsAttributes() {
        HashMap attributes = new HashMap<String, Attribute>();
        for (TextSegment textSegment : textSegments) {
            addStringListAsAttributes(attributes, textSegment.getLemmaUnigrams());
        }
        return attributes;
    }

    private HashMap getAllLemmaBigramsAsAttributes() {
        HashMap attributes = new HashMap<String, Attribute>();
        for (TextSegment textSegment : textSegments) {
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
        trainingTextSegments = splitTextSegmentList(testDataPercentage, false);
        testTextSegments = splitTextSegmentList(testDataPercentage, true);
    }

    private ArrayList<TextSegment> splitTextSegmentList(int takeEveryXCorpus, Boolean isTestData) {
        ArrayList<TextSegment> partData = new ArrayList<>();

        for (int index = 0; index < textSegments.size(); index++) {
            if (isTestData) {
                if (index % takeEveryXCorpus == 0)
                    partData.add(textSegments.get(index));
            }
            else {
                if (index % takeEveryXCorpus != 0)
                    partData.add(textSegments.get(index));
            }
        }
        return partData;
    }

    private void createTrainingSet() {
        trainingSet = new Instances("trainingSet", attributeVector, trainingTextSegments.size()+1);
        trainingSet.setClass(classAttribute);

        trainingSet.addAll(createDefaultInstancesList(trainingTextSegments.size(), attributeVector));

        addValuesToInstances(trainingSet, trainingTextSegments);
    }

    private void createTestingSet() {
        testingSet = new Instances("testingSet", attributeVector, testTextSegments.size()+1);
        testingSet.setClass(classAttribute);

        testingSet.addAll(createDefaultInstancesList(testTextSegments.size(), attributeVector));

        addValuesToInstances(testingSet, testTextSegments);
    }

    protected void addValuesToInstances(Instances trainingSet, List<TextSegment> trainingTextSegments) {
        for (int i = 0; i < trainingTextSegments.size(); i++) {
            //ClassValue
            setStringValue(trainingSet.get(i), getClassValue(trainingTextSegments.get(i)), classAttribute);
            //Set other values
            setStringValuesToOne(trainingSet.get(i), trainingTextSegments.get(i).getLemmaUnigrams(), attributes.get(0));
            setStringValuesToOne(trainingSet.get(i), trainingTextSegments.get(i).getLemmaUnigramsFromPrecedingSegment(), attributes.get(0));
            setStringValuesToOne(trainingSet.get(i), trainingTextSegments.get(i).getLemmaUnigramsFromSubsequentSegment(), attributes.get(0));
            setStringValuesToOne(trainingSet.get(i), trainingTextSegments.get(i).getLemmaBigrams(), attributes.get(1));
        }
    }

    protected void learnModel() {
        try {
            // Create a naïve bayes classifier
            cModel = (weka.classifiers.Classifier)new NaiveBayes();
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
