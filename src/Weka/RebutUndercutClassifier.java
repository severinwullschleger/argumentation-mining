package Weka;

import Main.MicroText;
import Main.Model.type.Rebut;
import Main.Model.type.UndefinedAttack;
import Main.Model.type.UndefinedSupport;
import Main.Model.type.Undercut;
import Main.TextSegment;
import weka.core.Attribute;
import weka.core.Instance;

import java.util.ArrayList;
import java.util.List;

public class RebutUndercutClassifier extends TextSegmentClassifier {

    @Override
    protected List<TextSegment> createTextSegmentList(List<MicroText> microTexts) {
        textSegments = new ArrayList<>();
        for(MicroText microText : microTexts) {
            for(int i = 0; i < microText.getTextSegments().size(); i++) {
                TextSegment textSegment = microText.getTextSegment(i);
                if (textSegment.isAttack())
                    textSegments.add(textSegment);
            }
        }
        return textSegments;
    }

    @Override
    protected Attribute defineClassAttribute() {
        ArrayList<String> attSupValues = new ArrayList<>(2);
        attSupValues.add("reb");
        attSupValues.add("und");
        classAttribute = new Attribute("rebUnd", attSupValues);
        return classAttribute;
    }

    @Override
    protected String getClassValue(TextSegment textSegment) {
        return textSegment.getWekaRebutOrUndercut();
    }

    @Override
    protected MicroText makeDecisionsFor(List<Instance> instances, MicroText myMicroText) {
        List<double[]> distributions = new ArrayList<>();
        for(int i = 0; i < instances.size(); i++) {
            if (myMicroText.getTextSegment(i).isAttack()) {
                double[] distribution = getDistributionFor(instances.get(i));
                distributions.add(distribution);
                handleDecisionDistribution(distribution, myMicroText.getTextSegment(i));
            }
        }
        return myMicroText;
    }

    @Override
    protected void handleDecisionDistribution(double[] distribution, TextSegment textSegment) {
        if (distribution[0] >= distribution[1])
            textSegment.setRelation(new Rebut(textSegment));
        else
            textSegment.setRelation(new Undercut(textSegment));
    }
}
