package Main.Model.role;

import InputOutput.FileWriter;
import Main.TextSegment;

import java.nio.file.Paths;

/**
 * Created by LuckyP on 16.12.17.
 */
public class Proponent extends TextSegment {
    public Proponent() {

    }

    @Override
    public void writeToProOppFolder(String path) {
        String fullPath = Paths.get(path).toAbsolutePath().toString() + "/result_classes/textsegments/pro/";
        FileWriter.makeSureDirectoryExists(fullPath);
        writeToFile(fullPath);
    }
}
