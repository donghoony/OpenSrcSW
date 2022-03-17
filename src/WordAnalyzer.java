import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;

public class WordAnalyzer {

    private final XMLParser xmlParser;
    private final KeywordExtractor keywordExtractor;

    public WordAnalyzer(XMLParser xmlParser, KeywordExtractor keywordExtractor){
        this.xmlParser = xmlParser;
        this.keywordExtractor = keywordExtractor;
    }

    public Document buildAnalyzedXML(String path) throws IOException, SAXException {
        Document document = xmlParser.loadDocument(path);
        NodeList nodeList = document.getElementsByTagName("body");

        ArrayList<String> analyzedStrList = analyzeXML(nodeList);
        for(int i = 0; i < nodeList.getLength(); i++){
            nodeList.item(i).setTextContent(analyzedStrList.get(i));
        }
        return document;
    }

    private ArrayList<String> analyzeXML(NodeList XMLList) {
        ArrayList<String> analyzedStrList = new ArrayList<>();
        for(int i = 0; i < XMLList.getLength(); i++){
            String targetStr = analyzeString(XMLList.item(i).getTextContent());
            analyzedStrList.add(analyzeString(targetStr));
        }
        return analyzedStrList;
    }

    private String analyzeString(String str){
        StringBuilder data = new StringBuilder();
        KeywordList keywords = keywordExtractor.extractKeyword(str, true);
        for (Keyword keyword : keywords) {
            data.append(keyword.getString()).append(":").append(keyword.getCnt()).append("#");
        }
        return data.toString();
    }

}
