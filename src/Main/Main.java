package Main;

import ConfigurationManager.ConfigurationManager;
import Reader.FileReader;
import StandfordParserManager.NLPManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LuckyP on 02.12.17.
 */
public class Main {

    final static String language_config = "en_files";       //  en_files  de_files  test_file

    public static void main(String args[]) {
        final String DATASET_PATH = ConfigurationManager.getInstance().getFilePath(language_config);
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
