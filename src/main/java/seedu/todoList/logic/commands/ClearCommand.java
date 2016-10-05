package seedu.todoList.logic.commands;

import seedu.todoList.model.TodoList;

/**
 * Clears the Todo book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Todo book has been cleared!";

    public ClearCommand() {}


    @Override
    public CommandResult execute() {
        assert model != null;
        model.resetData(TodoList.getEmptyTodoList());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
