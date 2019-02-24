package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertParseSuccess(ClearCommand.class, ClearCommand.COMMAND_WORD);
        assertParseSuccess(ClearCommand.class, ClearCommand.COMMAND_WORD + " 3");
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertParseSuccess(ExitCommand.class, ExitCommand.COMMAND_WORD);
        assertParseSuccess(ExitCommand.class, ExitCommand.COMMAND_WORD + " 3");
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + String.join(" ", keywords));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertParseSuccess(HelpCommand.class, HelpCommand.COMMAND_WORD);
        assertParseSuccess(HelpCommand.class, HelpCommand.COMMAND_WORD + " 3");
    }

    @Test
    public void parseCommand_history() throws Exception {
        assertParseSuccess(HistoryCommand.class, HistoryCommand.COMMAND_WORD);
        assertParseSuccess(HistoryCommand.class, HistoryCommand.COMMAND_WORD + " 3");

        assertParseFailure(ParseException.class, MESSAGE_UNKNOWN_COMMAND, "histories");
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertParseSuccess(ListCommand.class, ListCommand.COMMAND_WORD);
        assertParseSuccess(ListCommand.class, ListCommand.COMMAND_WORD + " 3");
    }

    @Test
    public void parseCommand_select() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_redoCommandWord_returnsRedoCommand() throws Exception {
        assertParseSuccess(RedoCommand.class, RedoCommand.COMMAND_WORD);
        assertParseSuccess(RedoCommand.class, "redo 1");
    }

    @Test
    public void parseCommand_undoCommandWord_returnsUndoCommand() throws Exception {
        assertParseSuccess(UndoCommand.class, UndoCommand.COMMAND_WORD);
        assertParseSuccess(UndoCommand.class, "undo 3");
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() throws Exception {
        assertParseFailure(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), "");
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() throws Exception {
        assertParseFailure(ParseException.class, MESSAGE_UNKNOWN_COMMAND, "unknownCommand");
    }

    private void assertParseSuccess(Class<? extends Command> commandClass, String command) throws Exception {
        assertTrue(commandClass.isInstance(parser.parseCommand(command)));
    }

    private void assertParseFailure(Class<? extends Throwable> errorClass, String message, String command) {
        assertThrows(errorClass, message, () -> parser.parseCommand(command));
    }
}
