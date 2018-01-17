package Main.Model.type;

import Main.ITarget;
import Main.Model.role.NullTextSegment;
import Main.Model.typegen.Attack;
import Main.TextSegment;

public class UndefinedAttack extends Attack {

    public UndefinedAttack(String relationId, String src, String trg) {
        super(relationId, src, trg);
    }

    public UndefinedAttack(TextSegment textSegment) {
        super(textSegment);
        targetId = "";
        target = new NullTextSegment();
    }

    @Override
    public String getWekaRebutOrUndercut() {
        return "undefinedAttack";
    }

    @Override
    public void setTargetId(int id) {
        targetId = "a"+id;
    }

    @Override
    public boolean isUndercut() {
        return false;
    }
}
