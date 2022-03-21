import org.snu.ids.kkma.index.KeywordExtractor;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import java.io.IOException;
import java.util.Objects;

public class Main {
    public static void main(String[] args) throws ParserConfigurationException, IOException, TransformerException, SAXException {
        XMLParser xmlParser = new XMLParser();
        KeywordExtractor keywordExtractor = new KeywordExtractor();

        if (Objects.equals(args[0], "-c") && args.length == 3){
            HTMLParser htmlParser = new HTMLParser();
            FileHelper fileHelper = new FileHelper();
            HTMLCollector htmlCollector = new HTMLCollector(fileHelper, xmlParser, htmlParser);
            Document collection = htmlCollector.collect(args[1]);
            xmlParser.saveXMLAs(collection, args[2]);
        }

        else if (Objects.equals(args[0], "-k") && args.length == 3){
            WordAnalyzer wordAnalyzer = new WordAnalyzer(xmlParser, keywordExtractor);
            Document document = wordAnalyzer.buildAnalyzedXML(args[1]);
            xmlParser.saveXMLAs(document, args[2]);
        }
        else{
            System.err.println("Invalid arguments or options.");
        }
    }
}
