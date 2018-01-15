package Main.Model.type;

import Main.Model.typegen.Attack;

/**
 * Created by LuckyP on 16.12.17.
 */
public class Undercut extends Attack {

    public Undercut(String relationId, String src, String trg) {
        super(relationId, src, trg);
    }

    @Override
    public String getWekaRebutOrUndercut() {
        return "und";
    }
}
