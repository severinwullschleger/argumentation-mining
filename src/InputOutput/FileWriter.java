package InputOutput;


import Main.MicroText;
import Main.Enums.Stance;
import Main.TextSegment;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

public class FileWriter {

    public static void writeFile() {}


    public static void writeToProConFolder(List<MicroText> microTexts, String path) {

        String proPath = Paths.get(path).toAbsolutePath().toString() + "/result_classes/pro/";
        makeSureDirectoryExists(proPath);

        String conPath = Paths.get(path).toAbsolutePath().toString() + "/result_classes/con_/";
        makeSureDirectoryExists(conPath);

        for (MicroText microText : microTexts) {
            if (microText.getStance().equals(Stance.PRO))
                microText.writeToFile(proPath);
            else if (microText.getStance().equals(Stance.CON))
                microText.writeToFile(conPath);
        }
    }

    public static void writeTextSegmentToProOppFolder(List<MicroText> microTexts, String path) {

        for (MicroText microText : microTexts) {
            for (TextSegment textSegment : microText.getTextSegments())
                textSegment.writeToProOppFolder(path);
        }
    }

    public static void makeSureDirectoryExists(String path) {
        File dir = new File (path);
        if (!dir.exists()) {
            boolean b = dir.mkdirs();
        }
    }
}
