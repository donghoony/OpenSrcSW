import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import java.io.IOException;

    public class Main {
        public static void main(String[] args) throws ParserConfigurationException, IOException, TransformerException, SAXException {
            WordAnalyzer.analyze();
        }
}
