import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.io.IOException;

public class HTMLCollector {
    private final XMLParser xmlParser;
    private final FileHelper fileHelper;
    private final HTMLParser htmlParser;

    public HTMLCollector(FileHelper fileHelper, XMLParser xmlParser, HTMLParser htmlParser){
        this.fileHelper = fileHelper;
        this.htmlParser = htmlParser;
        this.xmlParser = xmlParser;
    }

    public Document collect(String sourceDirectory) throws IOException{

        File directory = new File(sourceDirectory);
        File[] contents = directory.listFiles();

        Document doc = xmlParser.loadDocument();
        Element docs = doc.createElement("docs");
        doc.appendChild(docs);

        int doc_id = 0;
        for(File f: contents){
            if (fileHelper.getExtension(f.getName()).equals(".html")) continue;

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
        return doc;
    }
}
