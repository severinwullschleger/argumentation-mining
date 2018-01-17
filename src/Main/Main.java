package Main;

import ConfigurationManager.ConfigurationManager;
import GUI.GUI;
import InputOutput.XMLParser;
import InputOutput.XMLWriter;
import Weka.*;

import java.util.List;


/**
 * Created by LuckyP on 02.12.17.
 */
public class Main {


    private static List<MicroText> microTexts;
    private static ProponentOponentClassifier proponentOponentClassifier;
    private static WekaMachineLearning machineLearning;

    public static void main(String args[]) {
        proponentOponentClassifier = new ProponentOponentClassifier();
        machineLearning = new WekaMachineLearning();
        final String DATASET_PATH = ConfigurationManager.getInstance().getFilePath();
        XMLParser xmlParser = XMLParser.getInstance();
        microTexts = xmlParser.walkXMLFiles(DATASET_PATH);


        GUI.startGUI();


//        IsClaimClassifier isClaimClassifier = new IsClaimClassifier();
//        isClaimClassifier.run(microTexts, 10);
//        AttackSupportClassifier attackSupportClassifier = new AttackSupportClassifier();
//        attackSupportClassifier.run(microTexts, 10);
//        ProponentOponentClassifier proponentOponentClassifier = new ProponentOponentClassifier();
//        proponentOponentClassifier.run(microTexts, 10);
//        IsClaimClassifier isClaimClassifier = new IsClaimClassifier();
//        isClaimClassifier.run(microTexts, 10);
//        AttackSupportClassifier attackSupportClassifier = new AttackSupportClassifier();
//        attackSupportClassifier.run(microTexts, 10);

//        RebutUndercutClassifier rebutUndercutClassifier = new RebutUndercutClassifier();
//        rebutUndercutClassifier.run(microTexts, 10);
//        TargetClassifier targetClassifier = new TargetClassifier();
//        targetClassifier.run(microTexts, 10);

//        StanceClassifier stanceClassifier = new StanceClassifier();
//        stanceClassifier.run(microTexts);


//        useClassifier();


//        generateXMLFiles();
    }

    public static void useClassifier(List<String> stringSentences) {
        String myString = "Adoption should be permitted,\n" +
                "because it prevents a life from evolving\n" +
                "and this is as bad as killing a living person.";
        MicroTextFactory microTextFactory = new MicroTextFactory();
        // get plain Microtext from text entry

        MicroText myMicroText = microTextFactory.createMicroText(stringSentences);

        machineLearning.decide(myMicroText);

        System.out.println(myMicroText);
    }

    public static void runClassifier(int testDataPercentage) {
        machineLearning.learn(microTexts, testDataPercentage);
    }

    /**
     * Generate XML File for ach MicroText which has been analysed with WEKA
     */
    private static void generateXMLFiles() {
        XMLWriter xmlWriter = XMLWriter.getInstance();
        microTexts.forEach(xmlWriter::writeXMLFile);
    }
}
