package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DATE_TIME;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DURATION;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.testutil.TypicalTutees.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.commands.AddTuitionTaskCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class AddTuitionTaskCommandParserTest {
    private AddTuitionTaskCommandParser parser = new AddTuitionTaskCommandParser();

    //@@author ChoChihTun
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Invalid format
        assertParseFailure(parser, "1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTuitionTaskCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "11/01/2018 11:11 1h30m tuition homework",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTuitionTaskCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1 11:11 1h30m tuition homework",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTuitionTaskCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1 11/01/2018 1h30m tuition homework",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTuitionTaskCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1 11/01/2018 11:11 tuition homework",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTuitionTaskCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1 11/01/2018 11:11 1h tuition homework",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTuitionTaskCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1 11/01/2018 11:11 30m tuition homework",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTuitionTaskCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1 11:11 32/01/2018 1h30m tuition homework",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTuitionTaskCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "aaa 32/01/2018 11:11 1h30m tuition homework",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTuitionTaskCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1 aa/01/2018 11:11 1h30m tuition homework",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTuitionTaskCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1 32/01/2018 11:aa 1h30m tuition homework",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTuitionTaskCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1 32/01/2018 11:11 1haam tuition homework",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTuitionTaskCommand.MESSAGE_USAGE));

        // Invalid date
        assertParseFailure(parser, "1 32/01/2018 11:11 1h30m tuition homework",
                MESSAGE_INVALID_DATE_TIME + "\n" + AddTuitionTaskCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "1 29/02/2018 11:11 1h30m tuition homework",
                MESSAGE_INVALID_DATE_TIME + "\n" + AddTuitionTaskCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "1 31/04/2018 11:11 1h30m tuition homework",
                MESSAGE_INVALID_DATE_TIME + "\n" + AddTuitionTaskCommand.MESSAGE_USAGE);

        // Invalid time
        assertParseFailure(parser, "1 11/01/2018 31:11 1h30m tuition homework",
                MESSAGE_INVALID_DATE_TIME + "\n" + AddTuitionTaskCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "1 11/01/2018 11:60 1h30m tuition homework",
                MESSAGE_INVALID_DATE_TIME + "\n" + AddTuitionTaskCommand.MESSAGE_USAGE);

        // Invalid duration
        assertParseFailure(parser, "1 11/01/2018 11:11 1h60m tuition homework",
                MESSAGE_INVALID_DURATION + "\n" + AddTuitionTaskCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "1 11/01/2018 11:11 24h0m tuition homework",
                MESSAGE_INVALID_DURATION + "\n" + AddTuitionTaskCommand.MESSAGE_USAGE);
    }
}
