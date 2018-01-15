package Main.Model.typegen;

import Main.ITarget;
import Main.Model.role.NullTextSegment;
import Main.INullTarget;
import Main.Relation;
import Main.TextSegment;

public class NullRelation extends Relation implements INullTarget{

    public NullRelation() {

    }

    public TextSegment getSourceSegment() {
        return new NullTextSegment();
    }

    public void setSourceSegment(TextSegment sourceSegment) {
    }

    public ITarget getTarget() {
        return this;
    }

    public void setTarget(ITarget target) {
    }

    public String getRelationId() {
        return "";
    }


    public String getSourceId() {
        return "";
    }

    public String getTargetId() {
        return "";
    }

    public final String getWekaAttackOrSupport() {
        return "";
    }

    @Override
    public String getWekaRebutOrUndercut() {
        return "";
    }

    @Override
    public boolean isAttack() {
        return false;
    }

    @Override
    public boolean isValidRelation() {
        return false;
    }
}
