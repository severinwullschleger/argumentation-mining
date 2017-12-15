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

    public TextSentence(String fileId, String sentenceId, Language language, File correspondentFile, Sentence sentence) {
        this.fileId = fileId;
        this.sentenceId = sentenceId;
        this.language = language;
        this.correspondentFile = correspondentFile;
        this.sentence = sentence;
    }

    public TextSentence() {

    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getSentenceId() {
        return sentenceId;
    }

    public void setSentenceId(String sentenceId) {
        this.sentenceId = sentenceId;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public File getCorrespondentFile() {
        return correspondentFile;
    }

    public void setCorrespondentFile(File correspondentFile) {
        this.correspondentFile = correspondentFile;
    }

    public Sentence getSentence() {
        return sentence;
    }

    public void setSentence(Sentence sentence) {
        this.sentence = sentence;
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

    public void setArgumentType(String argumentType) {
        this.argumentType = argumentType;
    }
}
