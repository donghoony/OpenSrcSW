import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main {

    public static String getExtension(String fileName){
        String extension = "";
        int i = fileName.lastIndexOf('.');
        if (i >= 0) {
            extension = fileName.substring(i+1);
        }
        return extension;
    }

    public static void main(String[] args) throws ParserConfigurationException, IOException, TransformerException {

        File directory = new File("./resource");
        File[] contents = directory.listFiles();
//        for(File f: contents) System.out.println(f);

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();

        Element docs = doc.createElement("docs");
        doc.appendChild(docs);
        int doc_id = 0;
        for(File f: contents){
            if (getExtension(f.getName()).equals(".html")) continue;

            String docTitle = HTMLParser.getTitle(contents[doc_id]);
            String docBody = HTMLParser.getBody(contents[doc_id]);

            // <doc id = $doc_id>
            Element d = doc.createElement("doc");
            docs.appendChild(d);
            d.setAttribute("id", Integer.toString(doc_id));

            // title
            Element title = doc.createElement("title");
            title.appendChild(doc.createTextNode(docTitle));
            d.appendChild(title);

            // body
            Element body = doc.createElement("body");
            body.appendChild(doc.createTextNode(docBody));
            d.appendChild(body);

            doc_id++;
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

        DOMSource src = new DOMSource(doc);
        StreamResult res = new StreamResult(new FileOutputStream(new File("output/book.xml")));
        transformer.transform(src, res);
    }
}
