package Reader;

import ConfigurationManager.ConfigurationManager;
import Main.Corpus;
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

    public static List<Corpus> walkXMLFiles (String DATASET_PATH) {
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
            corpus.setStance(doc.getDocumentElement().getAttribute("stance"));
            corpus.setLanguage(ConfigurationManager.SENTENCES_LANGUAGE);
            corpus.setCorrespondentFile(inputFile);

            ArrayList<TextSentence> textSentences = new ArrayList<>();

            NodeList nListE = doc.getElementsByTagName("edu");
            NodeList nListA = doc.getElementsByTagName("adu");
            for (int temp = 0; temp < nListE.getLength(); temp++) {
                Node nNodeE = nListE.item(temp);
                TextSentence sentence = new TextSentence();
                sentence.setFileId(corpus.getFileId());
                sentence.setSentenceId(nNodeE.getAttributes().getNamedItem("id").getTextContent());
                sentence.setSentence(new Sentence(nNodeE.getTextContent()));
                sentence.setLanguage(ConfigurationManager.SENTENCES_LANGUAGE);
                sentence.setCorrespondentFile(inputFile);

                Node nNodeA = nListA.item(temp);
                sentence.setArgumentType(nNodeA.getAttributes().getNamedItem("type").getTextContent());

                textSentences.add(sentence);
            }
            corpus.setSentences(textSentences);

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        return corpus;
    }
}
