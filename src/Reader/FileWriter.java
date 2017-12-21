package Reader;


import Main.Corpus;
import Main.Enums.Stance;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

public abstract class FileWriter {

    public static void writeFile() {}


    public static void writeToProConFolder(List<Corpus> corpuses, String path) {

        String proPath = Paths.get(path).toAbsolutePath().toString() + "/result_classes/pro/";
        makeSureDirectoryExists(proPath);

        String conPath = Paths.get(path).toAbsolutePath().toString() + "/result_classes/con_/";
        makeSureDirectoryExists(conPath);

        for (Corpus corpus : corpuses) {
            if (corpus.getStance().equals(Stance.PRO))
                corpus.writeToFile(proPath);
            else if (corpus.getStance().equals(Stance.CON))
                corpus.writeToFile(conPath);
        }
    }

    private static void makeSureDirectoryExists(String path) {
        File dir = new File (path);
        if (!dir.exists()) {
            boolean b = dir.mkdirs();
        }
    }
}
