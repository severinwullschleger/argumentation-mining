package Main;

public abstract class Relation implements ITarget{

    private Main.TextSegment sourceSegment;
    private ITarget targetSegment;

    public Relation(Main.TextSegment sourceSegment, ITarget targetSegment) {
        this.sourceSegment = sourceSegment;
        this.targetSegment = targetSegment;
    }
}
