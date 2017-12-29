package Main.Model.type;

import Main.Relation;
import Main.Model.typegen.Attack;
import Main.TextSegment;

/**
 * Created by LuckyP on 16.12.17.
 */
public class Undercut extends Attack {
    public Undercut(TextSegment sourceSegment, Relation target) {
        super(sourceSegment, target);
    }
}
