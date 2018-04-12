package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.logic.parser.ParserUtil.parseDateTime;
import static seedu.address.logic.parser.ParserUtil.parseDuration;
import static seedu.address.testutil.TaskUtil.FORMATTER;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
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
import seedu.address.model.person.exceptions.DurationParseException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tutee.EducationLevel;
import seedu.address.model.tutee.Grade;
import seedu.address.model.tutee.School;
import seedu.address.model.tutee.Subject;
import seedu.address.testutil.Assert;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_SUBJECT = "#subject";
    private static final String INVALID_GRADE = "+B";
    private static final String INVALID_EDUCATIONAL_LEVEL = "University";
    private static final String INVALID_SCHOOL = "school12";
    private static final String INVALID_TIME_UNIT = "year";
    private static final String INVALID_DURATION = "1.5h";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";
    private static final String VALID_SUBJECT = "science";
    private static final String VALID_GRADE = "B+";
    private static final String VALID_EDUCATIONAL_LEVEL = "primary";
    private static final String VALID_SCHOOL = "valid primary school";
    private static final String VALID_TIME_UNIT = "y";
    private static final String VALID_DATE = "25/04/2018";
    private static final String VALID_TIME = "08:01";
    private static final String VALID_DURATION = "1h30m";
    private static final String VALID_DESCRIPTION = "homework";
    private static final String VALID_TASK_WITHOUT_DESCRIPTION = VALID_DATE + " " + VALID_TIME + " " + VALID_DURATION;
    private static final String VALID_TASK_WITH_DESCRIPTION = VALID_TASK_WITHOUT_DESCRIPTION + " " + VALID_DESCRIPTION;

    private static final String WHITESPACE = " \t\r\n";
    private static final int MAXIMUM_AMOUNT_OF_PARAMETERS = 4;

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

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
    public void parseName_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseName((Optional<String>) null));
    }

    @Test
    public void parseName_invalidValue_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseName(INVALID_NAME));
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseName(Optional.of(INVALID_NAME)));
    }

    @Test
    public void parseName_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseName(Optional.empty()).isPresent());
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
        assertEquals(Optional.of(expectedName), ParserUtil.parseName(Optional.of(VALID_NAME)));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
        assertEquals(Optional.of(expectedName), ParserUtil.parseName(Optional.of(nameWithWhitespace)));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone((String) null));
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone((Optional<String>) null));
    }

    @Test
    public void parsePhone_invalidValue_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parsePhone(Optional.of(INVALID_PHONE)));
    }

    @Test
    public void parsePhone_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parsePhone(Optional.empty()).isPresent());
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE));
        assertEquals(Optional.of(expectedPhone), ParserUtil.parsePhone(Optional.of(VALID_PHONE)));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithWhitespace));
        assertEquals(Optional.of(expectedPhone), ParserUtil.parsePhone(Optional.of(phoneWithWhitespace)));
    }

    @Test
    public void parseAddress_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseAddress((String) null));
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseAddress((Optional<String>) null));
    }

    @Test
    public void parseAddress_invalidValue_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseAddress(INVALID_ADDRESS));
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseAddress(Optional.of(INVALID_ADDRESS)));
    }

    @Test
    public void parseAddress_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseAddress(Optional.empty()).isPresent());
    }

    @Test
    public void parseAddress_validValueWithoutWhitespace_returnsAddress() throws Exception {
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(VALID_ADDRESS));
        assertEquals(Optional.of(expectedAddress), ParserUtil.parseAddress(Optional.of(VALID_ADDRESS)));
    }

    @Test
    public void parseAddress_validValueWithWhitespace_returnsTrimmedAddress() throws Exception {
        String addressWithWhitespace = WHITESPACE + VALID_ADDRESS + WHITESPACE;
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(addressWithWhitespace));
        assertEquals(Optional.of(expectedAddress), ParserUtil.parseAddress(Optional.of(addressWithWhitespace)));
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail((String) null));
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail((Optional<String>) null));
    }

    @Test
    public void parseEmail_invalidValue_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseEmail(INVALID_EMAIL));
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseEmail(Optional.of(INVALID_EMAIL)));
    }

    @Test
    public void parseEmail_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseEmail(Optional.empty()).isPresent());
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(VALID_EMAIL));
        assertEquals(Optional.of(expectedEmail), ParserUtil.parseEmail(Optional.of(VALID_EMAIL)));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(emailWithWhitespace));
        assertEquals(Optional.of(expectedEmail), ParserUtil.parseEmail(Optional.of(emailWithWhitespace)));
    }

    @Test
    public void parseTag_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseTag(null);
    }

    @Test
    public void parseTag_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseTag(INVALID_TAG);
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_1));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(tagWithWhitespace));
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

    //@@author ChoChihTun
    @Test
    public void parseSubject_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseSubject((String) null));
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseSubject((Optional<String>) null));
    }

    @Test
    public void parseSubject_invalidValue_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseSubject(INVALID_SUBJECT));
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseSubject(Optional.of(INVALID_SUBJECT)));
    }

    @Test
    public void parseSubject_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseSubject(Optional.empty()).isPresent());
    }

    @Test
    public void parseSubject_validValueWithoutWhitespace_returnsSubject() throws Exception {
        Subject expectedSubject = new Subject(VALID_SUBJECT);
        assertEquals(expectedSubject, ParserUtil.parseSubject(VALID_SUBJECT));
        assertEquals(Optional.of(expectedSubject), ParserUtil.parseSubject(Optional.of(VALID_SUBJECT)));
    }

    @Test
    public void parseSubject_validValueWithWhitespace_returnsTrimmedSubject() throws Exception {
        String subjectWithWhitespace = WHITESPACE + VALID_SUBJECT + WHITESPACE;
        Subject expectedSubject = new Subject(VALID_SUBJECT);
        assertEquals(expectedSubject, ParserUtil.parseSubject(subjectWithWhitespace));
        assertEquals(Optional.of(expectedSubject), ParserUtil.parseSubject(Optional.of(subjectWithWhitespace)));
    }

    @Test
    public void parseGrade_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseGrade((String) null));
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseGrade((Optional<String>) null));
    }

    @Test
    public void parseGrade_invalidValue_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseGrade(INVALID_GRADE));
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseGrade(Optional.of(INVALID_GRADE)));
    }

    @Test
    public void parseGrade_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseGrade(Optional.empty()).isPresent());
    }

    @Test
    public void parseGrade_validValueWithoutWhitespace_returnsGrade() throws Exception {
        Grade expectedGrade = new Grade(VALID_GRADE);
        assertEquals(expectedGrade, ParserUtil.parseGrade(VALID_GRADE));
        assertEquals(Optional.of(expectedGrade), ParserUtil.parseGrade(Optional.of(VALID_GRADE)));
    }

    @Test
    public void parseGrade_validValueWithWhitespace_returnsTrimmedGrade() throws Exception {
        String gradeWithWhitespace = WHITESPACE + VALID_GRADE + WHITESPACE;
        Grade expectedGrade = new Grade(VALID_GRADE);
        assertEquals(expectedGrade, ParserUtil.parseGrade(gradeWithWhitespace));
        assertEquals(Optional.of(expectedGrade), ParserUtil.parseGrade(Optional.of(gradeWithWhitespace)));
    }

    @Test
    public void parseEducationLevel_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseEducationLevel((String) null));
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseEducationLevel((Optional<String>) null));
    }

    @Test
    public void parseEducationLevel_invalidValue_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseEducationLevel(
                INVALID_EDUCATIONAL_LEVEL));
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseEducationLevel(
                Optional.of(INVALID_EDUCATIONAL_LEVEL)));
    }

    @Test
    public void parseEducationLevel_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseEducationLevel(Optional.empty()).isPresent());
    }

    @Test
    public void parseEducationLevel_validValueWithoutWhitespace_returnsEducationLevel() throws Exception {
        EducationLevel expectedEducationLevel = new EducationLevel(VALID_EDUCATIONAL_LEVEL);
        assertEquals(expectedEducationLevel, ParserUtil.parseEducationLevel(VALID_EDUCATIONAL_LEVEL));
        assertEquals(Optional.of(expectedEducationLevel), ParserUtil.parseEducationLevel(
                Optional.of(VALID_EDUCATIONAL_LEVEL)));
    }

    @Test
    public void parseEducationLevel_validValueWithWhitespace_returnsTrimmedEducationLevel() throws Exception {
        String educationLevelWithWhitespace = WHITESPACE + VALID_EDUCATIONAL_LEVEL + WHITESPACE;
        EducationLevel expectedEducationLevel = new EducationLevel(VALID_EDUCATIONAL_LEVEL);
        assertEquals(expectedEducationLevel, ParserUtil.parseEducationLevel(educationLevelWithWhitespace));
        assertEquals(Optional.of(expectedEducationLevel), ParserUtil.parseEducationLevel(
                Optional.of(educationLevelWithWhitespace)));
    }

    @Test
    public void parseSchool_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseSchool((String) null));
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseSchool((Optional<String>) null));
    }

    @Test
    public void parseSchool_invalidValue_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseSchool(INVALID_SCHOOL));
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseSchool(Optional.of(INVALID_SCHOOL)));
    }

    @Test
    public void parseSchool_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseSchool(Optional.empty()).isPresent());
    }

    @Test
    public void parseSchool_validValueWithoutWhitespace_returnsSchool() throws Exception {
        School expectedSchool = new School(VALID_SCHOOL);
        assertEquals(expectedSchool, ParserUtil.parseSchool(VALID_SCHOOL));
        assertEquals(Optional.of(expectedSchool), ParserUtil.parseSchool(Optional.of(VALID_SCHOOL)));
    }

    @Test
    public void parseSchool_validValueWithWhitespace_returnsTrimmedSchool() throws Exception {
        String schoolWithWhitespace = WHITESPACE + VALID_SCHOOL + WHITESPACE;
        School expectedSchool = new School(VALID_SCHOOL);
        assertEquals(expectedSchool, ParserUtil.parseSchool(schoolWithWhitespace));
        assertEquals(Optional.of(expectedSchool), ParserUtil.parseSchool(Optional.of(schoolWithWhitespace)));
    }

    @Test
    public void parseTimeUnit_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseTimeUnit(null));
    }

    @Test
    public void parseTimeUnit_invalidValue_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseTimeUnit(INVALID_TIME_UNIT));
    }

    @Test
    public void parseTimeUnit_validValueWithoutWhitespace_returnsTimeUnit() throws Exception {
        String expectedTimeUnit = VALID_TIME_UNIT;
        assertEquals(expectedTimeUnit, ParserUtil.parseTimeUnit(VALID_TIME_UNIT));
    }

    @Test
    public void parseTimeUnit_validValueWithWhitespace_returnsTrimmedTimeUnit() throws Exception {
        String timeUnitWithWhitespace = WHITESPACE + VALID_TIME_UNIT + WHITESPACE;
        assertEquals(VALID_TIME_UNIT, ParserUtil.parseTimeUnit(timeUnitWithWhitespace));
    }

    //@@author yungyung04
    @Test
    public void parseDateTime_invalidInput_throwsDateTimeParseException() {
        //null date and time
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseDateTime(null));

        //invalid date in non leap year
        Assert.assertThrows(DateTimeParseException.class, () -> ParserUtil
                .parseDateTime("29/02/2018 " + VALID_TIME));

        //invalid date in century year
        Assert.assertThrows(DateTimeParseException.class, () -> ParserUtil
                .parseDateTime("29/02/1900 " + VALID_TIME));

        //invalid date in month with 30 days
        Assert.assertThrows(DateTimeParseException.class, () -> ParserUtil
                .parseDateTime("31/04/2018 " + VALID_TIME));

        //invalid date in month with 31 days
        Assert.assertThrows(DateTimeParseException.class, () -> ParserUtil
                .parseDateTime("32/03/2018 " + VALID_TIME));

        //invalid hour
        Assert.assertThrows(DateTimeParseException.class, () -> ParserUtil
                .parseDateTime(VALID_DATE + " 25:00"));

        //invalid minute
        Assert.assertThrows(DateTimeParseException.class, () -> ParserUtil
                .parseDateTime(VALID_DATE + "12:60"));
    }

    @Test
    public void parseDateTime_validInput_parsedSuccessfully() {
        //beginning of the month
        LocalDateTime expectedDateTime = LocalDateTime.parse("01/10/2018 " + VALID_TIME, FORMATTER);
        assertEquals(expectedDateTime, parseDateTime("01/10/2018 " + VALID_TIME));

        //leap year
        expectedDateTime = LocalDateTime.parse("29/02/2020 " + VALID_TIME, FORMATTER);
        assertEquals(expectedDateTime, parseDateTime("29/02/2020 " + VALID_TIME));

        //month with 30 days
        expectedDateTime = LocalDateTime.parse("30/04/2020 " + VALID_TIME, FORMATTER);
        assertEquals(expectedDateTime, parseDateTime("30/04/2020 " + VALID_TIME));

        //month with 31 days
        expectedDateTime = LocalDateTime.parse("31/03/2020 " + VALID_TIME, FORMATTER);
        assertEquals(expectedDateTime, parseDateTime("31/03/2020 " + VALID_TIME));

        //valid time at boundary value
        expectedDateTime = LocalDateTime.parse(VALID_DATE + " 12:00", FORMATTER);
        assertEquals(expectedDateTime, parseDateTime(VALID_DATE + " 12:00"));
    }

    @Test
    public void parseDuration_invalidInput_throwsDateTimeParseException() {
        //null duration
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseDuration(null));

        //invalid duration
        Assert.assertThrows(DurationParseException.class, () -> ParserUtil
                .parseDuration(INVALID_DURATION));
    }

    @Test
    public void parseDuration_validInput_parsedSuccessfully() throws Exception {
        String expectedDuration = VALID_DURATION;
        assertEquals(expectedDuration, parseDuration(VALID_DURATION));
    }

    @Test
    public void parseDescription_noDescriptionWithinInput_returnsEmptyString() {
        //user input without description
        String[] validInputs = VALID_TASK_WITHOUT_DESCRIPTION.split("\\s+", MAXIMUM_AMOUNT_OF_PARAMETERS);
        String expectedDescription = "";
        assertEquals(expectedDescription, ParserUtil.parseDescription(validInputs, MAXIMUM_AMOUNT_OF_PARAMETERS));

        //user input with description
        validInputs = VALID_TASK_WITH_DESCRIPTION.split("\\s+", MAXIMUM_AMOUNT_OF_PARAMETERS);
        expectedDescription = VALID_DESCRIPTION;
        assertEquals(expectedDescription, ParserUtil.parseDescription(validInputs, MAXIMUM_AMOUNT_OF_PARAMETERS));
    }
}
