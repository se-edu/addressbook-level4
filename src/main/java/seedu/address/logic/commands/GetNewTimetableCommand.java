package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.timetable.Timetable;

/**
 * adds timetable to the system
 */
public class GetNewTimetableCommand extends Command {

    public static final String COMMAND_WORD = "new_timetable";
    public static final String MESSAGE_SUCCESS = "New timetable generated: %1$s";
    public static final String MESSAGE_USAGE = COMMAND_WORD;

    public GetNewTimetableCommand(Name name, String fileName, String locationTo, String mode) {
        Timetable timetable = new Timetable(name);
        timetable.getNewTimetable(locationTo, fileName, mode);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        return new CommandResult(COMMAND_WORD);
    }
}
