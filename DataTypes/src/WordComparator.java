import java.util.Comparator;

public class WordComparator implements Comparator<WordFrequency> {
    @Override
    public int compare(WordFrequency o1, WordFrequency o2) {
        if (o1.equals(o2)) return 0;
        if (o1.frequency == o2.frequency) return -o1.word.compareTo(o2.word);
        return o1.frequency > o2.frequency ? -1 : 1;
    }
}
