package InputOutput;

import ConfigurationManager.ConfigurationManager;
import Main.MicroText;
import Main.Enums.ArgumentType;
import Main.Enums.EnumsManager;
import Main.Model.role.Opponent;
import Main.Model.role.Proponent;
import Main.Model.role.UndefinedSentence;
import Main.TextSegment;
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
import java.text.ParseException;
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

            return microText;

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private MicroText addTextSegmentsToMicroText(MicroText microText, Document doc, File inputFile) {
        ArrayList<TextSegment> textSegments = new ArrayList<>();

        NodeList nListE = doc.getElementsByTagName("edu");
        NodeList nListA = doc.getElementsByTagName("adu");
        for (int temp = 0; temp < nListE.getLength(); temp++) {
            Node nNodeE = nListE.item(temp);
            Node nNodeA = nListA.item(temp);

            String argumentTypeAttribute = nNodeA.getAttributes().getNamedItem("type").getTextContent();
            ArgumentType sentenceType = EnumsManager.convertToRoleEnum(argumentTypeAttribute);

            TextSegment textSegment;
            switch (sentenceType) {
                case OPP:
                    textSegment = new Opponent();
                    break;
                case PRO:
                    textSegment = new Proponent();
                    break;
                default:
                    textSegment = new UndefinedSentence();
            }

            textSegment.setFileId(microText.getFileId());
            textSegment.setSentenceId(nNodeE.getAttributes().getNamedItem("id").getTextContent());
            textSegment.setSentence(new Sentence(nNodeE.getTextContent()));
            textSegment.setLanguage(ConfigurationManager.SENTENCES_LANGUAGE);
            textSegment.setCorrespondentFile(inputFile);
            textSegment.setMicroText(microText);
            textSegment.setSentenceIndex(microText.getTextSegments().size());
            microText.getTextSegments().add(textSegment);

            textSegments.add(textSegment);
        }
        microText.setSentences(textSegments);

        return microText;
    }
    private MicroText addIsClaimerToMicroText(MicroText microText, Document doc) {
        NodeList nListEdge = doc.getElementsByTagName("edge");
        for (int temp = 0; temp < nListEdge.getLength(); temp++) {
            Node nNodeEdge = nListEdge.item(temp);

            //extract source node
            String src = nNodeEdge.getAttributes().getNamedItem("src").getNodeValue();
            if(src.startsWith("a")) {
                try {
                    int sourcePosition = Integer.parseInt(src.substring(1));
                    microText.getTextSegments().get(sourcePosition-1).setClaim(false);
                } catch (Error e) {
                    System.err.println("XMLParser, String to Integer Parse Problem: " + e);
                }
            }


        }
        return microText;
    }
}
