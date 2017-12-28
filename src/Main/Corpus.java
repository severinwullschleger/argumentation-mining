package Main;

import Main.Enums.Language;
import Main.Enums.Stance;
import Main.SegmentLabels.role.Opponent;
import Main.SegmentLabels.role.Proponent;

import java.io.*;
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

    public boolean isStanceTagged() {
        if (stance.equals(Stance.CON) || stance.equals(Stance.PRO))
            return true;
        else
            return false;
    }

    public List<String> getLemmaUnigrams() {
        ArrayList unigrams = new ArrayList<>();
        for (TextSentence textSentence : textSentences)
            unigrams.addAll(textSentence.getLemmaUnigrams());
        return unigrams;
    }

    public List<String> getLemmaBigrams() {
        ArrayList bigrams = new ArrayList<>();
        for (TextSentence textSentence : textSentences)
            bigrams.addAll(textSentence.getLemmaBigrams());
        return bigrams;
    }

    public List<String> getLemmaUnigramsFromSentence(int i) {
        ArrayList lemmas = new ArrayList<>();
        if (i >= 0 && i < textSentences.size())
            lemmas.addAll(textSentences.get(i).getLemmaUnigrams());
        return lemmas;
    }

    public List<String> getLemmaBigramsFromSentence(int i) {
        ArrayList lemmas = new ArrayList<>();
        if (i >= 0 && i < textSentences.size())
            lemmas.addAll(textSentences.get(i).getLemmaBigrams());
        return lemmas;
    }

    public HashMap<String, List<String>> getAllLemmaUnigramsPerTextSentence() {
        HashMap unigramsPerSentence = new HashMap<String, List<String>>();
        for (TextSentence textSentence : textSentences)
            unigramsPerSentence.put(textSentence.getSentenceId(), textSentence.getLemmaUnigrams());
        return unigramsPerSentence;
    }

    public HashMap<String, List<String>> getAllLemmaBigramsPerTextSentence() {
        HashMap bigramsPerSentence = new HashMap<String, List<String>>();
        for (TextSentence textSentence : textSentences)
            bigramsPerSentence.put(textSentence.getSentenceId(), textSentence.getLemmaBigrams());
        return bigramsPerSentence;
    }


    public void printOpponentAndProponents() {
        System.out.println(getFileId() + "  has the following proponents: ");
        for (TextSentence proponent : getProponents()) {
            System.out.print(proponent.getSentenceId() + " , ");
        }
        System.out.println("\n");

        System.out.println(getFileId() + "  has the following opponents: ");
        for (TextSentence opponent : getOpponents()) {
            System.out.print(opponent.getSentenceId() + " , ");
        }
        System.out.println("\n");
    }


}
