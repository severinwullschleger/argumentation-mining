package Reader;

import ConfigurationManager.ConfigurationManager;
import Main.Corpus;
import Main.Enums.ArgumentType;
import Main.Enums.EnumsManager;
import Main.Enums.Stance;
import Main.SegmentLabels.role.Opponent;
import Main.SegmentLabels.role.Proponent;
import Main.SegmentLabels.role.UndefinedSentence;
import Main.TextSentence;
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

    public static List<Corpus> walkXMLFiles(String DATASET_PATH) {
        List<Corpus> corpuses = new ArrayList<>();
        File file = new File(DATASET_PATH);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    if (f.exists() && f.getName().contains(".xml")) {
                        corpuses.add(parseXML(f));
                    }
                }
            }
        }
        return corpuses;
    }

    private static Corpus parseXML(File inputFile) {
        Corpus corpus = new Corpus();
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            corpus.setFileId(doc.getDocumentElement().getAttribute("id"));
            corpus.setTopicId(doc.getDocumentElement().getAttribute("topic_id"));
            String stanceAttribute = doc.getDocumentElement().getAttribute("stance");
            corpus.setStance(convertToStanceEnum(stanceAttribute));
            corpus.setLanguage(ConfigurationManager.SENTENCES_LANGUAGE);
            corpus.setCorrespondentFile(inputFile);

            ArrayList<TextSentence> textSentences = new ArrayList<>();

            NodeList nListE = doc.getElementsByTagName("edu");
            NodeList nListA = doc.getElementsByTagName("adu");
            for (int temp = 0; temp < nListE.getLength(); temp++) {
                Node nNodeE = nListE.item(temp);

                Node nNodeA = nListA.item(temp);
                String argumentTypeAttribute = nNodeA.getAttributes().getNamedItem("type").getTextContent();
                ArgumentType sentenceType = convertToArgumentTypeEnum(argumentTypeAttribute);
                TextSentence textSentence;

                switch (sentenceType) {
                    case OPP:
                        textSentence = new Opponent();
                        break;
                    case PRO:
                        textSentence = new Proponent();
                        break;
                    default:
                        textSentence = new UndefinedSentence();
                }

                textSentence.setArgumentType(sentenceType);
                textSentence.setFileId(corpus.getFileId());
                textSentence.setSentenceId(nNodeE.getAttributes().getNamedItem("id").getTextContent());
                textSentence.setSentence(new Sentence(nNodeE.getTextContent()));
                textSentence.setLanguage(ConfigurationManager.SENTENCES_LANGUAGE);
                textSentence.setCorrespondentFile(inputFile);


                textSentences.add(textSentence);
            }
            corpus.setSentences(textSentences);

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        return corpus;
    }

    private static Stance convertToStanceEnum(String attribute) {
        List<String> stancesStrings = EnumsManager.getStancesToString();
        if (!stancesStrings.contains(attribute.toUpperCase())) {
            return Stance.UNDEFINED;
        }
        return Stance.valueOf(attribute.toUpperCase());
    }

    private static ArgumentType convertToArgumentTypeEnum(String attribute) {
        List<String> typesStrings = EnumsManager.getArgumentTypesToString();
        if (!typesStrings.contains(attribute.toUpperCase())) {
            return ArgumentType.UNDEFINED;
        }
        return ArgumentType.valueOf(attribute.toUpperCase());
    }
}
