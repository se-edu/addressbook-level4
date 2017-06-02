package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_MULTIPLE_VALUES_WARNING;
import static seedu.address.testutil.TypicalPersons.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";

    private static final ArgumentMultimap NO_REPEATED_FIELD = createArgumentMultimapNoRepeatedField();
    private static final ArgumentMultimap ONE_REPEATED_FIELD = createArgumentMultimapOneRepeatedField();
    private static final ArgumentMultimap TWO_REPEATED_FIELDS = createArgumentMultimapTwoRepeatedFields();

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    private static ArgumentMultimap createArgumentMultimapNoRepeatedField() {
        ArgumentMultimap argumentMultimap = new ArgumentMultimap();
        argumentMultimap.put(PREFIX_PHONE, VALID_PHONE);
        argumentMultimap.put(PREFIX_EMAIL, INVALID_EMAIL);
        return argumentMultimap;
    }

    private static ArgumentMultimap createArgumentMultimapOneRepeatedField() {
        ArgumentMultimap argumentMultimap = createArgumentMultimapNoRepeatedField();
        argumentMultimap.put(PREFIX_PHONE, INVALID_PHONE);
        return argumentMultimap;
    }

    private static ArgumentMultimap createArgumentMultimapTwoRepeatedFields() {
        ArgumentMultimap argumentMultimap = createArgumentMultimapOneRepeatedField();
        argumentMultimap.put(PREFIX_EMAIL, VALID_EMAIL);
        return argumentMultimap;
    }

    @Test
    public void parseIndex_invalidInput_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseIndex("10 a");
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(MESSAGE_INVALID_INDEX);
        ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void split_nullPreamble_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        ParserUtil.split(null, 2);
    }

    @Test
    public void split_invalidNumOfParts_throwsIllegalArgumentException() {
        assertSplitFailure("abc", -1);
        assertSplitFailure("abc", 0);
        assertSplitFailure("abc", 1);
    }

    @Test
    public void split_validInput_success() {
        // Single whitespace between parts
        assertSplitSuccess("abc 123", 2, asOptionalList("abc", "123"));

        // Leading and trailing whitespaces, multiple whitespaces between parts
        assertSplitSuccess(" \t abc  \n qwe \t  123\t\n", 3, asOptionalList("abc", "qwe", "123"));

        // More whitespaces than numOfParts
        assertSplitSuccess("abc 123 qwe 456", 2,  asOptionalList("abc", "123 qwe 456"));

        // More numOfParts than whitespaces
        assertSplitSuccess("abc", 2,  asOptionalList("abc", null));
    }

    /**
     * Asserts that {@code split(string, numOfParts)} is unsuccessful and a matching
     * {@code IllegalArgumentException} is thrown.
     */
    private void assertSplitFailure(String string, int numOfParts) {
        try {
            ParserUtil.split(string, numOfParts);
            fail("The expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException iae) {
            assertEquals(ParserUtil.MESSAGE_INSUFFICIENT_PARTS, iae.getMessage());
        }
    }

    /**
     * Asserts that {@code string} is successfully split into ordered parts of size {@code numOfParts}
     * and checks if the result is the same as {@code expectedValues}
     */
    private void assertSplitSuccess(String string, int numOfParts, List<Optional<String>> expectedValues) {
        List<Optional<String>> list = ParserUtil.split(string, numOfParts);

        assertTrue(list.equals(expectedValues));
    }

    /**
     * Returns {@code strings} as {@code List<Optional<String>>}. Null values will be converted to
     * {@code Optional.empty()}.
     */
    private List<Optional<String>> asOptionalList(String... strings) {
        return Arrays.stream(strings).map(Optional::ofNullable).collect(Collectors.toList());
    }

    @Test
    public void parseName_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseName(null);
    }

    @Test
    public void parseName_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseName(Optional.of(INVALID_NAME));
    }

    @Test
    public void parseName_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseName(Optional.empty()).isPresent());
    }

    @Test
    public void parseName_validValue_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        Optional<Name> actualName = ParserUtil.parseName(Optional.of(VALID_NAME));

        assertEquals(expectedName, actualName.get());
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parsePhone(null);
    }

    @Test
    public void parsePhone_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parsePhone(Optional.of(INVALID_PHONE));
    }

    @Test
    public void parsePhone_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parsePhone(Optional.empty()).isPresent());
    }

    @Test
    public void parsePhone_validValue_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        Optional<Phone> actualPhone = ParserUtil.parsePhone(Optional.of(VALID_PHONE));

        assertEquals(expectedPhone, actualPhone.get());
    }

    @Test
    public void parseAddress_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseAddress(null);
    }

    @Test
    public void parseAddress_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseAddress(Optional.of(INVALID_ADDRESS));
    }

    @Test
    public void parseAddress_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseAddress(Optional.empty()).isPresent());
    }

    @Test
    public void parseAddress_validValue_returnsAddress() throws Exception {
        Address expectedAddress = new Address(VALID_ADDRESS);
        Optional<Address> actualAddress = ParserUtil.parseAddress(Optional.of(VALID_ADDRESS));

        assertEquals(expectedAddress, actualAddress.get());
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseEmail(null);
    }

    @Test
    public void parseEmail_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseEmail(Optional.of(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseEmail(Optional.empty()).isPresent());
    }

    @Test
    public void parseEmail_validValue_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        Optional<Email> actualEmail = ParserUtil.parseEmail(Optional.of(VALID_EMAIL));

        assertEquals(expectedEmail, actualEmail.get());
    }

    @Test
    public void parseTags_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseTags(null);
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }

    @Test
    public void getWarningMessage() {
        // no repeated fields
        assertEquals(Optional.empty(), ParserUtil.getWarningMessage(NO_REPEATED_FIELD, PREFIX_PHONE, PREFIX_EMAIL));

        // one repeated field
        assertEquals(String.format(MESSAGE_MULTIPLE_VALUES_WARNING, "Phone"),
                ParserUtil.getWarningMessage(ONE_REPEATED_FIELD, PREFIX_PHONE, PREFIX_EMAIL).get());

        // two repeated fields
        assertEquals(String.format(MESSAGE_MULTIPLE_VALUES_WARNING, "Phone and Email"),
                ParserUtil.getWarningMessage(TWO_REPEATED_FIELDS, PREFIX_PHONE, PREFIX_EMAIL).get());

        // two repeated fields, only pass 1 prefix
        assertEquals(String.format(MESSAGE_MULTIPLE_VALUES_WARNING, "Email"),
                ParserUtil.getWarningMessage(TWO_REPEATED_FIELDS, PREFIX_EMAIL).get());
    }
}
