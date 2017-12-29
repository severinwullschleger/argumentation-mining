package Main;

public abstract class Relation implements ITarget{

    private TextSegment sourceSegment;
    private ITarget targetSegment;

    public Relation(TextSegment sourceSegment, ITarget targetSegment) {
        this.sourceSegment = sourceSegment;
        this.targetSegment = targetSegment;
    }
}
