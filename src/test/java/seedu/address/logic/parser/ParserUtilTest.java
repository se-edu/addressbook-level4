package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
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
    public void parseIndex_nonUnsignedPositiveInt_notPresent() {
        // Empty arg
        assertParseIndexNotPresent(" ");

        // Non-integer
        assertParseIndexNotPresent("abc");

        // Signed integer
        assertParseIndexNotPresent("-1");
    }

    @Test
    public void parseIndex_unsignedPositiveInt_present() {
        // Unsigned positive integer without white spaces
        assertParseIndexPresent("1", 1);

        // Unsigned positive integer with leading white space
        assertParseIndexPresent(" 1", 1);

        // Unsigned positive integer with trailing white space
        assertParseIndexPresent("1 ", 1);
    }

    /**
     * Parses an input which is invalid, and checks that the optional integer is not present
     * @param arg the input which must not be an unsigned positive integer
     */
    private void assertParseIndexNotPresent(String arg) {
        Optional<Integer> optionalInteger = ParserUtil.parseIndex(arg);

        assertFalse(optionalInteger.isPresent());
    }

    /**
     * Parses an input which is valid, and checks that the optional integer is present
     * @param arg the input which must be an unsigned positive integer
     */
    private void assertParseIndexPresent(String arg, int expectedValue) {
        Optional<Integer> optionalInteger = ParserUtil.parseIndex(arg);

        assertEquals(expectedValue, optionalInteger.get().intValue());
    }

    @Test
    public void splitPreambleTest() {
        //List of expected values
        List<Optional<String>> expected = new ArrayList<Optional<String>>();

        // Zero fields
        assertPreambleListCorrect(0, "", expected);

        // Empty arg
        expected.clear();
        expected.add(Optional.of(""));
        expected.add(Optional.of(""));
        assertPreambleListCorrect(2, " ", expected);

        // Valid string with no spaces
        expected.clear();
        expected.add(Optional.of("abc"));
        assertPreambleListCorrect(1, "abc", expected);

        // Valid string with spaces
        expected.add(Optional.of("123"));
        assertPreambleListCorrect(2, "abc 123", expected);

        // Valid string with multiple spaces
        assertPreambleListCorrect(2, "abc     123", expected);

        // String with fewer num fields than values
        expected.remove(1);
        expected.add(Optional.of("123 qwe 456"));
        assertPreambleListCorrect(2, "abc 123 qwe 456", expected);

        // String with more num fields than values
        expected.remove(1);
        expected.add(Optional.empty());
        assertPreambleListCorrect(2, "abc", expected);
    }

    /**
     * Splits a preamble into an ordered fields, and checks if the resulting list is the same as expected
     * @param numFields the number of fields in the string
     * @param arg the preamble string to be split into fields
     */
    private void assertPreambleListCorrect(int numFields, String arg, List<Optional<String>> expectedValues) {
        List<Optional<String>> list = ParserUtil.splitPreamble(arg, numFields);

        assertTrue(list.equals(expectedValues));
    }

    @Test
    public void parseName_nullArg_throwsAssertionError() throws IllegalValueException {
        thrown.expect(AssertionError.class);
        ParserUtil.parseName(null);
    }

    @Test
    public void parseName_invalidArg_throwsIllegalValueException() throws IllegalValueException {
        thrown.expect(IllegalValueException.class);
        String argName = "$*%";
        ParserUtil.parseName(Optional.of(argName));
    }

    @Test
    public void parseName_optionalEmptyArg_returnsOptionalEmpty() throws IllegalValueException {
        Optional<String> argName = Optional.empty();
        Optional<Name> name = ParserUtil.parseName(argName);

        assertFalse(name.isPresent());
    }

    @Test
    public void parseName_validArg() throws IllegalValueException {
        String argName = "Name 123";
        Optional<Name> name = ParserUtil.parseName(Optional.of(argName));

        assertEquals(name.get().toString(), argName);
    }

    @Test
    public void parsePhone_nullArg_throwsAssertionError() throws IllegalValueException {
        thrown.expect(AssertionError.class);
        ParserUtil.parsePhone(null);
    }

    @Test
    public void parsePhone_invalidArg_throwsIllegalValueException() throws IllegalValueException {
        thrown.expect(IllegalValueException.class);
        String argPhone = "$*%";
        ParserUtil.parsePhone(Optional.of(argPhone));
    }

    @Test
    public void parsePhone_optionalEmptyArg_returnsOptionalEmpty() throws IllegalValueException {
        Optional<String> argPhone = Optional.empty();
        Optional<Phone> phone = ParserUtil.parsePhone(argPhone);

        assertFalse(phone.isPresent());
    }

    @Test
    public void parsePhone_validArg() throws IllegalValueException {
        String argPhone = "123";
        Optional<Phone> phone = ParserUtil.parsePhone(Optional.of(argPhone));

        assertEquals(phone.get().toString(), argPhone);
    }

    @Test
    public void parseAddress_nullArg_throwsAssertionError() throws IllegalValueException {
        thrown.expect(AssertionError.class);
        ParserUtil.parseAddress(null);
    }

    @Test
    public void parseAddress_invalidArg_throwsIllegalValueException() throws IllegalValueException {
        thrown.expect(IllegalValueException.class);
        String argAddress = " ";
        ParserUtil.parseAddress(Optional.of(argAddress));
    }

    @Test
    public void parseAddress_optionalEmptyArg_returnsOptionalEmpty() throws IllegalValueException {
        Optional<String> argAddress = Optional.empty();
        Optional<Address> address = ParserUtil.parseAddress(argAddress);

        assertFalse(address.isPresent());
    }

    @Test
    public void parseAddress_validArg() throws IllegalValueException {
        String argAddress = "Address 123 #0505";
        Optional<Address> address = ParserUtil.parseAddress(Optional.of(argAddress));

        assertEquals(address.get().toString(), argAddress);
    }

    @Test
    public void parseEmail_nullArg_throwsAssertionError() throws IllegalValueException {
        thrown.expect(AssertionError.class);
        ParserUtil.parseEmail(null);
    }

    @Test
    public void parseEmail_invalidArg_throwsIllegalValueException() throws IllegalValueException {
        thrown.expect(IllegalValueException.class);
        String argEmail = "Email.com";
        ParserUtil.parseEmail(Optional.of(argEmail));
    }

    @Test
    public void parseEmail_optionalEmptyArg_returnsOptionalEmpty() throws IllegalValueException {
        Optional<String> argEmail = Optional.empty();
        Optional<Email> email = ParserUtil.parseEmail(argEmail);

        assertFalse(email.isPresent());
    }

    @Test
    public void parseEmail_validArg() throws IllegalValueException {
        String argEmail = "Email@123";
        Optional<Email> email = ParserUtil.parseEmail(Optional.of(argEmail));

        assertEquals(email.get().toString(), argEmail);
    }

    @Test
    public void parseTags_nullArg_throwsAssertionError() throws IllegalValueException {
        thrown.expect(AssertionError.class);
        ParserUtil.parseTags(null);
    }

    @Test
    public void parseTags_invalidCollection_throwsIllegalValueException() throws IllegalValueException {
        thrown.expect(IllegalValueException.class);
        Collection<String> argTags = new ArrayList<String>();
        argTags.add("$*%");
        ParserUtil.parseTags(argTags);
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws IllegalValueException {
        Collection<String> argTags = new ArrayList<String>();
        Set<Tag> tags = ParserUtil.parseTags(argTags);

        assertTrue(tags.isEmpty());
    }

    @Test
    public void parseTags_validCollection() throws IllegalValueException {
        Collection<String> argTags = new ArrayList<String>();
        argTags.add("tag1");
        argTags.add("tag2");
        Set<Tag> tags = ParserUtil.parseTags(argTags);

        // Verify that the tags set created is equal to the expected tags
        Set<String> expectedSet = new HashSet<String>(argTags);
        // Convert tags into a set of tagNames
        Set<String> tagsString = new HashSet<String>();
        for (Tag tag : tags) {
            tagsString.add(tag.tagName);
        }

        assertTrue(tagsString.equals(expectedSet));
    }
}
