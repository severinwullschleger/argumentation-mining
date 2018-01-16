package Weka;

import Main.Model.role.Opponent;
import Main.Model.role.Proponent;
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

    @Override
    protected void handleDecisionDistribution(double[] distribution, TextSegment textSegment) {
        if (distribution[0] >= distribution[1])
            textSegment.changeTypeTo(new Proponent());
        else
            textSegment.changeTypeTo(new Opponent());
    }
}
