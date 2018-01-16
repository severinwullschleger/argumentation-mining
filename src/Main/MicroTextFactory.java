package Main;

import java.util.ArrayList;
import java.util.List;

public class MicroTextFactory {
    public MicroText createMicroText(String myString) {
        String[] strings = myString.split("\n");
        MicroText microText = new MicroText();

        TextSegmentFactory tsf = new TextSegmentFactory();
        List<TextSegment> textSegmentList = new ArrayList<>();
        int id = 0;
        for (String str : strings) {
            TextSegment textSegment = tsf.createUndefinedTextSegment(str, id);
            textSegment.setSegmentPositionIndex(id);
            textSegment.setMicroText(microText);
            textSegmentList.add(textSegment);
            id++;
        }
        microText.setTextSegments(textSegmentList);
        return microText;
    }
}
