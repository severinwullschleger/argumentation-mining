package Main;

import ConfigurationManager.ConfigurationManager;
import InputOutput.XMLParser;
import InputOutput.XMLWriter;
import Weka.*;
import weka.core.Instance;

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

        WekaMachineLearning machineLearning = new WekaMachineLearning();
        machineLearning.learn(microTexts, 10);

        String myString = "Adoption should be permitted,\n" +
                "because it prevents a life from evolving\n" +
                "and this is as bad as killing a living person.";

        MicroTextFactory microTextFactory = new MicroTextFactory();
        // get plain Microtext from text entry
        MicroText myMicroText = microTextFactory.createMicroText(myString);

        machineLearning.decide(myMicroText);

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
