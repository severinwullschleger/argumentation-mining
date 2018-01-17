package StandfordParserManager;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.Tokenizer;
import edu.stanford.nlp.process.TokenizerFactory;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.stanford.nlp.trees.*;
import edu.stanford.nlp.util.CoreMap;
import org.ejml.simple.SimpleMatrix;

import java.io.IOException;
import java.io.StringReader;
import java.util.*;


public class StanfordNLP {
    static StanfordNLP instance;
    private StanfordNLP() {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
        pipeline = new StanfordCoreNLP(props);
        lp = LexicalizedParser.loadModel(PARSER_MODEL_PATH);
    }

    public static StanfordNLP getInstance() {
        if(instance == null){
            instance = new StanfordNLP();
        }
        return instance;
    }
    private final String [] RELEVANT_TYPED_DEPENDENCIES = {"nsubj", "dobj", "aux", "xcomp"};
    final String PARSER_MODEL_PATH = "edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz";

    static StanfordCoreNLP pipeline;

    LexicalizedParser lp;

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

    /**
     * Extracts the relevant Stanford Typed dependencies ordered by the word appearance in the sentence
     * @param sentence                     : the sentence to exam
     * @return list of the relevant dependencies, ordered by word-order
     */
    public TypedDependency[] getRelevantTypedDependencies(String sentence) {
        HashMap<String, List<TypedDependency>> allDependencies = createTypedDependenciesHashMap(sentence);
        List<TypedDependency> relevantDependencyList = new ArrayList<TypedDependency>();

        for (String dependencyName : this.RELEVANT_TYPED_DEPENDENCIES) {
            List<TypedDependency> dependencies = allDependencies.get(dependencyName);
            if (dependencies != null) {
                relevantDependencyList.add(dependencies.get(0));
            }
        }

        TypedDependency[] relevantArray = new TypedDependency[relevantDependencyList.size()];
        for (int i = 0; i < relevantArray.length; i++) {
            relevantArray[i] = relevantDependencyList.get(i);
        }
        //sort by word appearance
        Arrays.sort(relevantArray);

        return relevantArray;
    }

    private HashMap<String, List<TypedDependency>> createTypedDependenciesHashMap(String sentence) {
        TokenizerFactory<CoreLabel> tokenizerFactory =
                PTBTokenizer.factory(new CoreLabelTokenFactory(), "");
        Tokenizer<CoreLabel> tok =
                tokenizerFactory.getTokenizer(new StringReader(sentence));
        List<CoreLabel> rawWords2 = tok.tokenize();
        Tree parse = lp.apply(rawWords2);

        TreebankLanguagePack tlp = lp.treebankLanguagePack(); // PennTreebankLanguagePack for English
        GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();
        GrammaticalStructure gs = gsf.newGrammaticalStructure(parse);
        List<TypedDependency> tdl = gs.typedDependenciesCCprocessed();

        HashMap<String, List<TypedDependency>> typedDependencyHash = new HashMap<String, List<TypedDependency>>();
        for (TypedDependency typedDependency : tdl) {
            if (!typedDependencyHash.containsKey(typedDependency.reln())) {
                List<TypedDependency> list = new ArrayList<TypedDependency>();
                list.add(typedDependency);

                typedDependencyHash.put(typedDependency.reln().toString(), list);
            } else {
                typedDependencyHash.get(typedDependency.toString()).add(typedDependency);
            }
        }
        return typedDependencyHash;
    }
}
