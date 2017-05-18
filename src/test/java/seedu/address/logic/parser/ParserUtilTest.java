package seedu.address.logic.parser;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ParserUtilTest {
    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void splitPreamble_nullPreamble_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        ParserUtil.splitPreamble(null, 0);
    }

    @Test
    public void splitPreamble_negativeNumFields_throwsNegativeArraySizeException() {
        thrown.expect(NegativeArraySizeException.class);
        ParserUtil.splitPreamble("abc", -1);
    }

    @Test
    public void splitPreamble_validInput_success() {
        // Zero numFields
        assertPreambleListCorrect("abc", 0, Arrays.asList());

        // Empty string
        assertPreambleListCorrect("", 1, Arrays.asList(Optional.of("")));

        // No whitespaces
        assertPreambleListCorrect("abc", 1, Arrays.asList(Optional.of("abc")));

        // Single whitespace between fields
        assertPreambleListCorrect("abc 123", 2, Arrays.asList(Optional.of("abc"), Optional.of("123")));

        // Multiple whitespaces between fields
        assertPreambleListCorrect("abc     123", 2, Arrays.asList(Optional.of("abc"), Optional.of("123")));

        // More whitespaces than numFields
        assertPreambleListCorrect("abc 123 qwe 456", 2, Arrays.asList(Optional.of("abc"), Optional.of("123 qwe 456")));

        // More numFields than whitespaces
        assertPreambleListCorrect("abc", 2, Arrays.asList(Optional.of("abc"), Optional.empty()));
    }

    /**
     * Splits {@code toSplit} into ordered fields of size {@code numFields}
     * and checks if the result is the same as {@code expectedValues}
     */
    private void assertPreambleListCorrect(String preamble, int numFields, List<Optional<String>> expectedValues) {
        List<Optional<String>> list = ParserUtil.splitPreamble(preamble, numFields);

        assertTrue(list.equals(expectedValues));
    }
}
