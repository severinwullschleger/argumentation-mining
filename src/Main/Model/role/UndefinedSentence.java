package Main.Model.role;

import InputOutput.FileWriter;
import Main.Enums.Language;
import Main.TextSegment;
import edu.stanford.nlp.simple.Sentence;

import java.io.File;
import java.nio.file.Paths;

/**
 * Created by LuckyP on 16.12.17.
 */
public class UndefinedSentence extends TextSegment {

    public UndefinedSentence() {
    }

    public UndefinedSentence(String fileId, String sentenceId, Language sentencesLanguage, File file, Sentence sentence) {
        super( fileId,  sentenceId,  sentencesLanguage,  file,  sentence);
    }


    public String getType() {
        return " ";
    }

    @Override
    public void writeToProOppFolder(String path) {
        String fullPath = Paths.get(path).toAbsolutePath().toString() + "/result_classes/textsegments/na/";
        FileWriter.makeSureDirectoryExists(fullPath);
        writeToFile(fullPath);
    }
}
