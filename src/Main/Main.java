package Main;

import ConfigurationManager.ConfigurationManager;
import InputOutput.XMLParser;
import InputOutput.XMLWriter;
import Weka.AttackSupportClassifier;
import Weka.IsClaimClassifier;
import Weka.ProponentOponentClassifier;
import weka.core.Instance;
import Weka.StanceClassifier;
import java.util.List;

/**
 * Created by LuckyP on 02.12.17.
 */
public class Main {


    private static List<MicroText> microTexts;

    public static void main(String args[]) {
        final String DATASET_PATH = ConfigurationManager.getInstance().getFilePath();
        XMLParser xmlParser = XMLParser.getInstance();
        microTexts = xmlParser.walkXMLFiles(DATASET_PATH);

        ProponentOponentClassifier proponentOponentClassifier = new ProponentOponentClassifier();
        proponentOponentClassifier.run(microTexts, 10);
        IsClaimClassifier isClaimClassifier = new IsClaimClassifier();
        isClaimClassifier.run(microTexts, 10);
        AttackSupportClassifier attackSupportClassifier = new AttackSupportClassifier();
        attackSupportClassifier.run(microTexts, 10);
//        RebutUndercutClassifier rebutUndercutClassifier = new RebutUndercutClassifier();
//        rebutUndercutClassifier.run(microTexts, 10);
//        TargetClassifier targetClassifier = new TargetClassifier();
//        targetClassifier.run(microTexts, 10);

     StanceClassifier stanceClassifier = new StanceClassifier();
     stanceClassifier.run(microTexts);

        String myString = "Adoption should be permitted,\n" +
                "because it prevents a life from evolving\n" +
                "and this is as bad as killing a living person.";

        MicroTextFactory microTextFactory = new MicroTextFactory();
        // get plain Microtext from text entry
        MicroText myMicroText = microTextFactory.createMicroText(myString);

        proponentOponentClassifier.useClassifier(myMicroText);
        isClaimClassifier.useClassifier(myMicroText);
        attackSupportClassifier.useClassifier(myMicroText);
        System.out.println(myMicroText);




//        generateXMLFiles();
    }

    /**
     * Genearte XML File for ach MicroText which has been analysed with WEKA
     */
    private static void generateXMLFiles() {
        XMLWriter xmlWriter = XMLWriter.getInstance();
        microTexts.forEach(xmlWriter::writeXMLFile);
    }
}
