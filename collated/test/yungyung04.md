# yungyung04
###### \java\seedu\address\logic\parser\FindPersonCommandParserTest.java
``` java
public class FindPersonCommandParserTest {
    private static final int INDEX_FIRST_ELEMENT = 0;
    public static final String VALID_FIRST_NAME_BOB = VALID_NAME_BOB.toLowerCase().split("\\s+")[INDEX_FIRST_ELEMENT];
    private FindPersonCommandParser parser = new FindPersonCommandParser();

    private final String[] nameKeywords = {VALID_FIRST_NAME_BOB};
    private final String[] educationLevelKeywords = {VALID_EDUCATION_LEVEL_AMY.toLowerCase()};
    private final String[] gradeKeywords = {VALID_GRADE_AMY.toLowerCase(), VALID_GRADE_BOB.toLowerCase()};
    private final String[] schoolKeywords = VALID_SCHOOL_AMY.toLowerCase().split("\\s+");
    private final String[] subjectKeywords = {VALID_SUBJECT_AMY.toLowerCase(), VALID_SUBJECT_BOB.toLowerCase()};

    private final String invalidCategory = "age";

    @Test
    public void parse_invalidArg_throwsParseException() {
        //empty input
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPersonCommand.MESSAGE_USAGE));

        //not enough arguments
        assertParseFailure(parser, CATEGORY_GRADE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPersonCommand.MESSAGE_USAGE));

        //invalid category
        assertParseFailure(parser, invalidCategory + " " + schoolKeywords[INDEX_FIRST_ELEMENT],
                String.format(MESSAGE_INVALID_FILTER_CATEGORY, FindPersonCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // filter by name using a single keyword
        FindPersonCommand expectedFindName = new FindPersonCommand(CATEGORY_NAME, nameKeywords);
        assertParseSuccess(parser, CATEGORY_NAME + " Bob", expectedFindName);

        // filter by education level using a single keyword
        FindPersonCommand expectedFindEducatonLevel =
                new FindPersonCommand(CATEGORY_EDUCATION_LEVEL, educationLevelKeywords);
        assertParseSuccess(parser,
                CATEGORY_EDUCATION_LEVEL + " " + VALID_EDUCATION_LEVEL_AMY, expectedFindEducatonLevel);

        // filter by grade using 2 different keywords
        FindPersonCommand expectedFindGrade = new FindPersonCommand(CATEGORY_GRADE, gradeKeywords);
        assertParseSuccess(parser, CATEGORY_GRADE + " " + VALID_GRADE_AMY
                + " " + VALID_GRADE_BOB, expectedFindGrade);

        // filter by school using multiple keywords from a single school
        FindPersonCommand expectedFindSchool = new FindPersonCommand(CATEGORY_SCHOOL, schoolKeywords);
        assertParseSuccess(parser, CATEGORY_SCHOOL + " " + VALID_SCHOOL_AMY, expectedFindSchool);

        // filter by subject using 2 different keywords
        FindPersonCommand expectedFindSubject = new FindPersonCommand(CATEGORY_SUBJECT, subjectKeywords);
        assertParseSuccess(parser, CATEGORY_SUBJECT + " " + VALID_SUBJECT_AMY
                + " " + VALID_SUBJECT_BOB, expectedFindSubject);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, CATEGORY_NAME + " \n\t  " + "Bob", expectedFindName);
    }
}
```
###### \java\seedu\address\logic\parser\ParserUtilTest.java
``` java
    @Test
    public void parseDateTime_invalidInput_throwsDateTimeParseException() {
        //null date and time
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseDateTime(null));

        //invalid date
        Assert.assertThrows(DateTimeParseException.class, () -> ParserUtil
                .parseDateTime(INVALID_DATE_END_OF_FEBRUARY + VALID_TIME));

        Assert.assertThrows(DateTimeParseException.class, () -> ParserUtil
                .parseDateTime(INVALID_DATE_END_OF_APRIL + VALID_TIME));

        //invalid time
        Assert.assertThrows(DateTimeParseException.class, () -> ParserUtil
                .parseDateTime(VALID_DATE + INVALID_TIME));
    }

    @Test
    public void parseDateTime_validInput_parsedSuccessfully() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu HH:mm")
                .withResolverStyle(ResolverStyle.STRICT);
        LocalDateTime expectedDateTime = LocalDateTime.parse(VALID_DATE + " " + VALID_TIME, formatter);

        assertEquals(expectedDateTime, parseDateTime(VALID_DATE + " " + VALID_TIME));
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
```
###### \java\seedu\address\logic\parser\SortCommandParserTest.java
``` java
public class SortCommandParserTest {
    private SortCommandParser parser = new SortCommandParser();

    private final String invalidCategory = "age";

    @Test
    public void parse_invalidArg_throwsParseException() {
        //empty input
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));

        //too many arguments
        assertParseFailure(parser, CATEGORY_GRADE + " " + CATEGORY_EDUCATION_LEVEL,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));

        //invalid category
        assertParseFailure(parser, invalidCategory,
                String.format(MESSAGE_INVALID_SORTER_CATEGORY, SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // sort by name
        SortCommand expectedSortName = new SortCommand(CATEGORY_NAME);
        assertParseSuccess(parser, CATEGORY_NAME, expectedSortName);

        // sort by education level
        SortCommand expectedSortEducatonLevel = new SortCommand(CATEGORY_EDUCATION_LEVEL);
        assertParseSuccess(parser, CATEGORY_EDUCATION_LEVEL, expectedSortEducatonLevel);

        // sort by grade
        SortCommand expectedSortGrade = new SortCommand(CATEGORY_GRADE);
        assertParseSuccess(parser, CATEGORY_GRADE, expectedSortGrade);

        // sort by school
        SortCommand expectedSortSchool = new SortCommand(CATEGORY_SCHOOL);
        assertParseSuccess(parser, CATEGORY_SCHOOL, expectedSortSchool);

        // sort by subject
        SortCommand expectedSortSubject = new SortCommand(CATEGORY_SUBJECT);
        assertParseSuccess(parser, CATEGORY_SUBJECT, expectedSortSubject);

        // multiple whitespaces before and after sort category
        assertParseSuccess(parser, "   \n\t" + CATEGORY_NAME + "\n\t", expectedSortName);
    }
}
```
###### \java\seedu\address\storage\XmlAdaptedPersonTest.java
``` java
    //=========== Tutee Related Tests =============================================================

    @Test
    public void toModelType_validTuteeDetails_returnsTutee() throws Exception {
        XmlAdaptedPerson tutee = new XmlAdaptedPerson(ALICETUTEE);
        assertEquals(ALICETUTEE, tutee.toModelType());
    }

    @Test
    public void toModelType_invalidTuteeName_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(INVALID_NAME, VALID_TUTEE_PHONE, VALID_TUTEE_EMAIL, VALID_TUTEE_ADDRESS,
                        VALID_TUTEE_SUBJECT, VALID_TUTEE_GRADE, VALID_TUTEE_EDUCATION_LEVEL,
                        VALID_TUTEE_SCHOOL, VALID_TUTEE_TAGS);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullTuteeName_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(null, VALID_TUTEE_PHONE, VALID_TUTEE_EMAIL, VALID_TUTEE_ADDRESS,
                        VALID_TUTEE_SUBJECT, VALID_TUTEE_GRADE, VALID_TUTEE_EDUCATION_LEVEL,
                        VALID_TUTEE_SCHOOL, VALID_TUTEE_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTuteePhone_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_TUTEE_NAME, INVALID_PHONE, VALID_TUTEE_EMAIL, VALID_TUTEE_ADDRESS,
                        VALID_TUTEE_SUBJECT, VALID_TUTEE_GRADE, VALID_TUTEE_EDUCATION_LEVEL,
                        VALID_TUTEE_SCHOOL, VALID_TUTEE_TAGS);
        String expectedMessage = Phone.MESSAGE_PHONE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullTuteePhone_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_TUTEE_NAME, null, VALID_TUTEE_EMAIL, VALID_TUTEE_ADDRESS,
                        VALID_TUTEE_SUBJECT, VALID_TUTEE_GRADE, VALID_TUTEE_EDUCATION_LEVEL,
                        VALID_TUTEE_SCHOOL, VALID_TUTEE_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTuteeEmail_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_TUTEE_NAME, VALID_TUTEE_PHONE, INVALID_EMAIL, VALID_TUTEE_ADDRESS,
                        VALID_TUTEE_SUBJECT, VALID_TUTEE_GRADE, VALID_TUTEE_EDUCATION_LEVEL,
                        VALID_TUTEE_SCHOOL, VALID_TUTEE_TAGS);
        String expectedMessage = Email.MESSAGE_EMAIL_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullTuteeEmail_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_TUTEE_NAME, VALID_TUTEE_PHONE, null, VALID_TUTEE_ADDRESS,
                        VALID_TUTEE_SUBJECT, VALID_TUTEE_GRADE, VALID_TUTEE_EDUCATION_LEVEL,
                        VALID_TUTEE_SCHOOL, VALID_TUTEE_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTuteeAddress_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_TUTEE_NAME, VALID_TUTEE_PHONE, VALID_TUTEE_EMAIL, INVALID_ADDRESS,
                        VALID_TUTEE_SUBJECT, VALID_TUTEE_GRADE, VALID_TUTEE_EDUCATION_LEVEL,
                        VALID_TUTEE_SCHOOL, VALID_TUTEE_TAGS);
        String expectedMessage = Address.MESSAGE_ADDRESS_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullTuteeAddress_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_TUTEE_NAME, VALID_TUTEE_PHONE, VALID_TUTEE_EMAIL, null,
                        VALID_TUTEE_SUBJECT, VALID_TUTEE_GRADE, VALID_TUTEE_EDUCATION_LEVEL,
                        VALID_TUTEE_SCHOOL, VALID_TUTEE_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidSubject_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_TUTEE_NAME, VALID_TUTEE_PHONE, VALID_TUTEE_EMAIL, VALID_TUTEE_ADDRESS,
                        INVALID_SUBJECT, VALID_TUTEE_GRADE, VALID_TUTEE_EDUCATION_LEVEL,
                        VALID_TUTEE_SCHOOL, VALID_TUTEE_TAGS);
        String expectedMessage = Subject.MESSAGE_SUBJECT_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullSubject_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_TUTEE_NAME, VALID_TUTEE_PHONE, VALID_TUTEE_EMAIL, VALID_TUTEE_ADDRESS,
                null, VALID_TUTEE_GRADE, VALID_TUTEE_EDUCATION_LEVEL,
                VALID_TUTEE_SCHOOL, VALID_TUTEE_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Subject.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidGrade_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_TUTEE_NAME, VALID_TUTEE_PHONE, VALID_TUTEE_EMAIL, VALID_TUTEE_ADDRESS,
                        VALID_TUTEE_SUBJECT, INVALID_GRADE, VALID_TUTEE_EDUCATION_LEVEL,
                        VALID_TUTEE_SCHOOL, VALID_TUTEE_TAGS);
        String expectedMessage = Grade.MESSAGE_GRADE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullGrade_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_TUTEE_NAME, VALID_TUTEE_PHONE, VALID_TUTEE_EMAIL, VALID_TUTEE_ADDRESS,
                VALID_TUTEE_SUBJECT, null, VALID_TUTEE_EDUCATION_LEVEL,
                VALID_TUTEE_SCHOOL, VALID_TUTEE_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Grade.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEducationLevel_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_TUTEE_NAME, VALID_TUTEE_PHONE, VALID_TUTEE_EMAIL, VALID_TUTEE_ADDRESS,
                        VALID_TUTEE_SUBJECT, VALID_TUTEE_GRADE, INVALID_EDUCATION_LEVEL,
                        VALID_TUTEE_SCHOOL, VALID_TUTEE_TAGS);
        String expectedMessage = EducationLevel.MESSAGE_EDUCATION_LEVEL_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEducationLevel_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_TUTEE_NAME, VALID_TUTEE_PHONE, VALID_TUTEE_EMAIL, VALID_TUTEE_ADDRESS,
                VALID_TUTEE_SUBJECT, VALID_TUTEE_GRADE, null,
                VALID_TUTEE_SCHOOL, VALID_TUTEE_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, EducationLevel.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidSchool_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_TUTEE_NAME, VALID_TUTEE_PHONE, VALID_TUTEE_EMAIL, VALID_TUTEE_ADDRESS,
                        VALID_TUTEE_SUBJECT, VALID_TUTEE_GRADE, VALID_TUTEE_EDUCATION_LEVEL,
                        INVALID_SCHOOL, VALID_TUTEE_TAGS);
        String expectedMessage = School.MESSAGE_SCHOOL_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullSchool_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_TUTEE_NAME, VALID_TUTEE_PHONE, VALID_TUTEE_EMAIL, VALID_TUTEE_ADDRESS,
                VALID_TUTEE_SUBJECT, VALID_TUTEE_GRADE, VALID_TUTEE_EDUCATION_LEVEL,
                null, VALID_TUTEE_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, School.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_tuteeHasInvalidTags_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TUTEE_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_TUTEE_NAME, VALID_TUTEE_PHONE, VALID_TUTEE_EMAIL, VALID_TUTEE_ADDRESS,
                VALID_TUTEE_SUBJECT, VALID_TUTEE_GRADE, VALID_TUTEE_EDUCATION_LEVEL,
                VALID_TUTEE_SCHOOL, invalidTags);
        Assert.assertThrows(IllegalValueException.class, person::toModelType);
    }
}
```
