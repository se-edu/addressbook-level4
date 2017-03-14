package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ArgumentTokenizerTest {

    private final Prefix unknownPrefix = new Prefix("--u");
    private final Prefix slashP = new Prefix("/p");
    private final Prefix dashT = new Prefix("-t");
    private final Prefix hatQ = new Prefix("^Q");

    @Test
    public void tokenize_emptyArgsString_noValues() {
        ArgumentTokenizer tokenizer = new ArgumentTokenizer(slashP);
        String argsString = "  ";
        ArgumentMap argumentMap = tokenizer.tokenize(argsString);

        assertPreambleEmpty(argumentMap);
        assertArgumentAbsent(argumentMap, slashP);
    }

    private void assertPreamblePresent(ArgumentMap argumentMap, String expectedPreamble) {
        assertEquals(expectedPreamble, argumentMap.getPreamble());
    }

    private void assertPreambleEmpty(ArgumentMap argumentMap) {
        assertTrue(argumentMap.getPreamble().isEmpty());
    }

    private void assertArgumentPresent(ArgumentMap argumentMap, Prefix prefix, String... expectedValues) {

        // Verify the last value is returned
        assertEquals(expectedValues[expectedValues.length - 1], argumentMap.getValue(prefix).get());

        // Verify the number of values returned is as expected
        assertEquals(expectedValues.length, argumentMap.getAllValues(prefix).size());

        // Verify all values returned are as expected and in order
        for (int i = 0; i < expectedValues.length; i++) {
            assertEquals(expectedValues[i], argumentMap.getAllValues(prefix).get(i));
        }
    }

    private void assertArgumentAbsent(ArgumentMap argumentMap, Prefix prefix) {
        assertFalse(argumentMap.getValue(prefix).isPresent());
    }

    @Test
    public void tokenize_noPrefixes_allTakenAsPreamble() {
        ArgumentTokenizer tokenizer = new ArgumentTokenizer();
        String argsString = "  some random string /t tag with leading and trailing spaces ";
        ArgumentMap argumentMap = tokenizer.tokenize(argsString);

        // Same string expected as preamble, but leading/trailing spaces should be trimmed
        assertPreamblePresent(argumentMap, argsString.trim());

    }

    @Test
    public void tokenize_oneArgument() {
        ArgumentTokenizer tokenizer = new ArgumentTokenizer(slashP);

        // Preamble present
        String argsString = "  Some preamble string /p Argument value ";
        ArgumentMap argumentMap = tokenizer.tokenize(argsString);
        assertPreamblePresent(argumentMap, "Some preamble string");
        assertArgumentPresent(argumentMap, slashP, "Argument value");

        // No preamble
        argsString = " /p   Argument value ";
        argumentMap = tokenizer.tokenize(argsString);
        assertPreambleEmpty(argumentMap);
        assertArgumentPresent(argumentMap, slashP, "Argument value");

    }

    @Test
    public void tokenize_multipleArguments() {
        ArgumentTokenizer tokenizer = new ArgumentTokenizer(slashP, dashT, hatQ);

        // Only two arguments are present
        String argsString = "SomePreambleString -t dashT-Value/pslashP value";
        ArgumentMap argumentMap = tokenizer.tokenize(argsString);
        assertPreamblePresent(argumentMap, "SomePreambleString");
        assertArgumentPresent(argumentMap, slashP, "slashP value");
        assertArgumentPresent(argumentMap, dashT, "dashT-Value");
        assertArgumentAbsent(argumentMap, hatQ);

        /* Also covers: Cases where the prefix doesn't have a space before/after it */

        // All three arguments are present, no spaces before the prefixes
        argsString = "Different Preamble String^Q 111-t dashT-Value/p slashP value";
        argumentMap = tokenizer.tokenize(argsString);
        assertPreamblePresent(argumentMap, "Different Preamble String");
        assertArgumentPresent(argumentMap, slashP, "slashP value");
        assertArgumentPresent(argumentMap, dashT, "dashT-Value");
        assertArgumentPresent(argumentMap, hatQ, "111");

        /* Also covers: Reusing of the tokenizer multiple times */

        // Reuse tokenizer on an empty string to ensure ArgumentMap is correctly reset
        // (i.e. no stale values from the previous tokenizing remain)
        argsString = "";
        argumentMap = tokenizer.tokenize(argsString);
        assertPreambleEmpty(argumentMap);
        assertArgumentAbsent(argumentMap, slashP);

        /** Also covers: testing for prefixes not specified as a prefix **/

        // Prefixes not previously given to the tokenizer should not return any values
        argsString = unknownPrefix.getPrefix() + "some value";
        argumentMap = tokenizer.tokenize(argsString);
        assertArgumentAbsent(argumentMap, unknownPrefix);
        assertPreamblePresent(argumentMap, argsString); // Unknown prefix is taken as part of preamble
    }

    @Test
    public void tokenize_multipleArgumentsWithRepeats() {
        ArgumentTokenizer tokenizer = new ArgumentTokenizer(slashP, dashT, hatQ);

        // Two arguments repeated, some have empty values
        String argsString = "SomePreambleString -t dashT-Value ^Q ^Q-t another dashT value /p slashP value -t";
        ArgumentMap argumentMap = tokenizer.tokenize(argsString);
        assertPreamblePresent(argumentMap, "SomePreambleString");
        assertArgumentPresent(argumentMap, slashP, "slashP value");
        assertArgumentPresent(argumentMap, dashT, "dashT-Value", "another dashT value", "");
        assertArgumentPresent(argumentMap, hatQ, "", "");
    }

    @Test
    public void equalsMethod() {
        Prefix aaa = new Prefix("aaa");

        assertEquals(aaa, aaa);
        assertEquals(aaa, new Prefix("aaa"));

        assertNotEquals(aaa, "aaa");
        assertNotEquals(aaa, new Prefix("aab"));
    }

}
