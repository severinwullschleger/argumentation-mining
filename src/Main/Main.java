package Main;

import ConfigurationManager.ConfigurationManager;
import Reader.FileReader;

import java.util.List;

/**
 * Created by LuckyP on 02.12.17.
 */
public class Main {

    public static void main(String args[]) {
        List<Sentence> sentenceList = FileReader.readFile(ConfigurationManager.getInstance().getTestFilePath());
        System.out.println(sentenceList.toString());
    }
}
