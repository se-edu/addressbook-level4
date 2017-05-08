package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

public class ParserUtilTest {
    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void parseIndex_invalidInput_notPresent() {
        assertParseIndexNotPresent("abc");
    }

    @Test
    public void parseIndex_validInput_present() {
        // No whitespaces
        assertParseIndexPresent("1", 1);

        // Leading and trailing whitespaces
        assertParseIndexPresent(" 1 ", 1);
    }

    /**
     * Asserts {@code index} is unsuccessfully parsed
     */
    private void assertParseIndexNotPresent(String index) {
        Optional<Integer> actualValue = ParserUtil.parseIndex(index);

        assertFalse(actualValue.isPresent());
    }

    /**
     * Asserts {@code index} is successfully parsed and the parsed value equals to {@code expectedValue}
     */
    private void assertParseIndexPresent(String index, int expectedValue) {
        assertEquals(expectedValue, ParserUtil.parseIndex(index).get().intValue());
    }

    @Test
    public void splitPreamble() {
        // Empty string
        assertPreambleListCorrect(0, "", Arrays.asList());

        // Whitespaces only
        assertPreambleListCorrect(2, " ", Arrays.asList(Optional.of(""), Optional.of("")));

        // No whitespaces
        assertPreambleListCorrect(1, "abc", Arrays.asList(Optional.of("abc")));

        // Single whitespace
        assertPreambleListCorrect(2, "abc 123", Arrays.asList(Optional.of("abc"), Optional.of("123")));

        // Multiple whitespaces
        assertPreambleListCorrect(2, "abc     123", Arrays.asList(Optional.of("abc"), Optional.of("123")));

        // More whitespaces than numFields
        assertPreambleListCorrect(2, "abc 123 qwe 456", Arrays.asList(Optional.of("abc"), Optional.of("123 qwe 456")));

        // More numFields than whitespaces
        assertPreambleListCorrect(2, "abc", Arrays.asList(Optional.of("abc"), Optional.empty()));
    }

    /**
     * Splits {@code toSplit} into ordered fields of size {@code numFields}
     * and checks if the result is the same as {@code expectedValues}
     */
    private void assertPreambleListCorrect(int numFields, String toSplit, List<Optional<String>> expectedValues) {
        List<Optional<String>> list = ParserUtil.splitPreamble(toSplit, numFields);

        assertTrue(list.equals(expectedValues));
    }

    @Test
    public void parseName_null_throwsAssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        ParserUtil.parseName(null);
    }

    @Test
    public void parseName_invalidArg_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseName(Optional.of("$*%"));
    }

    @Test
    public void parseName_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseName(Optional.empty()).isPresent());
    }

    @Test
    public void parseName_validArg_returnsName() throws Exception {
        Name expectedName = new Name("Name 123");
        Optional<Name> actualName = ParserUtil.parseName(Optional.of("Name 123"));

        assertEquals(actualName.get(), expectedName);
    }

    @Test
    public void parsePhone_null_throwsAssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        ParserUtil.parsePhone(null);
    }

    @Test
    public void parsePhone_invalidArg_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parsePhone(Optional.of("$*%"));
    }

    @Test
    public void parsePhone_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parsePhone(Optional.empty()).isPresent());
    }

    @Test
    public void parsePhone_validArg_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone("123");
        Optional<Phone> actualPhone = ParserUtil.parsePhone(Optional.of("123"));

        assertEquals(actualPhone.get(), expectedPhone);
    }

    @Test
    public void parseAddress_null_throwsAssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        ParserUtil.parseAddress(null);
    }

    @Test
    public void parseAddress_invalidArg_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseAddress(Optional.of(" "));
    }

    @Test
    public void parseAddress_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseAddress(Optional.empty()).isPresent());
    }

    @Test
    public void parseAddress_validArg_returnsAddress() throws Exception {
        Address expectedAddress = new Address("Address 123 #0505");
        Optional<Address> actualAddress = ParserUtil.parseAddress(Optional.of("Address 123 #0505"));

        assertEquals(actualAddress.get(), expectedAddress);
    }

    @Test
    public void parseEmail_null_throwsAssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        ParserUtil.parseEmail(null);
    }

    @Test
    public void parseEmail_invalidArg_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseEmail(Optional.of("$*%"));
    }

    @Test
    public void parseEmail_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseEmail(Optional.empty()).isPresent());
    }

    @Test
    public void parseEmail_validArg_returnsEmail() throws Exception {
        Email expectedEmail = new Email("Email@123");
        Optional<Email> actualEmail = ParserUtil.parseEmail(Optional.of("Email@123"));

        assertEquals(actualEmail.get(), expectedEmail);
    }

    @Test
    public void parseTags_null_throwsAssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        ParserUtil.parseTags(null);
    }

    @Test
    public void parseTags_invalidCollection_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseTags(Arrays.asList("$*%"));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_validCollection_returnsTagList() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList("tag1", "tag2"));
        Set<Tag> expectedTagSet = new HashSet<Tag>(Arrays.asList(new Tag("tag1"), new Tag("tag2")));

        assertEquals(actualTagSet, expectedTagSet);
    }
}
