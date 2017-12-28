package Main;

import Main.Enums.ArgumentType;
import Main.Enums.Language;

import edu.stanford.nlp.simple.Sentence;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LuckyP on 02.12.17.
 */
public abstract class TextSentence implements ISource, ITarget{

    private Corpus corpus;
    private String fileId;
    private String sentenceId;
    private int sentenceIndex;
    private Language language;
    private File correspondentFile;
    private Sentence sentence;

    private Relation relation;

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

    public void setCorpus(Corpus corpus) {
        this.corpus = corpus;
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

    @Override
    public String toString() {
        return "\nSentence { \n" +
                "\tsentenceId = '" + sentenceId + "'\n" +
                "\tfileId = '" + fileId + "'\n" +
                "\tLanguage = " + language + "\n" +
                "\tcorrespondentFile = " + correspondentFile + "\n" +
                "\tsentence = '" + sentence + "'\n" +
                "}";
    }

    public List<String> getLemmaUnigrams() {
        return sentence.lemmas();
    }

    public List<String> getLemmasFromPrecedingSentence() {
        return corpus.getLemmaUnigramsFromSentence(sentenceIndex-1);
    }

    public List<String> getLemmasFromSubsequentSentence() {
        return corpus.getLemmaUnigramsFromSentence(sentenceIndex+1);
    }

    public List<String> getLemmaBigrams() {
        List<String> bigrams = new ArrayList<>();
        List<String> unigrams = sentence.lemmas();
        for (int i = 0; i < unigrams.size()-1; i++)
            bigrams.add(unigrams.get(i) + " " + unigrams.get(i+1));
        return bigrams;
    }
}
