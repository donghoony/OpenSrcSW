import org.snu.ids.kkma.index.KeywordExtractor;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import java.io.IOException;

    public class Main {
        public static void main(String[] args) throws ParserConfigurationException, IOException, TransformerException, SAXException {

            HTMLParser htmlParser = new HTMLParser();
            FileHelper fileHelper = new FileHelper();
            XMLParser xmlParser = new XMLParser();
            KeywordExtractor ke = new KeywordExtractor();

            // collection.xml
            HTMLCollector htmlCollector = new HTMLCollector(fileHelper, xmlParser, htmlParser);
            Document collection = htmlCollector.collect("./resource");
            xmlParser.saveXMLAs(collection, "output/collection.xml");

            // index.xml
            WordAnalyzer wordAnalyzer = new WordAnalyzer(xmlParser, ke);
            Document document = wordAnalyzer.analyzeXML("output/collection.xml");
            xmlParser.saveXMLAs(document, "output/index.xml");
        }
}
