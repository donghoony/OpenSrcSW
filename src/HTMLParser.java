import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;

// HTMLParser.java
public class HTMLParser {
    public String getTitle(File f) throws IOException {
        Document htmlDoc = Jsoup.parse(f, "UTF-8");
        return htmlDoc.title();
    }

    public String getBody(File f) throws IOException{
        Document htmlDoc = Jsoup.parse(f, "UTF-8");
        return htmlDoc.body().text();
    }
}
