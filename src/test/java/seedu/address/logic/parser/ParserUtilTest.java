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
    public void parseIndex_emptyArgsString_notPresent() {
        String argsString = " ";
        Optional<Integer> optionalInteger = ParserUtil.parseIndex(argsString);

        assertFalse(optionalInteger.isPresent());
    }

    @Test
    public void parseIndex_nonInteger_notPresent() {
        String argsString = "abc";
        Optional<Integer> optionalInteger = ParserUtil.parseIndex(argsString);

        assertFalse(optionalInteger.isPresent());
    }

    @Test
    public void parseIndex_signedNegativeInt_notPresent() {
        String argsString = "-1";
        Optional<Integer> optionalInteger = ParserUtil.parseIndex(argsString);

        assertFalse(optionalInteger.isPresent());
    }

    @Test
    public void parseIndex_signedPositiveInt_notPresent() {
        String argsString = "+1";
        Optional<Integer> optionalInteger = ParserUtil.parseIndex(argsString);

        assertFalse(optionalInteger.isPresent());
    }

    @Test
    public void parseIndex_unsignedPositiveInt_present() {
        String argsString = "1";
        Optional<Integer> optionalInteger = ParserUtil.parseIndex(argsString);

        assertTrue(optionalInteger.isPresent());
        assertEquals(argsString, optionalInteger.get().toString());
    }

    private void assertPreambleListCorrect(List<Optional<String>> list, String... expectedValues) {
        for (int i = 0; i < expectedValues.length; i++) {
            assertEquals(list.get(i).get(), expectedValues[i]);
        }
    }

    @Test
    public void splitPreamble_zeroFields_emptyList() {
        String argsPreamble = "";
        int argsNumFields = 0;
        List<Optional<String>> list = ParserUtil.splitPreamble(argsPreamble, argsNumFields);

        assertPreambleListCorrect(list);
    }

    @Test
    public void splitPreamble_nullArgs_listWithNullEntries() {
        String argsPreamble = " ";
        int argsNumFields = 2;
        List<Optional<String>> list = ParserUtil.splitPreamble(argsPreamble, argsNumFields);

        assertPreambleListCorrect(list, "", "");
    }

    @Test
    public void splitPreamble_validString() {
        String argsPreamble = "abc";
        int argsNumFields = 1;
        List<Optional<String>> list = ParserUtil.splitPreamble(argsPreamble, argsNumFields);

        assertPreambleListCorrect(list, "abc");
    }

    @Test
    public void splitPreamble_validStrings() {
        String argsPreamble = "abc 123";
        int argsNumFields = 2;
        List<Optional<String>> list = ParserUtil.splitPreamble(argsPreamble, argsNumFields);

        assertPreambleListCorrect(list, "abc", "123");
    }

    @Test
    public void splitPreamble_fewerNumFieldsThanValues() {
        String argsPreamble = "abc 123 qwe 456";
        int argsNumFields = 2;
        List<Optional<String>> list = ParserUtil.splitPreamble(argsPreamble, argsNumFields);

        assertPreambleListCorrect(list, "abc", "123 qwe 456");
    }

    @Test
    public void splitPreamble_moreNumFieldsThanValues() {
        String argsPreamble = "abc 123";
        int argsNumFields = 3;
        List<Optional<String>> list = ParserUtil.splitPreamble(argsPreamble, argsNumFields);

        thrown.expect(NoSuchElementException.class);
        assertPreambleListCorrect(list, "abc", "123", "");
    }

    @Test
    public void splitPreamble_validStringsMultipleSpaces() {
        String argsPreamble = "abc     123";
        int argsNumFields = 2;
        List<Optional<String>> list = ParserUtil.splitPreamble(argsPreamble, argsNumFields);

        assertPreambleListCorrect(list, "abc", "123");
    }

    private Optional<String> toOptionalString(String string) {
        return Optional.ofNullable(string).filter(s -> !s.isEmpty());
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
        ParserUtil.parseName(toOptionalString(argName));
    }

    @Test
    public void parseName_validString() throws IllegalValueException {
        String argName = "Name 123";
        Optional<Name> name = ParserUtil.parseName(toOptionalString(argName));

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
        ParserUtil.parsePhone(toOptionalString(argPhone));
    }

    @Test
    public void parsePhone_alphaCharacterArg_throwsIllegalValueException() throws IllegalValueException {
        thrown.expect(IllegalValueException.class);
        String argPhone = "abc";
        ParserUtil.parsePhone(toOptionalString(argPhone));
    }

    @Test
    public void parsePhone_shortNumber_throwsIllegalValueException() throws IllegalValueException {
        thrown.expect(IllegalValueException.class);
        String argPhone = "12";
        ParserUtil.parsePhone(toOptionalString(argPhone));
    }

    @Test
    public void parsePhone_validPhone() throws IllegalValueException {
        String argPhone = "123";
        Optional<Phone> phone = ParserUtil.parsePhone(toOptionalString(argPhone));

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
        Optional<Address> address = ParserUtil.parseAddress(toOptionalString(argAddress));

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
        ParserUtil.parseEmail(toOptionalString(argEmail));
    }

    @Test
    public void parseEmail_validEmail() throws IllegalValueException {
        String argEmail = "Email@123";
        Optional<Email> email = ParserUtil.parseEmail(toOptionalString(argEmail));

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
