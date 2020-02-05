import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeleteService {
    public static void deleteServiceSumbols(String fileName) throws IOException {
        LineNumberReader lnr = null;
        try {
            lnr = new LineNumberReader(new FileReader(new File(fileName)));
        } catch (FileNotFoundException e) {
            System.out.println("No file for this user " + fileName);
            return;
        }
        lnr.skip(Long.MAX_VALUE);
        System.out.println(lnr.getLineNumber() + 1 + " summary rows");
        lnr.close();

        Path path = Paths.get(fileName);

        String content = new String(Files.readAllBytes(path));
        content = content.replaceAll("[^\\p{L}\\p{Z}]", " ");

        Files.write(Paths.get(fileName), content.getBytes());
        System.out.println("DeleteService ended with "  + fileName);
    }
}
