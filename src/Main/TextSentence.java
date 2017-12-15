package Main;

import edu.stanford.nlp.simple.Sentence;

import java.io.File;
import java.util.List;

/**
 * Created by LuckyP on 02.12.17.
 */
public class TextSentence {

    private String fileId;
    private String sentenceId;
    private String argumentType;            // "pro" or "opp"
    private Language language;
    private File correspondentFile;
    private Sentence sentence;

    public TextSentence() {

    }

    public TextSentence(String fileId, String sentenceId, Language language, File correspondentFile, Sentence sentence) {
        this.fileId = fileId;
        this.sentenceId = sentenceId;
        this.language = language;
        this.correspondentFile = correspondentFile;
        this.sentence = sentence;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public void setSentenceId(String sentenceId) {
        this.sentenceId = sentenceId;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public void setCorrespondentFile(File correspondentFile) {
        this.correspondentFile = correspondentFile;
    }

    public void setSentence(Sentence sentence) {
        this.sentence = sentence;
    }

    public void setArgumentType(String argumentType) {
        this.argumentType = argumentType;
    }

    @Override
    public String toString() {
        return "\nSentence { \n" +
                "\tsentenceId = '" + sentenceId + "'\n" +
                "\tfileId = '" + fileId + "'\n" +
                "\tLanguage = " + language + "\n" +
                "\tcorrespondentFile = " + correspondentFile + "\n" +
                "\tsentence = '" + sentence + "'\n" +
                "\targumentType = '" + argumentType + "'\n" +
                "}";
    }
}