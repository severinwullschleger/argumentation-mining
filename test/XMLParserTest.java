import ConfigurationManager.ConfigurationManager;
import InputOutput.XMLParser;
import Main.MicroText;
import Main.TextSegment;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class XMLParserTest {

    private List<MicroText> microTexts;
    private final String DATASET_PATH = ConfigurationManager.getInstance().getFilePath();

    @Before
    public void setup() {
        XMLParser xmlParser = XMLParser.getInstance();
        microTexts = xmlParser.walkXMLFiles(DATASET_PATH);
    }

    /**
     * Tests at the first MicrosText, if the isClaim is set correctly
     */
    @Test
    public void testIsClaimSetter() {
        List<TextSegment> textSegments = microTexts.get(0).getTextSegments();

        Assert.assertFalse(textSegments.get(0).isClaim());
        Assert.assertFalse(textSegments.get(1).isClaim());
        Assert.assertFalse(textSegments.get(2).isClaim());
        Assert.assertFalse(textSegments.get(3).isClaim());
        Assert.assertTrue(textSegments.get(4).isClaim());
    }
}
