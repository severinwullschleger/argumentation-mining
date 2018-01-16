package Main;

public abstract class Relation implements ITarget{

    private String relationId = "";
    private String sourceId = "";
    protected TextSegment sourceSegment;
    protected String targetId = "";
    protected ITarget target;

    public Relation(){

    }

    public Relation(String relationId) {
        this.relationId = relationId;
    }

    public Relation(String relationId, String src, String trg) {
        this.relationId = relationId;
        this.sourceId = src;
        this.targetId = trg;
    }

    public Relation(TextSegment textSegment) {
        relationId = "c" + textSegment.getSegmentId().substring(1);
        sourceId = textSegment.getSegmentId();
        sourceSegment = textSegment;
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

    public abstract boolean isValidRelation();

    public abstract String getWekaAttackOrSupport();
    public abstract String getWekaRebutOrUndercut();

    public abstract boolean isAttack();
}
