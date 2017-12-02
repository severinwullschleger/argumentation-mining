package Main;

import java.io.File;
import java.util.List;

/**
 * Created by LuckyP on 02.12.17.
 */
public class Sentence {

    private String id;
    private boolean isEnglish;
    private File correspondentFile;
    private String sentence;
    private List<String> preprocessedWords;

    public Sentence(String id, boolean isEnglish, File correspondentFile, String sentence, List<String> preprocessedWords) {
        this.id = id;
        this.isEnglish = isEnglish;
        this.correspondentFile = correspondentFile;
        this.sentence = sentence;
        this.preprocessedWords = preprocessedWords;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isEnglish() {
        return isEnglish;
    }

    public void setEnglish(boolean english) {
        isEnglish = english;
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
        return "Sentence{" +
                "id='" + id + '\'' +
                ", isEnglish=" + isEnglish +
                ", correspondentFile=" + correspondentFile +
                ", sentence='" + sentence + '\'' +
                ", preprocessedWords=" + preprocessedWords +
                '}';
    }
}
