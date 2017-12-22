package Main;

import Main.Enums.Language;
import Main.Enums.Stance;
import Main.SegmentLabels.role.Opponent;
import Main.SegmentLabels.role.Proponent;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
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


    public Corpus() {
        this.textSentences = new ArrayList<>();

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
        StringBuilder rtn = new StringBuilder("\n---------" + fileId + "---------"
                + "\ntopicId = " + topicId
                + "\nstance = " + stance
                + "\nlanguage = " + language
                + "\nfile = " + correspondentFile);

        for (TextSentence textSentence : textSentences)
            rtn.append(textSentence.toString());

        return rtn.toString();
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public void setStance(Stance stance) {
        this.stance = stance;
    }

    public List<TextSentence> getTextSentences() {
        return textSentences;
    }

    public void setTextSentences(List<TextSentence> textSentences) {
        this.textSentences = textSentences;
    }

    public void setCorrespondentFile(File correspondentFile) {
        this.correspondentFile = correspondentFile;
    }

    public List<TextSentence> getOpponents() {
        List<TextSentence> opponents = new ArrayList<>();
        if (this.textSentences != null) {
            for (TextSentence textSentence : this.textSentences) {
                if (textSentence instanceof Opponent) {
                    opponents.add(textSentence);
                }
            }
            return opponents;
        }
        return null;
    }

    public List<TextSentence> getProponents() {
        List<TextSentence> proponents = new ArrayList<>();
        if (this.textSentences != null) {
            for (TextSentence textSentence : this.textSentences) {
                if (textSentence instanceof Proponent) {
                    proponents.add(textSentence);
                }
            }
            return proponents;
        }
        return null;
    }


    public Stance getStance() {
        return stance;
    }

    public void writeToFile(String path) {

        try {
            String fileName = fileId + ".txt";
            File file = new File (path+fileName);

            PrintWriter out = new PrintWriter(file);
            for (TextSentence sent : textSentences )
                out.println(sent.getSentence().text());

            out.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, List<String>> getAllLemmasPerTextSentence() {
        HashMap lemmasPerSentence = new HashMap<String, List<String>>();
        for (TextSentence textSentence : textSentences)
            lemmasPerSentence.put(textSentence.getSentenceId(), textSentence.getLemmas());
        return lemmasPerSentence;
    }

    public boolean isStanceTagged() {
        if (stance.equals(Stance.CON) || stance.equals(Stance.PRO))
            return true;
        else
            return false;
    }

    public ArrayList<String> getAllLemmas() {
        ArrayList lemmas = new ArrayList<>();
        for (TextSentence textSentence : textSentences)
            lemmas.addAll(textSentence.getLemmas());
        return lemmas;
    }
}
