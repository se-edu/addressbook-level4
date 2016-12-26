package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.tag.UniqueTagList;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing values will be overwritten by the input values. "
            + "Parameters: INDEX (must be a positive integer) [NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS ] [t/TAG]...\n"
            + "Example: " + COMMAND_WORD
            + " 1 p/91234567 e/johndoe@yahoo.com";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NO_ARGUMENT = "At least one of the fields should be edited";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";

    private final int targetIndex;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param targetIndex the index of the person to edit based on current list
     * @param editPersonDescriptor details of person to be edited
     */
    public EditCommand(int targetIndex, EditPersonDescriptor editPersonDescriptor) {
        assert targetIndex >= 0;
        assert editPersonDescriptor != null;

        this.targetIndex = targetIndex;
        this.editPersonDescriptor = editPersonDescriptor;
    }

    @Override
    public CommandResult execute() {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(targetIndex - 1);
        Person updatedPerson = createUpdatedPerson(personToEdit);

        try {
            model.updatePerson(personToEdit, updatedPerson);
        } catch (UniquePersonList.PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        } catch (UniquePersonList.DuplicatePersonException dpe) {
            return new CommandResult(MESSAGE_DUPLICATE_PERSON);
        }

        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, personToEdit));
    }

    /**
     * Creates and returns a {@code Person} with the updated details.
     */
    private Person createUpdatedPerson(ReadOnlyPerson personToEdit) {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElseGet(personToEdit::getName);
        Phone updatedPhone = editPersonDescriptor.getPhone().orElseGet(personToEdit::getPhone);
        Email updatedEmail = editPersonDescriptor.getEmail().orElseGet(personToEdit::getEmail);
        Address updatedAddress = editPersonDescriptor.getAddress().orElseGet(personToEdit::getAddress);
        UniqueTagList updatedTags = editPersonDescriptor.getTags().orElseGet(personToEdit::getTags);

        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedTags);
    }

}
