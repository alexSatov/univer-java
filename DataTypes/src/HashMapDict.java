import java.util.HashMap;
import java.util.ArrayList;

public class HashMapDict implements IWordDict {
    public HashMap<String, Integer> frequentWords;

    @Override
    public void buildDict(ArrayList<String> words) {
        frequentWords = new HashMap<>();
        for (String word : words){
            if (frequentWords.containsKey(word))
                frequentWords.put(word, frequentWords.get(word) + 1);
            else
                frequentWords.put(word, 1);
        }
    }

    @Override
    public void printMostFrequentWords(String prefix, int maxCount) {
        ArrayList<WordFrequency> mostFrequentWords = new ArrayList<>();
        ArrayList<WordFrequency> result = new ArrayList<>();
        for (String word : frequentWords.keySet()) {
            if (word.startsWith(prefix)) {
                mostFrequentWords.add(new WordFrequency(word, frequentWords.get(word)));
            }
        }

        mostFrequentWords.sort(new WordComparator());

        for (WordFrequency entry : mostFrequentWords) {
            if (entry.word.startsWith(prefix)) {
                result.add(entry);
                //System.out.println(entry);
                maxCount--;
            }
            if (maxCount == 0) break;
        }
    }
}
