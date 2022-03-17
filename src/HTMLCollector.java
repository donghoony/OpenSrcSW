import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import java.io.File;
import java.io.IOException;

public class HTMLCollector {

    public int collect() throws TransformerException, IOException, ParserConfigurationException {
        File directory = new File("./resource");
        File[] contents = directory.listFiles();
        //        for(File f: contents) System.out.println(f);

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();
        FileHelper filehelper = new FileHelper();
        HTMLParser htmlParser = new HTMLParser();

        Element docs = doc.createElement("docs");
        doc.appendChild(docs);
        int doc_id = 0;
        for(File f: contents){
            if (filehelper.getExtension(f.getName()).equals(".html")) continue;

            String docTitle = htmlParser.getTitle(contents[doc_id]);
            String docBody = htmlParser.getBody(contents[doc_id]);

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

        XMLParser.saveXmlAs(doc, "output/collection.xml");
        return doc_id;
    }
}
