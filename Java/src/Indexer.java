import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.*;
import java.util.HashMap;

public class Indexer {
    private final XMLParser xmlParser;
    private final TFIDFHashMap tfidfHashMap;

    public Indexer(XMLParser xmlParser, TFIDFHashMap hashmap){
        this.xmlParser = xmlParser;
        this.tfidfHashMap = hashmap;
    }

    // w[document][word] = weight of a word from document
    // tf[document][word]: term frequency -> same as index.xml
    // df[word] = calculate from tf
    // N = nodeList.getlength();

    public void calculateTFIDF(String path) throws IOException, SAXException {
        Document document = xmlParser.loadDocument(path);
        NodeList nodeList = document.getElementsByTagName("body");
        tfidfHashMap.setSize(nodeList.getLength());

        for(int i = 0; i < nodeList.getLength(); i++){
            String body = nodeList.item(i).getTextContent();
            calculateFrequency(i, body);
        }
        tfidfHashMap.assignAllWeight();
    }

    private void calculateFrequency(int documentNumber, String body){
        String word; int freq;
        String[] wordsWithFreq = body.split("#");
        for(String s: wordsWithFreq){
            if (s.equals("")) break;
            String[] temp = s.split(":");
            word = temp[0];
            freq = Integer.parseInt(temp[1]);
            tfidfHashMap.insertTF(documentNumber, word, freq);
        }
    }

    public void saveAs(String path) throws IOException {
        tfidfHashMap.dump(path);
    }
}
