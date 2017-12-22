package Main;

import Main.Enums.ArgumentType;
import Main.Enums.Language;

import Main.SegmentLabels.role.Opponent;
import Main.SegmentLabels.role.Proponent;
import edu.stanford.nlp.simple.Sentence;

import java.io.File;
import java.util.Collection;
import java.util.List;

/**
 * Created by LuckyP on 02.12.17.
 */
public class TextSentence {

    private String fileId;
    private String sentenceId;
    private ArgumentType argumentType;            // "pro" or "opp"
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

    public void setArgumentType(ArgumentType argumentType) {
        this.argumentType = argumentType;
    }

    public String getFileId() {
        return fileId;
    }

    public String getSentenceId() {
        return sentenceId;
    }

    public ArgumentType getArgumentType() {
        return argumentType;
    }

    public Language getLanguage() {
        return language;
    }

    public File getCorrespondentFile() {
        return correspondentFile;
    }

    public Sentence getSentence() {
        return sentence;
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

    public List<String> getLemmas() {
        return sentence.lemmas();
    }
}
