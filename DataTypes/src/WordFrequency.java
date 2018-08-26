public class WordFrequency {
    public String word;
    public int frequency;

    public WordFrequency(String word) {
        this.word = word;
        frequency = 1;
    }

    public WordFrequency(String word, Integer frequency) {
        this.word = word;
        this.frequency = frequency;
    }

    @Override
    public String toString() {
        return word + " " + frequency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WordFrequency that = (WordFrequency) o;

        if (frequency != that.frequency) return false;
        return word != null ? word.equals(that.word) : that.word == null;
    }

    @Override
    public int hashCode() {
        int result = word != null ? word.hashCode() : 0;
        result = 31 * result + frequency;
        return result;
    }
}
