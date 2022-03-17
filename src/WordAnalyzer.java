import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class WordAnalyzer {

    private final XMLParser xmlParser;
    private final KeywordExtractor ke;

    public WordAnalyzer(XMLParser xmlParser, KeywordExtractor ke){
        this.xmlParser = xmlParser;
        this.ke = ke;
    }

    public String analyzeString(String str){
        StringBuilder data = new StringBuilder();
        KeywordList kl = ke.extractKeyword(str, true);
        for (Keyword keyword : kl) {
            data.append(keyword.getString()).append(":").append(keyword.getCnt()).append("#");
        }
        return data.toString();
    }

    public Document analyzeXML(String path) throws IOException, SAXException {

        Document document = xmlParser.loadDocument(path);
        NodeList nodeList = document.getElementsByTagName("body");

        for(int i = 0; i < nodeList.getLength(); i++){
            String analyzedStr = analyzeString(nodeList.item(i).getTextContent());
            document.getElementsByTagName("body").item(i).setTextContent(analyzedStr);
        }

        return document;
    }
}
