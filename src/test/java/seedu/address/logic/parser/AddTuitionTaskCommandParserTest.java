package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DATE_TIME;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DURATION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_TIME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DURATION_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMPTY_TASK_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_WITHOUT_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_WITH_DESC_AMY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

import org.junit.Test;

import seedu.address.logic.commands.AddTuitionTaskCommand;

//@@author ChoChihTun
public class AddTuitionTaskCommandParserTest {
    private AddTuitionTaskCommandParser parser = new AddTuitionTaskCommandParser();

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
        assertParseFailure(parser, "1 29/02/2018 11:11 1h30m tuition homework",
                MESSAGE_INVALID_DATE_TIME + "\n" + AddTuitionTaskCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "1 31/04/2018 11:11 1h30m tuition homework",
                MESSAGE_INVALID_DATE_TIME + "\n" + AddTuitionTaskCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "1 32/01/2018 11:11 1h30m tuition homework",
                MESSAGE_INVALID_DATE_TIME + "\n" + AddTuitionTaskCommand.MESSAGE_USAGE);

        // Invalid time
        assertParseFailure(parser, "1 11/01/2018 24:00 1h30m tuition homework",
                MESSAGE_INVALID_DATE_TIME + "\n" + AddTuitionTaskCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "1 11/01/2018 11:60 1h30m tuition homework",
                MESSAGE_INVALID_DATE_TIME + "\n" + AddTuitionTaskCommand.MESSAGE_USAGE);

        // Invalid duration
        assertParseFailure(parser, "1 11/01/2018 11:11 1h60m tuition homework",
                MESSAGE_INVALID_DURATION + "\n" + AddTuitionTaskCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "1 11/01/2018 11:11 24h0m tuition homework",
                MESSAGE_INVALID_DURATION + "\n" + AddTuitionTaskCommand.MESSAGE_USAGE);
    }

    @Test
    public void parse_validArgs_success() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu HH:mm")
                .withResolverStyle(ResolverStyle.STRICT);

        LocalDateTime taskDateTime = LocalDateTime.parse(VALID_DATE_TIME_AMY, formatter);

        // With description
        assertParseSuccess(parser, "1 " + VALID_TASK_WITH_DESC_AMY,
                new AddTuitionTaskCommand(INDEX_FIRST_PERSON, taskDateTime, VALID_DURATION_AMY, VALID_TASK_DESC_AMY));

        // Without description
        assertParseSuccess(parser, "1 " + VALID_TASK_WITHOUT_DESC_AMY,
                new AddTuitionTaskCommand(INDEX_FIRST_PERSON, taskDateTime, VALID_DURATION_AMY, VALID_EMPTY_TASK_DESC));

        // Valid date
        taskDateTime = LocalDateTime.parse("28/02/2018 11:20", formatter);
        assertParseSuccess(parser, "1 28/02/2018 11:20 " + VALID_DURATION_AMY,
                new AddTuitionTaskCommand(INDEX_FIRST_PERSON, taskDateTime, VALID_DURATION_AMY, VALID_EMPTY_TASK_DESC));

        taskDateTime = LocalDateTime.parse("29/02/2016 11:20", formatter);
        assertParseSuccess(parser, "1 29/02/2016 11:20 " + VALID_DURATION_AMY,
                new AddTuitionTaskCommand(INDEX_FIRST_PERSON, taskDateTime, VALID_DURATION_AMY, VALID_EMPTY_TASK_DESC));

        taskDateTime = LocalDateTime.parse("30/04/2016 11:20", formatter);
        assertParseSuccess(parser, "1 30/04/2016 11:20 " + VALID_DURATION_AMY,
                new AddTuitionTaskCommand(INDEX_FIRST_PERSON, taskDateTime, VALID_DURATION_AMY, VALID_EMPTY_TASK_DESC));

        taskDateTime = LocalDateTime.parse("31/01/2016 11:20", formatter);
        assertParseSuccess(parser, "1 31/01/2016 11:20 " + VALID_DURATION_AMY,
                new AddTuitionTaskCommand(INDEX_FIRST_PERSON, taskDateTime, VALID_DURATION_AMY, VALID_EMPTY_TASK_DESC));

        // Valid Time
        taskDateTime = LocalDateTime.parse("11/01/2018 00:00", formatter);
        assertParseSuccess(parser, "1 11/01/2018 00:00 " + VALID_DURATION_AMY,
                new AddTuitionTaskCommand(INDEX_FIRST_PERSON, taskDateTime, VALID_DURATION_AMY, VALID_EMPTY_TASK_DESC));
    }

}
