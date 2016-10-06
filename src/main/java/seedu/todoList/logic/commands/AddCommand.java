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

    private Task toAddTask;
    private Task toAddEvent;
    private int indicator = 0;
    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name, String startTime, String endTime) throws IllegalValueException {
        if(endTime.isEmpty()){
            endTime = "-";
            this.toAddTask = new Task(
                    new Name(name),
                    new StartTime(startTime),
                    new EndTime(endTime)
            );
            indicator = 1;
        }else{
            this.toAddEvent = new Task(
                    new Name(name),
                    new StartTime(startTime),
                    new EndTime(endTime)
            );
            indicator = 2;
        }
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            //Add event if indicator is 2, else add task as usual
            if(indicator == 2){
                model.addTask(toAddEvent);
                return new CommandResult(String.format(MESSAGE_TASK_SUCCESS, toAddEvent));
            }else{
                model.addTask(toAddTask);
                return new CommandResult(String.format(MESSAGE_EVENT_SUCCESS, toAddTask));
            }
        } catch (UniqueTaskList.DuplicatetaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }

    }

}
