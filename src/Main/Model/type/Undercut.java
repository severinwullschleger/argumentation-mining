package Main.Model.type;

import Main.Relation;
import Main.Model.typegen.Attack;
import Main.TextSentence;

/**
 * Created by LuckyP on 16.12.17.
 */
public class Undercut extends Attack {
    public Undercut(TextSentence sourceSegment, Relation target) {
        super(sourceSegment, target);
    }
}
