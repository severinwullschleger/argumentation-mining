package Main.Model.typegen;


import Main.Relation;
import Main.TextSentence;

/**
 * Created by LuckyP on 16.12.17.
 */
public abstract class Support extends Relation {
    public Support(TextSentence sourceSegment, TextSentence targetSegment) {
        super(sourceSegment, targetSegment);
    }
}
