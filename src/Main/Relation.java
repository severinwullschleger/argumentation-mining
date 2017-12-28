package Main;

public abstract class Relation implements ITarget{

    private TextSentence sourceSegment;
    private ITarget targetSegment;

    public Relation(TextSentence sourceSegment, ITarget targetSegment) {
        this.sourceSegment = sourceSegment;
        this.targetSegment = targetSegment;
    }
}
