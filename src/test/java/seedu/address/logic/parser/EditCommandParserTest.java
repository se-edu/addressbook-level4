package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.address.logic.commands.EditCommand;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

public class EditCommandParserTest extends ParserTest {

    @Test
    public void parse_invalidValues_failure() {
        assertParseFailure("edit 1 *&", Name.MESSAGE_NAME_CONSTRAINTS);

        assertParseFailure("edit 1 p/abcd", Phone.MESSAGE_PHONE_CONSTRAINTS);

        assertParseFailure("edit 1 e/yahoo!!!", Email.MESSAGE_EMAIL_CONSTRAINTS);

        assertParseFailure("edit 1 a/", Address.MESSAGE_ADDRESS_CONSTRAINTS);

        assertParseFailure("edit 1 t/*&", Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    @Test
    public void parse_noFieldsSpecified_failure() {
        assertParseFailure("edit 1", EditCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void parse_missingPersonIndex_failure() {
        assertParseFailure("edit Bobby", String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
    }
}
