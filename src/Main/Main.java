package Main;

import ConfigurationManager.ConfigurationManager;
import GUI.GUI;
import InputOutput.XMLParser;
import InputOutput.XMLWriter;
import Weka.*;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by LuckyP on 02.12.17.
 */
public class Main {


    private static List<MicroText> microTexts;
    private static WekaMachineLearning machineLearning;
    public static boolean systemHasLearn;

    public static void main(String args[]) {
        machineLearning = new WekaMachineLearning();
        systemHasLearn = false;
        final String DATASET_PATH = ConfigurationManager.getInstance().getFilePath();
        XMLParser xmlParser = XMLParser.getInstance();
        microTexts = xmlParser.walkXMLFiles(DATASET_PATH);

        GUI.startGUI();

//        useClassifier();


//        generateXMLFiles();
    }

    public static void useClassifier(List<String> stringSentences) {
        if (systemHasLearn) {
            String myString = "Adoption should be permitted,\n" +
                    "because it prevents a life from evolving\n" +
                    "and this is as bad as killing a living person.";
            MicroTextFactory microTextFactory = new MicroTextFactory();
            // get plain Microtext from text entry
            MicroText myMicroText = microTextFactory.createMicroText(stringSentences);

            machineLearning.decide(myMicroText);

            System.out.println(myMicroText);
        }
        else {
            List<String> errors = new ArrayList<>();
            errors.add("In order to use our Classifier you have firstly to run it!");
            GUI.showNotification(errors);
        }

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
