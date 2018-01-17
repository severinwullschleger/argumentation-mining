package Main;

import java.util.ArrayList;
import java.util.List;

public class MicroTextFactory {
    public MicroText createMicroText(List <String> stringSentences) {
        MicroText microText = new MicroText();

        TextSegmentFactory tsf = new TextSegmentFactory();
        List<TextSegment> textSegmentList = new ArrayList<>();
        int id = 0;
        for (String str : stringSentences) {
            TextSegment textSegment = tsf.createUndefinedTextSegment(str, id+1);
            textSegment.setSegmentPositionIndex(id);
            textSegment.setMicroText(microText);
            textSegmentList.add(textSegment);
            id++;
        }
        microText.setTextSegments(textSegmentList);
        return microText;
    }
}
