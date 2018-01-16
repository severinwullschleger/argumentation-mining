package StandfordParserManager;

import edu.stanford.nlp.trees.TypedDependency;

public class POSTypeDecider {
    private static POSTypeDecider instance = null;

    protected POSTypeDecider(){
    }

    public static POSTypeDecider getInstance() {
        if(instance == null) {
            instance = new POSTypeDecider();

        }
        return instance;
    }

    public POSType getPOSType(String sentence) {
        TypedDependency[] typedDependencies = StanfordNLP.getInstance().getRelevantTypedDependencies(sentence);

        int listSize = typedDependencies.length;

        switch (listSize) {
            case 0:
                return POSType.NOTHING;
            case 1:
                return getTypeForLengthOne(typedDependencies);
            case 2:
                return getTypeForLengthTwo(typedDependencies);
            case 3:
                return getTypeForLengthThree(typedDependencies);
            default:
                return POSType.AUX_DOBJ_NSUBJ_XCOMP;
        }
    }

    private POSType getTypeForLengthOne(TypedDependency[] typedDependencies) {
        String type = typedDependencies[0].toString();
        if (type.contains("aux")) {
            return POSType.AUX;
        } else if (type.contains("dobj")) {
            return POSType.DOBJ;
        } else if (type.contains("nsubj")) {
            return POSType.NSUBJ;
        } else {
            return POSType.XCOMP;
        }
    }

    private POSType getTypeForLengthTwo(TypedDependency[] typedDependencies) {
        String types = typedDependencies[0].toString();
        types += typedDependencies[1].toString();

        if (types.contains("aux") && types.contains("dobj")) {
            return POSType.AUX_DOBJ;
        } else if (types.contains("aux") && types.contains("nsubj")) {
            return POSType.AUX_NSUBJ;
        } else if (types.contains("aux") && types.contains("xcomp")) {
            return POSType.AUX_XCOMP;
        } else if (types.contains("dobj") && types.contains("nsubj")) {
            return POSType.DOBJ_NSUBJ;
        } else if (types.contains("dobj") && types.contains("xcomp")) {
            return POSType.DOBJ_XCOMP;
        } else {
            return POSType.NSUBJ_XCOMP;
        }
    }

    private POSType getTypeForLengthThree(TypedDependency[] typedDependencies) {
        String types = typedDependencies[0].toString();
        types += typedDependencies[1].toString();
        types += typedDependencies[2].toString();

        if (types.contains("aux") && types.contains("dobj") && types.contains("nsubj")) {
            return POSType.AUX_DOBJ_NSUBJ;
        } else if (types.contains("aux") && types.contains("dobj") && types.contains("xcomp")) {
            return POSType.AUX_DOBJ_XCOMP;
        } else if (types.contains("aux") && types.contains("nsubj") && types.contains("xcomp")) {
            return POSType.AUX_NSUBJ_XCOMP;
        } else {
            return POSType.DOBJ_NSUBJ_XCOMP;
        }
    }
}

