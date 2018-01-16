package Weka;

import Main.MicroText;
import weka.core.Attribute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public abstract class MicroTextClassifier extends Classifier {

    protected ArrayList<MicroText> splitMicroTextList(List<MicroText> microTexts, int takeEveryXCorpus, Boolean isTestData) {
        ArrayList<MicroText> partData = new ArrayList<>();
        for (int index = 0; index < microTexts.size(); index++) {
            if (isTestData) {
                if (index % takeEveryXCorpus == 0)
                    partData.add(microTexts.get(index));
            }
            else {
                if (index % takeEveryXCorpus != 0)
                    partData.add(microTexts.get(index));
            }
        }
        return partData;
    }

    protected HashMap getAllLemmaUnigramsFromMicroTextAsAttributes(List<MicroText> microTexts) {
        HashMap attributes = new HashMap<String, Attribute>();
        for (MicroText microText : microTexts) {
            HashMap lemmaUnigramsPerSentence = microText.getAllLemmaUnigramsPerTextSentence();
            addStringsFromMicroTextAsAttributes(attributes, lemmaUnigramsPerSentence);
        }
        return attributes;
    }

    protected HashMap getAllLemmaBigramsFromMicroTextAsAttributes(List<MicroText> microTexts) {
        HashMap attributes = new HashMap<String, Attribute>();
        for (MicroText microText : microTexts) {
            HashMap lemmaBigramsPerSentence = microText.getAllLemmaBigramsPerTextSentence();
            addStringsFromMicroTextAsAttributes(attributes, lemmaBigramsPerSentence);
        }
        return attributes;
    }

    protected void addStringsFromMicroTextAsAttributes(HashMap attributes, HashMap lemmaPerSentence) {
        Set<String> sentenceIds = lemmaPerSentence.keySet();
        for (String sentenceId : sentenceIds) {
            List<String> strings = (List<String>) lemmaPerSentence.get(sentenceId);
            addStringListAsAttributes(attributes, strings);
        }
    }

    protected void getAllDiscourseMarkersAsAttributes(List<MicroText> microTexts) {
        for (MicroText microText : microTexts) {
            System.out.println(microText.discourseMarkers());
        }
    }

    protected List getAllSentimentValuesAsAttributes(List<MicroText> microTexts) {
        HashMap attributesAsStrings = new HashMap<MicroText, List<String>>();
        int longestSentimentScoreListSize = 0;
        for (MicroText microText : microTexts) {
            List<Integer> microTextSentimentScores = microText.getSentimentValues();
            if (microTextSentimentScores.size() > longestSentimentScoreListSize) {
                longestSentimentScoreListSize = microTextSentimentScores.size();
            }
            attributesAsStrings.put(microText, microTextSentimentScores);
        }
        List<Attribute> attributes = new ArrayList<Attribute>();
        for (MicroText microText : microTexts) {
            List<Integer> sentValuesFilled = microText.getSentimentValues();
            int timesToAdd = longestSentimentScoreListSize - sentValuesFilled.size();
            if (timesToAdd > 0) {
                for (int i = 0; i < timesToAdd; i++) {
                    sentValuesFilled.add(-1);
                }
            }
            List<Attribute> sentimentAttributes = new ArrayList<Attribute>();
            for (int i = 0; i < sentValuesFilled.size(); i++) {
                int sentimentValue = sentValuesFilled.get(i);
                Attribute attribute = new Attribute("sentLabel_".concat(microText.getFileId()).concat("_").concat(String.valueOf(i + 1)));
                attribute.setWeight((double)sentimentValue);
                sentimentAttributes.add(attribute);
                System.out.println(attribute);
            }
            for (Attribute attribute : sentimentAttributes) {
                attributes.add(attribute);
            }
        }
        return attributes;
    }

}
