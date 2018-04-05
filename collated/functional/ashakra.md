# ashakra
###### \java\seedu\address\logic\commands\ListTaskCommand.java
``` java
/**
 * Lists all tasks in the application to the user.
 */
public class ListTaskCommand extends Command {

    public static final String COMMAND_WORD = "listTask";
    public static final String COMMAND_ALIAS = "lt";

    public static final String MESSAGE_SUCCESS = "Listed all tasks";


    @Override
    public CommandResult execute() {
        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
```
