package Main;

import Main.Model.type.Addition;
import Main.Model.type.Rebut;
import Main.Model.type.UndefinedSupport;
import Main.Model.type.Undercut;
import Main.Model.typegen.NullRelation;
import org.w3c.dom.Node;

public class RelationFactory {
    public RelationFactory() {

    }

    public Relation createRelation(Node nNodeEdge, MicroText microText) {
        //extract source, trg and type node
        String relationId = nNodeEdge.getAttributes().getNamedItem("id").getNodeValue();
        String src = nNodeEdge.getAttributes().getNamedItem("src").getNodeValue();
        String trg = nNodeEdge.getAttributes().getNamedItem("trg").getNodeValue();
        String type = nNodeEdge.getAttributes().getNamedItem("type").getNodeValue();

        if(src.startsWith("a")) {
            if (type.equals("sup"))
                return new UndefinedSupport(relationId, src, trg);
            if (type.equals("reb"))
                return new Rebut(relationId, src, trg);
            if (type.equals("und"))
                return new Undercut(relationId, src, trg);
            if (type.equals("add"))
                return new Addition(relationId, src, trg);

        }
        return new NullRelation(relationId);
    }
}
