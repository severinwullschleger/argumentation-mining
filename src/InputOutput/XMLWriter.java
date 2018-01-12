package InputOutput;

import Main.MicroText;
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

            rootElement.setAttributeNode(id);
            rootElement.setAttributeNode(stance);
            doc.appendChild(rootElement);


            for (TextSegment textSegment : microText.getTextSegments()) {
                Element edu = doc.createElement("edu");
                rootElement.appendChild(edu);

                Node cdata = doc.createCDATASection( textSegment.getSentence().text());
                edu.appendChild(cdata);

                // set attribute to staff element
                Attr attr = doc.createAttribute("id");
                attr.setValue(textSegment.getEdgeId());
                edu.setAttributeNode(attr);
            }

            for (TextSegment textSegment : microText.getTextSegments()) {
                Element adu = doc.createElement("adu");
                rootElement.appendChild(adu);


                // set attribute to staff element
                Attr attr = doc.createAttribute("id");
                attr.setValue(textSegment.getSegmentId());
                adu.setAttributeNode(attr);
            }


            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
//            StreamResult result = new StreamResult(new File(FILE_NAME));


            File file = new File(microText.getFileId() + ".xml");

            // Output to console for testing
            StreamResult result = new StreamResult(file);

            transformer.transform(source, result);

            System.out.println("File saved!");

        } catch (ParserConfigurationException | TransformerException pce) {
            pce.printStackTrace();
        }

    }
}
