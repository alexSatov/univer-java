import java.util.TreeSet;
import java.util.ArrayList;

public class TreeSetDict implements IWordDict {
    public TreeSet<WordFrequency> frequentWords;

    @Override
    public void buildDict(ArrayList<String> words) {
        frequentWords = new TreeSet<>(new WordComparator());

        for (String word : words){
            WordFrequency entry = find(word);
            if (entry != null){
                WordFrequency newEntry = new WordFrequency(entry.word, entry.frequency + 1);
                frequentWords.remove(entry);
                frequentWords.add(newEntry);
            }
            else
                frequentWords.add(new WordFrequency(word));
        }
    }

    private WordFrequency find(String word) {
        for (WordFrequency entry : frequentWords)
            if (entry.word.equals(word))
                return entry;
        return null;
    }

    @Override
    public void printMostFrequentWords(String prefix, int maxCount) {
        ArrayList<WordFrequency> mostFrequentWords = new ArrayList<>();
        for (WordFrequency entry : frequentWords) {
            if (entry.word.startsWith(prefix)) {
                mostFrequentWords.add(entry);
                //System.out.println(entry);
                maxCount--;
            }
            if (maxCount == 0) break;
        }
    }
}
