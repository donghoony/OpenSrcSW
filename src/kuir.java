import org.snu.ids.kkma.index.KeywordExtractor;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class kuir {

    public static void main(String[] args) throws ParserConfigurationException, IOException, TransformerException, SAXException, ClassNotFoundException {

        String command = args[0];
        String path = args[1];

        switch (command) {
            case "-c" -> {
                HTMLParser htmlParser = new HTMLParser();
                XMLParser xmlParser = new XMLParser();

                HTMLCollector htmlCollector = new HTMLCollector( xmlParser, htmlParser);
                Document collection = htmlCollector.collect(path);
                xmlParser.saveXMLAs(collection, "./collection.xml");
                System.out.println("Saved as ./collection.xml");
            }
            case "-k" -> {
                XMLParser xmlParser = new XMLParser();
                KeywordExtractor keywordExtractor = new KeywordExtractor();

                WordAnalyzer wordAnalyzer = new WordAnalyzer(xmlParser, keywordExtractor);
                Document document = wordAnalyzer.buildAnalyzedXML(path);
                xmlParser.saveXMLAs(document, "./index.xml");
                System.out.println("Saved as ./index.xml");
            }
            case "-i" -> {
                XMLParser xmlParser = new XMLParser();
                TFIDFHashMap tfidfHashMap = new TFIDFHashMap();

                Indexer indexer = new Indexer(xmlParser, tfidfHashMap);
                indexer.calculateTFIDF(path);
                indexer.saveAs("./index.post");
                System.out.println("Saved as ./index.post");
            }
            case "-s" -> {
                TFIDFHashMap tfidfHashMap = new TFIDFHashMap();
                KeywordExtractor keywordExtractor = new KeywordExtractor();
                XMLParser xmlParser = new XMLParser();
                WordAnalyzer wordAnalyzer = new WordAnalyzer(xmlParser, keywordExtractor);

                Searcher searcher = new Searcher(tfidfHashMap, wordAnalyzer);
                String option = args[2];
                String query = args[3];

                Document document = xmlParser.loadDocument("./index.xml");
                List<Map.Entry<Integer, Double>> similarity = searcher.CalcSim(path, query);
                if(similarity.isEmpty()) System.out.println("검색된 문서가 없습니다.");
                for(Map.Entry<Integer, Double> entry: similarity){
                    System.out.println(document.getElementsByTagName("title").item(entry.getKey()).getTextContent() + " " + String.format("%.5f",entry.getValue()));
                }
            }
            default -> System.err.println("Invalid arguments or options.");
        }
    }
}
