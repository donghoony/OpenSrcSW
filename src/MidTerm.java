import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordList;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import java.io.IOException;

public class MidTerm {

    private final XMLParser xmlParser;
    private final WordAnalyzer wordAnalyzer;
    public MidTerm(XMLParser xmlParser, WordAnalyzer wordAnalyzer){
        this.wordAnalyzer = wordAnalyzer;
        this.xmlParser = xmlParser;
    }
    
    public void showSnippet(String path, String query) throws IOException, SAXException {
        Document document = xmlParser.loadDocument(path);
        NodeList bodyNodeList = document.getElementsByTagName("body");
        NodeList titleNodeList = document.getElementsByTagName("title");
        
        // 키워드리스트
        KeywordList keywordList = wordAnalyzer.analyzeStringToKeywords(query);

        // 해당 document의 score가 제일 높은 r-index 위치
        int[] maxMatchedDocumentIndex = new int[bodyNodeList.getLength()];

        // 해당 document의 ret_r 기준 count 개수
        int[] maxMatchedDocumentCount = new int[bodyNodeList.getLength()];

        // 초기화
        for(int i = 0; i < bodyNodeList.getLength(); i++){
            maxMatchedDocumentIndex[i] = -1;
            maxMatchedDocumentCount[i] = 0;
        }

        for(int i = 0; i < bodyNodeList.getLength(); i++){
            Node node = bodyNodeList.item(i);
            String bodyString = node.getTextContent();
            int[] curMatchedCount = getMatchingScore(bodyString, keywordList);
            // 각 body에 대해서 반복해주면 됨
            maxMatchedDocumentIndex[i] = curMatchedCount[0];
            maxMatchedDocumentCount[i] = curMatchedCount[1];
        }

        for(int i = 0; i < bodyNodeList.getLength(); i++){
            // 점수가 0이면 출력하지 않음
            if(maxMatchedDocumentCount[i] == 0) continue;

            System.out.print(titleNodeList.item(i).getTextContent() + ", ");
            System.out.print(bodyNodeList.item(i).getTextContent().substring(maxMatchedDocumentIndex[i]-30, maxMatchedDocumentIndex[i]) + ", ");
            System.out.println(maxMatchedDocumentCount[i]);
        }
    }

    public int[] getMatchingScore(String s, KeywordList keywords){
        // 30씩 substr 해가면서 {count가 많은 r-index, count} 리턴
        int cnt;
        int ret_r = 30, maxi = 0;
        for(int r = 30; r <= s.length(); r++){
            cnt = 0;
            String tmp = s.substring(r-30, r);

            for(Keyword key : keywords) {
                // Keyword는 String 클래스가 아님. getString으로 단어만 가져옴
                if (tmp.contains(key.getString())) cnt++;
            }

            if (cnt > maxi){
                ret_r = r; maxi = cnt;
            }
        }
        return new int[]{ret_r, maxi};
    }
}
