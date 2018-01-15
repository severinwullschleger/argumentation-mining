package Weka;

import Main.TextSegment;
import weka.core.Attribute;

import java.util.ArrayList;

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
}
