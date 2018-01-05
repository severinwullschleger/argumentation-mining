package Main;

import Main.Enums.Language;

import edu.stanford.nlp.simple.Sentence;
import edu.stanford.nlp.simple.SentimentClass;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LuckyP on 02.12.17.
 */
public abstract class TextSegment implements ISource, ITarget{

    private MicroText microText;
    private String fileId;
    private String sentenceId;
    private int sentenceIndex;
    private Language language;
    private File correspondentFile;
    private Sentence sentence;
    private Boolean isClaim;
    private int SentenceScore;

    private Relation relation;

    public TextSegment() {
        //will be set to false by XMLParser, if it is not a Claim-text-element
        isClaim = true;
    }

    public TextSegment(String fileId, String sentenceId, Language language, File correspondentFile, Sentence sentence) {
        this.fileId = fileId;
        this.sentenceId = sentenceId;
        this.language = language;
        this.correspondentFile = correspondentFile;
        this.sentence = sentence;
    }

    public abstract void writeToProOppFolder(String path);

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

    public void setMicroText(MicroText microText) {
        this.microText = microText;
    }

    public void setSentenceIndex(int sentenceIndex) {
        this.sentenceIndex = sentenceIndex;
    }

    public String getFileId() {
        return fileId;
    }

    public String getSentenceId() {
        return sentenceId;
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

    public Boolean getClaim() {
        return isClaim;
    }

    public void setClaim(Boolean claim) {
        isClaim = claim;
    }

    @Override
    public String toString() {
        return "\nSentence { \n" +
                "\tsentenceId = '" + sentenceId + "'\n" +
                "\tfileId = '" + fileId + "'\n" +
                "\tLanguage = " + language + "\n" +
                "\tcorrespondentFile = " + correspondentFile + "\n" +
                "\tsentence = '" + sentence + "'\n" +
                "\tisClaim = '" + isClaim + "'\n" +
                "}";
    }

    public List<String> getLemmaUnigrams() {
        return sentence.lemmas();
    }

    public List<String> getLemmasFromPrecedingSentence() {
        return microText.getLemmaUnigramsFromSentence(sentenceIndex-1);
    }

    public List<String> getLemmasFromSubsequentSentence() {
        return microText.getLemmaUnigramsFromSentence(sentenceIndex+1);
    }

    public List<String> getLemmaBigrams() {
        List<String> bigrams = new ArrayList<>();
        List<String> unigrams = sentence.lemmas();
        for (int i = 0; i < unigrams.size()-1; i++)
            bigrams.add(unigrams.get(i) + " " + unigrams.get(i+1));
        return bigrams;
    }

    public int getSentimentScore() {
        String segmentSentiment = sentence.sentiment().toString();
        if (segmentSentiment.equals("VERY_NEGATIVE")) {
            return 0;
        }
        else if (segmentSentiment.equals("NEGATIVE")) {
            return 1;
        }
        else if (segmentSentiment.equals("NEUTRAL")) {
            return 2;
        }
        else if (segmentSentiment.equals("POSITIVE")) {
            return 3;
        }
        else if (segmentSentiment.equals("VERY_POSITIVE")) {
            return 4;
        }
        else return -1;

    }


    public void writeToFile(String path) {

        try {
            String fileName = fileId + "_" + sentenceId + ".txt";
            File file = new File (path+fileName);

            PrintWriter out = new PrintWriter(file);
            out.println(getSentence().text());

            out.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


}
