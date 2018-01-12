package Main;

import Main.Enums.Language;
import Main.Enums.Stance;
import Main.Model.role.NullTextSegment;
import Main.Model.role.Opponent;
import Main.Model.role.Proponent;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Corpus represents a textfile
 * Each corpus (e.g. micro_b001.txt) has a list of <sentence>sentences</sentence>
 */
public class MicroText {

    private String fileId;
    private String topicId;         // e.g waste_separation
    private Stance stance;          // "pro" or "opp"
    private List<TextSegment> textSegments;
    private Language language;
    private File correspondentFile;


    public MicroText() {
        this.textSegments = new ArrayList<>();

    }

    public MicroText(String fileId, List<TextSegment> sentences, Language language, List<String> preprocessedCorpus) {
        this.fileId = fileId;
        this.textSegments = sentences;
        this.language = language;
    }

    public MicroText(String fileId, Language language) {
        this.fileId = fileId;
        this.language = language;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
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

        for (TextSegment textSegment : textSegments)
            rtn.append(textSegment.toString());

        return rtn.toString();
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setStance(Stance stance) {
        this.stance = stance;
    }

    public List<TextSegment> getTextSegments() {
        return textSegments;
    }

    public void setTextSegments(List<TextSegment> textSegments) {
        this.textSegments = textSegments;
    }

    public void setCorrespondentFile(File correspondentFile) {
        this.correspondentFile = correspondentFile;
    }

    public List<TextSegment> getOpponents() {
        List<TextSegment> opponents = new ArrayList<>();
        if (this.textSegments != null) {
            for (TextSegment textSegment : this.textSegments) {
                if (textSegment instanceof Opponent) {
                    opponents.add(textSegment);
                }
            }
            return opponents;
        }
        return null;
    }

    public List<TextSegment> getProponents() {
        List<TextSegment> proponents = new ArrayList<>();
        if (this.textSegments != null) {
            for (TextSegment textSegment : this.textSegments) {
                if (textSegment instanceof Proponent) {
                    proponents.add(textSegment);
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
            for (TextSegment sent : textSegments)
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
        for (TextSegment textSegment : textSegments)
            unigrams.addAll(textSegment.getLemmaUnigrams());
        return unigrams;
    }

    public List<String> getLemmaBigrams() {
        ArrayList bigrams = new ArrayList<>();
        for (TextSegment textSegment : textSegments)
            bigrams.addAll(textSegment.getLemmaBigrams());
        return bigrams;
    }

    public List<String> getLemmaUnigramsFromSentence(int i) {
        ArrayList lemmas = new ArrayList<>();
        if (i >= 0 && i < textSegments.size())
            lemmas.addAll(textSegments.get(i).getLemmaUnigrams());
        return lemmas;
    }

    public List<String> getLemmaBigramsFromSentence(int i) {
        ArrayList lemmas = new ArrayList<>();
        if (i >= 0 && i < textSegments.size())
            lemmas.addAll(textSegments.get(i).getLemmaBigrams());
        return lemmas;
    }

    public HashMap<String, List<String>> getAllLemmaUnigramsPerTextSentence() {
        HashMap unigramsPerSentence = new HashMap<String, List<String>>();
        for (TextSegment textSegment : textSegments)
            unigramsPerSentence.put(textSegment.getSegmentId(), textSegment.getLemmaUnigrams());
        return unigramsPerSentence;
    }

    public HashMap<String, List<String>> getAllLemmaBigramsPerTextSentence() {
        HashMap bigramsPerSentence = new HashMap<String, List<String>>();
        for (TextSegment textSegment : textSegments)
            bigramsPerSentence.put(textSegment.getSegmentId(), textSegment.getLemmaBigrams());
        return bigramsPerSentence;
    }

    public List<Integer> getSentimentValues(){
        List<Integer> sentimentScores = new ArrayList<>();
        for (TextSegment textSegment : textSegments) {
            sentimentScores.add(textSegment.getSentimentScore());
        }
        return sentimentScores;
    }


    public void printOpponentAndProponents() {
        System.out.println(getFileId() + "  has the following proponents: ");
        for (TextSegment proponent : getProponents()) {
            System.out.print(proponent.getSegmentId() + " , ");
        }
        System.out.println("\n");

        System.out.println(getFileId() + "  has the following opponents: ");
        for (TextSegment opponent : getOpponents()) {
            System.out.print(opponent.getSegmentId() + " , ");
        }
        System.out.println("\n");
    }

    private TextSegment getTextSegmentsById(String segmentId) {
        for (TextSegment textSegment : textSegments)
            if (textSegment.hasId(segmentId))
                return textSegment;

        return new NullTextSegment();
    }

    public void setIsClaimInTextSegment(String segmentId, boolean isClaim) {
        TextSegment segment = getTextSegmentsById(segmentId);
        segment.setClaim(isClaim);
    }

    public void addRelationToItsSourceSegment(Relation relation) {
        TextSegment sourceSegment = getTextSegmentsById(relation.getSourceId());
        sourceSegment.setRelation(relation);
        relation.setSourceSegment(sourceSegment);
    }

    public void connectRelationsWithTargets() {
        for (TextSegment textSegment : textSegments) {
            textSegment.connectWithTarget();
        }
    }

    public ITarget getTargetById(String targetId) {
        for (TextSegment textSegment : textSegments) {
            if (textSegment.hasId(targetId))
                return textSegment;
            else if (textSegment.hasRelationId(targetId))
                return textSegment.getRelation();
        }
        return new NullTextSegment();
    }
}
