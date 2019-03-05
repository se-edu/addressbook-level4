package seedu.address.logic.parser;

/**
 * A keyword that specifies the type of sorting
 * E.g. 'age' in 'sort age'.
 */
public class SortWord {
    private final String sortWord;

    public SortWord(String sortWord) {
        this.sortWord = sortWord;
    }

    public String getSortWord() {
        return sortWord;
    }

    public String toString() {
        return getSortWord();
    }

    @Override
    public int hashCode() {
        return sortWord == null ? 0 : sortWord.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof SortWord)) {
            return false;
        }
        if (obj == this) {
            return true;
        }

        SortWord otherSortWord = (SortWord) obj;
        return otherSortWord.getSortWord().equals(getSortWord());
    }
}
