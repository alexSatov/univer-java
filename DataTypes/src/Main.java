import java.util.ArrayList;
import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        HashMapDict dict1 = new HashMapDict();
        ArrayListDict dict2 = new ArrayListDict();
        LinkedListDict dict3 = new LinkedListDict();
        TreeSetDict dict4 = new TreeSetDict();
        ArrayList<String> words = FileReader.ReadWordsFromFile(
                "C:/Users/aleks/Desktop/Java/DataTypes/texts/hungry_games.txt");

        measureBuildingTime(dict1, words);
        measureBuildingTime(dict2, words);
        measureBuildingTime(dict3, words);
        measureBuildingTime(dict4, words);

        measureSearchingTime(dict1, "p", 100);
        measureSearchingTime(dict2, "p", 100);
        measureSearchingTime(dict3, "p", 100);
        measureSearchingTime(dict4, "p", 100);
    }

    public static void measureBuildingTime(IWordDict dict, ArrayList<String> words){
        long startTime = System.currentTimeMillis();
        dict.buildDict(words);
        long endTime = System.currentTimeMillis();
        long deltaTime = endTime - startTime;

        double elapsedSeconds = deltaTime/1000.0;
        System.out.println(elapsedSeconds);
    }

    public static void measureSearchingTime(IWordDict dict, String prefix, int maxWordsCount){
        long startTime = System.nanoTime();
        dict.printMostFrequentWords(prefix, maxWordsCount);
        long endTime = System.nanoTime();
        long deltaTime = endTime - startTime;

        System.out.println(deltaTime);
    }
}
