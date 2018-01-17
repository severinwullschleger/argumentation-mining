package InputOutput;

import ConfigurationManager.ConfigurationManager;
import Main.MicroText;
import Main.Model.role.Opponent;
import Main.Relation;
import Main.RelationFactory;
import Main.TextSegment;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

/**
 * Created by LuckyP on 12.01.18.
 */
public class XMLWriter {

    private static XMLWriter instance = null;

    public static XMLWriter getInstance() {
        if (instance == null) {
            instance = new XMLWriter();
        }
        return instance;
    }

    public void writeXMLFile(MicroText microText) {
        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // root elements
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("arggraph");

            Attr id = doc.createAttribute("id");
            id.setValue(microText.getFileId());
            Attr stance = doc.createAttribute("stance");
            stance.setValue(microText.getStance().toString().toLowerCase());
            Attr topicId = doc.createAttribute("topic_id");
            topicId.setValue(microText.getTopicId());


            rootElement.setAttributeNode(id);
            rootElement.setAttributeNode(stance);
            rootElement.setAttributeNode(topicId);
            doc.appendChild(rootElement);


            // EDU
            for (TextSegment textSegment : microText.getTextSegments()) {
                Element edu = doc.createElement("edu");
                rootElement.appendChild(edu);

                Node cdata = doc.createCDATASection(textSegment.getSentence().text());
                edu.appendChild(cdata);

                Attr attr = doc.createAttribute("id");
                attr.setValue(textSegment.getSegmentId());
                edu.setAttributeNode(attr);
            }

            // ADU
            for (TextSegment textSegment : microText.getTextSegments()) {
                Element adu = doc.createElement("adu");
                rootElement.appendChild(adu);

                Attr attr = doc.createAttribute("id");
                attr.setValue(textSegment.getSegmentId());
                Attr type = doc.createAttribute("type");
                String typeStr = textSegment instanceof Opponent ? "opp" : "pro";
                type.setValue(typeStr);

                adu.setAttributeNode(attr);
                adu.setAttributeNode(type);
            }

            // EDGE
            for (Relation relation : microText.getRelations()) {
                RelationFactory relationFactory = new RelationFactory();
                Element adu = doc.createElement("edge");
                rootElement.appendChild(adu);

                Attr src = doc.createAttribute("src");
                Attr relId = doc.createAttribute("id");
                Attr type = doc.createAttribute("type");
                Attr trg = doc.createAttribute("trg");


                src.setValue(relation.getSourceId());
                relId.setValue(relation.getRelationId());
                type.setValue(relationFactory.getRelationString(relation));
                trg.setValue(relation.getTargetId());


                adu.setAttributeNode(src);
                adu.setAttributeNode(relId);
                adu.setAttributeNode(type);
                adu.setAttributeNode(trg);
            }


            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
//            StreamResult result = new StreamResult(new File(FILE_NAME));

            FileWriter.makeSureDirectoryExists(ConfigurationManager.getInstance().getOutputXMLDir());
            final String FILE_NAME = ConfigurationManager.getInstance().getOutputXMLDir() + "/" + microText.getFileId() + ".xml";
            File file = new File(FILE_NAME);

            // Output to console for testing
            StreamResult result = new StreamResult(file);

            transformer.transform(source, result);

            System.out.println(FILE_NAME + " has been saved!");

        } catch (ParserConfigurationException | TransformerException pce) {
            pce.printStackTrace();
        }

    }
}
