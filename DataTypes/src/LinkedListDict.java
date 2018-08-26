import java.util.ArrayList;
import java.util.LinkedList;

public class LinkedListDict implements IWordDict{
    public LinkedList<WordFrequency> frequentWords;

    @Override
    public void buildDict(ArrayList<String> words) {
        frequentWords = new LinkedList<>();
        for (String word : words){
            WordFrequency entry = find(word);
            if (entry != null)
                entry.frequency++;
            else
                frequentWords.add(new WordFrequency(word));
        }

        frequentWords.sort(new WordComparator());
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
