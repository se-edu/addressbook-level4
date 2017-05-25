package seedu.address.commons.core.index;

/**
 * Represents a zero-based or one-based index.
 */
public class Index {
    private int zeroBasedIndex;

    /**
     * Index can only be created by calling {@link Index#createFromZeroBased(int)} or
     * {@link Index#createFromOneBased(int)}.
     */
    private Index(int zeroBasedIndex) {
        if (zeroBasedIndex < 0) {
            throw new IndexOutOfBoundsException();
        }

        this.zeroBasedIndex = zeroBasedIndex;
    }

    public int getZeroBased() {
        return zeroBasedIndex;
    }

    public int getOneBased() {
        return zeroBasedIndex + 1;
    }

    /**
     * Creates a new {@code Index} using a zero-based index.
     */
    public static Index createFromZeroBased(int zeroBasedIndex) {
        return new Index(zeroBasedIndex);
    }

    /**
     * Creates a new {@code Index} using a one-based index.
     */
    public static Index createFromOneBased(int oneBasedIndex) {
        return new Index(oneBasedIndex - 1);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Index // instanceof handles nulls
                && this.zeroBasedIndex == ((Index) other).zeroBasedIndex); // state check
    }
}
