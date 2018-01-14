package Weka;

import Main.MicroText;
import Main.TextSegment;
import weka.core.Attribute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class TextSegmentClassifier extends Classifier{

    protected abstract Attribute getClassAttribute();
    protected abstract String getClassValue(TextSegment textSegment);

    protected List<TextSegment> createTextSegmentList(List<MicroText> microTexts) {
        //create list with all textsegments
        List<TextSegment> textSegments = new ArrayList<>();
        for(MicroText microText : microTexts) {
            textSegments.addAll(microText.getTextSegments());
        }
        return textSegments;
    }

    protected ArrayList<TextSegment> splitTextSegmentList(List<TextSegment> textSegments, int takeEveryXCorpus, Boolean isTestData) {
        ArrayList<TextSegment> partData = new ArrayList<>();

        for (int index = 0; index < textSegments.size(); index++) {
            if (isTestData) {
                if (index % takeEveryXCorpus == 0)
                    partData.add(textSegments.get(index));
            }
            else {
                if (index % takeEveryXCorpus != 0)
                    partData.add(textSegments.get(index));
            }
        }
        return partData;
    }

    protected HashMap getAllLemmaUnigramsFromTextSegmentAsAttributes(List<TextSegment> textSegments) {
        HashMap attributes = new HashMap<String, Attribute>();
        for (TextSegment textSegment : textSegments) {
            addStringsAsAttributes(attributes, textSegment.getLemmasFromPrecedingSegment());
            addStringsAsAttributes(attributes, textSegment.getLemmaUnigrams());
            addStringsAsAttributes(attributes, textSegment.getLemmasFromSubsequentSegment());
        }
        return attributes;
    }

    protected HashMap getAllLemmaBigramsFromTextSegmentAsAttributes(List<TextSegment> textSegments) {
        HashMap attributes = new HashMap<String, Attribute>();
        for (TextSegment textSegment : textSegments) {
            addStringsAsAttributes(attributes, textSegment.getLemmaBigrams());
        }
        return attributes;
    }

}
