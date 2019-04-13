package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINEDATE_DESC_ONE;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINEDATE_DESC_TWO;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINETIME_DESC_ONE;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINETIME_DESC_TWO;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DEADLINEDATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DEADLINETIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TASKNAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_ONE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_TWO;
import static seedu.address.logic.commands.CommandTestUtil.TASKNAME_DESC_ONE;
import static seedu.address.logic.commands.CommandTestUtil.TASKNAME_DESC_TWO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINEDATE_TWO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINETIME_TWO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_IMPORTANT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_PROJECT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASKNAME_TWO;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalTasks.TASKONE;
import static seedu.address.testutil.TypicalTasks.TASKTWO;

import org.junit.Test;

import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.DeadlineDate;
import seedu.address.model.task.DeadlineTime;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskName;
import seedu.address.testutil.TaskBuilder;

public class AddTaskCommandParserTest {
    private AddTaskCommandParser parser = new AddTaskCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Task expectedTask = new TaskBuilder(TASKONE).withTags(VALID_TAG_IMPORTANT).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + TASKNAME_DESC_ONE
                + DEADLINETIME_DESC_ONE + DEADLINEDATE_DESC_ONE
                + TAG_DESC_ONE, new AddTaskCommand(expectedTask));

        // multiple names - last name accepted
        assertParseSuccess(parser, TASKNAME_DESC_TWO + TASKNAME_DESC_ONE + DEADLINETIME_DESC_ONE + DEADLINEDATE_DESC_ONE
                + TAG_DESC_ONE, new AddTaskCommand(expectedTask));

        // multiple Deadline Times - last Deadline Time accepted.
        assertParseSuccess(parser, TASKNAME_DESC_ONE + DEADLINETIME_DESC_TWO
                + DEADLINETIME_DESC_ONE + DEADLINEDATE_DESC_ONE
                + TAG_DESC_ONE, new AddTaskCommand(expectedTask));

        // multiple Deadline Date - last Deadline Date accepted
        assertParseSuccess(parser, TASKNAME_DESC_ONE + DEADLINETIME_DESC_ONE
                + DEADLINEDATE_DESC_TWO + DEADLINEDATE_DESC_ONE
                + TAG_DESC_ONE, new AddTaskCommand(expectedTask));

        // multiple tags - all accepted
        Task expectedTaskMultipleTags = new TaskBuilder(TASKTWO).withTags(VALID_TAG_IMPORTANT, VALID_TAG_PROJECT)
                .build();
        assertParseSuccess(parser, TASKNAME_DESC_TWO + DEADLINETIME_DESC_TWO + DEADLINEDATE_DESC_TWO
                + TAG_DESC_ONE + TAG_DESC_TWO, new AddTaskCommand(expectedTaskMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Task expectedTask = new TaskBuilder(TASKTWO).withTags().build();
        assertParseSuccess(parser, TASKNAME_DESC_TWO + DEADLINETIME_DESC_TWO + DEADLINEDATE_DESC_TWO ,
                new AddTaskCommand(expectedTask));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE);

        // missing task name prefix
        assertParseFailure(parser, VALID_TASKNAME_TWO + DEADLINETIME_DESC_TWO + DEADLINEDATE_DESC_TWO,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, TASKNAME_DESC_TWO + VALID_DEADLINETIME_TWO + DEADLINEDATE_DESC_TWO,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, TASKNAME_DESC_TWO + DEADLINETIME_DESC_TWO + VALID_DEADLINEDATE_TWO,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_TASKNAME_TWO + VALID_DEADLINETIME_TWO + VALID_DEADLINEDATE_TWO,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid Task Name
        assertParseFailure(parser, INVALID_TASKNAME_DESC + DEADLINETIME_DESC_TWO + DEADLINEDATE_DESC_TWO
                + TAG_DESC_TWO + TAG_DESC_ONE, TaskName.MESSAGE_CONSTRAINTS);

        // invalid DeadLineTime
        assertParseFailure(parser, TASKNAME_DESC_TWO + INVALID_DEADLINETIME_DESC + DEADLINEDATE_DESC_TWO
                + TAG_DESC_TWO + TAG_DESC_ONE, DeadlineTime.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, TASKNAME_DESC_TWO + DEADLINETIME_DESC_TWO + INVALID_DEADLINEDATE_DESC
                + TAG_DESC_TWO + TAG_DESC_ONE, DeadlineDate.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, TASKNAME_DESC_TWO + DEADLINETIME_DESC_TWO + DEADLINEDATE_DESC_TWO
                + INVALID_TAG_DESC + VALID_TAG_IMPORTANT, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_TASKNAME_DESC + DEADLINETIME_DESC_TWO + DEADLINEDATE_DESC_TWO,
                TaskName.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + TASKNAME_DESC_TWO
                        + DEADLINETIME_DESC_TWO + DEADLINEDATE_DESC_TWO
                        + TAG_DESC_TWO + TAG_DESC_ONE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));
    }
}
