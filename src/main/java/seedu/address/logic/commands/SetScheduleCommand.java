package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.PrintWriter;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Sets schedule of a person in the address book to the user.
 */
public class SetScheduleCommand extends Command {

    public static final String COMMAND_WORD = "setschedule";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists the schedule of the person identified"
            + "Parameters: INDEX (must be a positive integer)";

    public static final String MESSAGE_SCHEDULE_SUCCESS = "Set Schedule Successful";
    public static final String MESSAGE_SCHEDULE_FAIL = "Person not found in address book.";

    private static final String FILE_PATH = "data\\schedule.txt";
    private final String scheduleDescriptor;

    public SetScheduleCommand(String scheduleDescriptor) {
        requireNonNull(scheduleDescriptor);

        this.scheduleDescriptor = scheduleDescriptor;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        try {
            PrintWriter writer = new PrintWriter(FILE_PATH, "UTF-8");
            writer.println(scheduleDescriptor);
            writer.close();

            return new CommandResult(MESSAGE_SCHEDULE_SUCCESS);
        } catch (Exception e) {
            return new CommandResult(MESSAGE_SCHEDULE_FAIL);
        }
    }

}
