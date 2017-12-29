package Weka;

import Main.MicroText;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

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
        List<Instance> set = new ArrayList<Instance>();
        for (MicroText microText : microTexts) {
            Instance corpusInstance = createDefaultInstance(vector);
            set.add(corpusInstance);
        }
        return set;
    }

    private Instance createDefaultInstance(ArrayList<Attribute> vector) {
        // Create the instance
        Instance corpusInstance = new DenseInstance(vector.size());
        // add default values to attributes
        for (int i = 1; i < corpusInstance.numAttributes(); i++) {
            corpusInstance.setValue(vector.get(i), 0.0);
        }
        return corpusInstance;
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
