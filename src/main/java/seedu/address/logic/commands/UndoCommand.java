package seedu.address.logic.commands;

import java.util.EmptyStackException;

//@@author A0121261Y
/*
 * Undo a undoable command recently executed.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_SUCCESS = "Undone your previous action successfully: ";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Reverse your last undoable "
            + "action executed in SmartyDo. \n"
            + "Example: " + "add "
            + "John Doe t/9876 d/johnd's description a/311, Clementi Ave 2, #02-25 t/friends t/owesMoney \n"
            + COMMAND_WORD;


    public static final String MESSAGE_NO_UNDOABLE_COMMAND = "There was no undoable command executed recently. \n"
            + MESSAGE_USAGE;

    public UndoCommand() {}

    @Override
    public CommandResult execute() {
        String lastUndoableMessage;
        Command lastCommand;

        assert model != null;
        assert undoRedoManager != null;

        try {
        	lastCommand = undoRedoManager.getUndo().pop();
        	assert lastCommand instanceof Undoable;
        	undoRedoManager.transferToRedo(lastCommand);
            lastUndoableMessage = ((Undoable) lastCommand).unexecute().feedbackToUser;
            return new CommandResult(MESSAGE_SUCCESS + System.lineSeparator() + lastUndoableMessage);
        }catch (EmptyStackException ese) {
            return new CommandResult(MESSAGE_NO_UNDOABLE_COMMAND);
        }
    }
}
