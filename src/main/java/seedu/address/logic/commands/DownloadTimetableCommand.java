package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE_LOCATION;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.timetable.Timetable;

/**
 * download a timetable to a specific location
 */
public class DownloadTimetableCommand extends Command {

    public static final String COMMAND_WORD = "download_timetable";
    public static final String MESSAGE_USAGE =
        COMMAND_WORD + ": download timetable from the person identified "
            + "by the index number used in the displayed person list. "
            + "file must not be created in the download folder location \n"
            + "Parameters : INDEX (must be a positive integer) "
            + "[" + PREFIX_FILE_LOCATION + "] \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_FILE_LOCATION + "C:\\Users\\admin\\Downloads";

    public static final String MESSAGE_DOWNLOAD_TIMETABLE_SUCCESS = "timetable downloaded successfully";
    private final Index index;
    private final String locationTo;

    /**
     * /**
     *
     * @param index of the person in the filtered person list to edit
     * @param locationTo to save the file of timetable to
     */
    public DownloadTimetableCommand(Index index, String locationTo) {
        this.index = index;
        this.locationTo = locationTo;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToDownloadTimetable = lastShownList.get(index.getZeroBased());
        Timetable timetable = personToDownloadTimetable.getTimetable();
        timetable.downloadTimetable(locationTo);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_DOWNLOAD_TIMETABLE_SUCCESS));
    }
}
