import org.snu.ids.kkma.index.KeywordExtractor;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import java.io.IOException;
import java.util.Objects;

public class Main {
    public static void main(String[] args) throws ParserConfigurationException, IOException, TransformerException, SAXException, ClassNotFoundException {

        String command = args[0];
        String path = args[1];

        if (command.equals("-c")){
            XMLParser xmlParser = new XMLParser();
            HTMLParser htmlParser = new HTMLParser();
            FileHelper fileHelper = new FileHelper();

            HTMLCollector htmlCollector = new HTMLCollector(fileHelper, xmlParser, htmlParser);
            Document collection = htmlCollector.collect(path);
            xmlParser.saveXMLAs(collection, "./output/collection.xml");
            System.out.println("Saved as ./output/collection.xml");
        }

        else if (command.equals("-k")){
            XMLParser xmlParser = new XMLParser();
            KeywordExtractor keywordExtractor = new KeywordExtractor();

            WordAnalyzer wordAnalyzer = new WordAnalyzer(xmlParser, keywordExtractor);
            Document document = wordAnalyzer.buildAnalyzedXML(path);
            xmlParser.saveXMLAs(document, "./output/index.xml");
            System.out.println("Saved as ./output/index.xml");
        }

        else if (command.equals("-i")){
            XMLParser xmlParser = new XMLParser();
            TFIDFHashMap tfidfHashMap = new TFIDFHashMap();

            Indexer indexer = new Indexer(xmlParser, tfidfHashMap);
            indexer.calculateTFIDF(path);
            indexer.saveAs("./output/index.post");
            indexer.readDumpedHashMap("./output/index.post");
            System.out.println("Saved as ./output/index.post");
        }

        else{
            System.err.println("Invalid arguments or options.");
        }
    }
}
