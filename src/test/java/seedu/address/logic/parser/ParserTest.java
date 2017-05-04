package seedu.address.logic.parser;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;

public class ParserTest {
    Parser parser = new Parser();

    @Test
    public void parseCommand_undoCommandWord_returnsUndoCommand() {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD) instanceof UndoCommand);
        assertTrue(parser.parseCommand("undo 3") instanceof UndoCommand);
        assertFalse(parser.parseCommand("Undo") instanceof UndoCommand);
    }

    @Test
    public void parseCommand_redoCommandWord_returnsRedoCommand() {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD) instanceof RedoCommand);
        assertTrue(parser.parseCommand("redo 1") instanceof RedoCommand);
        assertFalse(parser.parseCommand("Redo") instanceof RedoCommand);
    }
}
