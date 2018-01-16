package Weka;

import Main.MicroText;
import Main.TextSegment;
//import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;

import java.util.*;
import java.util.List;

public abstract class Classifier {

    protected void addStringListAsAttributes(HashMap attributes, List<String> strings) {
        for (String s : strings)
            if (!attributes.containsKey(s))
                attributes.put(s, new Attribute(s));
    }

    protected List<Instance> createDefaultInstancesList(int listSize, ArrayList<Attribute> vector) {
        List<Instance> set = new ArrayList<>();
        for (int i=0; i<listSize; i++) {
            Instance corpusInstance = createDefaultInstance(vector);
            set.add(corpusInstance);
        }
        return set;
    }

    protected Instance createDefaultInstance(ArrayList<Attribute> vector) {
        // Create the instance
        Instance instance = new DenseInstance(vector.size());
        // add default values to attributes
        for (int i = 1; i < instance.numAttributes(); i++) {
            instance.setValue(vector.get(i), 0.0);
        }
        return instance;
    }

    protected Instance setStringValue(Instance instance, String value, Attribute attribute) {
        instance.setValue(attribute, value);
        return instance;
    }

    protected Instance setStringValuesToOne(Instance instance, List<String> stringValues, HashMap attributes) {
        // change 0 to 1
        for (String s : stringValues) {
            instance.setValue((Attribute)attributes.get(s), 1.0);
        }
        return instance;
    }

    /**
     * Sets the attribute of the given Instance to the value
     * @param instance
     * @param attributes
     * @param value
     * @param attributeName
     * @return
     */
    protected Instance setNumericValue(Instance instance, int value, HashMap attributes, String attributeName) {

        String valueText = String.valueOf(value);
        instance.setValue((Attribute) attributes.get(attributeName), valueText);
        return instance;
    }
}
