import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;

public class WordAnalyzer {

    public static void analyze() throws ParserConfigurationException, IOException, SAXException, TransformerException {

        File file = new File("output/collection.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(file);

        KeywordExtractor ke = new KeywordExtractor();

        NodeList nodeList = document.getElementsByTagName("doc");
        String[] analyzedList = new String[nodeList.getLength()];

        for(int i = 0; i < nodeList.getLength(); i++){
            analyzedList[i] = nodeList.item(i).getChildNodes().item(1).getTextContent();
            KeywordList kl = ke.extractKeyword(analyzedList[i], true);
            StringBuilder data = new StringBuilder();
            for(int k = 0; k < kl.size(); k++){
                Keyword keyword = kl.get(k);
                data.append(keyword.getString()).append(":").append(keyword.getCnt()).append("#");
            }
            document.getElementsByTagName("doc").item(i).getChildNodes().item(1).setTextContent(data.toString());
        }
        XMLParser.saveXmlAs(document, "output/index.xml");
    }
}
