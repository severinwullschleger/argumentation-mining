package Main.Model.type;

import Main.Model.typegen.Attack;
import Main.TextSegment;

/**
 * Created by LuckyP on 16.12.17.
 */
public class Rebut extends Attack {

    public Rebut(String relationId, String src, String trg) {
        super(relationId, src, trg);
    }

    public Rebut(TextSegment textSegment) {
        super(textSegment);
    }

    @Override
    public String getWekaRebutOrUndercut() {
        return "reb";
    }
}
