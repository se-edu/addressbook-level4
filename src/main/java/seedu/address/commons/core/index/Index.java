package seedu.address.commons.core.index;

/**
 * Allows the index to be zero-based or one-based.
 */
public class Index {
    private int zeroBasedIndex;

    private Index(int zeroBasedIndex) {
        this.zeroBasedIndex = zeroBasedIndex;
    }

    /**
     * Returns the index that starts from 0.
     */
    public int getZeroBasedIndex() {
        return this.zeroBasedIndex;
    }

    /**
     * Returns the index that starts from 1.
     */
    public int getOneBasedIndex() {
        return this.zeroBasedIndex + 1;
    }

    /**
     * Creates a new {@code Index} if the parameter passed in is zero-based.
     */
    public static Index createFromZeroBased(int zeroBased) {
        return new Index(zeroBased);
    }

    /**
     * Creates a new {@code Index} if the parameter passed in is one-based.
     */
    public static Index createFromOneBased(int oneBased) {
        return new Index(oneBased - 1);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Index // instanceof handles nulls
                && this.zeroBasedIndex == ((Index) other).zeroBasedIndex); // state check
    }
}
