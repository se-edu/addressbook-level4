package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.DATEOFBIRTH_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DATEOFBIRTH_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.DEPARTMENT_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DEPARTMENT_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMPLOYEEID_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMPLOYEEID_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATEOFBIRTH_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DEPARTMENT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMPLOYEEID_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_POSITION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SALARY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.POSITION_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.POSITION_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.SALARY_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.SALARY_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATEOFBIRTH_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEPARTMENT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMPLOYEEID_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POSITION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SALARY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.person.Address;
import seedu.address.model.person.DateOfBirth;
import seedu.address.model.person.Department;
import seedu.address.model.person.Email;
import seedu.address.model.person.EmployeeId;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Position;
import seedu.address.model.person.Salary;
import seedu.address.model.person.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + EMPLOYEEID_DESC_BOB
                + NAME_DESC_BOB + DATEOFBIRTH_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + DEPARTMENT_DESC_BOB
                + POSITION_DESC_BOB + ADDRESS_DESC_BOB + SALARY_DESC_BOB
                + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple names - last name accepted
        assertParseSuccess(parser, EMPLOYEEID_DESC_BOB
                + NAME_DESC_AMY + NAME_DESC_BOB + DATEOFBIRTH_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + DEPARTMENT_DESC_BOB + POSITION_DESC_BOB + ADDRESS_DESC_BOB + SALARY_DESC_BOB
                + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, EMPLOYEEID_DESC_BOB
                + NAME_DESC_BOB + DATEOFBIRTH_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + DEPARTMENT_DESC_BOB + POSITION_DESC_BOB + ADDRESS_DESC_BOB + SALARY_DESC_BOB
                + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple emails - last email accepted
        assertParseSuccess(parser, EMPLOYEEID_DESC_BOB
                + NAME_DESC_BOB + DATEOFBIRTH_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_AMY + EMAIL_DESC_BOB
                + DEPARTMENT_DESC_BOB + POSITION_DESC_BOB + ADDRESS_DESC_BOB + SALARY_DESC_BOB
                + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, EMPLOYEEID_DESC_BOB
                + NAME_DESC_BOB + DATEOFBIRTH_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + DEPARTMENT_DESC_BOB + POSITION_DESC_BOB + ADDRESS_DESC_AMY + ADDRESS_DESC_BOB + SALARY_DESC_BOB
                + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple tags - all accepted
        Person expectedPersonMultipleTags = new PersonBuilder(BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        assertParseSuccess(parser, EMPLOYEEID_DESC_BOB
                + NAME_DESC_BOB + DATEOFBIRTH_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + DEPARTMENT_DESC_BOB
                + POSITION_DESC_BOB + ADDRESS_DESC_BOB + SALARY_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, new AddCommand(expectedPersonMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Person expectedPerson = new PersonBuilder(AMY).withTags().build();
        assertParseSuccess(parser, EMPLOYEEID_DESC_AMY + NAME_DESC_AMY + DATEOFBIRTH_DESC_AMY
                + PHONE_DESC_AMY + EMAIL_DESC_AMY + DEPARTMENT_DESC_AMY + POSITION_DESC_AMY
                + ADDRESS_DESC_AMY + SALARY_DESC_AMY, new AddCommand(expectedPerson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing employeeid prefix
        assertParseFailure(parser, VALID_EMPLOYEEID_BOB + NAME_DESC_BOB + DATEOFBIRTH_DESC_BOB
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + DEPARTMENT_DESC_BOB + POSITION_DESC_BOB
                + ADDRESS_DESC_BOB + SALARY_DESC_BOB, expectedMessage);

        // missing name prefix
        assertParseFailure(parser, EMPLOYEEID_DESC_BOB + VALID_NAME_BOB + DATEOFBIRTH_DESC_BOB
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + DEPARTMENT_DESC_BOB + POSITION_DESC_BOB
                + ADDRESS_DESC_BOB + SALARY_DESC_BOB, expectedMessage);

        // missing date of birth prefix
        assertParseFailure(parser, EMPLOYEEID_DESC_BOB + NAME_DESC_BOB + VALID_DATEOFBIRTH_BOB
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + DEPARTMENT_DESC_BOB + POSITION_DESC_BOB
                + ADDRESS_DESC_BOB + SALARY_DESC_BOB, expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, EMPLOYEEID_DESC_BOB + NAME_DESC_BOB + DATEOFBIRTH_DESC_BOB
                + VALID_PHONE_BOB + DEPARTMENT_DESC_BOB + POSITION_DESC_BOB
                + ADDRESS_DESC_BOB + SALARY_DESC_BOB, expectedMessage);

        // missing email prefix
        assertParseFailure(parser, EMPLOYEEID_DESC_BOB + NAME_DESC_BOB + DATEOFBIRTH_DESC_BOB
                + PHONE_DESC_BOB + VALID_EMAIL_BOB + DEPARTMENT_DESC_BOB + POSITION_DESC_BOB
                + ADDRESS_DESC_BOB + SALARY_DESC_BOB, expectedMessage);

        // missing department prefix
        assertParseFailure(parser, EMPLOYEEID_DESC_BOB + NAME_DESC_BOB + DATEOFBIRTH_DESC_BOB
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + VALID_DEPARTMENT_BOB + POSITION_DESC_BOB
                + ADDRESS_DESC_BOB + SALARY_DESC_BOB, expectedMessage);

        // missing position prefix
        assertParseFailure(parser, EMPLOYEEID_DESC_BOB + NAME_DESC_BOB + DATEOFBIRTH_DESC_BOB
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + DEPARTMENT_DESC_BOB + VALID_POSITION_BOB
                + ADDRESS_DESC_BOB + SALARY_DESC_BOB, expectedMessage);

        // missing address prefix
        assertParseFailure(parser, EMPLOYEEID_DESC_BOB + NAME_DESC_BOB + DATEOFBIRTH_DESC_BOB
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + DEPARTMENT_DESC_BOB + POSITION_DESC_BOB
                + VALID_ADDRESS_BOB + SALARY_DESC_BOB, expectedMessage);

        // missing position prefix
        assertParseFailure(parser, EMPLOYEEID_DESC_BOB + NAME_DESC_BOB + DATEOFBIRTH_DESC_BOB
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + DEPARTMENT_DESC_BOB + POSITION_DESC_BOB
                + ADDRESS_DESC_BOB + VALID_SALARY_BOB, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, EMPLOYEEID_DESC_BOB + VALID_NAME_BOB + VALID_DATEOFBIRTH_BOB
                + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_DEPARTMENT_BOB + VALID_POSITION_BOB
                + VALID_ADDRESS_BOB + VALID_SALARY_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid emplyoeeid
        assertParseFailure(parser, EMPLOYEEID_DESC_BOB + INVALID_EMPLOYEEID_DESC + NAME_DESC_BOB
                + DATEOFBIRTH_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + DEPARTMENT_DESC_BOB + POSITION_DESC_BOB
                + ADDRESS_DESC_BOB + SALARY_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, EmployeeId.MESSAGE_EMPLOYEEID_CONSTRAINTS);

        // invalid name
        assertParseFailure(parser, EMPLOYEEID_DESC_BOB + INVALID_NAME_DESC + DATEOFBIRTH_DESC_BOB
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + DEPARTMENT_DESC_BOB + POSITION_DESC_BOB
                + ADDRESS_DESC_BOB + SALARY_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid date of birth
        assertParseFailure(parser, EMPLOYEEID_DESC_BOB + NAME_DESC_BOB + INVALID_DATEOFBIRTH_DESC
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + DEPARTMENT_DESC_BOB + POSITION_DESC_BOB
                + ADDRESS_DESC_BOB + SALARY_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, DateOfBirth.MESSAGE_DATEOFBIRTH_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, EMPLOYEEID_DESC_BOB + NAME_DESC_BOB + DATEOFBIRTH_DESC_BOB
                + INVALID_PHONE_DESC + EMAIL_DESC_BOB + DEPARTMENT_DESC_BOB + POSITION_DESC_BOB
                + ADDRESS_DESC_BOB + SALARY_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Phone.MESSAGE_PHONE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, EMPLOYEEID_DESC_BOB + NAME_DESC_BOB + DATEOFBIRTH_DESC_BOB
                + PHONE_DESC_BOB + INVALID_EMAIL_DESC + DEPARTMENT_DESC_BOB + POSITION_DESC_BOB
                + ADDRESS_DESC_BOB + SALARY_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Email.MESSAGE_EMAIL_CONSTRAINTS);

        // invalid department
        assertParseFailure(parser, EMPLOYEEID_DESC_BOB + NAME_DESC_BOB + DATEOFBIRTH_DESC_BOB
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_DEPARTMENT_DESC + POSITION_DESC_BOB
                + ADDRESS_DESC_BOB + SALARY_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Department.MESSAGE_DEPARTMENT_CONSTRAINTS);

        // invalid position
        assertParseFailure(parser, EMPLOYEEID_DESC_BOB + NAME_DESC_BOB + DATEOFBIRTH_DESC_BOB
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + DEPARTMENT_DESC_BOB + INVALID_POSITION_DESC
                + ADDRESS_DESC_BOB + SALARY_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Position.MESSAGE_POSITION_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, EMPLOYEEID_DESC_BOB + NAME_DESC_BOB + DATEOFBIRTH_DESC_BOB
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + DEPARTMENT_DESC_BOB + POSITION_DESC_BOB
                + INVALID_ADDRESS_DESC + SALARY_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Address.MESSAGE_ADDRESS_CONSTRAINTS);

        // invalid salary
        assertParseFailure(parser, EMPLOYEEID_DESC_BOB + NAME_DESC_BOB + DATEOFBIRTH_DESC_BOB
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + DEPARTMENT_DESC_BOB + POSITION_DESC_BOB
                + ADDRESS_DESC_BOB + INVALID_SALARY_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Salary.MESSAGE_SALARY_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, EMPLOYEEID_DESC_BOB + NAME_DESC_BOB + DATEOFBIRTH_DESC_BOB
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + DEPARTMENT_DESC_BOB + POSITION_DESC_BOB
                + ADDRESS_DESC_BOB + SALARY_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_TAG_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, EMPLOYEEID_DESC_BOB + INVALID_NAME_DESC + DATEOFBIRTH_DESC_BOB
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + DEPARTMENT_DESC_BOB + POSITION_DESC_BOB
                + INVALID_ADDRESS_DESC + SALARY_DESC_BOB, Name.MESSAGE_NAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + EMPLOYEEID_DESC_BOB + NAME_DESC_BOB
                + DATEOFBIRTH_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + DEPARTMENT_DESC_BOB + POSITION_DESC_BOB
                + ADDRESS_DESC_BOB + SALARY_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
