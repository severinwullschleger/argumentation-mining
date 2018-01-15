package Main;

import ConfigurationManager.ConfigurationManager;
import InputOutput.XMLParser;
import InputOutput.XMLWriter;
import Weka.*;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

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
//        TargetClassifier targetClassifier = new TargetClassifier();
//        targetClassifier.run(microTexts, 10);
//        IsClaimClassifier isClaimClassifier = new IsClaimClassifier();
//        isClaimClassifier.run(microTexts, 10);


//        AttackSupportClassifier attackSupportClassifier = new AttackSupportClassifier();
//        attackSupportClassifier.run(microTexts, 10);
        generateXMLFiles();
    }

    /**
     * Genearte XML File for ach MicroText which has been analysed with WEKA
     */
    public static void generateXMLFiles() {
        XMLWriter xmlWriter = XMLWriter.getInstance();
        microTexts.forEach(xmlWriter::writeXMLFile);
    }
}

//        FileWriter.writeToProConFolder(corpuses, DATASET_PATH);

//        //create arff file from directories
//        String[] arguments = { "-dir"
//                , Paths.get(DATASET_PATH + "/result_classes/").toAbsolutePath().toString()  };
//        weka.core.converters.TextDirectoryLoader.main(arguments);
//        //copy paste from terminal and save as arff file.