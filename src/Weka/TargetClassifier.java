package Weka;

import Main.MicroText;
import Main.TextSegment;
import weka.core.Attribute;
import weka.core.Instance;

import java.util.ArrayList;
import java.util.List;

public class TargetClassifier extends TextSegmentClassifier {

    @Override
    protected Attribute defineClassAttribute() {
        // add Target class attribute
        ArrayList<String> targetClassValues = new ArrayList<>(6);
        targetClassValues.add("0");
        targetClassValues.add("1");
        targetClassValues.add("2");
        targetClassValues.add("3");
        targetClassValues.add("4");
        targetClassValues.add("5");
        classAttribute = new Attribute("target", targetClassValues);
        return classAttribute;
    }

    @Override
    protected String getClassValue(TextSegment textSegment) {
        if (textSegment.isClaim())
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

    @Override
    protected MicroText makeDecisionsFor(List<Instance> instances, MicroText myMicroText) {
        return null;
    }

    @Override
    protected void handleDecisionDistribution(double[] distribution, TextSegment textSegment) {

    }
}
