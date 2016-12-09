package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.exceptions.NoArgumentException;
import seedu.address.model.person.*;
import seedu.address.model.person.UniquePersonList.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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

    public final int targetIndex;
    public final HashMap<String, Object> detailsToEdit = new HashMap<String, Object>();

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public EditCommand(int targetIndex, Optional<String> name, Optional<String> phone,
            Optional<String> email, Optional<String> address, Set<String> tags)
                    throws IllegalValueException, NoArgumentException {
        this.targetIndex = targetIndex;
        
        if (name.isPresent()) {
            detailsToEdit.put(Name.KEY, new Name(name.get()));
        }

        if (phone.isPresent()) {
            detailsToEdit.put(Phone.KEY, new Phone(phone.get()));
        }

        if (email.isPresent()) {
            detailsToEdit.put(Email.KEY, new Email(email.get()));
        }

        if (address.isPresent()) {
            detailsToEdit.put(Address.KEY, new Address(address.get()));
        }

        if (!tags.isEmpty()) {
            if (isRemoveTags(tags)) {
                detailsToEdit.put(Tag.RESET_KEY, Boolean.TRUE);
            } else {
                final Set<Tag> tagSet = new HashSet<>();
                for (String tagName : tags) {
                    tagSet.add(new Tag(tagName));
                }
                detailsToEdit.put(Tag.KEY, tagSet);
            }
        }

        if (detailsToEdit.isEmpty()) {
            throw new NoArgumentException(MESSAGE_NO_ARGUMENT);
        }
    }

    /**
     * Returns true if user input fulfills the case of removing existing tags of user.
     *
     * @param tags  Set of tags
     * @return  True if tags consists of only 1 element, and the element is an empty string.
     *          i.e user entered "t/ " or variants of it with differing number of whitespace as input.
     */
    private boolean isRemoveTags(Set<String> tags) {
        return tags.size() == 1 && tags.contains("");
    }

    @Override
    public CommandResult execute() {
        UnmodifiableObservableList<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(targetIndex - 1);

        try {
            model.editPerson(personToEdit, detailsToEdit);
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        } catch (UniquePersonList.DuplicatePersonException e) {
            return new CommandResult(MESSAGE_DUPLICATE_PERSON);
        }

        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, personToEdit));
    }
}
