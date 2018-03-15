package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Contact;
import seedu.address.model.person.Email;
import seedu.address.model.person.Lead;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.person.exceptions.PersonWrongType;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing person in the address book.
 */
public class ConvertCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "convert";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Converts the selected Lead to a Contact "
            + "by the index number used in the last person listing. "
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_CONVERT_PERSON_SUCCESS = "Converted Person: %1$s";
    public static final String MESSAGE_NOT_CONVERTED = "Person is already a Contact.";
    public static final String MESSAGE_NO_INDEX = "Index was not specified.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;

    private Lead oldLead;
    private Contact newContact;

    /**
     * @param index of the person in the filtered person list to edit
     */
    public ConvertCommand(Index index) throws ParseException {
        requireNonNull(index);

        this.index = index;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.convertPerson(oldLead, newContact);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonWrongType pwt) {
            throw new AssertionError("The target person is not a Lead");
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_CONVERT_PERSON_SUCCESS, oldLead));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Person test = lastShownList.get(0);

        oldLead = (Lead) lastShownList.get(index.getZeroBased());
        newContact = createContact(oldLead);
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Contact createContact(Lead oldLead) {
        assert oldLead != null;

        Name updatedName = oldLead.getName();
        Phone updatedPhone = oldLead.getPhone();
        Email updatedEmail = oldLead.getEmail();
        Address updatedAddress = oldLead.getAddress();
        Set<Tag> updatedTags = oldLead.getTags();

        return new Contact(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ConvertCommand)) {
            return false;
        }

        // state check
        ConvertCommand e = (ConvertCommand) other;
        return index.equals(e.index)
                && Objects.equals(oldLead, e.oldLead);
    }

}
