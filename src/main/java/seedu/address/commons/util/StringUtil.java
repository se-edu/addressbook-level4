package seedu.address.commons.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Helper functions for handling strings.
 */
public class StringUtil {
    public static final String MULTILINE_REGEX = "(?s).*\\R.*"; // (?s) so that . matches line terminators as well

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
            if (wordInSentence.equalsIgnoreCase(preppedWord)) return true;
        }
        return false;
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

    /**
     * Returns true if s is null, empty or contains only (Unicode) whitespace characters.
     */
    public static boolean isBlank(String s) {
        if (s == null) {
            return true;
        }

        boolean hasOnlyWhitespace = s.codePoints().parallel().allMatch(cp -> Character.isWhitespace(cp));
        return hasOnlyWhitespace;
    }

    /**
     * Removes all Unicode line terminators.
     * @param s cannot be null
     */
    public static String stripLineTerminators(String s) {
        assert s != null : "s parameter cannot be null";
        return s.replaceAll("\\R", "");
    }
}
