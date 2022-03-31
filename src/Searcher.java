import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordList;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;


import java.util.*;
import java.io.IOException;
import java.util.Map.Entry;

public class Searcher {

    // get Weight TF from query, inner product with weight
    //
    private final TFIDFHashMap tfidfHashMap;
    private final WordAnalyzer wordAnalyzer;

    public Searcher(TFIDFHashMap tfidfHashMap, WordAnalyzer wordAnalyzer, XMLParser xmlParser){
        this.tfidfHashMap = tfidfHashMap;
        this.wordAnalyzer = wordAnalyzer;
    }

    // 1. query 분석
    // 2. 각 "문서"에 대한 거야...
    public List<Entry<Integer, Double>> CalcSim(String path, String query) throws IOException, ClassNotFoundException, SAXException {
        // returns documentId by sorted similarity
        HashMap<String, ArrayList<Double>> index = tfidfHashMap.readDumpedHashMap(path);
        KeywordList analyzedQuery = wordAnalyzer.analyzeStringToKeywords(query);

        ArrayList<Integer> documentIdList = getDocumentIndices(index, analyzedQuery);
        HashMap<Integer, Double> similarityHashMap = calculateInnerProduct(index, analyzedQuery, documentIdList);

        return getTopSimilarityDocumentIdList(similarityHashMap, 3);
    }

    public List<Entry<Integer, Double>> getTopSimilarityDocumentIdList(HashMap<Integer, Double> similarityHashMap, int documentCount){
        List<Entry<Integer, Double>> sortedSimilarity = sort(similarityHashMap);
        List<Entry<Integer, Double>> ret = new ArrayList<>();
        for(int i = 0; i < Math.min(documentCount, sortedSimilarity.size()); i++){
            ret.add(sortedSimilarity.get(i));
        }
        return ret;
    }


    private List<Entry<Integer, Double>> sort(HashMap<Integer, Double> similarityHashMap){
        List<Entry<Integer, Double>> entryList = new ArrayList<>(similarityHashMap.entrySet());
        entryList.sort(Entry.comparingByKey());
        entryList.sort(Entry.<Integer, Double>comparingByValue().reversed());
        return entryList;
    }


    private HashMap<Integer, Double> calculateInnerProduct(HashMap<String, ArrayList<Double>> index, KeywordList analyzedQuery, ArrayList<Integer> documentIdList){
        // <documentId, similarity>
        HashMap<Integer, Double> similarity = new HashMap<>();
        for(int documentId : documentIdList){
            double sim = 0d;
            for(Keyword keyword : analyzedQuery){
                String word = keyword.getString();
                if (!index.containsKey(word)) continue;
                sim += keyword.getCnt() * index.get(keyword.getString()).get(documentId);
            }
            similarity.put(documentId, sim);
        }
        return similarity;
    }


    public ArrayList<Integer> getDocumentIndices(HashMap<String, ArrayList<Double>> index, KeywordList analyzedQuery) {
        ArrayList<Boolean> containKeywordList = new ArrayList<>();
        ArrayList<Integer> containDocumentIdList = new ArrayList<>();

        for(Keyword keyword : analyzedQuery){
            String word = keyword.getString();
            if(!index.containsKey(word)) continue;
            ArrayList<Double> weightArray = index.get(word);
            for(int documentId = 0; documentId < weightArray.size(); documentId++){
                if (containKeywordList.size() < documentId+1) containKeywordList.add(Boolean.FALSE);
                if (weightArray.get(documentId) > 0) containKeywordList.set(documentId, Boolean.TRUE);
            }
        }

        for(int i = 0; i < containKeywordList.size(); i++){
            if (containKeywordList.get(i)) containDocumentIdList.add(i);
        }

        return containDocumentIdList;
    }
}
