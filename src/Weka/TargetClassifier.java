package Weka;

import Main.MicroText;
import Main.TextSegment;
import org.apache.commons.lang3.ArrayUtils;
import weka.core.Attribute;
import weka.core.Instance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TargetClassifier extends TextSegmentClassifier {

    @Override
    protected List<TextSegment> createTextSegmentList(List<MicroText> microTexts) {
        textSegments = new ArrayList<>();
        for(MicroText microText : microTexts) {
            for(int i = 0; i < microText.getTextSegments().size(); i++) {
                TextSegment textSegment = microText.getTextSegment(i);
                if (textSegment.hasRelation())
                    textSegments.add(textSegment);
            }
        }
        return textSegments;
    }

    @Override
    protected Attribute defineClassAttribute() {
        // add Target class attribute
        ArrayList<String> targetClassValues = new ArrayList<>(6);
        targetClassValues.add("1");
        targetClassValues.add("2");
        targetClassValues.add("3");
        targetClassValues.add("4");
        targetClassValues.add("5");
        targetClassValues.add("0");
        classAttribute = new Attribute("target", targetClassValues);
        return classAttribute;
    }

    @Override
    protected String getClassValue(TextSegment textSegment) {

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
        return "0";
    }

    @Override
    protected MicroText makeDecisionsFor(List<Instance> instances, MicroText myMicroText) {
        List<double[]> distributions = new ArrayList<>();
        for(int i = 0; i < instances.size(); i++) {
            if (!myMicroText.getTextSegment(i).isClaim()) {
                double[] distribution = getDistributionFor(instances.get(i));
                distributions.add(distribution);
                handleDecisionDistribution(distribution, myMicroText.getTextSegment(i));
            }
        }

        return myMicroText;
    }

    @Override
    protected void handleDecisionDistribution(double[] distribution, TextSegment textSegment) {
        Double[] doubleArray = ArrayUtils.toObject(distribution);
        List<Double> dist = Arrays.asList(doubleArray);

        Double maxPercentage = Collections.max(dist);
        int maxPercentageIndex = dist.indexOf(maxPercentage);

        while (maxPercentageIndex >= 5 || maxPercentageIndex >= textSegment.getMicroText().getTextSegments().size()) {
            dist.remove(maxPercentageIndex);
            maxPercentage = Collections.max(dist);
            maxPercentageIndex = dist.indexOf(maxPercentage);
        }
        textSegment.addRelationTarget(maxPercentageIndex+1);
    }
}
