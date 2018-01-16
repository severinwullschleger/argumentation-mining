import ConfigurationManager.ConfigurationManager;
import InputOutput.XMLParser;
import Main.MicroText;
import Main.TextSegment;
import Weka.ProponentOponentClassifier;
import Weka.StanceClassifier;
import Weka.TargetClassifier;
import Weka.TextSegmentClassifier;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import weka.core.Attribute;

import java.util.List;

public class AllClassifierTest {
    final String DATASET_PATH = ConfigurationManager.getInstance().getFilePath();
    List<MicroText> microTexts;

    @Before
    public void setup() {
        XMLParser xmlParser = XMLParser.getInstance();
        microTexts = xmlParser.walkXMLFiles(DATASET_PATH);
    }

    /**
     * Runs the StanceClassifier
     */
    @Test
    public void testStanceClassifier() {
        StanceClassifier stanceClassifer = new StanceClassifier();
        stanceClassifer.run(microTexts);
    }

    /**
     * Runs the ProponenOpponenetClassifier
     */
    @Test
    public void testProponentOponentClassifier() {
        ProponentOponentClassifier proponentOponentClassifier = new ProponentOponentClassifier();
        proponentOponentClassifier.run(microTexts, 10);
    }

    /**
     * Runs the TargetClassifier
     */
    @Test
    public void testTargetClassifier() {
        TargetClassifier targetClassifier = new TargetClassifier();
        targetClassifier.run(microTexts, 10);
    }
}
