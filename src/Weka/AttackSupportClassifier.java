package Weka;

import Main.MicroText;
import Main.Model.role.Opponent;
import Main.Model.role.Proponent;
import Main.Model.type.UndefinedAttack;
import Main.Model.type.UndefinedSupport;
import Main.Model.typegen.Attack;
import Main.TextSegment;
import weka.core.Attribute;
import weka.core.Instance;

import java.util.ArrayList;
import java.util.List;

public class AttackSupportClassifier extends TextSegmentClassifier {

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
        ArrayList<String> attSupValues = new ArrayList<>(2);
        attSupValues.add("att");
        attSupValues.add("sup");
        classAttribute = new Attribute("attSup", attSupValues);
        return classAttribute;
    }

    @Override
    protected String getClassValue(TextSegment textSegment) {
        return textSegment.getWekaAttackOrSupport();
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
        if (distribution[0] >= distribution[1])
            textSegment.setRelation(new UndefinedAttack(textSegment));
        else
            textSegment.setRelation(new UndefinedSupport(textSegment));
    }

}
