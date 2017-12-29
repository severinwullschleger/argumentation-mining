package StandfordParserManager;


import edu.stanford.nlp.simple.Document;
import edu.stanford.nlp.simple.Sentence;

import java.util.List;

/**
 * Created by LucasPelloni on 14.12.17.
 */
public class NLPManager {

    public static List<Sentence> splitSentence(String text) {
        Document document = new Document(text);
        return document.sentences();
    }
}
