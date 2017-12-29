package Main.Model.role;

import Main.Enums.Language;
import Main.TextSegment;
import edu.stanford.nlp.simple.Sentence;

import java.io.File;

/**
 * Created by LuckyP on 16.12.17.
 */
public class UndefinedSentence extends TextSegment {

    public UndefinedSentence() {
    }

    public UndefinedSentence(String fileId, String sentenceId, Language sentencesLanguage, File file, Sentence sentence) {
        super( fileId,  sentenceId,  sentencesLanguage,  file,  sentence);
    }
}
