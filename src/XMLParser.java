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
    private final TransformerFactory transformerFactory;
    private final Transformer transformer;
    private final DocumentBuilderFactory documentBuilderFactory;
    private final DocumentBuilder documentBuilder;

    public XMLParser() throws TransformerConfigurationException, ParserConfigurationException {
        this.transformerFactory = TransformerFactory.newInstance();
        this.transformer = transformerFactory.newTransformer();
        this.documentBuilderFactory = DocumentBuilderFactory.newInstance();
        this.documentBuilder = documentBuilderFactory.newDocumentBuilder();
    }

    public void saveXMLAs(Document doc, String path) throws TransformerException, FileNotFoundException {
        DOMSource src = new DOMSource(doc);
        StreamResult res = new StreamResult(new FileOutputStream(new File(path)));
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.transform(src, res);
    }

    public Document loadDocument(){
        return documentBuilder.newDocument();
    }
    public Document loadDocument(String path) throws IOException, SAXException {
        return documentBuilder.parse(path);
    }
}
