import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class MovieAPI {

    private String getID(){
        return "my8IkCrg9SQTG6hDwGNt";
    }
    private String getToken(){
        return "JY7m0L93Tt";
    }

    public String getMovieInformation(String query) throws IOException {
        String queryText = URLEncoder.encode(query, "UTF-8");
        final String apiURL = "https://openapi.naver.com/v1/search/movie.json?query=" + queryText;
        URL url = new URL(apiURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("X-Naver-Client-Id", this.getID());
        connection.setRequestProperty("X-Naver-Client-Secret", this.getToken());

        //
        BufferedReader br;
        int responseCode = connection.getResponseCode();
        if(responseCode == 200){
            br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        }else{
            br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
        }
        String line;
        StringBuffer res = new StringBuffer();
        while((line = br.readLine()) != null){
            res.append(line);
        }
        br.close();
        return res.toString();
    }
}
