package Main;

import ConfigurationManager.ConfigurationManager;
import GUI.GUI;
import InputOutput.XMLParser;
import InputOutput.XMLWriter;
import Weka.WekaMachineLearning;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by LuckyP on 02.12.17.
 */
public class Main {


    public static boolean systemHasLearn;
    private static List<MicroText> microTexts;
    private static List<MicroText> generatedMicroTexts;
    private static WekaMachineLearning machineLearning;
    private static int index;

    public static void main(String args[]) {
        machineLearning = new WekaMachineLearning();
        generatedMicroTexts = new ArrayList<>();
        index = 1;
        systemHasLearn = false;
        final String DATASET_PATH = ConfigurationManager.getInstance().getFilePath();
        XMLParser xmlParser = XMLParser.getInstance();
        microTexts = xmlParser.walkXMLFiles(DATASET_PATH);

        GUI.startGUI();

//        machineLearning.learn(microTexts, 10);
//        StanceClassifier stanceClassifer = new StanceClassifier();
//        stanceClassifer.run(microTexts, 10);
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
        systemHasLearn = true;
    }

    public static MicroText useClassifier(List<String> stringSentences) {
        MicroText myMicroText;
        if (systemHasLearn) {
            MicroTextFactory microTextFactory = new MicroTextFactory();
            // get plain Microtext from text entry
            index++;
            myMicroText = microTextFactory.createMicroText(stringSentences, index);

            myMicroText = machineLearning.decide(myMicroText);
            generatedMicroTexts.add(myMicroText);

            System.out.println(myMicroText);
            List<String> notif = new ArrayList<>();
            notif.add("The MicroText " + myMicroText.getFileId() + " has been successfully generated!");
            GUI.showNotification(notif);
            return myMicroText;
        } else {
            List<String> errors = new ArrayList<>();
            errors.add("In order to use our Classifier you have firstly to run it!");
            GUI.showNotification(errors);
        }

        return null;
    }

    public static void generateXMLFromMicroText(MicroText microText) {
        XMLWriter xmlWriter = XMLWriter.getInstance();
        xmlWriter.writeXMLFile(microText);
        List<String> notif = new ArrayList<>();
        notif.add(microText.getFileId() + " has been successfully saved!");
        GUI.showNotification(notif);
    }

    /**
     * Generate XML File for ach MicroText which has been analysed with WEKA
     */
    private static void generateXMLFiles() {
        XMLWriter xmlWriter = XMLWriter.getInstance();
        microTexts.forEach(xmlWriter::writeXMLFile);
    }
}
