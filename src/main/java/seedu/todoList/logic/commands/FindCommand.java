package seedu.todoList.logic.commands;

import java.util.Set;

/**
 * Finds and lists all tasks in TodoList whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final String INVALID_DATA_TYPE_MESSAGE = "Invalid Data type";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: TASK_TYPE KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " all homework urgent";

    private final Set<String> keywords;
    private final String dataType;

    public FindCommand(Set<String> keywords, String dataType) {
        this.keywords = keywords;
        this.dataType = dataType;
    }

    @Override
    public CommandResult execute() {
    	CommandResult result = new CommandResult(INVALID_DATA_TYPE_MESSAGE);;
    	switch(dataType) {
    		case "todo":
				model.updateFilteredTodoList(keywords);
				result = new CommandResult(getMessageFortaskListShownSummary(model.getFilteredTodoList().size()));
				break;
    		case "event":
				model.updateFilteredEventList(keywords);
				result = new CommandResult(getMessageFortaskListShownSummary(model.getFilteredEventList().size()));
				break;
    		case "deadline":
				model.updateFilteredDeadlineList(keywords);
				result = new CommandResult(getMessageFortaskListShownSummary(model.getFilteredDeadlineList().size()));
    	}
    	return result;
    }
}
