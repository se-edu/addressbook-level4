package seedu.address.logic.parser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//@@author yungyung04
/**
 * Provides utilities to recognize and translate natural language from user input into processable values
 */
public class NaturalLanguageIdentifier {
    public static final String NATURAL_NOW = "now";
    public static final String NATURAL_TODAY = "today";
    public static final String NATURAL_CURRENT = "current";
    public static final String NATURAL_LAST = "last";
    public static final String NATURAL_THIS = "this";
    public static final String NATURAL_NEXT = "next";
    public static final String NATURAL_MONTH = "month";
    public static final String NATURAL_LAST_MONTH = NATURAL_LAST + " " + NATURAL_MONTH;
    public static final String NATURAL_THIS_MONTH = NATURAL_THIS + " " + NATURAL_MONTH;
    public static final String NATURAL_NEXT_MONTH = NATURAL_NEXT + " " + NATURAL_MONTH;
    public static final String NATURAL_CURRENT_MONTH = NATURAL_CURRENT + " " + NATURAL_MONTH;

    private static List<String> twoWordedNaturalLanguages = new ArrayList<>(Arrays.asList(
            NATURAL_LAST_MONTH, NATURAL_THIS_MONTH, NATURAL_NEXT_MONTH, NATURAL_CURRENT_MONTH));

    private static NaturalLanguageIdentifier naturalLanguageIdentifier = null;
    private LocalDateTime currentDateTime = null;

    /**
     * Constructs a NaturalLanguageIdentifier object which stores the current date and time.
     */
    private NaturalLanguageIdentifier() {
        currentDateTime = LocalDateTime.now();
    }

    /**
     * Returns an instance of NaturalLanguageIdentifier object
     */
    public static NaturalLanguageIdentifier getInstance() {
        if (naturalLanguageIdentifier == null) {
            naturalLanguageIdentifier = new NaturalLanguageIdentifier();
        }
        return naturalLanguageIdentifier;
    }

    /**
     * Converts any keywords that are recognizable as month-related natural languages into their month representation.
     */
    public String[] convertNaturalLanguagesIntoMonths(String[] keywords) {
        String[] mergedKeywords = mergeTwoWordedNaturalLanguage(keywords);
        for (int i = 0; i < mergedKeywords.length; i++) {
            mergedKeywords[i] = getMonthAsString(mergedKeywords[i]);
        }
        return mergedKeywords;
    }

    /**
     * Converts natural language into its month representation if possible.
     */
    public String getMonthAsString(String userInput) {
        String result;
        switch (userInput) {
        case NATURAL_TODAY:
        case NATURAL_NOW:
        case NATURAL_CURRENT_MONTH:
        case NATURAL_THIS_MONTH:
            result = currentDateTime.getMonth().name();
            break;
        case NATURAL_LAST_MONTH:
            result = currentDateTime.minusMonths(1).getMonth().name();
            break;
        case NATURAL_NEXT_MONTH:
            result = currentDateTime.plusMonths(1).getMonth().name();
            break;
        default:
            result = userInput;
        }
        return result;
    }

    /**
     * Merges 2 adjoin strings if they can form a valid natural language.
     * Keywords are case-sensitive.
     */
    public static String[] mergeTwoWordedNaturalLanguage(String[] keywords) {
        if (keywords.length <= 1) {
            return keywords;
        }

        ArrayList<String> mergedKeywords = new ArrayList<>();
        for (int i = 0; i < keywords.length - 1; i++) {
            if (isMergeable(keywords[i], keywords[i + 1])) {
                mergedKeywords.add(keywords[i] + " " + keywords[i + 1]);
                i++;
            } else {
                mergedKeywords.add(keywords[i]);
            }
        }
        return mergedKeywords.toArray(new String[mergedKeywords.size()]);
    }

    /**
     * Checks whether 2 given words can form a valid natural language.
     */
    private static boolean isMergeable(String prefix, String suffix) {
        return twoWordedNaturalLanguages.contains(prefix + " " + suffix);
    }

}
