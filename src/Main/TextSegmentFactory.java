package Main;

import ConfigurationManager.ConfigurationManager;
import Main.Enums.ArgumentType;
import Main.Enums.EnumsManager;
import Main.Model.role.Opponent;
import Main.Model.role.Proponent;
import Main.Model.role.UndefinedSentence;
import edu.stanford.nlp.simple.Sentence;
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

    public TextSegment createUndefinedTextSegment(String str, int id) {
        UndefinedSentence undefinedSentence = new UndefinedSentence();
        undefinedSentence.setTextSegmentId(String.valueOf(id));
        undefinedSentence.setSentence(new Sentence(str));
        undefinedSentence.setLanguage(ConfigurationManager.SENTENCES_LANGUAGE);
        return undefinedSentence;
    }
}
