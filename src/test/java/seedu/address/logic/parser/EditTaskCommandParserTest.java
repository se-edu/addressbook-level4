package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINEDATE_DESC_ONE;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINETIME_DESC_ONE;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINETIME_DESC_TWO;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DEADLINEDATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DEADLINETIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TASKNAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_ONE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_TWO;
import static seedu.address.logic.commands.CommandTestUtil.TASKNAME_DESC_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINEDATE_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINETIME_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINETIME_TWO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_IMPORTANT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_PROJECT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASKNAME_ONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_TASK;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditTaskCommand;
import seedu.address.logic.commands.EditTaskCommand.EditTaskDescriptor;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.DeadlineDate;
import seedu.address.model.task.DeadlineTime;
import seedu.address.model.task.TaskName;
import seedu.address.testutil.EditTaskDescriptorBuilder;


public class EditTaskCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTaskCommand.MESSAGE_USAGE);

    private EditTaskCommandParser parser = new EditTaskCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_TASKNAME_ONE, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditTaskCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + TASKNAME_DESC_ONE, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + TASKNAME_DESC_ONE, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_TASKNAME_DESC, TaskName.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_DEADLINETIME_DESC, DeadlineTime.MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, "1" + INVALID_DEADLINEDATE_DESC, DeadlineDate.MESSAGE_CONSTRAINTS); // invalid email
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // invalid phone followed by valid email
        assertParseFailure(parser, "1" + INVALID_DEADLINETIME_DESC
                + DEADLINEDATE_DESC_ONE, DeadlineTime.MESSAGE_CONSTRAINTS);

        // valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + DEADLINETIME_DESC_TWO
                + INVALID_DEADLINETIME_DESC, DeadlineTime.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Person} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_ONE + TAG_DESC_TWO + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_ONE + TAG_EMPTY + TAG_DESC_TWO, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_ONE + TAG_DESC_TWO, Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_TASKNAME_DESC + INVALID_DEADLINEDATE_DESC + VALID_DEADLINETIME_ONE,
                TaskName.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_TASK;
        String userInput = targetIndex.getOneBased() + DEADLINETIME_DESC_TWO + TAG_DESC_TWO
                + DEADLINEDATE_DESC_ONE + TASKNAME_DESC_ONE + TAG_DESC_ONE;

        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withTaskName(VALID_TASKNAME_ONE)
                .withDeadlineTime(VALID_DEADLINETIME_TWO).withDeadlineDate(VALID_DEADLINEDATE_ONE)
                .withTags(VALID_TAG_IMPORTANT, VALID_TAG_PROJECT).build();
        EditTaskCommand expectedCommand = new EditTaskCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_TASK;
        String userInput = targetIndex.getOneBased() + DEADLINETIME_DESC_TWO + DEADLINEDATE_DESC_ONE;

        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withDeadlineTime(VALID_DEADLINETIME_TWO)
                .withDeadlineDate(VALID_DEADLINEDATE_ONE).build();
        EditTaskCommand expectedCommand = new EditTaskCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_TASK;
        String userInput = targetIndex.getOneBased() + TASKNAME_DESC_ONE;
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withTaskName(VALID_TASKNAME_ONE).build();
        EditTaskCommand expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetIndex.getOneBased() + DEADLINETIME_DESC_ONE;
        descriptor = new EditTaskDescriptorBuilder().withDeadlineTime(VALID_DEADLINETIME_ONE).build();
        expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = targetIndex.getOneBased() + DEADLINEDATE_DESC_ONE;
        descriptor = new EditTaskDescriptorBuilder().withDeadlineDate(VALID_DEADLINEDATE_ONE).build();
        expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_ONE;
        descriptor = new EditTaskDescriptorBuilder().withTags(VALID_TAG_IMPORTANT).build();
        expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_TASK;
        String userInput = targetIndex.getOneBased() + DEADLINETIME_DESC_ONE + DEADLINEDATE_DESC_ONE
                + TAG_DESC_ONE + DEADLINETIME_DESC_ONE + DEADLINEDATE_DESC_ONE + TAG_DESC_ONE
                + DEADLINETIME_DESC_TWO + DEADLINEDATE_DESC_ONE + TAG_DESC_TWO;

        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withDeadlineTime(VALID_DEADLINETIME_TWO)
                .withDeadlineDate(VALID_DEADLINEDATE_ONE).withTags(VALID_TAG_PROJECT, VALID_TAG_IMPORTANT)
                .build();
        EditTaskCommand expectedCommand = new EditTaskCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_TASK;
        String userInput = targetIndex.getOneBased() + INVALID_DEADLINETIME_DESC + DEADLINETIME_DESC_TWO;
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder()
                .withDeadlineTime(VALID_DEADLINETIME_TWO).build();
        EditTaskCommand expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + DEADLINEDATE_DESC_ONE + INVALID_DEADLINETIME_DESC
                + DEADLINETIME_DESC_TWO;
        descriptor = new EditTaskDescriptorBuilder().withDeadlineTime(VALID_DEADLINETIME_TWO)
                .withDeadlineDate(VALID_DEADLINEDATE_ONE).build();
        expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_TASK;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withTags().build();
        EditTaskCommand expectedCommand = new EditTaskCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
