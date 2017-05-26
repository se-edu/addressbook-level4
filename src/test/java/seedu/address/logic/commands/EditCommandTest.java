package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.testutil.EditCommandTestUtil.DESC_AMY;
import static seedu.address.testutil.EditCommandTestUtil.DESC_BOB;
import static seedu.address.testutil.EditCommandTestUtil.VALID_NAME_BOB;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TypicalPersons;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    // zero-based index is used for interacting with Model, one-based index is used for interacting with EditCommand
    private static final int ZERO_BASED_INDEX_FIRST_PERSON = 0;
    private static final int ONE_BASED_INDEX_FIRST_PERSON = 1;
    private static final int ONE_BASED_INDEX_SECOND_PERSON =  ONE_BASED_INDEX_FIRST_PERSON + 1;

    private Model model = new ModelManager(new TypicalPersons().getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validCommand_succeeds() throws Exception {
        Person editedPerson = new PersonBuilder().build();
        EditPersonDescriptor descriptor = createEditPersonDescriptor(editedPerson);
        EditCommand editCommand = prepareCommand(ONE_BASED_INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);

        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        expectedAddressBook.updatePerson(ZERO_BASED_INDEX_FIRST_PERSON, editedPerson);
        FilteredList<ReadOnlyPerson> expectedFilteredList = new FilteredList<>(expectedAddressBook.getPersonList());

        assertCommandSuccess(editCommand, expectedMessage, expectedAddressBook, expectedFilteredList);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() throws Exception {
        Person firstPerson = new Person(model.getFilteredPersonList().get(ZERO_BASED_INDEX_FIRST_PERSON));
        EditPersonDescriptor descriptor = createEditPersonDescriptor(firstPerson);
        EditCommand editCommand = prepareCommand(ONE_BASED_INDEX_SECOND_PERSON, descriptor);
        assertCommandFailure(editCommand, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_invalidPersonIndex_throwsCommandException() throws Exception {
        int oneBasedOutOfBoundIndex = model.getFilteredPersonList().size() + 1;
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = prepareCommand(oneBasedOutOfBoundIndex, descriptor);
        assertCommandFailure(editCommand, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(1, DESC_AMY);

        // same values -> returns true
        EditPersonDescriptor copyDescriptor = new EditPersonDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(1, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(2, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(1, DESC_BOB)));
    }

    /**
     * Returns an {@code EditCommand} with parameters {@code oneBasedIndex} and {@code descriptor}
     */
    private EditCommand prepareCommand(int oneBasedIndex, EditPersonDescriptor descriptor) {
        EditCommand editCommand = new EditCommand(oneBasedIndex, descriptor);
        editCommand.setData(model);
        return editCommand;
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code person}'s values
     */
    private EditPersonDescriptor createEditPersonDescriptor(ReadOnlyPerson person) throws IllegalValueException {
        EditPersonDescriptor descriptor = new EditPersonDescriptor();
        descriptor.setName(Optional.of(person.getName()));
        descriptor.setPhone(Optional.of(person.getPhone()));
        descriptor.setEmail(Optional.of(person.getEmail()));
        descriptor.setAddress(Optional.of(person.getAddress()));
        descriptor.setTags(Optional.of(person.getTags()));

        return descriptor;
    }

    /**
     * Executes the given {@code editCommand}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the address book in the model matches {@code expectedAddressBook} <br>
     * - the filtered person list in the model matches {@code expectedFilteredList}
     */
    private void assertCommandSuccess(EditCommand editCommand, String expectedMessage,
            ReadOnlyAddressBook expectedAddressBook,
            List<? extends ReadOnlyPerson> expectedFilteredList) throws CommandException {
        CommandResult result = editCommand.execute();
        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(expectedAddressBook, model.getAddressBook());
        assertEquals(expectedFilteredList, model.getFilteredPersonList());
    }

    /**
     * Executes the given {@code editCommand}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book in the model remains unchanged <br>
     * - the filtered person list in the model remains unchanged
     */
    private void assertCommandFailure(EditCommand editCommand, String expectedMessage) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        List<ReadOnlyPerson> expectedFilteredList = new ArrayList<>(model.getFilteredPersonList());
        try {
            editCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedAddressBook, model.getAddressBook());
            assertEquals(expectedFilteredList, model.getFilteredPersonList());
        }
    }
}
