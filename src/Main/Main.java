package Main;

import ConfigurationManager.ConfigurationManager;
import GUI.GUI;
import InputOutput.XMLParser;
import InputOutput.XMLWriter;
import Weka.ProponentOponentClassifier;

import java.util.List;

/**
 * Created by LuckyP on 02.12.17.
 */
public class Main {


    private static List<MicroText> microTexts;
    private static ProponentOponentClassifier proponentOponentClassifier;

    public static void main(String args[]) {
        proponentOponentClassifier = new ProponentOponentClassifier();
        final String DATASET_PATH = ConfigurationManager.getInstance().getFilePath();
        XMLParser xmlParser = XMLParser.getInstance();
        microTexts = xmlParser.walkXMLFiles(DATASET_PATH);

        GUI.startGUI();



//        IsClaimClassifier isClaimClassifier = new IsClaimClassifier();
//        isClaimClassifier.run(microTexts, 10);
//        AttackSupportClassifier attackSupportClassifier = new AttackSupportClassifier();
//        attackSupportClassifier.run(microTexts, 10);
//        RebutUndercutClassifier rebutUndercutClassifier = new RebutUndercutClassifier();
//        rebutUndercutClassifier.run(microTexts, 10);
//        TargetClassifier targetClassifier = new TargetClassifier();
//        targetClassifier.run(microTexts, 10);

//        StanceClassifier stanceClassifer = new StanceClassifier();
//        stanceClassifer.run(microTexts);


//        useClassifier();


//        generateXMLFiles();
    }

    public static MicroText useClassifier(List<String> stringSentences) {
        String myString = "Adoption should be permitted,\n" +
                "because it prevents a life from evolving\n" +
                "and this is as bad as killing a living person.";

        MicroTextFactory microTextFactory = new MicroTextFactory();
        // get plain Microtext from text entry
        MicroText myMicroText = microTextFactory.createMicroText(stringSentences);
        System.out.println(myMicroText);

        proponentOponentClassifier.useClassifier(myMicroText);
        return myMicroText;
    }

    public static void runProponentOponentClassifier(int testDataPercentage) {
        proponentOponentClassifier.run(microTexts, testDataPercentage);
    }

    /**
     * Generate XML File for ach MicroText which has been analysed with WEKA
     */
    private static void generateXMLFiles() {
        XMLWriter xmlWriter = XMLWriter.getInstance();
        microTexts.forEach(xmlWriter::writeXMLFile);
    }
}
