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

        List<Corpus> stanceTaggedCorpuses = new ArrayList<>();
        for (Corpus corpus : corpuses)
            if (corpus.isStanceTagged())
                stanceTaggedCorpuses.add(corpus);

        ArrayList<String> stanceClassValues = new ArrayList<>(2);
        stanceClassValues.add("pro");
        stanceClassValues.add("con");
        Attribute stanceClassAttribute = new Attribute("stance", stanceClassValues);

        HashMap lemmaUnigramAttributes = new HashMap<String, Attribute>();
        addAllLemmasAsAttributes(stanceTaggedCorpuses, lemmaUnigramAttributes);

        // Declare the feature vector -> changed to ArrayList (FastVector depreciated
        ArrayList<Attribute> vector = new ArrayList<>();
        vector.add(stanceClassAttribute);
        vector.addAll(lemmaUnigramAttributes.values());

        ArrayList<Corpus> trainingCorpuses = splitData(stanceTaggedCorpuses, 10, false);
        ArrayList<Corpus> testCorpuses = splitData(stanceTaggedCorpuses, 10, true);

        // Create an empty training set
        Instances trainingSet = createInstanceSet("trainingSet", 0, lemmaUnigramAttributes, vector, trainingCorpuses);
        System.out.println(trainingSet);
        Instances testingSet = createInstanceSet("testingSet", 0, lemmaUnigramAttributes, vector, testCorpuses);
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

    private ArrayList<Corpus> splitData(List<Corpus> corpuses, int takeEveryXCorpus, Boolean isTestData) {
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


    private void addAllLemmasAsAttributes(List<Corpus> corpuses, HashMap attributes) {
        for (Corpus corpus : corpuses) {
            HashMap lemmasPerSentence = corpus.getAllLemmasPerTextSentence();
            addAllLemmasFromCorpusAttributes(attributes, lemmasPerSentence);
        }
    }

    private void addAllLemmasFromCorpusAttributes(HashMap attributes, HashMap lemmaPerSentence) {
        Set<String> sentenceIds = lemmaPerSentence.keySet();
        for (String sentenceId : sentenceIds) {
            List<String> lemmas = (List<String>) lemmaPerSentence.get(sentenceId);
            addAllLemmasFromSentenceAsAttributes(attributes, lemmas);
        }
    }

    private void addAllLemmasFromSentenceAsAttributes(HashMap attributes, List<String> lemmas) {
        for (String lemma : lemmas)
            if (!attributes.containsKey(lemma))
                attributes.put(lemma, new Attribute(lemma));
    }

    private Instances createInstanceSet(String setName, int classIndex, HashMap lemmaUnigramAttributes, ArrayList<Attribute> vector, ArrayList<Corpus> corpuses) {
        // Create an empty training set
        Instances set = new Instances(setName, vector, corpuses.size()+1);
        // Set class index
        set.setClassIndex(classIndex);

        for (Corpus corpus : corpuses) {
            // Create the instance
            Instance corpusInstance = new DenseInstance(vector.size());
            // add the stance to the first attribute
            corpusInstance.setValue(vector.get(0),corpus.getStance().getStanceToString());
            // add default value to attributes
            for (int i = 1; i < corpusInstance.numAttributes(); i++) {
                corpusInstance.setValue(vector.get(i), 0.0);
            }
            // change 0 to 1 if for the lemmas that are in the corpus
            for (String lemma : corpus.getAllLemmas()) {
                corpusInstance.setValue((Attribute)lemmaUnigramAttributes.get(lemma), 1.0);
            }
            // add the instance to training set
            set.add(corpusInstance);
        }
        return set;
    }
}
