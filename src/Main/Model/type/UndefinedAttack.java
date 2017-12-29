package Main.Model.type;

import Main.Model.typegen.Attack;
import Main.TextSegment;

public class UndefinedAttack extends Attack {
    public UndefinedAttack(TextSegment sourceSegment, TextSegment targetSegment) {
        super(sourceSegment, targetSegment);
    }
}
