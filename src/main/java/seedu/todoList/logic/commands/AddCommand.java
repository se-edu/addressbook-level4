package seedu.todoList.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.todoList.commons.exceptions.IllegalValueException;
import seedu.todoList.model.tag.Tag;
import seedu.todoList.model.tag.UniqueTagList;
import seedu.todoList.model.task.*;

/**
 * Adds a task to the TodoList.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_ADD_EVENT = COMMAND_WORD + ": Adds an event to the TodoList. "
            + "Parameters: EVENT_NAME d/DATE e/START_TIME e/END_TIME \n"
            + "Example: " + COMMAND_WORD
            + " Tim’s birthday party d/25-12-2016 s/1400 e/1600";
    
    public static final String MESSAGE_ADD_TASK = COMMAND_WORD + ": Adds a task to the TodoList. "
            + "Parameters: EVENT_NAME d/DATE \n"
            + "Example: " + COMMAND_WORD
            + " Tim’s birthday party d/25-12-2016";

    public static final String MESSAGE_EVENT_SUCCESS = "New event added: %1$s";
    public static final String MESSAGE_TASK_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in the Todo list";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the Todo list";

    private final Task toAdd;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name, String startTime, String endTime)
            throws IllegalValueException {
        this.toAdd = new Task(
                new Name(name),
                new StartTime(startTime),
                new EndTime(endTime)
        );
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            model.addTask(toAdd);
            return new CommandResult(String.format(MESSAGE_TASK_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicatetaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }

    }

}
