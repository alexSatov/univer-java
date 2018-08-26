import java.util.ArrayList;

public interface IWordDict {
    void buildDict(ArrayList<String> words);
    void printMostFrequentWords(String prefix, int maxCount);
}
