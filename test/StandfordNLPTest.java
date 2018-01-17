import StandfordParserManager.POSType;
import StandfordParserManager.POSTypeDecider;
import StandfordParserManager.StanfordNLP;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests the class 'StanfordNLP' and its public functionality
 */
public class StandfordNLPTest {


    @Before
    public void initialize() {
    }


    /**
     * Tests the sentimentScore with different sentences
     */
    @Test
    public void testSentimentScore() {
        final String NEGATIVE = "I hate this awful and annying excersice. It is suck a pain and idiotic";
        final String NEUTRAL = "This is my book";
        final String POSITIV = "This is great";

        assertEquals("String VERY_NEGATIVE is returning 1 as a sentiment score in the Stanford NLP", 1, StanfordNLP.getInstance().getSentimentScore(NEGATIVE));
        assertEquals("String NEGATIVE is returning 2 as a sentiment score in the Stanford NLP", 2, StanfordNLP.getInstance().getSentimentScore(NEUTRAL));
        assertEquals("String NEUTRAL is returning 3 as a sentiment score in the Stanford NLP", 3, StanfordNLP.getInstance().getSentimentScore(POSITIV));
    }

    @Test
    public void testRelevantTypedDependencies() {
        String emptyString = "e";

        assertEquals("Empty String returns POSType Nothing", POSType.NOTHING, POSTypeDecider.getInstance().getPOSType(emptyString));
    }

    @Test
    public void testFullSentence() {
        String fullString = "Firefox may not have been able to install its own updates on Ubuntu";
        assertEquals("Full String returns POSType AUX_DOBJ_NSUBJ_XCOMP", POSType.AUX_DOBJ_NSUBJ_XCOMP, POSTypeDecider.getInstance().getPOSType(fullString));
    }

    @Test
    public void testHalfSentence() {
        String halfString = "He eats a cake";
        assertEquals("Half String returns NLPType DOBJ_NSUBJ ", POSType.DOBJ_NSUBJ, POSTypeDecider.getInstance().getPOSType(halfString));
    }
}
