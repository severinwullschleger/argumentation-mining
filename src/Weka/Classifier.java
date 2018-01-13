package Weka;

import Main.MicroText;
//import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;

import java.util.*;
import java.util.List;

public abstract class Classifier {
    protected ArrayList<MicroText> splitCorpuses(List<MicroText> microTexts, int takeEveryXCorpus, Boolean isTestData) {
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


    protected HashMap getAllLemmaUnigramsAsAttributes(List<MicroText> microTexts) {
        HashMap attributes = new HashMap<String, Attribute>();
        for (MicroText microText : microTexts) {
            HashMap lemmaUnigramsPerSentence = microText.getAllLemmaUnigramsPerTextSentence();
            addStringsFromCorpusAsAttributes(attributes, lemmaUnigramsPerSentence);
        }
        return attributes;
    }

    protected HashMap getAllLemmaBigramsAsAttributes(List<MicroText> microTexts) {
        HashMap attributes = new HashMap<String, Attribute>();
        for (MicroText microText : microTexts) {
            HashMap lemmaBigramsPerSentence = microText.getAllLemmaBigramsPerTextSentence();
            addStringsFromCorpusAsAttributes(attributes, lemmaBigramsPerSentence);
        }
        return attributes;
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

    private void addStringsFromCorpusAsAttributes(HashMap attributes, HashMap lemmaPerSentence) {
        Set<String> sentenceIds = lemmaPerSentence.keySet();
        for (String sentenceId : sentenceIds) {
            List<String> strings = (List<String>) lemmaPerSentence.get(sentenceId);
            addStringsFromSentenceAsAttributes(attributes, strings);
        }
    }

    private void addStringsFromSentenceAsAttributes(HashMap attributes, List<String> strings) {
        for (String s : strings)
            if (!attributes.containsKey(s))
                attributes.put(s, new Attribute(s));
    }


    protected List<Instance> createDefaultInstances(ArrayList<MicroText> microTexts, ArrayList<Attribute> vector) {
        List<Instance> set = new ArrayList<>();
        for (MicroText microText : microTexts) {
            Instance corpusInstance = createDefaultInstance(vector);
            set.add(corpusInstance);
        }
        return set;
    }

    protected List<Instance> createDefaultInstances(int listSize, ArrayList<Attribute> vector) {
        List<Instance> set = new ArrayList<>();
        for (int i=0; i<listSize; i++) {
            Instance corpusInstance = createDefaultInstance(vector);
            set.add(corpusInstance);
        }
        return set;
    }

    private Instance createDefaultInstance(ArrayList<Attribute> vector) {
        // Create the instance
        Instance instance = new DenseInstance(vector.size());
        // add default values to attributes
        for (int i = 1; i < instance.numAttributes(); i++) {
            instance.setValue(vector.get(i), 0.0);
        }
        return instance;
    }

    protected Instance setStringValue(Instance corpusInstance, String value, Attribute attribute) {
        // change 0 to 1
        corpusInstance.setValue(attribute, value);
        return corpusInstance;
    }

    protected Instance setStringValuesInCorpusInstance(Instance corpusInstance, List<String> corpusValues, HashMap attributes) {
        // change 0 to 1
        for (String s : corpusValues) {
            corpusInstance.setValue((Attribute)attributes.get(s), 1.0);
        }
        return corpusInstance;
    }


}
