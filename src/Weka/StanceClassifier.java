package Weka;

import Main.Corpus;
import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.evaluation.Evaluation;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class StanceClassifier extends Weka.Classifier{

    public void run(List<Corpus> corpuses) {

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

        ArrayList<Corpus> trainingCorpuses = splitCorpuses(stanceTaggedCorpuses, 13, false);
        ArrayList<Corpus> testingCorpuses = splitCorpuses(stanceTaggedCorpuses, 13, true);

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
}
