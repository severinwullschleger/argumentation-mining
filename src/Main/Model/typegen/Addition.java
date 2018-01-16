package Main.Model.typegen;

import Main.Relation;

public class Addition extends Relation {

    public Addition(String relationId, String src, String trg) {
        super(relationId, src, trg);
    }

    @Override
    public boolean isValidRelation() {
        return false;
    }

    @Override
    public String getWekaAttackOrSupport() {
        return target.getWekaAttackOrSupport();
    }

    @Override
    public String getWekaRebutOrUndercut() {
        return target.getWekaRebutOrUndercut();
    }

    @Override
    public boolean isAttack() {
        return false;    // alternatively:: return target.isAttack();
    }
}
