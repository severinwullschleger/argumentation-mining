package StandfordParserManager;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.stanford.nlp.trees.*;
import edu.stanford.nlp.util.CoreMap;
import org.ejml.simple.SimpleMatrix;

import java.io.IOException;
import java.util.*;


public class StanfordNLP {
    static StanfordCoreNLP pipeline;

    public StanfordNLP() {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
        pipeline = new StanfordCoreNLP(props);
    }

    /**
     * Gives a number, which indicates the sentimentScore of a sentence as follows:
     * 0:   very negative
     * 1:   negative
     * 2:   neutral
     * 3:   positive
     * 4:   very positive
     * @param text the sentence to examine
     * @return sentimentScore
     */
    public int getSentimentScore(String text) {

        int mainSentiment = 0;
        if (text != null && text.length() > 0) {
            int longest = 0;
            Annotation annotation = pipeline.process(text);
            for (CoreMap sentence : annotation
                    .get(CoreAnnotations.SentencesAnnotation.class)) {
                Tree tree = sentence
                        .get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
                int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
                SimpleMatrix sentiment_new = RNNCoreAnnotations.getPredictions(tree);
                String partText = sentence.toString();
                if (partText.length() > longest) {
                    mainSentiment = sentiment;
                    longest = partText.length();
                }
            }
        }
        return mainSentiment;
    }

    public void tag(String text) throws IOException, ClassNotFoundException {

        MaxentTagger maxentTagger = new MaxentTagger("english-left3words-distsim.tagger");;
        String tag = maxentTagger.tagString(text);
        String[] eachTag = tag.split("\\s+");
        System.out.println("Word      " + "Standford tag");
        System.out.println("----------------------------------");
        for(int i = 0; i< eachTag.length; i++) {
            System.out.println(eachTag[i].split("_")[0] +"           "+ eachTag[i].split("_")[1]);
        }

    }
}
