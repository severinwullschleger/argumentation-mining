package Main;

import Main.Enums.Language;

import Main.Model.role.Proponent;
import Main.Model.typegen.NullRelation;
import edu.stanford.nlp.simple.Sentence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by LuckyP on 02.12.17.
 */
public abstract class TextSegment implements ISource, ITarget {

    private MicroText microText;
    private String fileId;
    private String segmentId;
    private int segmentPositionIndex;
    private Language language;
    private File correspondentFile;
    private Sentence sentence;
    private Boolean isClaim;
    private int SentenceScore;
    private String edgeId;
    private String typeStr;

    private Relation relation;

    public TextSegment() {
        //will be set to false by XMLParser, if it is not a Claim-text-element
        isClaim = true;
        relation = new NullRelation();
    }

    public TextSegment(String fileId, String segmentId, Language language, File correspondentFile, Sentence sentence) {
        this.fileId = fileId;
        this.segmentId = segmentId;
        this.language = language;
        this.correspondentFile = correspondentFile;
        this.sentence = sentence;
    }

    public abstract void writeToProOppFolder(String path);

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public void setTextSegmentId(String sentenceId) {
        this.segmentId = sentenceId;
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

    public void setSegmentPositionIndex(int sentenceIndex) {
        this.segmentPositionIndex = sentenceIndex;
    }

    public String getFileId() {
        return fileId;
    }

    public String getSegmentId() {
        return segmentId;
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

    public Boolean isClaim() {
        return isClaim;
    }

    public void setClaim(Boolean claim) {
        isClaim = claim;
    }

    public String getEdgeId() {
        return edgeId;
    }

    public void setEdgeId(String edgeId) {
        this.edgeId = edgeId;
    }

    public String getTypeStr() {
        return typeStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }

    @Override
    public String toString() {
        return "\nSentence { \n" +
                "\tsegmentId = '" + segmentId + "'\n" +
                "\tfileId = '" + fileId + "'\n" +
                "\tLanguage = " + language + "\n" +
                "\tcorrespondentFile = " + correspondentFile + "\n" +
                "\tsentence = '" + sentence + "'\n" +
                "\tisClaim = '" + isClaim + "'\n" +
                "\tType = '" + getType() + "'\n" +
                "}";
    }

    public List<String> getLemmaUnigrams() {
        return sentence.lemmas();
    }

    public List<String> getLemmaUnigramsFromPrecedingSegment() {
        return microText.getLemmaUnigramsFromSentence(segmentPositionIndex - 1);
    }

    public List<String> getLemmaUnigramsFromSubsequentSegment() {
        return microText.getLemmaUnigramsFromSentence(segmentPositionIndex + 1);
    }

    public List<String> getLemmaBigrams() {
        List<String> bigrams = new ArrayList<>();
        List<String> unigrams = sentence.lemmas();
        for (int i = 0; i < unigrams.size() - 1; i++)
            bigrams.add(unigrams.get(i) + " " + unigrams.get(i + 1));
        return bigrams;
    }

    public abstract String getType();

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
            String fileName = fileId + "_" + segmentId + ".txt";
            File file = new File (path+fileName);

            PrintWriter out = new PrintWriter(file);
            out.println(getSentence().text());

            out.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean hasId(String segmentId) {
        return this.segmentId.equals(segmentId);
    }

    public boolean hasRelationId(String relationId) {
        return relation.getRelationId().equals(relationId);
    }

    public Relation getRelation() {
        return relation;
    }

    public void setRelation(Relation relation) {
        this.relation = relation;
    }

    public void connectWithTarget() {
        ITarget target = microText.getTargetById(relation.getTargetId());
        relation.setTarget(target);
    }

    public String getRelationTargetId() {
        return relation.getTargetId();
    }

    public String getWekaAttackOrSupport() {
        return relation.getWekaAttackOrSupport();
    }

    public boolean hasRelation() {
        return relation.isValidRelation();
    }

    public String getWekaRebutOrUndercut() {
        return relation.getWekaRebutOrUndercut();
    }

    public boolean isAttack() {
        return relation.isAttack();
    }

    public void changeTypeTo(TextSegment textSegment) {
        textSegment.setMicroText(microText);
        textSegment.setFileId(fileId);
        textSegment.setTextSegmentId(segmentId);
        textSegment.setSegmentPositionIndex(segmentPositionIndex);
        textSegment.setLanguage(language);
        textSegment.setCorrespondentFile(correspondentFile);
        textSegment.setSentence(sentence);
        textSegment.setClaim(isClaim);
        textSegment.setEdgeId(edgeId);
        textSegment.setTypeStr(typeStr);
        textSegment.setRelation(relation);

        relation.setSourceSegment(textSegment);
        microText.replaceTextSegment(segmentPositionIndex, textSegment);
    }
}
