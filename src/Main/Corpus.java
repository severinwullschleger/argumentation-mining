package Main;

import Main.Enums.Language;
import Main.Enums.Stance;
import Main.SegmentLabels.role.Opponent;
import Main.SegmentLabels.role.Proponent;

import java.io.File;
import java.util.List;

/**
 * Corpus represents a textfile
 * Each corpus (e.g. micro_b001.txt) has a list of <sentence>sentences</sentence>
 */
public class Corpus {

    private String fileId;
    private String topicId;         // e.g waste_separation
    private Stance stance;          // "pro" or "opp"
    private List<TextSentence> textSentences;
    private Language language;
    private File correspondentFile;
    private List<Opponent> opponents;
    private List<Proponent> proponents;

    public Corpus() {

    }

    public Corpus(String fileId, List<TextSentence> sentences, Language language, List<String> preprocessedCorpus) {
        this.fileId = fileId;
        this.textSentences = sentences;
        this.language = language;
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

    public void setSentences(List<TextSentence> sentences) {
        this.textSentences = sentences;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String toString() {
        String rtn = "\n---------" + fileId + "---------"
                + "\ntopicId = " + topicId
                + "\nstance = " + stance
                + "\nlanguage = " + language
                + "\nfile = " + correspondentFile;

        for (TextSentence textSentence : textSentences)
            rtn += textSentence.toString();

        return rtn;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public void setStance(Stance stance) {
        this.stance = stance;
    }

    public List<Opponent> getOpponents() {
        return opponents;
    }

    public void setOpponents(List<Opponent> opponents) {
        this.opponents = opponents;
    }

    public List<Proponent> getProponents() {
        return proponents;
    }

    public void setProponents(List<Proponent> proponents) {
        this.proponents = proponents;
    }

    public void setCorrespondentFile(File correspondentFile) {
        this.correspondentFile = correspondentFile;
    }
}
