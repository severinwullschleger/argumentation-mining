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
import java.util.ArrayList;
import java.util.List;

public abstract class XMLParser {

    public static List<MicroText> walkXMLFiles(String DATASET_PATH) {
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

    private static MicroText parseXML(File inputFile) {
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

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
