package Main;

import ConfigurationManager.ConfigurationManager;
import GUI.GUI;
import InputOutput.XMLParser;
import InputOutput.XMLWriter;
import Weka.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by LuckyP on 02.12.17.
 */
public class Main {


    private static List<MicroText> microTexts;
    private static List<MicroText> generatedMicroTexts;
    private static WekaMachineLearning machineLearning;
    public static boolean systemHasLearn;

    public static void main(String args[]) {
        machineLearning = new WekaMachineLearning();
        generatedMicroTexts = new ArrayList<>();
        systemHasLearn = false;
        final String DATASET_PATH = ConfigurationManager.getInstance().getFilePath();
        XMLParser xmlParser = XMLParser.getInstance();
        microTexts = xmlParser.walkXMLFiles(DATASET_PATH);

        GUI.startGUI();

//        machineLearning.learn(microTexts, 10);
//
//        String myString = "Abortion should be permitted,\n" +
//                "because it prevents a life from evolving\n" +
//                "and this is as bad as killing a living person.";
//        useClassifier(Arrays.asList(myString.split("\n")));

//        useClassifier();
//        generateXMLFiles();
    }

    public static void runClassifier(int testDataPercentage) {
        machineLearning.learn(microTexts, testDataPercentage);
    }

    public static MicroText useClassifier(List<String> stringSentences) {
        MicroText myMicroText;
        if (systemHasLearn) {
            MicroTextFactory microTextFactory = new MicroTextFactory();
            // get plain Microtext from text entry
            myMicroText = microTextFactory.createMicroText(stringSentences,1);

            myMicroText = machineLearning.decide(myMicroText);
            generatedMicroTexts.add(myMicroText);

            System.out.println(myMicroText);
            return myMicroText;
        }
        else {
            List<String> errors = new ArrayList<>();
            errors.add("In order to use our Classifier you have firstly to run it!");
            GUI.showNotification(errors);
        }

        return null;
    }

    public static void generateXMLFromMicroText(MicroText microText) {
        XMLWriter xmlWriter = XMLWriter.getInstance();
        xmlWriter.writeXMLFile(microText);
    }

    /**
     * Generate XML File for ach MicroText which has been analysed with WEKA
     */
    private static void generateXMLFiles() {
        XMLWriter xmlWriter = XMLWriter.getInstance();
        microTexts.forEach(xmlWriter::writeXMLFile);
    }
}
