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
public class AddTimetableCommand extends Command {

    public static final String COMMAND_WORD = "add_timetable";
    public static final String MESSAGE_SUCCESS = "New timetable added: %1$s";
    public static final String MESSAGE_USAGE = COMMAND_WORD;

    public AddTimetableCommand(Name name, String fileName, String location) {
        Timetable timetable = new Timetable(name);
        timetable.addTimetable(location, fileName);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        return new CommandResult(COMMAND_WORD);
    }
}
