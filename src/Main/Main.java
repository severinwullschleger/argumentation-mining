package Main;

import ConfigurationManager.ConfigurationManager;
import InputOutput.XMLParser;
import InputOutput.XMLWriter;
import Weka.IsClaimClassifier;

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
//        StanceClassifier stanceClassifer = new StanceClassifier();
//        stanceClassifer.run(microTexts);
//
//        FileWriter.writeTextSegmentToProOppFolder(microTexts, DATASET_PATH);
//        StanceClassifier stanceClassifer = new StanceClassifier();
//        stanceClassifer.run(microTexts);

//        ProponentOponentClassifier proponentOponentClassifier = new ProponentOponentClassifier();
//        proponentOponentClassifier.run(microTexts, 10);
        IsClaimClassifier isClaimClassifier = new IsClaimClassifier();
        isClaimClassifier.run(microTexts, 10);
//        TargetClassifier targetClassifier = new TargetClassifier();
//        targetClassifier.run(microTexts, 10);


//        AttackSupportClassifier attackSupportClassifier = new AttackSupportClassifier();
//        attackSupportClassifier.run(microTexts, 10);

        //        RebutUndercutClassifier rebutUndercutClassifier = new RebutUndercutClassifier();
//        rebutUndercutClassifier.run(microTexts, 10);


        generateXMLFiles();
    }

    /**
     * Genearte XML File for ach MicroText which has been analysed with WEKA
     */
    private static void generateXMLFiles() {
        XMLWriter xmlWriter = XMLWriter.getInstance();
        microTexts.forEach(xmlWriter::writeXMLFile);
    }
}
