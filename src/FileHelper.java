import java.io.File;
import java.io.IOException;

public class FileHelper {
    public static String getExtension(String fileName){
        String extension = "";
        int i = fileName.lastIndexOf('.');
        if (i >= 0) {
            extension = fileName.substring(i+1);
        }
        return extension;
    }

    public static File getFile(String path) throws IOException {
        File f = new File(path);
        f.getParentFile().mkdirs();
        f.createNewFile();
        return f;
    }
}
