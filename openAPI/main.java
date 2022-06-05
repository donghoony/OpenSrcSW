import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class main {
    public static void main(String[] args) throws IOException, ParseException {

        MovieAPI movieAPI = new MovieAPI();
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        System.out.printf("ENTER TITLE : ");
        String query = in.readLine().strip();
        String res = movieAPI.getMovieInformation(query);

        JSONParser parser = new JSONParser();
        JSONObject object = (JSONObject) parser.parse(res.toString());
        JSONArray array = (JSONArray) object.get("items");

        for(int i = 0; i < array.size(); i++){
            System.out.println("================= ITEM " + (i+1) + "=====================");
            JSONObject item = (JSONObject) array.get(i);
            System.out.println("TITLE : "+ item.get("title").toString().replace("<b>", "").replace("</b>", ""));
            System.out.println("SUBTITLE  : "+ item.get("subtitle").toString().replace("<b>", "").replace("</b>", ""));
            System.out.println("DIRECTOR : "+ item.get("director").toString().replace("|", ", "));
            System.out.println("ACTORS : "+ item.get("actor").toString().replace("|", ", "));
            System.out.println("RATE  : "+ item.get("userRating"));
        }
    }
}
