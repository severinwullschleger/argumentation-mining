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

public class TargetClassifier extends TextSegmentClassifier {

    @Override
    protected Attribute getClassAttribute() {
        // add Target class attribute
        ArrayList<String> targetClassValues = new ArrayList<>(6);
        targetClassValues.add("0");
        targetClassValues.add("1");
        targetClassValues.add("2");
        targetClassValues.add("3");
        targetClassValues.add("4");
        targetClassValues.add("5");
        return new Attribute("target", targetClassValues);
    }

    @Override
    protected String getClassValue(TextSegment textSegment) {
        if (textSegment.getClaim())
            return "0";

        ArrayList<String> validStrings = new ArrayList<>(5);
        validStrings.add("1");
        validStrings.add("2");
        validStrings.add("3");
        validStrings.add("4");
        validStrings.add("5");

        String targetId = textSegment.getRelationTargetId();
        if (targetId.length() > 1)
            if (validStrings.contains(targetId.substring(1)))
                return targetId.substring(1);
        return "5";
    }

    public void run(List<MicroText> microTexts) {

        List<TextSegment> textSegments = createTextSegmentList(microTexts);
        Attribute targetClassAttribute = getClassAttribute();

        // define different attribute sets
        HashMap lemmaUnigramAttributes = getAllLemmaUnigramsFromTextSegmentAsAttributes(textSegments);
        HashMap lemmaBigramAttributes = getAllLemmaBigramsFromTextSegmentAsAttributes(textSegments);

        // Declare the feature vector (changed to ArrayList; FastVector depreciated)
        ArrayList<Attribute> attributeVector = new ArrayList<>();
        // add ClassAttribute
        attributeVector.add(targetClassAttribute);

        // add different attribute sets
        attributeVector.addAll(lemmaUnigramAttributes.values());
        attributeVector.addAll(lemmaBigramAttributes.values());


        // split textsegments into training and testing data
        ArrayList<TextSegment> trainingTextSegments = splitTextSegmentList(textSegments, 10, false);
        ArrayList<TextSegment> testTextSegments = splitTextSegmentList(textSegments, 10, true);


        //Create training set
        Instances trainingSet = new Instances("trainingSet", attributeVector, trainingTextSegments.size()+1);
        trainingSet.setClass(targetClassAttribute);
        // create and add instances to TRAINING set
        trainingSet.addAll(createDefaultInstancesList(trainingTextSegments.size(), attributeVector));
        // set class value
        for (int i = 0; i < trainingTextSegments.size(); i++) {
            setStringValue(trainingSet.get(i), getClassValue(trainingTextSegments.get(i)), targetClassAttribute);
            setStringValuesToOne(trainingSet.get(i), trainingTextSegments.get(i).getLemmaUnigrams(),lemmaUnigramAttributes);
            setStringValuesToOne(trainingSet.get(i), trainingTextSegments.get(i).getLemmaBigrams(),lemmaBigramAttributes);
        }

        // Create testing set
        Instances testingSet = new Instances("testingSet", attributeVector, testTextSegments.size()+1);
        testingSet.setClass(targetClassAttribute);
        // create and add instances to TRAINING set
        testingSet.addAll(createDefaultInstancesList(testTextSegments.size(), attributeVector));
        // set class value
        for (int i = 0; i < testTextSegments.size(); i++) {
            setStringValue(testingSet.get(i), getClassValue(testTextSegments.get(i)), targetClassAttribute);
            setStringValuesToOne(testingSet.get(i), testTextSegments.get(i).getLemmaUnigrams(),lemmaUnigramAttributes);
            setStringValuesToOne(testingSet.get(i), testTextSegments.get(i).getLemmaBigrams(),lemmaBigramAttributes);
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
