package Main.Model.typegen;


import Main.Relation;
import Main.TextSegment;

/**
 * Created by LuckyP on 16.12.17.
 */
public abstract class Support extends Relation {
    public Support(TextSegment sourceSegment, TextSegment targetSegment) {
        super(sourceSegment, targetSegment);
    }
}
