package seedu.address.logic.commands;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.IntegerUtil;
import seedu.address.commons.util.ListUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the persons at the specified INDICES\n"
            + "Parameters: INDICES\n"
            + "INDICES can only be positive integers or integer ranges separated by spaces.\n"
            + "Example: " + COMMAND_WORD + " 1 4-6 3 13-9";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS =
            "Deleted %1$s Person(s):\n"
            + "%2$s";

    public final Collection<Integer> targetIndices;

    public DeleteCommand(Collection<Integer> targetIndices) {
        assert !CollectionUtil.isAnyNull(targetIndices);
        assert Collections.min(targetIndices) >= 1 : "DeleteCommand: targetIndices not verified to be > 0 by caller";
        this.targetIndices = targetIndices;
    }

    @Override
    public CommandResult execute() {
        UnmodifiableObservableList<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        IntegerUtil.applyOffset(targetIndices, -1); // converting to zero-indexed indices

        if (!ListUtil.areIndicesWithinBounds(lastShownList, targetIndices)) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        List<ReadOnlyPerson> personsToDelete = ListUtil.subList(lastShownList, targetIndices);

        Optional<Collection<ReadOnlyPerson>> missingPersons = model.deletePersons(personsToDelete);
        if (missingPersons.isPresent()) {
            assert false : "Tried to delete " + missingPersons.get().size() + " person(s) not found in the list";
        }

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personsToDelete.size(),
                                               StringUtil.toIndexedListString(personsToDelete)));
    }

}
