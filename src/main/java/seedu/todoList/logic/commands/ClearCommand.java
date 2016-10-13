package seedu.todoList.logic.commands;

import seedu.todoList.model.TaskList;

/**
 * Clears the TodoList.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String INVALID_TYPE = "Invalid data type";
    public static final String MESSAGE_SUCCESS = "TodoList has been cleared!";
    
    private String dataType;

    public ClearCommand(String args) {
    	this.dataType = args;
    }


    @Override
    public CommandResult execute() {
        assert model != null;
        switch(dataType) {
        	case "todo":
        		model.resetTodoListData(TaskList.getEmptyTaskList());
                return new CommandResult(MESSAGE_SUCCESS);
        	case "event":
        		model.resetEventListData(TaskList.getEmptyTaskList());
                return new CommandResult(MESSAGE_SUCCESS);
        	case "deadline":
        		model.resetDeadlineListData(TaskList.getEmptyTaskList());
                return new CommandResult(MESSAGE_SUCCESS);
        }
        return new CommandResult(INVALID_TYPE);
    }
}
