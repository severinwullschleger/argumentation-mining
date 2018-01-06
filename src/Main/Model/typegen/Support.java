package Main.Model.typegen;


import Main.Relation;

/**
 * Created by LuckyP on 16.12.17.
 */
public abstract class Support extends Relation {
    public Support(String relationId, String src, String trg) {
        super(relationId, src, trg);
    }
}
