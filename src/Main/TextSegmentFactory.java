package Main;

import Main.Enums.ArgumentType;
import Main.Enums.EnumsManager;
import Main.Model.role.Opponent;
import Main.Model.role.Proponent;
import Main.Model.role.UndefinedSentence;
import org.w3c.dom.Node;

public class TextSegmentFactory {

    public TextSegment createTextSegment(Node nNodeA) {
        String argumentTypeAttribute = nNodeA.getAttributes().getNamedItem("type").getTextContent();
        ArgumentType sentenceType = EnumsManager.convertToRoleEnum(argumentTypeAttribute);
        switch (sentenceType) {
            case OPP:
                return new Opponent();
            case PRO:
                return new Proponent();
            default:
                return new UndefinedSentence();
        }
    }
}
