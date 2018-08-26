import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

public class FileReader {
    public static ArrayList<String> ReadWordsFromFile(String filename) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filename));
        ArrayList<String> allWords = new ArrayList<>();


        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] words = line.split("[^а-яА-ЯёЁa-zA-Z']");
            for (String word : words) {
                if (word.length() > 3)
                    allWords.add(word.toLowerCase());
            }
        }

        return allWords;
    }
}
