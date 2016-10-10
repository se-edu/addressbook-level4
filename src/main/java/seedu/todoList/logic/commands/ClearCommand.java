package seedu.todoList.logic.commands;

import seedu.todoList.model.TaskList;

/**
 * Clears the TodoList.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "TodoList has been cleared!";

    public ClearCommand() {}


    @Override
    public CommandResult execute() {
        assert model != null;
        model.resetData(TaskList.getEmptyTodoList());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
