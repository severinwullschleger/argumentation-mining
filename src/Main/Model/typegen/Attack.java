package Main.Model.typegen;


import Main.ITarget;
import Main.Relation;
import Main.TextSentence;

/**
 * Created by LuckyP on 16.12.17.
 */
public abstract class Attack extends Relation{
    public Attack(TextSentence sourceSegment, ITarget target) {
        super(sourceSegment, target);
    }
}
