package Weka;

import Main.MicroText;
import Main.Model.role.Opponent;
import Main.Model.role.Proponent;
import Main.TextSegment;
import weka.core.Attribute;
import weka.core.Instance;

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
        if (textSegment.isClaim())
            return "yes";
        else
            return "no";
    }

    @Override
    protected MicroText makeDecisionsFor(List<Instance> instances, MicroText myMicroText) {
        List<double[]> distributions = new ArrayList<>();
        for(int i = 0; i < instances.size(); i++) {
            double[] distribution = getDistributionFor(instances.get(i));
            distributions.add(distribution);
        }
        TextSegment textSegment = findTextSegmentWithHighestClaimPercentage(distributions, myMicroText);
        myMicroText.setClaimSegment(textSegment);
        return myMicroText;
    }

    private TextSegment findTextSegmentWithHighestClaimPercentage(List<double[]> distributions, MicroText myMicroText) {
        TextSegment claimSegment = myMicroText.getTextSegment(0);
        double highestClaimPercentage = distributions.get(0)[0];
        for (int i = 1; i<distributions.size();i++) {
            if (highestClaimPercentage < distributions.get(i)[0]) {
                highestClaimPercentage = distributions.get(i)[0];
                claimSegment = myMicroText.getTextSegment(i);
            }
        }
        return claimSegment;
    }

    @Override
    protected void handleDecisionDistribution(double[] distribution, TextSegment textSegment) {
        // Decision is made for the whole microtext
    }
}
