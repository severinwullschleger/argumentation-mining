package Main;

import ConfigurationManager.ConfigurationManager;
import GUI.GUI;
import Main.Enums.EnumsManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MicroTextFactory {
    public MicroText createMicroText(List<String> stringSentences, int index) {
        MicroText microText = new MicroText();
        microText.setTopicId("m0");
        microText.setLanguage(ConfigurationManager.SENTENCES_LANGUAGE);
        File file = new File("test " + index);
        microText.setCorrespondentFile(file);
        microText.setFileId(file.getName());
        // TODO: SEVI IS GAY
        microText.setStance(EnumsManager.convertToStanceEnum(""));

        TextSegmentFactory tsf = new TextSegmentFactory();
        List<TextSegment> textSegmentList = new ArrayList<>();
        int id = 0;
        for (String str : stringSentences) {
            TextSegment textSegment = tsf.createUndefinedTextSegment(str, id + 1);
            textSegment.setSegmentPositionIndex(id);
            textSegment.setMicroText(microText);
            textSegmentList.add(textSegment);
            id++;
        }
        microText.setTextSegments(textSegmentList);
        return microText;
    }
}
