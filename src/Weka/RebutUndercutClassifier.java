package Weka;

import Main.MicroText;
import Main.TextSegment;
import weka.core.Attribute;

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
    protected void handleDecisionDistribution(double[] distribution, TextSegment textSegment) {

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
}
