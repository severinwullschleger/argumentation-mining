package Main.Model.role;

import Main.INullTarget;
import Main.ITarget;
import Main.TextSegment;

public class NullTextSegment extends TextSegment implements INullTarget{

    public NullTextSegment() {

    }

    public TextSegment getSourceSegment() {
        return this;
    }

    public void setSourceSegment(TextSegment sourceSegment) {
    }

    public ITarget getTarget() {
        return this;
    }

    public void setTarget(ITarget target) {
    }

    public String getRelationId() {
        return "";
    }

    public String getSourceId() {
        return "";
    }

    public String getTargetId() {
        return "";
    }

    @Override
    public void writeToProOppFolder(String path) {

    }

    @Override
    public String getType() {
        return "";
    }
}
