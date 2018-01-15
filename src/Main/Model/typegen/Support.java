package Main.Model.typegen;


import Main.Model.role.NullTextSegment;
import Main.Relation;

/**
 * Created by LuckyP on 16.12.17.
 */
public abstract class Support extends Relation {

    public Support() {
        sourceSegment = new NullTextSegment();
        target = new NullTextSegment();
    }

    public Support(String relationId, String src, String trg) {
        super(relationId, src, trg);
    }

    public final String getWekaAttackOrSupport() {
        return "sup";
    }

    @Override
    public boolean isValidRelation() {
        return true;
    }
}
