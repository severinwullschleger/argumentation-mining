package Main;

import ConfigurationManager.ConfigurationManager;
import InputOutput.FileWriter;
import InputOutput.XMLParser;
import Weka.StanceClassifier;

import java.util.List;

/**
 * Created by LuckyP on 02.12.17.
 */
public class Main {

    public static void main(String args[]) {
        final String DATASET_PATH = ConfigurationManager.getInstance().getFilePath();

        XMLParser xmlParser = XMLParser.getInstance();
        List<MicroText> microTexts = xmlParser.walkXMLFiles(DATASET_PATH);

        StanceClassifier stanceClassifer = new StanceClassifier();
        stanceClassifer.run(microTexts);

//        FileWriter.writeTextSegmentToProOppFolder(microTexts, DATASET_PATH);
    }
}

//        FileWriter.writeToProConFolder(corpuses, DATASET_PATH);

//        //create arff file from directories
//        String[] arguments = { "-dir"
//                , Paths.get(DATASET_PATH + "/result_classes/").toAbsolutePath().toString()  };
//        weka.core.converters.TextDirectoryLoader.main(arguments);
//        //copy paste from terminal and save as arff file.


//    public static void main(String args[]) {
//        final String DATASET_PATH = ConfigurationManager.getInstance().getFilePath();
//        List<Corpus> corpuses = new ArrayList<>();
//
//        FileReader.walkDatasetDirectory(DATASET_PATH).forEach((fileId, textSentences) -> {
//            Corpus currentCorpus = new Corpus(fileId, ConfigurationManager.SENTENCES_LANGUAGE);
//            currentCorpus.addSentences(textSentences);
//            corpuses.add(currentCorpus);
//        });
//        System.out.println(corpuses);
//    }