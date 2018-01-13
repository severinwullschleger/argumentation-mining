package InputOutput;

import ConfigurationManager.ConfigurationManager;
import Main.*;
import Main.Enums.EnumsManager;
import edu.stanford.nlp.simple.Sentence;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XMLParser {
    private static XMLParser instance = null;
    //Singleton constructor
    protected XMLParser() {}

    public static XMLParser getInstance() {
        if(instance == null) {
            instance = new XMLParser();
        }
        return instance;
    }

    public  List<MicroText> walkXMLFiles(String DATASET_PATH) {
        List<MicroText> microTexts = new ArrayList<>();
        File file = new File(DATASET_PATH);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    if (f.exists() && f.getName().contains(".xml")) {
                        microTexts.add(parseXML(f));
                    }
                }
            }
        }
        return microTexts;
    }

    private  MicroText parseXML(File inputFile) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            MicroText microText = new MicroText();
            microText.setFileId(doc.getDocumentElement().getAttribute("id"));
            microText.setTopicId(doc.getDocumentElement().getAttribute("topic_id"));
            String stanceAttribute = doc.getDocumentElement().getAttribute("stance");
            microText.setStance(EnumsManager.convertToStanceEnum(stanceAttribute));
            microText.setLanguage(ConfigurationManager.SENTENCES_LANGUAGE);
            microText.setCorrespondentFile(inputFile);

            // add corresponding segments (Teilsaetze)
            microText = addTextSegmentsToMicroText(microText, doc, inputFile);
            microText = addIsClaimerToMicroText(microText, doc);
            microText = addRelationsToMicroText(microText, doc);

            return microText;

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private MicroText addTextSegmentsToMicroText(MicroText microText, Document doc, File inputFile) {
        ArrayList<TextSegment> textSegments = new ArrayList<>();

        TextSegmentFactory textSegmentFactory = new TextSegmentFactory();

        NodeList nListE = doc.getElementsByTagName("edu");
        NodeList nListA = doc.getElementsByTagName("adu");
        for (int temp = 0; temp < nListE.getLength(); temp++) {
            Node nNodeE = nListE.item(temp);
            Node nNodeA = nListA.item(temp);

            TextSegment textSegment = textSegmentFactory.createTextSegment(nNodeA);

            textSegment.setFileId(microText.getFileId());
            textSegment.setTextSegmentId(nNodeA.getAttributes().getNamedItem("id").getTextContent());
            textSegment.setSentence(new Sentence(nNodeE.getTextContent()));
            textSegment.setLanguage(ConfigurationManager.SENTENCES_LANGUAGE);
            textSegment.setCorrespondentFile(inputFile);
            textSegment.setMicroText(microText);
            textSegment.setSegmentPositionIndex(microText.getTextSegments().size());

            textSegments.add(textSegment);
        }
        microText.setTextSegments(textSegments);

        return microText;
    }
    private MicroText addIsClaimerToMicroText(MicroText microText, Document doc) {
        NodeList nListEdge = doc.getElementsByTagName("edge");
        for (int temp = 0; temp < nListEdge.getLength(); temp++) {
            Node nNodeEdge = nListEdge.item(temp);

            //extract source node
            String src = nNodeEdge.getAttributes().getNamedItem("src").getNodeValue();
            if(src.startsWith("a"))
                microText.setIsClaimInTextSegment(src,false);
        }
        return microText;
    }

    private MicroText addRelationsToMicroText(MicroText microText, Document doc) {
        NodeList nListEdge = doc.getElementsByTagName("edge");
        RelationFactory relationFactory = new RelationFactory();

        for (int temp = 0; temp < nListEdge.getLength(); temp++) {
            Node nNodeEdge = nListEdge.item(temp);
            Relation relation = relationFactory.createRelation(nNodeEdge, microText);
            microText.addRelationToItsSourceSegment(relation);
        }
        microText.connectRelationsWithTargets();
        return microText;
    }
}
