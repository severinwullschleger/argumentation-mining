package Weka;

import Main.MicroText;
import Main.TextSegment;
import weka.core.Attribute;

import java.util.ArrayList;
import java.util.List;

public class IsClaimClassifier extends TextSegmentClassifier {

    @Override
    protected Attribute defineClassAttribute() {
        ArrayList<String> isClaimClassValues = new ArrayList<>(2);
        isClaimClassValues.add("yes");
        isClaimClassValues.add("no");
        classAttribute = new Attribute("isClaim", isClaimClassValues);
        return classAttribute;
    }

    @Override
    protected String getClassValue(TextSegment textSegment) {
        if (textSegment.getClaim())
            return "yes";
        else
            return "no";
    }
}
