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
        ArgumentMap argMap = tokenizer.tokenize(argsString);

        assertPreambleEmpty(argMap);
        assertArgumentAbsent(argMap, slashP);
    }

    private void assertPreamblePresent(ArgumentMap argMap, String expectedPreamble) {
        assertEquals(expectedPreamble, argMap.getPreamble());
    }

    private void assertPreambleEmpty(ArgumentMap argMap) {
        assertTrue(argMap.getPreamble().isEmpty());
    }

    private void assertArgumentPresent(ArgumentMap argMap, Prefix prefix, String... expectedValues) {

        // Verify the last value is returned
        assertEquals(expectedValues[expectedValues.length - 1], argMap.getValue(prefix).get());

        // Verify the number of values returned is as expected
        assertEquals(expectedValues.length, argMap.getAllValues(prefix).size());

        // Verify all values returned are as expected and in order
        for (int i = 0; i < expectedValues.length; i++) {
            assertEquals(expectedValues[i], argMap.getAllValues(prefix).get(i));
        }
    }

    private void assertArgumentAbsent(ArgumentMap argMap, Prefix prefix) {
        assertFalse(argMap.getValue(prefix).isPresent());
    }

    @Test
    public void tokenize_noPrefixes_allTakenAsPreamble() {
        ArgumentTokenizer tokenizer = new ArgumentTokenizer();
        String argsString = "  some random string /t tag with leading and trailing spaces ";
        ArgumentMap argMap = tokenizer.tokenize(argsString);

        // Same string expected as preamble, but leading/trailing spaces should be trimmed
        assertPreamblePresent(argMap, argsString.trim());

    }

    @Test
    public void tokenize_oneArgument() {
        ArgumentTokenizer tokenizer = new ArgumentTokenizer(slashP);

        // Preamble present
        String argsString = "  Some preamble string /p Argument value ";
        ArgumentMap argMap = tokenizer.tokenize(argsString);
        assertPreamblePresent(argMap, "Some preamble string");
        assertArgumentPresent(argMap, slashP, "Argument value");

        // No preamble
        argsString = " /p   Argument value ";
        argMap = tokenizer.tokenize(argsString);
        assertPreambleEmpty(argMap);
        assertArgumentPresent(argMap, slashP, "Argument value");

    }

    @Test
    public void tokenize_multipleArguments() {
        ArgumentTokenizer tokenizer = new ArgumentTokenizer(slashP, dashT, hatQ);

        // Only two arguments are present
        String argsString = "SomePreambleString -t dashT-Value/pslashP value";
        ArgumentMap argMap = tokenizer.tokenize(argsString);
        assertPreamblePresent(argMap, "SomePreambleString");
        assertArgumentPresent(argMap, slashP, "slashP value");
        assertArgumentPresent(argMap, dashT, "dashT-Value");
        assertArgumentAbsent(argMap, hatQ);

        /* Also covers: Cases where the prefix doesn't have a space before/after it */

        // All three arguments are present, no spaces before the prefixes
        argsString = "Different Preamble String^Q 111-t dashT-Value/p slashP value";
        argMap = tokenizer.tokenize(argsString);
        assertPreamblePresent(argMap, "Different Preamble String");
        assertArgumentPresent(argMap, slashP, "slashP value");
        assertArgumentPresent(argMap, dashT, "dashT-Value");
        assertArgumentPresent(argMap, hatQ, "111");

        /* Also covers: Reusing of the tokenizer multiple times */

        // Reuse tokenizer on an empty string to ensure ArgumentMap is correctly reset
        // (i.e. no stale values from the previous tokenizing remain)
        argsString = "";
        argMap = tokenizer.tokenize(argsString);
        assertPreambleEmpty(argMap);
        assertArgumentAbsent(argMap, slashP);

        /** Also covers: testing for prefixes not specified as a prefix **/

        // Prefixes not previously given to the tokenizer should not return any values
        argsString = unknownPrefix.getPrefix() + "some value";
        argMap = tokenizer.tokenize(argsString);
        assertArgumentAbsent(argMap, unknownPrefix);
        assertPreamblePresent(argMap, argsString); // Unknown prefix is taken as part of preamble
    }

    @Test
    public void tokenize_multipleArgumentsWithRepeats() {
        ArgumentTokenizer tokenizer = new ArgumentTokenizer(slashP, dashT, hatQ);

        // Two arguments repeated, some have empty values
        String argsString = "SomePreambleString -t dashT-Value ^Q ^Q-t another dashT value /p slashP value -t";
        ArgumentMap argMap = tokenizer.tokenize(argsString);
        assertPreamblePresent(argMap, "SomePreambleString");
        assertArgumentPresent(argMap, slashP, "slashP value");
        assertArgumentPresent(argMap, dashT, "dashT-Value", "another dashT value", "");
        assertArgumentPresent(argMap, hatQ, "", "");
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
