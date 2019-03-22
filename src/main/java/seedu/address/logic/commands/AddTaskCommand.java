package seedu.address.logic.commands;



import static java.util.Objects.requireNonNull;


import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.task.Task;


public class AddTaskCommand extends Command {
    public static final String COMMAND_WORD = "addTask";

    public static final String MESSAGE_SUCCESS = "New task added: ";

    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task list";

    private final Task newTask;

    public AddTaskCommand(Task task) {
        requireNonNull(task);
        newTask = task;
    }

    /*private final Task toAdd;*/

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

//        if (model.hasPerson(toAdd)) {
//            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
//        }
        model.addTask(newTask);
        model.commitTaskList();
        String toBePrinted = MESSAGE_SUCCESS  + newTask.getTaskName() + " | "
                + "DEADLINE: " + newTask.getDeadlineDate() + ' ' + newTask.getDeadlineTime() + "HRS";
        return new CommandResult(String.format(toBePrinted, newTask));
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddTaskCommand // instanceof handles nulls
                && newTask.equals(((AddTaskCommand) other).newTask));
    }
}
