package Main.Model.type;

import Main.Relation;

public class Addition extends Relation {

    public Addition(String relationId, String src, String trg) {
        super(relationId, src, trg);
    }

    @Override
    public String getWekaAttackOrSupport() {
        return "add";
    }

    @Override
    public boolean isValidRelation() {
        return false;
    }
}
