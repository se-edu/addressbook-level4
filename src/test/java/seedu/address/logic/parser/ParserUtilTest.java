package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void parseIndex_invalidInput_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseIndex("10 a");
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsNumberFormatException() throws Exception {
        thrown.expect(NumberFormatException.class);
        ParserUtil.parseIndex("3423423423423432");
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(1, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(1, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsAssertionError() throws Exception {
        thrown.expect(AssertionError.class);
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
    public void parsePhone_null_throwsAssertionError() throws Exception {
        thrown.expect(AssertionError.class);
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
    public void parseAddress_null_throwsAssertionError() throws Exception {
        thrown.expect(AssertionError.class);
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
    public void parseEmail_null_throwsAssertionError() throws Exception {
        thrown.expect(AssertionError.class);
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
    public void parseTags_null_throwsAssertionError() throws Exception {
        thrown.expect(AssertionError.class);
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
    public void parsePhones() throws Exception {
        final String validPhoneOne = "11111111";
        final String validPhoneTwo = "222222222";
        final String validPhoneThree = "33333333";
        final String invalidPhoneOne = "1";

        // one invalid phone number
        try {
            ParserUtil.parsePhones(Arrays.asList(validPhoneOne, invalidPhoneOne, validPhoneTwo));
            fail("expected IllegalValueException was not thrown.");
        } catch (IllegalValueException ive) {
            // expected behaviour
        }

        // all valid phone numbers
        List<Phone> actualResult = ParserUtil.parsePhones(Arrays.asList(
                validPhoneOne, validPhoneTwo, validPhoneThree));

        assertEquals(Arrays.asList(new Phone(validPhoneOne), new Phone(validPhoneTwo),
                new Phone(validPhoneThree)), actualResult);
    }

    @Test
    public void parseEmails() throws Exception {
        final String validEmailOne = "alice@example.com";
        final String validEmailTwo = "bobby@example.com";
        final String validEmailThree = "charlie@example.com";
        final String invalidEmailOne = "@@@";

        // one invalid email
        try {
            ParserUtil.parseEmails(Arrays.asList(validEmailOne, invalidEmailOne, validEmailTwo));
            fail("expected IllegalValueException was not thrown.");
        } catch (IllegalValueException ive) {
            // expected behaviour
        }

        // all valid emails
        List<Email> actualResult = ParserUtil.parseEmails(Arrays.asList(
                validEmailOne, validEmailTwo, validEmailThree));

        assertEquals(Arrays.asList(new Email(validEmailOne), new Email(validEmailTwo),
                new Email(validEmailThree)), actualResult);
    }

    @Test
    public void parseAddresses() throws Exception {
        final String validAddressOne = "Alice Street 91";
        final String validAddressTwo = "Bob Street 92";
        final String validAddressThree = "Charlie Street 93";
        final String invalidAddressOne = " ";

        // one invalid address
        try {
            ParserUtil.parseEmails(Arrays.asList(validAddressOne, invalidAddressOne, validAddressTwo));
            fail("expected IllegalValueException was not thrown.");
        } catch (IllegalValueException ive) {
            // expected behaviour
        }

        // all valid addresses
        List<Address> actualResult = ParserUtil.parseAddresses(Arrays.asList(
                validAddressOne, validAddressTwo, validAddressThree));

        assertEquals(Arrays.asList(new Address(validAddressOne), new Address(validAddressTwo),
                new Address(validAddressThree)), actualResult);
    }
}
