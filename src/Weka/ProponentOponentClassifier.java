package Weka;

import Main.MicroText;
import Main.TextSegment;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.evaluation.Evaluation;
import weka.core.Attribute;
import weka.core.Instances;

import java.util.*;

public class ProponentOponentClassifier extends Weka.Classifier {

    public void run(List<MicroText> microTexts) {


        //create list with all textsegments
        List<TextSegment> textSegments = new ArrayList<>();
        for(MicroText microText : microTexts) {
            textSegments.addAll(microText.getTextSegments());
        }

        // add PropOp attribute
        ArrayList<String> propOpClassValues = new ArrayList<>(2);
        propOpClassValues.add("pro");
        propOpClassValues.add("opp");
        Attribute propOpClassAttribute = new Attribute("proponentOpponent", propOpClassValues);

        // Declare the feature vector (changed to ArrayList; FastVector depreciated)
        ArrayList<Attribute> attributeVector = new ArrayList<>();

        // define different attribute sets
        HashMap lemmaUnigramAttributes = getAllLemmaUnigramsFromTextSegmentAsAttributes(textSegments);
        HashMap lemmaBigramAttributes = getAllLemmaBigramsFromTextSegmentAsAttributes(textSegments);

        ArrayList<TextSegment> trainingTextSegments = splitCorpusTextSegment(textSegments, 10, false);
        ArrayList<TextSegment> testTextSegments = splitCorpusTextSegment(textSegments, 10, true);


        //Create training set
        Instances trainingSet = new Instances("trainingSet", attributeVector, trainingTextSegments.size()+1);
        trainingSet.setClass(propOpClassAttribute);

        // create and add instances to TRAINING set
        trainingSet.addAll(createDefaultInstancesTextSegment(trainingTextSegments, attributeVector));
        // set class value
        for (int i = 0; i < trainingTextSegments.size(); i++) {
            setStringValue(trainingSet.get(i), trainingTextSegments.get(i).getTyp(), propOpClassAttribute);
            setStringValuesInCorpusInstance(trainingSet.get(i), trainingTextSegments.get(i).getLemmaUnigrams(),lemmaUnigramAttributes);
            setStringValuesInCorpusInstance(trainingSet.get(i), trainingTextSegments.get(i).getLemmaBigrams(),lemmaBigramAttributes);

        }

        // Create testing set
        Instances testingSet = new Instances("testingSet", attributeVector, testTextSegments.size()+1);
        testingSet.setClass(propOpClassAttribute);

        // create and add instances to TRAINING set
        testingSet.addAll(createDefaultInstancesTextSegment(testTextSegments, attributeVector));
        // set class value
        for (int i = 0; i < testTextSegments.size(); i++) {
            setStringValue(testingSet.get(i), testTextSegments.get(i).getTyp(), propOpClassAttribute);
            setStringValuesInCorpusInstance(testingSet.get(i), testTextSegments.get(i).getLemmaUnigrams(),lemmaUnigramAttributes);
            setStringValuesInCorpusInstance(testingSet.get(i), testTextSegments.get(i).getLemmaBigrams(),lemmaBigramAttributes);

        }

        try {
            // Create a naïve bayes classifier
            weka.classifiers.Classifier cModel = (weka.classifiers.Classifier)new NaiveBayes();
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
