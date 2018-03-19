package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DATE_TIME;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DURATION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_TIME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DURATION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMPTY_TASK_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_WITHOUT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_WITH_DESC;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

import org.junit.Test;

import seedu.address.logic.commands.AddTuitionTaskCommand;
import seedu.address.model.tutee.TuitionTask;

public class AddTuitionTaskCommandParserTest {
    private AddTuitionTaskCommandParser parser = new AddTuitionTaskCommandParser();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu HH:mm")
            .withResolverStyle(ResolverStyle.STRICT);

    @Test
    public void parse_validArgs_success() {
        // With description
        LocalDateTime taskDateTime = LocalDateTime.parse(VALID_DATE_TIME, formatter);
        TuitionTask tuition = new TuitionTask("dummy", taskDateTime, VALID_DURATION, VALID_TASK_DESC);
        assertParseSuccess(parser, "1 " + VALID_TASK_WITH_DESC,
                new AddTuitionTaskCommand(tuition, INDEX_FIRST_PERSON));

        // Without description
        tuition = new TuitionTask("dummy", taskDateTime, VALID_DURATION, VALID_EMPTY_TASK_DESC);
        assertParseSuccess(parser, "1 " + VALID_TASK_WITHOUT_DESC,
                new AddTuitionTaskCommand(tuition, INDEX_FIRST_PERSON));

        // Check leap year
        tuition = new TuitionTask("dummy", LocalDateTime.parse("29/02/2016 11:20", formatter),
                VALID_DURATION, VALID_EMPTY_TASK_DESC);
        assertParseSuccess(parser, "1 29/02/2016 11:20 1h11m",
                new AddTuitionTaskCommand(tuition, INDEX_FIRST_PERSON));
    }

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
