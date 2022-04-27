import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.io.IOException;

public class HTMLCollector {
    private final XMLParser xmlParser;
    private final HTMLParser htmlParser;

    public HTMLCollector(XMLParser xmlParser, HTMLParser htmlParser){
        this.htmlParser = htmlParser;
        this.xmlParser = xmlParser;
    }

    public Document collect(String sourceDirectory) throws IOException{
        File directory = new File(sourceDirectory);
        File[] contents = directory.listFiles();

        Document document = xmlParser.loadDocument();
        Element docsElement = document.createElement("docs");
        document.appendChild(docsElement);

        int docId = 0;
        for(File html: contents){
            if (FileHelper.getExtension(html.getName()).equals(".html")) continue;
            appendElement(document, docId++, html);
        }
        return document;
    }

    private Document appendElement(Document document, int docId, File html) throws IOException {
        String documentTitle = htmlParser.getTitle(html);
        String documentBody = htmlParser.getBody(html);
        Element docsElement = (Element) document.getElementsByTagName("docs").item(0);

        Element d = appendChildElementWithAttribute(document, docsElement, "doc", "id", Integer.toString(docId));
        appendChildElementWithText(document, d, "title", documentTitle);
        appendChildElementWithText(document, d, "body", documentBody);

        return document;
    }

    private Element appendChildElementWithAttribute(Document document, Element parent, String tagName, String attributeName, String attributeValue){
        Element child = appendChildElement(document, parent, tagName);
        child.setAttribute(attributeName, attributeValue);
        return child;
    }

    private Element appendChildElementWithText(Document document, Element parent, String tagName, String text){
        Element child = appendChildElement(document, parent, tagName);
        child.appendChild(document.createTextNode(text));
        return child;
    }

    private Element appendChildElement(Document document, Element parent, String tagName){
        Element child = document.createElement(tagName);
        parent.appendChild(child);
        return child;
    }
}
