package seedu.address.logic.commands;

import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.*;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;

import java.util.HashSet;
import java.util.Set;


/**
 * Adds a task to the SmartyDo.
 */
public class AddCommand extends Command implements Undoable {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the SmartyDo. "
            + "Parameters: NAME t;DATE et;TIME d;DESCRIPTION a;LOCATION  [tag;TAG]...\n"
            + "Example: " + COMMAND_WORD
            + " John Doe t;10-12-2016 et;10:00AM d;description a;311, Clementi Ave 2, #02-25 t/friends t/owesMoney";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the SmartyDo";

    private final Task toAdd;
    private boolean isExecutedBefore;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */

    public AddCommand(String name, String time, String period, String description, String address, Set<String> tags)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = new Task(
                new Name(name),
                new Time(time),
                new Period(period),
                new Description(description),
                new Location(address),
                new UniqueTagList(tagSet)
        );
        isExecutedBefore = false;
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        assert undoRedoManager != null;

        try {
            model.addTask(toAdd);
            isExecutedBefore = pushCmdToUndo(isExecutedBefore);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }

    }

    @Override
    public CommandResult unexecute() {
        int toRemove;

        assert model != null;
        assert undoRedoManager != null;

        toRemove = model.getToDo().getTaskList().indexOf(toAdd);
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        ReadOnlyTask taskToDelete = lastShownList.get(toRemove);

        try {
            model.deleteTask(taskToDelete);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

	@Override
	public boolean pushCmdToUndo(boolean isExecuted) {
        if (!isExecuted){
        	undoRedoManager.addToUndo(this);
        }
		return true;
	}
}
