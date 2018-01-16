import StandfordParserManager.StanfordNLP;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the class 'StanfordNLP' and its public functionality
 */
public class StandfordNLPTest {
    StanfordNLP stanfordNLP;

    @Before
    public void initialize() {
        stanfordNLP = new StanfordNLP();
    }


    /**
     * Tests the sentimentScore with different sentences
     */
    @Test
    public void testSentimentScore() {
        final String NEGATIVE = "I hate this awful and annying excersice. It is suck a pain and idiotic";
        final String NEUTRAL = "This is my book";
        final String POSITIV = "This is great";

        Assert.assertEquals("String VERY_NEGATIVE is returning 1 as a sentiment score in the Stanford NLP", 1, stanfordNLP.getSentimentScore(NEGATIVE));
        Assert.assertEquals("String NEGATIVE is returning 2 as a sentiment score in the Stanford NLP", 2, stanfordNLP.getSentimentScore(NEUTRAL));
        Assert.assertEquals("String NEUTRAL is returning 3 as a sentiment score in the Stanford NLP", 3, stanfordNLP.getSentimentScore(POSITIV));
    }
}
