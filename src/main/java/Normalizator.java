import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Normalizator {
    public static void main(String[] args) throws IOException {
        List<String> friends = getCurrentFriends();
        for(String id : friends){
            DeleteService.deleteServiceSumbols(id + ".txt");
            Normalizator.normalizeText(id + ".txt");
        }
    }

    public static void normalizeText (String fileName) throws IOException {

        LineNumberReader lnr = null;
        try {
            lnr = new LineNumberReader(new FileReader(new File(fileName)));
        } catch (FileNotFoundException e) {
            System.out.println(e);
            return;
        }
        lnr.skip(Long.MAX_VALUE);
        System.out.println(lnr.getLineNumber() + 1 + " summary rows");
        lnr.close();
        Path path = Paths.get(fileName);
        String content = new String(Files.readAllBytes(path));

        String[] words = content.split("\\s*(\\s|,|!|\\.)\\s*");
        for(int i = 0;i < words.length; i++){
            words[i] = words[i].toLowerCase();
        }

        doStemmingProcess(words,fileName);
        System.out.println("normalization done for " + fileName);
    }

    private static void doStemmingProcess(String[] words, String fileName) throws IOException {
        Pattern pattern = Pattern.compile("[а-яА-я]");
        List<String> russianBefore = new ArrayList<>();
        List<String> russianAfter = new ArrayList<>();
        List<String> englishBefore = new ArrayList<>();
        List<String> englishAfter = new ArrayList<>();
        String result = null;
        Matcher matcher;
        Stemmer enStemmer = new Stemmer();
        for(String string: words){
            matcher = pattern.matcher(string);
            if(matcher.find()){
                russianBefore.add(string);
                result = stemmer.porter.ru.StemmerPorterRU.stem(string);
                russianAfter.add(result);
            }
            else{
                englishBefore.add(string);
                result = enStemmer.stem(string);
                englishAfter.add(result);
            }
        }
        for(int i = 0; i < russianBefore.size();i++){
            System.out.println(russianBefore.get(i) + "   " + russianAfter.get(i));
        }
        for(int i = 0; i < englishBefore.size();i++){
            System.out.println(englishBefore.get(i) + "   " + englishAfter.get(i));
        }
        writeToEndFile(russianAfter, englishAfter, fileName);
    }

    private static void writeToEndFile(List<String> russianAfter, List<String> englishAfter, String fileName) {
        PrintWriter pw;
        try {
            pw = new PrintWriter(new File(fileName));
        } catch (FileNotFoundException e) {
            throw new IllegalStateException(e);
        }
        for(int i = 0; i < russianAfter.size();i++){
            pw.print(russianAfter.get(i)+ " ");
        }
        for(int i = 0; i < englishAfter.size();i++){
            pw.print(englishAfter.get(i) + " ");

        }
    }

    private static List<String> getCurrentFriends() {
        ArrayList<String> friends = new ArrayList<>();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("friends.txt"));
            String k = reader.readLine();
            while(k != null){
                friends.add(k);
                k = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            throw new IllegalStateException(e);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        return friends;
    }
}