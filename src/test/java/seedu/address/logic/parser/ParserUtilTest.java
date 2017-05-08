package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
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
        // Non-integer
        assertParseIndexNotPresent("abc");
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
     * Parses {@code index} which is invalid, and checks that the optional integer is not present
     */
    private void assertParseIndexNotPresent(String index) {
        Optional<Integer> optionalIndex = ParserUtil.parseIndex(index);

        assertFalse(optionalIndex.isPresent());
    }

    /**
     * Asserts {@code index} is successfully parsed and the parsed value equals to {@code expectedValue}
     */
    private void assertParseIndexPresent(String index, int expectedValue) {
        Optional<Integer> optionalIndex = ParserUtil.parseIndex(index);

        assertEquals(expectedValue, optionalIndex.get().intValue());
    }

    @Test
    public void splitPreamble() {
        // Zero fields
        assertPreambleListCorrect(0, "", Arrays.asList());

        // Empty arg
        assertPreambleListCorrect(2, " ", Arrays.asList(Optional.of(""), Optional.of("")));

        // Valid string with no spaces
        assertPreambleListCorrect(1, "abc", Arrays.asList(Optional.of("abc")));

        // Valid string with spaces
        assertPreambleListCorrect(2, "abc 123", Arrays.asList(Optional.of("abc"), Optional.of("123")));

        // Valid string with multiple spaces
        assertPreambleListCorrect(2, "abc     123", Arrays.asList(Optional.of("abc"), Optional.of("123")));

        // String with fewer num fields than values
        assertPreambleListCorrect(2, "abc 123 qwe 456", Arrays.asList(Optional.of("abc"), Optional.of("123 qwe 456")));

        // String with more num fields than values
        assertPreambleListCorrect(2, "abc", Arrays.asList(Optional.of("abc"), Optional.empty()));
    }

    /**
     * Splits {@code arg} into ordered fields, and checks if the result is the same as {@code expectedValues}
     */
    private void assertPreambleListCorrect(int numFields, String arg, List<Optional<String>> expectedValues) {
        List<Optional<String>> list = ParserUtil.splitPreamble(arg, numFields);

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
        Optional<String> argName = Optional.empty();
        Optional<Name> name = ParserUtil.parseName(argName);

        assertFalse(name.isPresent());
    }

    @Test
    public void parseName_validArg() throws Exception {
        String argName = "Name 123";
        Optional<Name> name = ParserUtil.parseName(Optional.of(argName));

        assertEquals(name.get().toString(), argName);
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
        Optional<String> argPhone = Optional.empty();
        Optional<Phone> phone = ParserUtil.parsePhone(argPhone);

        assertFalse(phone.isPresent());
    }

    @Test
    public void parsePhone_validArg() throws Exception {
        String argPhone = "123";
        Optional<Phone> phone = ParserUtil.parsePhone(Optional.of(argPhone));

        assertEquals(phone.get().toString(), argPhone);
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
        Optional<String> argAddress = Optional.empty();
        Optional<Address> address = ParserUtil.parseAddress(argAddress);

        assertFalse(address.isPresent());
    }

    @Test
    public void parseAddress_validArg() throws Exception {
        String argAddress = "Address 123 #0505";
        Optional<Address> address = ParserUtil.parseAddress(Optional.of(argAddress));

        assertEquals(address.get().toString(), argAddress);
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
        Optional<String> argEmail = Optional.empty();
        Optional<Email> email = ParserUtil.parseEmail(argEmail);

        assertFalse(email.isPresent());
    }

    @Test
    public void parseEmail_validArg() throws Exception {
        String argEmail = "Email@123";
        Optional<Email> email = ParserUtil.parseEmail(Optional.of(argEmail));

        assertEquals(email.get().toString(), argEmail);
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
        Collection<String> argTags = new ArrayList<String>();
        Set<Tag> tags = ParserUtil.parseTags(argTags);

        assertTrue(tags.isEmpty());
    }

    @Test
    public void parseTags_validCollection_returnsTagList() throws Exception {
        Set<Tag> tags = ParserUtil.parseTags(Arrays.asList("tag1", "tag2"));

        // Verify that the tags set created is equal to the expected tags
        Set<String> expectedSet = new HashSet<String>(Arrays.asList("tag1", "tag2"));
        // Convert tags into a set of tagNames
        Set<String> tagsString = new HashSet<String>();
        for (Tag tag : tags) {
            tagsString.add(tag.tagName);
        }

        assertTrue(tagsString.equals(expectedSet));
    }
}
