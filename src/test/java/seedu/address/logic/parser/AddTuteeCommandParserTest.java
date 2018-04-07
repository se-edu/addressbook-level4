package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EDUCATION_LEVEL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EDUCATION_LEVEL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.GRADE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.GRADE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EDUCATION_LEVEL;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GRADE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SCHOOL;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SUBJECT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.SCHOOL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.SCHOOL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.SUBJECT_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.SUBJECT_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EDUCATION_LEVEL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EDUCATION_LEVEL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SCHOOL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SCHOOL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.AddTuteeCommand;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.tutee.EducationLevel;
import seedu.address.model.tutee.Grade;
import seedu.address.model.tutee.School;
import seedu.address.model.tutee.Subject;
import seedu.address.model.tutee.Tutee;
import seedu.address.testutil.TuteeBuilder;

//@@author ChoChihTun
public class AddTuteeCommandParserTest {
    private AddTuteeCommandParser parser = new AddTuteeCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Tutee expectedTutee = new TuteeBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withSubject(VALID_SUBJECT_BOB)
                .withGrade(VALID_GRADE_BOB).withEducationLevel(VALID_EDUCATION_LEVEL_BOB).withSchool(VALID_SCHOOL_BOB)
                .withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + SUBJECT_DESC_BOB + GRADE_DESC_BOB + EDUCATION_LEVEL_DESC_BOB
                + SCHOOL_DESC_BOB + TAG_DESC_FRIEND, new AddTuteeCommand(expectedTutee));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + SUBJECT_DESC_BOB + GRADE_DESC_BOB + EDUCATION_LEVEL_DESC_BOB
                + SCHOOL_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_FRIEND, new AddTuteeCommand(expectedTutee));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + SUBJECT_DESC_BOB + GRADE_DESC_BOB + EDUCATION_LEVEL_DESC_BOB
                + SCHOOL_DESC_BOB + TAG_DESC_FRIEND, new AddTuteeCommand(expectedTutee));

        // multiple emails - last email accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_AMY + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + SUBJECT_DESC_BOB + GRADE_DESC_BOB + EDUCATION_LEVEL_DESC_BOB
                + SCHOOL_DESC_BOB + TAG_DESC_FRIEND, new AddTuteeCommand(expectedTutee));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_AMY
                + ADDRESS_DESC_BOB + SUBJECT_DESC_BOB + GRADE_DESC_BOB + EDUCATION_LEVEL_DESC_BOB
                + SCHOOL_DESC_BOB + TAG_DESC_FRIEND, new AddTuteeCommand(expectedTutee));

        // multiple subjects - last subject accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + SUBJECT_DESC_AMY + SUBJECT_DESC_BOB + GRADE_DESC_BOB + EDUCATION_LEVEL_DESC_BOB
                + SCHOOL_DESC_BOB + TAG_DESC_FRIEND, new AddTuteeCommand(expectedTutee));

        // multiple grades - last grade accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + SUBJECT_DESC_BOB + GRADE_DESC_AMY + GRADE_DESC_BOB + EDUCATION_LEVEL_DESC_BOB
                + SCHOOL_DESC_BOB + TAG_DESC_FRIEND, new AddTuteeCommand(expectedTutee));

        // multiple education levels - last education level accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + SUBJECT_DESC_BOB + GRADE_DESC_BOB + EDUCATION_LEVEL_DESC_AMY + EDUCATION_LEVEL_DESC_BOB
                + SCHOOL_DESC_BOB + TAG_DESC_FRIEND, new AddTuteeCommand(expectedTutee));

        // multiple schools - last school accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + SUBJECT_DESC_BOB + GRADE_DESC_BOB + EDUCATION_LEVEL_DESC_BOB + SCHOOL_DESC_AMY
                + SCHOOL_DESC_BOB + TAG_DESC_FRIEND, new AddTuteeCommand(expectedTutee));

        // multiple tags - all accepted
        Tutee expectedTuteeMultipleTags = new TuteeBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withSubject(VALID_SUBJECT_BOB)
                .withGrade(VALID_GRADE_BOB).withEducationLevel(VALID_EDUCATION_LEVEL_BOB).withSchool(VALID_SCHOOL_BOB)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND).build();
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + SUBJECT_DESC_BOB + GRADE_DESC_BOB + EDUCATION_LEVEL_DESC_BOB + SCHOOL_DESC_BOB + TAG_DESC_FRIEND
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, new AddTuteeCommand(expectedTuteeMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Tutee expectedTutee = new TuteeBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withSubject(VALID_SUBJECT_AMY)
                .withGrade(VALID_GRADE_AMY).withEducationLevel(VALID_EDUCATION_LEVEL_AMY)
                .withSchool(VALID_SCHOOL_AMY).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                        + SUBJECT_DESC_AMY + GRADE_DESC_AMY + EDUCATION_LEVEL_DESC_AMY + SCHOOL_DESC_AMY,
                new AddTuteeCommand(expectedTutee));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTuteeCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + SUBJECT_DESC_BOB + GRADE_DESC_BOB + EDUCATION_LEVEL_DESC_BOB + SCHOOL_DESC_BOB,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + SUBJECT_DESC_BOB + GRADE_DESC_BOB + EDUCATION_LEVEL_DESC_BOB + SCHOOL_DESC_BOB,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB + ADDRESS_DESC_BOB
                        + SUBJECT_DESC_BOB + GRADE_DESC_BOB + EDUCATION_LEVEL_DESC_BOB + SCHOOL_DESC_BOB,
                expectedMessage);

        // missing address prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + VALID_ADDRESS_BOB
                        + SUBJECT_DESC_BOB + GRADE_DESC_BOB + EDUCATION_LEVEL_DESC_BOB + SCHOOL_DESC_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_ADDRESS_BOB
                        + SUBJECT_DESC_BOB + GRADE_DESC_BOB + EDUCATION_LEVEL_DESC_BOB + SCHOOL_DESC_BOB,
                expectedMessage);

        // missing subject prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + GRADE_DESC_BOB + EDUCATION_LEVEL_DESC_BOB + SCHOOL_DESC_BOB,
                expectedMessage);

        // missing grade prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + SUBJECT_DESC_BOB + EDUCATION_LEVEL_DESC_BOB + SCHOOL_DESC_BOB,
                expectedMessage);

        // missing education level prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + SUBJECT_DESC_BOB + GRADE_DESC_BOB + SCHOOL_DESC_BOB,
                expectedMessage);

        // missing school prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + SUBJECT_DESC_BOB + GRADE_DESC_BOB + EDUCATION_LEVEL_DESC_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + SUBJECT_DESC_BOB + GRADE_DESC_BOB + EDUCATION_LEVEL_DESC_BOB + SCHOOL_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + SUBJECT_DESC_BOB + GRADE_DESC_BOB + EDUCATION_LEVEL_DESC_BOB + SCHOOL_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Phone.MESSAGE_PHONE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB
                + SUBJECT_DESC_BOB + GRADE_DESC_BOB + EDUCATION_LEVEL_DESC_BOB + SCHOOL_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Email.MESSAGE_EMAIL_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                + SUBJECT_DESC_BOB + GRADE_DESC_BOB + EDUCATION_LEVEL_DESC_BOB + SCHOOL_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Address.MESSAGE_ADDRESS_CONSTRAINTS);

        // invalid subject
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INVALID_SUBJECT_DESC + GRADE_DESC_BOB + EDUCATION_LEVEL_DESC_BOB + SCHOOL_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Subject.MESSAGE_SUBJECT_CONSTRAINTS);

        // invalid grade
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + SUBJECT_DESC_BOB + INVALID_GRADE_DESC + EDUCATION_LEVEL_DESC_BOB + SCHOOL_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Grade.MESSAGE_GRADE_CONSTRAINTS);

        // invalid education level
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + SUBJECT_DESC_BOB + GRADE_DESC_BOB + INVALID_EDUCATION_LEVEL + SCHOOL_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, EducationLevel.MESSAGE_EDUCATION_LEVEL_CONSTRAINTS);

        // invalid school
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + SUBJECT_DESC_BOB + GRADE_DESC_BOB + EDUCATION_LEVEL_DESC_BOB + INVALID_SCHOOL
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, School.MESSAGE_SCHOOL_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + SUBJECT_DESC_BOB + GRADE_DESC_BOB + EDUCATION_LEVEL_DESC_BOB + SCHOOL_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_TAG_CONSTRAINTS);

        // three invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                        + SUBJECT_DESC_BOB + GRADE_DESC_BOB + INVALID_EDUCATION_LEVEL + SCHOOL_DESC_BOB,
                Name.MESSAGE_NAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB + SUBJECT_DESC_BOB + GRADE_DESC_BOB + INVALID_EDUCATION_LEVEL
                        + SCHOOL_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTuteeCommand.MESSAGE_USAGE));
    }
}
