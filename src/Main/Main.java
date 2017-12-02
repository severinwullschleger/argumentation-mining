package Main;

import ConfigurationManager.ConfigurationManager;
import Reader.FileReader;

import java.util.List;

/**
 * Created by LuckyP on 02.12.17.
 */
public class Main {

    public static void main(String args[]) {
        final String DATASET_PATH = ConfigurationManager.getInstance().getENFilesPath();
        FileReader.walkDatasetDirectory(DATASET_PATH).forEach((fileId, sentences) -> {
            System.out.println("\n---------"+fileId +"---------\n");
            for (Sentence sentence: sentences) {
                System.out.println(sentence);
            }
        });
    }
}
