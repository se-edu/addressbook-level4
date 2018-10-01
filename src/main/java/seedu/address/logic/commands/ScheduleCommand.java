package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * List schedule of a person in the address book to the user.
 */
public class ScheduleCommand extends Command {

    public static final String COMMAND_WORD = "schedule";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists the schedule of the person identified"
            + "Parameters: INDEX (must be a positive integer)";

    public static final String MESSAGE_SCHEDULE_SUCCESS = "Listed Schedule:";
    public static final String MESSAGE_SCHEDULE_FAIL = "Person not found in address book.";

    private static final String FILE_PATH = "data\\schedule.txt";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        File file = new File(FILE_PATH);

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            String st = br.readLine();
            requireNonNull(model);
            return new CommandResult(MESSAGE_SCHEDULE_SUCCESS + "\n" + st);
        } catch (Exception e) {
            return new CommandResult(MESSAGE_SCHEDULE_FAIL);
        }
    }
}
