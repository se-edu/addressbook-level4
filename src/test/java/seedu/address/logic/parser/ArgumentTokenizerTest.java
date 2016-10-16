package seedu.address.logic.parser;

import org.junit.Test;
import seedu.address.logic.parser.ArgumentTokenizer.Prefix;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

public class ArgumentTokenizerTest {

    private final Prefix unknownPrefix = new Prefix("--u");
    private final Prefix slashP = new Prefix("/p");
    private final Prefix dashT = new Prefix("-t");
    private final Prefix hatQ = new Prefix("^Q");

    @Test
    public void accessors_notTokenizedYet() {
        ArgumentTokenizer tokenizer = new ArgumentTokenizer(slashP);
        assertPreambleAbsent(tokenizer);
        assertArgumentAbsent(tokenizer, slashP);
    }

    @Test
    public void tokenize_emptyArgsString_noValues() {
        ArgumentTokenizer tokenizer = new ArgumentTokenizer(slashP);
        String argsString = "  ";
        tokenizer.tokenize(argsString);

        assertPreambleAbsent(tokenizer);
        assertArgumentAbsent(tokenizer, slashP);
    }

    private void assertPreamblePresent(ArgumentTokenizer argsTokenizer, String expectedPreamble) {
        assertEquals(expectedPreamble, argsTokenizer.getPreamble().get());
    }

    private void assertPreambleAbsent(ArgumentTokenizer argsTokenizer) {
        assertFalse(argsTokenizer.getPreamble().isPresent());
    }

    private void assertArgumentPresent(ArgumentTokenizer argsTokenizer, Prefix prefix, String... expectedValues) {

        // Verify the last value is returned
        assertEquals(expectedValues[expectedValues.length - 1], argsTokenizer.getValue(prefix).get());

        // Verify the number of values returned is as expected
        assertEquals(expectedValues.length, argsTokenizer.getAllValues(prefix).get().size());

        // Verify all values returned are as expected and in order
        for (int i = 0; i < expectedValues.length; i++) {
            assertEquals(expectedValues[i], argsTokenizer.getAllValues(prefix).get().get(i));
        }
    }

    private void assertArgumentAbsent(ArgumentTokenizer argsTokenizer, Prefix prefix) {
        assertFalse(argsTokenizer.getValue(prefix).isPresent());
    }

    @Test
    public void tokenize_noPrefixes_allTakenAsPreamble() {
        ArgumentTokenizer tokenizer = new ArgumentTokenizer();
        String argsString = "  some random string /t tag with leading and trailing spaces ";
        tokenizer.tokenize(argsString);

        // Same string expected as preamble, but leading/trailing spaces should be trimmed
        assertPreamblePresent(tokenizer, argsString.trim());

    }

    @Test
    public void tokenize_oneArgument() {
        ArgumentTokenizer tokenizer = new ArgumentTokenizer(slashP);

        // Preamble present
        tokenizer.tokenize("  Some preamble string /p Argument value ");
        assertPreamblePresent(tokenizer, "Some preamble string");
        assertArgumentPresent(tokenizer, slashP, "Argument value");

        // No preamble
        tokenizer.tokenize(" /p   Argument value ");
        assertPreambleAbsent(tokenizer);
        assertArgumentPresent(tokenizer, slashP, "Argument value");

    }

    @Test
    public void tokenize_multipleArguments() {
        ArgumentTokenizer tokenizer = new ArgumentTokenizer(slashP, dashT, hatQ);

        // Only two arguments are present
        tokenizer.tokenize("SomePreambleString -t dashT-Value/pslashP value");
        assertPreamblePresent(tokenizer, "SomePreambleString");
        assertArgumentPresent(tokenizer, slashP, "slashP value");
        assertArgumentPresent(tokenizer, dashT, "dashT-Value");
        assertArgumentAbsent(tokenizer, hatQ);

        /* Also covers: Cases where the prefix doesn't have a space before/after it */

        // All three arguments are present, no spaces before the prefixes
        tokenizer.tokenize("Different Preamble String^Q 111-t dashT-Value/p slashP value");
        assertPreamblePresent(tokenizer, "Different Preamble String");
        assertArgumentPresent(tokenizer, slashP, "slashP value");
        assertArgumentPresent(tokenizer, dashT, "dashT-Value");
        assertArgumentPresent(tokenizer, hatQ, "111");

        /* Also covers: Reusing of the tokenizer multiple times */

        // Reuse tokenizer on an empty string to ensure state is correctly reset
        //   (i.e. no stale values from the previous tokenizing remain in the state)
        tokenizer.tokenize("");
        assertPreambleAbsent(tokenizer);
        assertArgumentAbsent(tokenizer, slashP);

        /** Also covers: testing for prefixes not specified as a prefix **/

        // Prefixes not previously given to the tokenizer should not return any values
        String stringWithUnknownPrefix = unknownPrefix.getPrefix() + "some value";
        tokenizer.tokenize(stringWithUnknownPrefix);
        assertArgumentAbsent(tokenizer, unknownPrefix);
        assertPreamblePresent(tokenizer, stringWithUnknownPrefix); // Unknown prefix is taken as part of preamble
    }

    @Test
    public void tokenize_multipleArgumentsWithRepeats() {
        ArgumentTokenizer tokenizer = new ArgumentTokenizer(slashP, dashT, hatQ);

        // Two arguments repeated, some have empty values
        tokenizer.tokenize("SomePreambleString -t dashT-Value ^Q ^Q-t another dashT value /p slashP value -t");
        assertPreamblePresent(tokenizer, "SomePreambleString");
        assertArgumentPresent(tokenizer, slashP, "slashP value");
        assertArgumentPresent(tokenizer, dashT, "dashT-Value", "another dashT value", "");
        assertArgumentPresent(tokenizer, hatQ, "", "");
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
