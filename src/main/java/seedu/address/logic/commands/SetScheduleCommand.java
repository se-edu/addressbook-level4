package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

public class SetScheduleCommand extends Command{

    public static final String COMMAND_WORD = "setschedule";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists the schedule of the person identified"
            + "Parameters: INDEX (must be a positive integer)";

    public static final String MESSAGE_SCHEDULE_SUCCESS = "Set Schedule of Person: %1$s";
    public static final String MESSAGE_SCHEDULE_FAIL = "Person not found in address book.";


    @Override
    public CommandResult execute(Model model, CommandHistory history){
        requireNonNull(model);
        return new CommandResult(MESSAGE_SCHEDULE_SUCCESS);
    }

}
