package Main.Model.typegen;


import Main.ITarget;
import Main.Relation;
import Main.TextSegment;

/**
 * Created by LuckyP on 16.12.17.
 */
public abstract class Attack extends Relation{
    public Attack(TextSegment sourceSegment, ITarget target) {
        super(sourceSegment, target);
    }
}
