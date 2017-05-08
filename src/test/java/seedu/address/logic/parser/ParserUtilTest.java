package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
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
    public void parseIndex_invalidArgs_notPresent() {
        //empty arg
        assertParseIndexNotPresent(" ");

        //non-integer
        assertParseIndexNotPresent("abc");

        //signed negative integer
        assertParseIndexNotPresent("-1");

        //zero
        assertParseIndexNotPresent("0");

        //signed positive integer
        assertParseIndexNotPresent("+1");
    }

    @Test
    public void parseIndex_validArgs_present() {
        //unsigned positive integer
        assertParseIndexPresent("1");
    }

    private void assertParseIndexNotPresent(String arg) {
        Optional<Integer> optionalInteger = ParserUtil.parseIndex(arg);

        assertFalse(optionalInteger.isPresent());
    }

    private void assertParseIndexPresent(String arg) {
        Optional<Integer> optionalInteger = ParserUtil.parseIndex(arg);

        assertTrue(optionalInteger.isPresent());
        assertEquals(arg, optionalInteger.get().toString());
    }

    @Test
    public void splitPreambleTest() {
        //zero fields
        assertPreambleListCorrect(0, "");

        //empty arg
        assertPreambleListCorrect(2, " ", "", "");

        //valid string with no spaces
        assertPreambleListCorrect(1, "abc", "abc");

        //valid string with spaces
        assertPreambleListCorrect(2, "abc 123", "abc", "123");

        //valid string with multiple spaces
        assertPreambleListCorrect(2, "abc     123", "abc", "123");

        //string with fewer num fields than values
        assertPreambleListCorrect(2, "abc 123 qwe 456", "abc", "123 qwe 456");

        //string with more num fields than values - throws NoSuchElementException
        thrown.expect(NoSuchElementException.class);
        assertPreambleListCorrect(3, "abc 123", "abc", "123", "");
    }

    private void assertPreambleListCorrect(int numFields, String arg, String... expectedValues) {
        List<Optional<String>> list = ParserUtil.splitPreamble(arg, numFields);

        for (int i = 0; i < expectedValues.length; i++) {
            assertEquals(list.get(i).get(), expectedValues[i]);
        }
    }

    @Test
    public void parseName_nullArg_throwsAssertionError() throws IllegalValueException {
        thrown.expect(AssertionError.class);
        ParserUtil.parseName(null);
    }

    @Test
    public void parseName_specialCharacterArg_throwsIllegalValueException() throws IllegalValueException {
        thrown.expect(IllegalValueException.class);
        String argName = "$*%";
        ParserUtil.parseName(Optional.of(argName));
    }

    @Test
    public void parseName_validString() throws IllegalValueException {
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
    public void parsePhone_specialCharacterArg_throwsIllegalValueException() throws IllegalValueException {
        thrown.expect(IllegalValueException.class);
        String argPhone = "$*%";
        ParserUtil.parsePhone(Optional.of(argPhone));
    }

    @Test
    public void parsePhone_alphaCharacterArg_throwsIllegalValueException() throws IllegalValueException {
        thrown.expect(IllegalValueException.class);
        String argPhone = "abc";
        ParserUtil.parsePhone(Optional.of(argPhone));
    }

    @Test
    public void parsePhone_shortNumber_throwsIllegalValueException() throws IllegalValueException {
        thrown.expect(IllegalValueException.class);
        String argPhone = "12";
        ParserUtil.parsePhone(Optional.of(argPhone));
    }

    @Test
    public void parsePhone_validPhone() throws IllegalValueException {
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
    public void parseAddress_validAddress() throws IllegalValueException {
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
    public void parseEmail_invalidEmail_throwsIllegalValueException() throws IllegalValueException {
        thrown.expect(IllegalValueException.class);
        String argEmail = "Email.com";
        ParserUtil.parseEmail(Optional.of(argEmail));
    }

    @Test
    public void parseEmail_validEmail() throws IllegalValueException {
        String argEmail = "Email@123";
        Optional<Email> email = ParserUtil.parseEmail(Optional.of(argEmail));

        assertEquals(email.get().toString(), argEmail);
    }

    private void assertTagSetCorrect(Set<Tag> tags, String... expectedValues) {
        int i = 0;
        for (Tag tag : tags) {
            assertEquals(tag.tagName, expectedValues[i]);
            i++;
        }
    }

    @Test
    public void parseTags_nullArg_throwsAssertionError() throws IllegalValueException {
        thrown.expect(AssertionError.class);
        ParserUtil.parseTags(null);
    }

    @Test
    public void parseTags_specialCharacterArg_throwsIllegalValueException() throws IllegalValueException {
        thrown.expect(IllegalValueException.class);
        Collection<String> argTags = new ArrayList<String>();
        argTags.add("$*%");
        ParserUtil.parseTags(argTags);
    }

    @Test
    public void parseTags_validCollection() throws IllegalValueException {
        String arg1 = "tag1";
        String arg2 = "tag2";

        Collection<String> argTags = new ArrayList<String>();
        argTags.add(arg1);
        argTags.add(arg2);
        Set<Tag> tags = ParserUtil.parseTags(argTags);

        assertTagSetCorrect(tags, arg1, arg2);
    }
}
