package seedu.address.logic.commands;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.IntegerUtil;
import seedu.address.commons.util.ListUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.UniquePersonList.PersonNotFoundException;

/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes person(s) identified by the index number used in the last person listing.\n"
            + "You can also specify a range of persons to delete.\n"
            + "Parameters: INDEX [INDEX]... "
            + "('INDEX' has to be a positive integer or positive integers surrounding '-')\n"
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

        IntegerUtil.applyOffset(targetIndices, -1);

        if (!ListUtil.areIndicesWithinBounds(lastShownList, targetIndices)) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        List<ReadOnlyPerson> deletedPersons = deletePersonsFrom(lastShownList, targetIndices);

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, deletedPersons.size(),
                StringUtil.toIndexedListString(deletedPersons)));
    }

    /** Deletes persons from {@code targetList} then returns the list of deleted persons. */
    private List<ReadOnlyPerson> deletePersonsFrom(List<ReadOnlyPerson> targetList,
            Collection<Integer> indicesToDelete) {

        List<ReadOnlyPerson> personsToDelete = ListUtil.subList(targetList, indicesToDelete);
        try {
            for (ReadOnlyPerson person : personsToDelete) {
                model.deletePerson(person);
            }
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        }
        return personsToDelete;
    }

}
