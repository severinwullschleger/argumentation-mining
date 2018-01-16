package Main.Model.type;

import Main.Model.role.NullTextSegment;
import Main.Model.typegen.Support;
import Main.TextSegment;

public class UndefinedSupport extends Support {

    public UndefinedSupport(String relationId, String src, String trg) {
        super(relationId, src, trg);
    }

    public UndefinedSupport(TextSegment textSegment) {
        super(textSegment);
        targetId = "";
        target = new NullTextSegment();
    }

    @Override
    public String getWekaRebutOrUndercut() {
        return "undefinedSupport";
    }
}
