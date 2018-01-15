package Weka;

import Main.TextSegment;
import weka.core.Attribute;

import java.util.*;

public class ProponentOponentClassifier extends TextSegmentClassifier {

    @Override
    protected Attribute defineClassAttribute() {
        // add PropOp attribute
        ArrayList<String> propOpClassValues = new ArrayList<>(2);
        propOpClassValues.add("pro");
        propOpClassValues.add("opp");
        classAttribute = new Attribute("proOpp", propOpClassValues);
        return classAttribute;
    }

    @Override
    protected String getClassValue(TextSegment textSegment) {
        return textSegment.getType();
    }
}
