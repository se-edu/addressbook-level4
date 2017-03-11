package seedu.address.logic.parser;

/**
 * A prefix that marks the beginning of an argument in an arguments string.
 * E.g. '/t' in 'add James /t friend'.
 */
public class Prefix {
    final String prefix;

    Prefix(String prefix) {
        this.prefix = prefix;
    }

    String getPrefix() {
        return prefix;
    }

    @Override
    public int hashCode() {
        return prefix == null ? 0 : prefix.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Prefix)) {
            return false;
        }
        if (obj == this) {
            return true;
        }

        Prefix otherPrefix = (Prefix) obj;
        return otherPrefix.getPrefix().equals(getPrefix());
    }
}