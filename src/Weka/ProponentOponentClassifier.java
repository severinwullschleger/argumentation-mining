package Weka;

import Main.MicroText;
import Main.Model.role.Opponent;
import Main.Model.role.Proponent;
import Main.TextSegment;
import weka.core.Attribute;
import weka.core.Instance;

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
    protected MicroText makeDecisionsFor(List<Instance> instances, MicroText myMicroText) {
        List<double[]> distributions = new ArrayList<>();
        for(int i = 0; i < instances.size(); i++) {
            double[] distribution = getDistributionFor(instances.get(i));
            distributions.add(distribution);
            handleDecisionDistribution(distribution, myMicroText.getTextSegment(i));
        }
        return myMicroText;
    }

    @Override
    protected void handleDecisionDistribution(double[] distribution, TextSegment textSegment) {
        if (distribution[0] >= distribution[1])
            textSegment.changeTypeTo(new Proponent());
        else
            textSegment.changeTypeTo(new Opponent());
    }
}
