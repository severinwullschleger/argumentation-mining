package Main.Model.role;


import InputOutput.FileWriter;
import Main.TextSegment;

import java.nio.file.Paths;

/**
 * Created by LuckyP on 16.12.17.
 */
public class Opponent extends TextSegment {

    public Opponent() {
    }

    public String getType() {
        return "opp";
    }

    @Override
    public void writeToProOppFolder(String path) {
        String fullPath = Paths.get(path).toAbsolutePath().toString() + "/result_classes/textsegments/opp/";
        FileWriter.makeSureDirectoryExists(fullPath);
        writeToFile(fullPath);
    }
}
