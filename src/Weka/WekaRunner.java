package Weka;

import Main.Corpus;
import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.evaluation.Evaluation;
import weka.core.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class WekaRunner {

    public WekaRunner() {

    }

    public void wekaExample() {
        // Declare two numeric attributes
        Attribute Attribute1 = new Attribute("pos_tags");
        Attribute Attribute2 = new Attribute("secondNumeric");

        // Declare a nominal attribute along with its values
        FastVector fvNominalVal = new FastVector(3);
        fvNominalVal.addElement("blue");
        fvNominalVal.addElement("gray");
        fvNominalVal.addElement("black");
        Attribute Attribute3 = new Attribute("aNominal", fvNominalVal);

        // Declare the class attribute along with its values
        FastVector fvClassVal = new FastVector(2);
        fvClassVal.addElement("proponent");
        fvClassVal.addElement("oponent");
//        fvClassVal.addElement("thesis");
//        fvClassVal.addElement("support");
//        fvClassVal.addElement("attack");
        Attribute ClassAttribute = new Attribute("role", fvClassVal);

        // Declare the feature vector
        FastVector fvWekaAttributes = new FastVector(4);
        fvWekaAttributes.addElement(Attribute1);
        fvWekaAttributes.addElement(Attribute2);
        fvWekaAttributes.addElement(Attribute3);
        fvWekaAttributes.addElement(ClassAttribute);

        // Create an empty training set
        Instances isTrainingSet = new Instances("Rel", fvWekaAttributes, 10);
        // Set class index
        isTrainingSet.setClassIndex(3);

        // Create the instance
        Instance iExample = new DenseInstance(4);
        iExample.setValue((Attribute)fvWekaAttributes.elementAt(0), 1.0);
        iExample.setValue((Attribute)fvWekaAttributes.elementAt(1), 0.5);
        iExample.setValue((Attribute)fvWekaAttributes.elementAt(2), "gray");
        iExample.setValue((Attribute)fvWekaAttributes.elementAt(3), "positive");

        // add the instance
        isTrainingSet.add(iExample);

        // Create a naïve bayes classifier
        Classifier cModel = (Classifier)new NaiveBayes();
        try {
            cModel.buildClassifier(isTrainingSet);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // TODO testing set

    }

    public void runStanceClassifier(List<Corpus> corpuses) {

        // only corpuses which are tagged with stances
        List<Corpus> stanceTaggedCorpuses = new ArrayList<>();
        for (Corpus corpus : corpuses)
            if (corpus.isStanceTagged())
                stanceTaggedCorpuses.add(corpus);

        // add Stance attribute
        ArrayList<String> stanceClassValues = new ArrayList<>(2);
        stanceClassValues.add("pro");
        stanceClassValues.add("con");
        Attribute stanceClassAttribute = new Attribute("stance", stanceClassValues);

        // define different attribute sets
            HashMap lemmaUnigramAttributes = getAllLemmaUnigramsAsAttributes(stanceTaggedCorpuses);
            HashMap lemmaBigramAttributes = getAllLemmaBigramsAsAttributes(stanceTaggedCorpuses);


        // Declare the feature vector (changed to ArrayList; FastVector depreciated)
        ArrayList<Attribute> attributeVector = new ArrayList<>();
        // add ClassAttribute
        attributeVector.add(stanceClassAttribute);
            // add different attribute sets
            attributeVector.addAll(lemmaUnigramAttributes.values());
            attributeVector.addAll(lemmaBigramAttributes.values());

        ArrayList<Corpus> trainingCorpuses = splitCorpuses(stanceTaggedCorpuses, 10, false);
        ArrayList<Corpus> testingCorpuses = splitCorpuses(stanceTaggedCorpuses, 10, true);

        // Create training set
        Instances trainingSet = new Instances("trainingSet", attributeVector, trainingCorpuses.size()+1);
        trainingSet.setClass(stanceClassAttribute);
        // Create testing set
        Instances testingSet = new Instances("testingSet", attributeVector, testingCorpuses.size()+1);
        testingSet.setClass(stanceClassAttribute);

        // create and add instances to TRAINING set
        trainingSet.addAll(createDefaultInstances(trainingCorpuses, attributeVector));
        // set class value
        for (int i = 0; i < trainingCorpuses.size(); i++) {
            setStringValue(trainingSet.get(i), trainingCorpuses.get(i).getStance().getStanceToString(), stanceClassAttribute);
        }
        // add 1 for lemma unigrams
        for (int i = 0; i < trainingCorpuses.size(); i++) {
            setStringValuesInCorpusInstance(trainingSet.get(i), trainingCorpuses.get(i).getLemmaUnigrams(),lemmaUnigramAttributes);
        }
        // add 1s for lemma bigrams
        for (int i = 0; i < trainingCorpuses.size(); i++) {
            setStringValuesInCorpusInstance(trainingSet.get(i), trainingCorpuses.get(i).getLemmaBigrams(),lemmaBigramAttributes);
        }

        // create and add instances to TESTING set
        testingSet.addAll(createDefaultInstances(testingCorpuses, attributeVector));
        // set class value
        for (int i = 0; i < testingCorpuses.size(); i++) {
            setStringValue(testingSet.get(i), testingCorpuses.get(i).getStance().getStanceToString(), stanceClassAttribute);
        }
        // add 1 for lemma unigrams
        for (int i = 0; i < testingCorpuses.size(); i++) {
            setStringValuesInCorpusInstance(testingSet.get(i), testingCorpuses.get(i).getLemmaUnigrams(),lemmaUnigramAttributes);
        }
        // add 1s for lemma bigrams
        for (int i = 0; i < testingCorpuses.size(); i++) {
            setStringValuesInCorpusInstance(testingSet.get(i), testingCorpuses.get(i).getLemmaBigrams(),lemmaBigramAttributes);
        }

        System.out.println(trainingSet);
        System.out.println(testingSet);

        try {
            // Create a naïve bayes classifier
            Classifier cModel = (Classifier)new NaiveBayes();
            cModel.buildClassifier(trainingSet);

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


    private ArrayList<Corpus> splitCorpuses(List<Corpus> corpuses, int takeEveryXCorpus, Boolean isTestData) {
        ArrayList<Corpus> partData = new ArrayList<>();
        for (int index = 0; index < corpuses.size(); index++) {
            if (isTestData) {
                if (index % takeEveryXCorpus == 0)
                    partData.add(corpuses.get(index));
            }
            else {
                if (index % takeEveryXCorpus != 0)
                    partData.add(corpuses.get(index));
            }
        }
        return partData;
    }


    private HashMap getAllLemmaUnigramsAsAttributes(List<Corpus> corpuses) {
        HashMap attributes = new HashMap<String, Attribute>();
        for (Corpus corpus : corpuses) {
            HashMap lemmaUnigramsPerSentence = corpus.getAllLemmaUnigramsPerTextSentence();
            addStringsFromCorpusAsAttributes(attributes, lemmaUnigramsPerSentence);
        }
        return attributes;
    }

    private HashMap getAllLemmaBigramsAsAttributes(List<Corpus> corpuses) {
        HashMap attributes = new HashMap<String, Attribute>();
        for (Corpus corpus : corpuses) {
            HashMap lemmaBigramsPerSentence = corpus.getAllLemmaBigramsPerTextSentence();
            addStringsFromCorpusAsAttributes(attributes, lemmaBigramsPerSentence);
        }
        return attributes;
    }

    private void addStringsFromCorpusAsAttributes(HashMap attributes, HashMap lemmaPerSentence) {
        Set<String> sentenceIds = lemmaPerSentence.keySet();
        for (String sentenceId : sentenceIds) {
            List<String> strings = (List<String>) lemmaPerSentence.get(sentenceId);
            addStringsFromSentenceAsAttributes(attributes, strings);
        }
    }

    private void addStringsFromSentenceAsAttributes(HashMap attributes, List<String> strings) {
        for (String s : strings)
            if (!attributes.containsKey(s))
                attributes.put(s, new Attribute(s));
    }

    private List<Instance> createDefaultInstances(ArrayList<Corpus> corpuses, ArrayList<Attribute> vector) {
        List<Instance> set = new ArrayList<Instance>();
        for (Corpus corpus : corpuses) {
            Instance corpusInstance = createDefaultInstance(vector);
            set.add(corpusInstance);
        }
        return set;
    }

    private Instance createDefaultInstance(ArrayList<Attribute> vector) {
        // Create the instance
        Instance corpusInstance = new DenseInstance(vector.size());
        // add default values to attributes
        for (int i = 1; i < corpusInstance.numAttributes(); i++) {
            corpusInstance.setValue(vector.get(i), 0.0);
        }
        return corpusInstance;
    }

    private Instance setStringValue(Instance corpusInstance, String value, Attribute attribute) {
        // change 0 to 1
        corpusInstance.setValue(attribute, value);
        return corpusInstance;
    }

    private Instance setStringValuesInCorpusInstance(Instance corpusInstance, List<String> corpusValues, HashMap attributes) {
        // change 0 to 1
        for (String s : corpusValues) {
            corpusInstance.setValue((Attribute)attributes.get(s), 1.0);
        }
        return corpusInstance;
    }
}
