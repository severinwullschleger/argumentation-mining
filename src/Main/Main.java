package Main;

import ConfigurationManager.ConfigurationManager;
import InputOutput.FileWriter;
import InputOutput.XMLParser;
import Weka.ProponentOponentClassifier;
import Weka.StanceClassifier;
import Weka.TargetClassifier;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by LuckyP on 02.12.17.
 */
public class Main {

    public static void main(String args[]) {
        final String DATASET_PATH = ConfigurationManager.getInstance().getFilePath();

        XMLParser xmlParser = XMLParser.getInstance();
        List<MicroText> microTexts = xmlParser.walkXMLFiles(DATASET_PATH);

//        StanceClassifier stanceClassifer = new StanceClassifier();
//        stanceClassifer.run(microTexts);
        ProponentOponentClassifier proponentOponentClassifier = new ProponentOponentClassifier();
        proponentOponentClassifier.run(microTexts, 10);
//        TargetClassifier targetClassifier = new TargetClassifier();
//        targetClassifier.run(microTexts, 10);
    }
}

//        FileWriter.writeToProConFolder(corpuses, DATASET_PATH);

//        //create arff file from directories
//        String[] arguments = { "-dir"
//                , Paths.get(DATASET_PATH + "/result_classes/").toAbsolutePath().toString()  };
//        weka.core.converters.TextDirectoryLoader.main(arguments);
//        //copy paste from terminal and save as arff file.