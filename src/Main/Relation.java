package Main;

public abstract class Relation implements ITarget{

    private String relationId = "";
    private String sourceId = "";
    protected TextSegment sourceSegment;
    private String targetId = "";
    protected ITarget target;

    public Relation(){

    }

    public Relation(String relationId, String src, String trg) {
        this.relationId = relationId;
        this.sourceId = src;
        this.targetId = trg;
    }

    public TextSegment getSourceSegment() {
        return sourceSegment;
    }

    public void setSourceSegment(TextSegment sourceSegment) {
        this.sourceSegment = sourceSegment;
    }

    public ITarget getTarget() {
        return target;
    }

    public void setTarget(ITarget target) {
        this.target = target;
    }

    public String getRelationId() {
        return relationId;
    }


    public String getSourceId() {
        return sourceId;
    }

    public String getTargetId() {
        return targetId;
    }
}
