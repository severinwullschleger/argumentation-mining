package Main;

import java.io.File;
import java.util.List;

/**
 * Created by LuckyP on 02.12.17.
 */
public class TextSentence {

    private String fileId;
    private String sentenceId;
    private Language language;;
    private File correspondentFile;
    private String sentence;
    private List<String> preprocessedWords;

    public TextSentence(String fileId, String sentenceId, Language language, File correspondentFile, String sentence, List<String> preprocessedWords) {
        this.fileId = fileId;
        this.sentenceId = sentenceId;
        this.language = language;
        this.correspondentFile = correspondentFile;
        this.sentence = sentence;
        this.preprocessedWords = preprocessedWords;
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

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public List<String> getPreprocessedWords() {
        return preprocessedWords;
    }

    public void setPreprocessedWords(List<String> preprocessedWords) {
        this.preprocessedWords = preprocessedWords;
    }

    @Override
    public String toString() {
        return "\nSentence { \n" +
                "\tsentenceId='" + sentenceId + "'\n" +
                "\tfileId='" + fileId + "'\n" +
                "\tLanguage=" + language.toString() + "\n" +
                "\tcorrespondentFile=" + correspondentFile + "\n" +
                "\tsentence='" + sentence + "'\n" +
                "\tpreprocessedWords=" + preprocessedWords + "\n" +
                "}";
    }
}
