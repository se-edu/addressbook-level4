package seedu.todoList.logic.commands;

import seedu.todoList.commons.exceptions.IllegalValueException;
import seedu.todoList.model.task.*;

/**
 * Adds a task to the TodoList.
 */
public class AddEventCommand extends Command {

    public static final String COMMAND_WORD_EVENT = "add_event";
    public static final String MESSAGE_USAGE = COMMAND_WORD_EVENT + ": Adds an event to Tdoo. "
            + "Parameters: EVENT_NAME p/PRIORITY s/START_TIME e/END_TIME\n"
            + "Example: " + COMMAND_WORD_EVENT
            + " Assignment 3 p/1 s/1400 e/1600";
    
    
    public static final String MESSAGE_SUCCESS = "New event added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This event already exists in the to-do-list";

    private final Task toAdd;
    
    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddEventCommand(String name, String priority, String startTime, String endTime) throws IllegalValueException {
        this.toAdd = new Task(
                new Name(name),
                new Priority(priority),
                new StartTime(startTime),
                new EndTime(endTime)
        );
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            model.addTask(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicatetaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }

    }

}
