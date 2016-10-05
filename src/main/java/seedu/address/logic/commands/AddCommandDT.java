package seedu.address.logic.commands;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.datetimepair.DateTimePair;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by haliq on 5/10/16.
 */
public class AddCommandDT extends Command {

    public static final String COMMAND_WORD = "adddt";
    public static final String MESSAGE_USAGE =
            COMMAND_WORD + ": Adds a datetime pair to the system. "
            + "Parameters: NAME [OPEN] CLOSE [GROUP] [WORKLOAD [COMPLETION]] [FLOAT]\n"
            + "Example: " + COMMAND_WORD + " errands 5/10/2016:0600 5/10/2016:1600 F home 1 0";
    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
    public static final String MESSAGE_BLOCKED = "This clashes with another event.";
    public static final String MESSAGE_INVALID_DT = "The datetime pairs are invalid; dd/mm/yyyy:HHMM";
    public static final String MESSAGE_INVALID_FLOAT = "The float flag is invalid; only T or F.";
    public static final String MESSAGE_INVALID_COMPLETION = "The completion value is a number from 0 to 1 only.";
    public static final String MESSAGE_INVALID_WORKLOAD = "The workload value is a non negative number.";

    public DateTimePair toAdd;

    public AddCommandDT(String open, String close, String name, boolean flt, Set<String> tags)
    throws IllegalValueException {
        this.toAdd = new DateTimePair(open, close, name, flt, getUniqueTagList(tags));
    }

    /**
     * Generate tag set to be used in DateTimePair creation
     * @param tags
     * @return
     * @throws IllegalValueException
     */
    private UniqueTagList getUniqueTagList(Set<String> tags) throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for(String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        return new UniqueTagList(tagSet);
    }

    @Override
    public CommandResult execute() {
        //TODO add constructed dt model to system model
        return null;
    }
}
