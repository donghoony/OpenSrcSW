import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class TFIDFHashMap {
    private final HashMap<String, ArrayList<Integer>> hashMap;
    private final HashMap<String, ArrayList<Double>> weightHashMap;
    private int size;

    public TFIDFHashMap(){
        this.hashMap = new HashMap<>();
        this.weightHashMap = new HashMap<>();
    }


    private void initialize(String s, int size){

        if (!hashMap.containsKey(s)){
            ArrayList<Integer> arr = new ArrayList<>();
            ArrayList<Double> darr = new ArrayList<>();
            for(int i = 0; i < size; i++){
                arr.add(0);
                darr.add(0d);
            }
            hashMap.put(s, arr);
            weightHashMap.put(s, darr);
        }
    }

    public int getTF(String word, int documentNumber){
        ArrayList<Integer> arr = hashMap.get(word);
        return arr.get(documentNumber);
    }

    public int getDF(String word){
        ArrayList<Integer> arr = hashMap.get(word);
        int ret = 0;
        for(int i = 0; i < size; i++){
            if (arr.get(i) > 0) ret++;
        }
        return ret;
    }

    public void setSize(int size){
        this.size = size;
    }

    public void insertTF(int documentNumber, String key, int freq){
        initialize(key, this.size);
        hashMap.get(key).set(documentNumber, hashMap.get(key).get(documentNumber)+freq);
    }

    public double getWeight(int documentNumber, String word){
        return weightHashMap.get(word).get(documentNumber);
    }

    private double calculateWeight(int documentNumber, String word){
        int tf = getTF(word, documentNumber);
        int df = getDF(word);
        return (double) tf * Math.log(size/(double)df);
    }

    public void assignAllWeight(){
        for(int i = 0; i < size; i++){
            for(String key: hashMap.keySet()){
                weightHashMap.get(key).set(i, calculateWeight(i, key));
            }
        }
    }

    public void dump(String path) throws IOException {
        File f = FileHelper.getFile(path);
        FileOutputStream fileOutputStream = new FileOutputStream(f);

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(weightHashMap);
        objectOutputStream.close();
    }

    public HashMap<String, ArrayList<Double>> readDumpedHashMap(String path) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(path);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

        Object obj = objectInputStream.readObject();
        objectInputStream.close();

        return (HashMap<String, ArrayList<Double>>) obj;
    }
}
