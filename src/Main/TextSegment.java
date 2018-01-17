package Main;

import Main.Enums.Language;

import Main.Model.type.Rebut;
import Main.Model.typegen.NullRelation;
import edu.stanford.nlp.simple.Sentence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.*;
import java.util.ArrayList;
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

    public MicroText getMicroText() {
        return microText;
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
                "\tRelation1 = '" + getWekaAttackOrSupport() + "'\n" +
                "\tRelation2 = '" + getWekaRebutOrUndercut() + "'\n" +
                "\tTarget = '" + relation.getTargetId() + "'\n" +
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

    public boolean negationMarker() {
        List<String> negationMarkers = new ArrayList<>(Arrays.asList("not", "none", "nothing", "without", "instead", "no-one", "nobody", "neither", "nor", "deny", "denial", "refute", "refuse", "missing", "avoid", "avoidance", "exclude", "exclusion", "not", "none", "nothing", "without", "instead", "no-one", "nobody", "neither", "nor", "deny", "denial", "refute", "refuse", "missing", "avoid", "avoidance", "exclude", "exclusion", "under no circumstances", "never", "nowhere", "not at all", "by no means", "no", "aint", "ain't", "doesn't", "doesnt", "havent", "haven't", "hasnt", "hasn't", "shouldnt", "shouldn't ", "isnt", "isn't", "hardly", "lack", "wouldnt", "wouldn't", "doubt", "object", "under no circumstances", "never", "nowhere", "not at all", "by no means", "no", "aint", "ain't", "doesn't", "doesnt", "havent", "haven't", "hasnt", "hasn't", "shouldnt", "shouldn't ", "isnt", "isn't", "hardly", "lack", "wouldnt", "wouldn't", "doubt", "object"));
        List<String> sentenceLemmas = sentence.lemmas();
        if (!Collections.disjoint(sentenceLemmas, negationMarkers) ||  negationMarkers.stream().anyMatch(s -> sentence.text().contains(s))) {
            return true;
        }
        else {
            return false;
        }
    }

    public List<List<String>> getDiscourseMarkers() {
        HashMap<String, String> discourseMarkers = new HashMap<>();
        discourseMarkers.put("nevertheless", "concession");
        discourseMarkers.put("nonetheless", "concession");
        discourseMarkers.put("however", "concession");
        discourseMarkers.put("still", "concession");
        discourseMarkers.put("yet", "concession");
        discourseMarkers.put("despite", "concession");
        discourseMarkers.put("although", "concession");
        discourseMarkers.put("even though", "concession");
        discourseMarkers.put("but", "concession");
        discourseMarkers.put("firstly", "ordering");
        discourseMarkers.put("secondly", "ordering");
        discourseMarkers.put("thirdly", "ordering");
        discourseMarkers.put("in addition", "ordering");
        discourseMarkers.put("in conclusion", "ordering");
        discourseMarkers.put("moreover", "ordering");
        discourseMarkers.put("on the one hand", "ordering");
        discourseMarkers.put("on the other hand", "ordering");
        discourseMarkers.put("to begin with", "ordering");
        discourseMarkers.put("in sum", "ordering");
        discourseMarkers.put("what is more", "ordering");
        discourseMarkers.put("in the end", "ordering");
        discourseMarkers.put("lastly", "ordering");
        discourseMarkers.put("for a start", "ordering");
        discourseMarkers.put("on top of that", "ordering");
        discourseMarkers.put("whereas", "ordering");
        discourseMarkers.put("furthermore", "ordering");
        discourseMarkers.put("actually", "attitude");
        discourseMarkers.put("I mean", "attitude");
        discourseMarkers.put("frankly", "attitude");
        discourseMarkers.put("admittedly", "attitude");
        discourseMarkers.put("hopefully", "attitude");
        discourseMarkers.put("amazingly", "attitude");
        discourseMarkers.put("honestly", "attitude");
        discourseMarkers.put("surprisingly", "attitude");
        discourseMarkers.put("naturally", "attitude");
        discourseMarkers.put("thankfully", "attitude");
        discourseMarkers.put("basically", "attitude");
        discourseMarkers.put("ideally", "attitude");
        discourseMarkers.put("no doubt", "attitude");
        discourseMarkers.put("to be honest", "attitude");
        discourseMarkers.put("certainly", "attitude");
        discourseMarkers.put("if you ask me", "attitude");
        discourseMarkers.put("obviously", "attitude");
        discourseMarkers.put("clearly", "attitude");
        discourseMarkers.put("of course", "attitude");
        discourseMarkers.put("understandably", "attitude");
        discourseMarkers.put("confidentially", "attitude");
        discourseMarkers.put("predictably", "attitude");
        discourseMarkers.put("undoubtedly", "attitude");
        discourseMarkers.put("definitely", "attitude");
        discourseMarkers.put("really", "attitude");
        discourseMarkers.put("unfortunately", "attitude");
        discourseMarkers.put("essentially", "attitude");
        discourseMarkers.put("in fact", "attitude");
        discourseMarkers.put("sadly", "attitude");
        discourseMarkers.put("fortunately", "attitude");
        discourseMarkers.put("indeed", "attitude");
        discourseMarkers.put("seriously", "attitude");
        discourseMarkers.put("apparently", "attitude");
        discourseMarkers.put("for example", "example");
        discourseMarkers.put("for instance", "example");
        discourseMarkers.put("in particular", "example");
        discourseMarkers.put("consequently", "consequence");
        discourseMarkers.put("therefore", "consequence");
        discourseMarkers.put("this is why", "consequence");
        discourseMarkers.put("that's why", "consequence");
        discourseMarkers.put("that is why", "consequence");

        Set<String> dms = discourseMarkers.keySet();
        List<List<String>> dmsContained = new ArrayList<>();
        for (String dm : dms) {
            if (sentence.text().toLowerCase().contains(dm)) {
                List<String> pair = new ArrayList<>();
                pair.add(dm);
                pair.add(discourseMarkers.get(dm));
                dmsContained.add(pair);
            }

        }
        return dmsContained;

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

    public void changeTo(TextSegment textSegment) {
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

    public void addRelationTarget(int id) {
        // claim has no relation to be undercutted
        if (relation.isUndercut() && microText.getTextSegmentById(id).isClaim())
            setRelation(new Rebut(this));

        relation.setTargetId(id);
        connectWithTarget();
    }

    public int getIdNumber() {
        return Integer.valueOf(segmentId.substring(1));
    }
}
