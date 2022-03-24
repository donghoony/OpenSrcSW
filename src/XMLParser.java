import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class XMLParser {
    private final Transformer transformer;
    private final DocumentBuilder documentBuilder;

    public XMLParser() throws TransformerConfigurationException, ParserConfigurationException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        transformer = transformerFactory.newTransformer();
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilder = documentBuilderFactory.newDocumentBuilder();
    }

    public void saveXMLAs(Document doc, String path) throws TransformerException, IOException {
        DOMSource src = new DOMSource(doc);

        File f = new File(path);
        f.getParentFile().mkdirs();
        f.createNewFile();
        StreamResult res = new StreamResult(new FileOutputStream(f));

        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        transformer.transform(src, res);
    }

    public Document loadDocument(){
        return documentBuilder.newDocument();
    }

    public Document loadDocument(String path) throws IOException, SAXException {
        return documentBuilder.parse(path);
    }
}
