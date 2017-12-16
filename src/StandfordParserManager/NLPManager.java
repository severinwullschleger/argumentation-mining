package StandfordParserManager;


import Main.TextSentence;
import edu.stanford.nlp.simple.Document;
import edu.stanford.nlp.simple.Sentence;
import edu.stanford.nlp.simple.Token;

import java.util.List;

/**
 * Created by LucasPelloni on 14.12.17.
 */
public class NLPManager {

    public static List<Sentence> splitSentence(String text) {
        Document document = new Document(text);
        return document.sentences();
    }

    public static List<String> lemmatizeSentence (Sentence sentence) {
        return sentence.lemmas();
    }
}
