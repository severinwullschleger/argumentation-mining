package Main;

public interface INullTarget {

    TextSegment getSourceSegment();

    ITarget getTarget();

    void setTarget(ITarget target);

    String getRelationId();


    String getSourceId();

    String getTargetId();
}
