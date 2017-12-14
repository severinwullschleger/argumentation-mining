package Main;

import java.util.ArrayList;
import java.util.List;

/**
 * Corpus represents a textfile
 * Each corpus (e.g. micro_b001.txt) has a list of <sentence>sentences</sentence>
 */
public class Corpus {

    private String fileId;
    private List<Sentence> sentences;
    private Language language;
    private List<String> preprocessedCorpus;

    public Corpus(String fileId, List<Sentence> sentences, Language language, List<String> preprocessedCorpus) {
        this.fileId = fileId;
        this.sentences = sentences;
        this.language = language;
        this.preprocessedCorpus = preprocessedCorpus;
    }

    public Corpus(String fileId, Language language) {
            this.fileId = fileId;
            this.language = language;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public List<Sentence> getSentences() {
        return sentences;
    }

    public void setSentences(List<Sentence> sentences) {
        this.sentences = sentences;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public List<String> getPreprocessedCorpus() {
        return preprocessedCorpus;
    }

    public void setPreprocessedCorpus(List<String> preprocessedCorpus) {
        this.preprocessedCorpus = preprocessedCorpus;
    }

    public void addSentences(List<Sentence> sentences) {
        this.sentences = sentences;
    }

    public String toString() {
        String rtn = "\n---------"+fileId +"---------\n";
        for (Sentence sentence : sentences)
            rtn += sentence.toString();

        return rtn;
    }
}
