package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper functions for handling strings.
 */
public class StringUtil {

    /**
     * Returns true if the {@code sentence} contains the {@code word}.
     *   Ignores case, but a full word match is required.
     *   <br>examples:<pre>
     *       containsWordIgnoreCase("ABc def", "abc") == true
     *       containsWordIgnoreCase("ABc def", "DEF") == true
     *       containsWordIgnoreCase("ABc def", "AB") == false //not a full word match
     *       </pre>
     * @param sentence cannot be null
     * @param word cannot be null, cannot be empty, must be a single word
     */
    public static boolean containsWordIgnoreCase(String sentence, String word) {
        assert word != null : "Word parameter cannot be null";
        assert sentence != null : "Sentence parameter cannot be null";

        String preppedWord = word.trim();
        assert !preppedWord.isEmpty() : "Word parameter cannot be empty";
        assert preppedWord.split("\\s+").length == 1 : "Word parameter should be a single word";

        String preppedSentence = sentence;
        String[] wordsInPreppedSentence = preppedSentence.split("\\s+");

        for (String wordInSentence: wordsInPreppedSentence) {
            if (wordInSentence.equalsIgnoreCase(preppedWord)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Joins each element except the last element in {@code strings} with a comma,
     * and joins the last element with "and". e.g If {@code strings} contain
     * {"Phone", "Name", "Email"}, the returned result is: "Phone, Name and Email".
     *
     * @throws IllegalArgumentException if {@code strings.size()} < 2 or any null values exist in it.
     */
    public static String joinStrings(List<String> strings) {
        requireNonNull(strings);
        if (strings.size() < 2 || CollectionUtil.isAnyNull(strings)) {
            throw new IllegalArgumentException();
        }

        ArrayList<String> copy = new ArrayList<>(strings);
        String lastString = copy.remove(copy.size() - 1);
        return String.join(", ", copy) + " and " + lastString;
    }

    /**
     * Returns a detailed message of the t, including the stack trace.
     */
    public static String getDetails(Throwable t) {
        assert t != null;
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return t.getMessage() + "\n" + sw.toString();
    }

    /**
     * Returns true if s represents an unsigned integer e.g. 1, 2, 3, ... <br>
     * Will return false if the string is:
     * null, empty string, "-1", "0", "+1", and " 2 " (untrimmed) "3 0" (contains whitespace).
     * @param s Should be trimmed.
     */
    public static boolean isUnsignedInteger(String s) {
        return s != null && s.matches("^0*[1-9]\\d*$");
    }
}
