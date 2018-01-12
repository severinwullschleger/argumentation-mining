package Weka;

import Main.MicroText;
import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.evaluation.Evaluation;
import weka.core.Attribute;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StanceClassifier extends Weka.Classifier{

    public void run(List<MicroText> microTexts) {

        // only corpuses which are tagged with stances
        List<MicroText> stanceTaggedMicroTexts = new ArrayList<>();
        for (MicroText microText : microTexts)
            if (microText.isStanceTagged())
                stanceTaggedMicroTexts.add(microText);

        // add Stance attribute
        ArrayList<String> stanceClassValues = new ArrayList<>(2);
        stanceClassValues.add("pro");
        stanceClassValues.add("con");
        Attribute stanceClassAttribute = new Attribute("stance", stanceClassValues);

        // define different attribute sets
        HashMap lemmaUnigramAttributes = getAllLemmaUnigramsFromMicroTextAsAttributes(stanceTaggedMicroTexts);
        HashMap lemmaBigramAttributes = getAllLemmaBigramsAsAttributes(stanceTaggedMicroTexts);


        // Declare the feature vector (changed to ArrayList; FastVector depreciated)
        ArrayList<Attribute> attributeVector = new ArrayList<>();
        // add ClassAttribute
        attributeVector.add(stanceClassAttribute);
        // add different attribute sets
        attributeVector.addAll(lemmaUnigramAttributes.values());
        attributeVector.addAll(lemmaBigramAttributes.values());

        ArrayList<MicroText> trainingMicroTexts = splitCorpuses(stanceTaggedMicroTexts, 10, false);
        ArrayList<MicroText> testingMicroTexts = splitCorpuses(stanceTaggedMicroTexts, 10, true);

        // Create training set
        Instances trainingSet = new Instances("trainingSet", attributeVector, trainingMicroTexts.size()+1);
        trainingSet.setClass(stanceClassAttribute);
        // Create testing set
        Instances testingSet = new Instances("testingSet", attributeVector, testingMicroTexts.size()+1);
        testingSet.setClass(stanceClassAttribute);

        // create and add instances to TRAINING set
        trainingSet.addAll(createDefaultInstances(trainingMicroTexts, attributeVector));
        // set class value
        for (int i = 0; i < trainingMicroTexts.size(); i++) {
            setStringValue(trainingSet.get(i), trainingMicroTexts.get(i).getStance().getStanceToString(), stanceClassAttribute);
        }
        // add 1s for lemma unigrams
        for (int i = 0; i < trainingMicroTexts.size(); i++) {
            setStringValuesInCorpusInstance(trainingSet.get(i), trainingMicroTexts.get(i).getLemmaUnigrams(),lemmaUnigramAttributes);
        }
        // add 1s for lemma bigrams
        for (int i = 0; i < trainingMicroTexts.size(); i++) {
            setStringValuesInCorpusInstance(trainingSet.get(i), trainingMicroTexts.get(i).getLemmaBigrams(),lemmaBigramAttributes);
        }

        // create and add instances to TESTING set
        testingSet.addAll(createDefaultInstances(testingMicroTexts, attributeVector));
        // set class value
        for (int i = 0; i < testingMicroTexts.size(); i++) {
            setStringValue(testingSet.get(i), testingMicroTexts.get(i).getStance().getStanceToString(), stanceClassAttribute);
        }
        // add 1 for lemma unigrams
        for (int i = 0; i < testingMicroTexts.size(); i++) {
            setStringValuesInCorpusInstance(testingSet.get(i), testingMicroTexts.get(i).getLemmaUnigrams(),lemmaUnigramAttributes);
        }
        // add 1s for lemma bigrams
        for (int i = 0; i < testingMicroTexts.size(); i++) {
            setStringValuesInCorpusInstance(testingSet.get(i), testingMicroTexts.get(i).getLemmaBigrams(),lemmaBigramAttributes);
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
