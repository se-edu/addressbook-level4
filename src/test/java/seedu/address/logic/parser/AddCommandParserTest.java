package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.ParserTestUtil;

/**
 * Tests the parsing of AddCommand.
 */
public class AddCommandParserTest {
    @Test
    public void parse_validInput_success() {
        // no tags
        ParserTestUtil.assertParseSuccess(
                "add Betsy Crowe p/1234567 a/Newgate Prison e/betsycrowe@example.com");

        // leading and trailing whitespace
        ParserTestUtil.assertParseSuccess(
                "    add Betsy Crowe p/1234567 a/Newgate Prison e/betsycrowe@example.com    ");

        // single tag
        ParserTestUtil.assertParseSuccess(
                "add Betsy Crowe e/betsycrowe@example.com t/friend a/Newgate Prison p/1234567");

        // multiple non-sequential tags
        ParserTestUtil.assertParseSuccess(
                "add Betsy Crowe p/1234567 t/friend e/betsycrowe@example.com t/criminal a/Newgate Prison");

        // multiple sequential tags
        ParserTestUtil.assertParseSuccess(
                "add Betsy Crowe t/criminal t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 ");
    }

    @Test
    public void parse_invalidArgsFormat_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
        ParserTestUtil.assertParseFailure("add wrong args wrong args", expectedMessage);
        ParserTestUtil.assertParseFailure(
                "add Valid Name 12345 e/valid@email.butNoPhonePrefix a/valid,address", expectedMessage);
        ParserTestUtil.assertParseFailure(
                "add Valid Name p/12345 valid@email.butNoPrefix a/valid, address", expectedMessage);
        ParserTestUtil.assertParseFailure(
                "add Valid Name p/12345 e/valid@email.butNoAddressPrefix valid, address", expectedMessage);
    }

    @Test
    public void parse_invalidPersonData_failure() {
        ParserTestUtil.assertParseFailure("add []\\[;] p/12345 e/valid@e.mail a/valid, address",
                Name.MESSAGE_NAME_CONSTRAINTS);
        ParserTestUtil.assertParseFailure("add Valid Name p/not_numbers e/valid@e.mail a/valid, address",
                Phone.MESSAGE_PHONE_CONSTRAINTS);
        ParserTestUtil.assertParseFailure("add Valid Name p/12345 e/notAnEmail a/valid, address",
                Email.MESSAGE_EMAIL_CONSTRAINTS);
        ParserTestUtil.assertParseFailure("add Valid Name p/12345 e/valid@e.mail a/valid, address t/invalid_-[.tag",
                Tag.MESSAGE_TAG_CONSTRAINTS);
    }
}
