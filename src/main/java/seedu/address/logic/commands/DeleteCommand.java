package seedu.address.logic.commands;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.IndexUtil;
import seedu.address.commons.util.ListUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.UniquePersonList.PersonsNotFoundException;

/**
 * Deletes persons identified using their last displayed indices from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the persons at the specified INDICES\n"
            + "Parameters: INDICES (positive integers or integer ranges separated by spaces)\n"
            + "INDICES must match those on the last shown list.\n"
            + "Example: " + COMMAND_WORD + " 1 4-6 3 13-9";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS =
            "Deleted %1$s Person(s):\n"
            + "%2$s";

    public final Set<Integer> zeroBasedTargetIndices;

    public DeleteCommand(Set<Integer> oneBasedTargetIndices) {
        assert !CollectionUtil.isAnyNull(oneBasedTargetIndices);
        this.zeroBasedTargetIndices = IndexUtil.oneToZeroIndex(oneBasedTargetIndices);
    }

    @Override
    public CommandResult execute() throws CommandException {
        UnmodifiableObservableList<ReadOnlyPerson> filteredPersonList = model.getFilteredPersonList();

        if (!IndexUtil.areIndicesWithinBounds(zeroBasedTargetIndices, filteredPersonList)) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        List<ReadOnlyPerson> personsToDelete = ListUtil.subList(filteredPersonList, zeroBasedTargetIndices);
        try {
            model.deletePersons(personsToDelete);
        } catch (PersonsNotFoundException pnfe) {
            throw new AssertionError(pnfe);
        }

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personsToDelete.size(),
                                               StringUtil.toIndexedListString(personsToDelete)));
    }

}
