import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Map.Entry;

public class Searcher {
    private final TFIDFHashMap tfidfHashMap;
    private final WordAnalyzer wordAnalyzer;

    public Searcher(TFIDFHashMap tfidfHashMap, WordAnalyzer wordAnalyzer){
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
        HashMap<Integer, Double> similarityHashMap = calculateCosineSimilarity(index, analyzedQuery, documentIdList);

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

    private HashMap<Integer, Double> calculateCosineSimilarity(HashMap<String, ArrayList<Double>> index, KeywordList analyzedQuery, ArrayList<Integer> documentIdList){
        HashMap<Integer, Double> similarity = new HashMap<>();
        HashMap<Integer, Double> innerProduct = calculateInnerProduct(index, analyzedQuery, documentIdList);

        for (int documentId: documentIdList){
            double query_square = 0d;
            double index_square = 0d;
            for(Keyword keyword : analyzedQuery){
                String word = keyword.getString();
                if(!index.containsKey(word)) continue;
                query_square += Math.pow(keyword.getCnt(), 2);
                index_square += Math.pow(index.get(keyword.getString()).get(documentId), 2);
            }
            if(index_square > 0 && query_square > 0)
                similarity.put(documentId, innerProduct.get(documentId) / (Math.sqrt(query_square) * Math.sqrt(index_square)));
        }
        return similarity;
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
