package Main.Model.type;

import Main.Model.typegen.Attack;
import Main.TextSegment;

/**
 * Created by LuckyP on 16.12.17.
 */
public class Undercut extends Attack {

    public Undercut(String relationId, String src, String trg) {
        super(relationId, src, trg);
    }

    public Undercut(TextSegment textSegment) {
        super(textSegment);
    }

    @Override
    public String getWekaRebutOrUndercut() {
        return "und";
    }
}
