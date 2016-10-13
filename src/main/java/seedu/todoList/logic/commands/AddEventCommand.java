package seedu.todoList.logic.commands;

import seedu.todoList.commons.exceptions.IllegalValueException;
import seedu.todoList.model.task.*;
import seedu.todoList.model.task.attributes.Date;
import seedu.todoList.model.task.attributes.EndTime;
import seedu.todoList.model.task.attributes.StartTime;

/**
 * Adds a task to the TodoList.
 */
public class AddEventCommand extends Command {

    public static final String COMMAND_WORD_EVENT = "add_event";
    public static final String MESSAGE_USAGE = COMMAND_WORD_EVENT + ": Adds an event to Tdoo. "
            + "Parameters: EVENT_NAME d/DATE s/START_TIME e/END_TIME\n"
            + "Example: " + COMMAND_WORD_EVENT + " Tim's birthday party d/25-12-2016 s/1400 e/1600\n"
            + "Example: " + COMMAND_WORD_EVENT + " Assignment 3 deadline d/25-12-2016 s/1600 e/1600";
    
    
    public static final String MESSAGE_SUCCESS = "New event added: %1$s";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in the to-do-list";

    private final Event toAdd;
    
    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddEventCommand(String todo, String date, String startTime, String endTime) throws IllegalValueException {
        this.toAdd = new Event(
                new Todo(todo),
                new Date(date),
                new StartTime(startTime),
                new EndTime(endTime)
        );
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            model.addEvent(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicatetaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_EVENT);
        }

    }

}
