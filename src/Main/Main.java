package Main;

import ConfigurationManager.ConfigurationManager;
import Reader.FileReader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LuckyP on 02.12.17.
 */
public class Main {

    public static void main(String args[]) {
        final String DATASET_PATH = ConfigurationManager.getInstance().getENFilesPath();
        List<Corpus> corpuses = new ArrayList<>();
        FileReader.walkDatasetDirectory(DATASET_PATH).forEach((fileId, textSentences) -> {
            Corpus currentCorpus = new Corpus(fileId, Language.ENGLISH);
            currentCorpus.addSentences(textSentences);
            corpuses.add(currentCorpus);
            //System.out.println(currentCorpus);
        });

        System.out.println(corpuses);
    }
}
