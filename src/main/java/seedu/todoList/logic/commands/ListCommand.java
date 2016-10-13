package seedu.todoList.logic.commands;


/**
 * Lists all tasks in the TodoList to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows a list of all tasks in the task-list of the given type.\n"
            + "Parameters: TASK_TYPE\n"
            + "Example: " + COMMAND_WORD + " todo\n";
    
    public static final String MESSAGE_SUCCESS = "Listed all tasks";
    
    private final String dataType;

    public ListCommand(String dataType) {
    	this.dataType = dataType;
    }

    @Override
    public CommandResult execute() {
    	switch(dataType) {
    		case "todo":
    			model.updateFilteredTodoListToShowAll();
    			return new CommandResult(MESSAGE_SUCCESS);
    		case "event":
    			model.updateFilteredEventListToShowAll();
    			return new CommandResult(MESSAGE_SUCCESS);
    		case "deadline":
    			model.updateFilteredDeadlineListToShowAll();
    			return new CommandResult(MESSAGE_SUCCESS);
    	}
    	return new CommandResult(MESSAGE_USAGE);
    }
}
