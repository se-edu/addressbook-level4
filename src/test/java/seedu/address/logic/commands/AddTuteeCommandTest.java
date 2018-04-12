package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.tutee.Tutee;
import seedu.address.testutil.ModelStub;
import seedu.address.testutil.TuteeBuilder;

//@@author ChoChihTun
public class AddTuteeCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullTutee_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddTuteeCommand(null);
    }

    @Test
    public void execute_tuteeAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Tutee validTutee = new TuteeBuilder().build();

        CommandResult commandResult = getAddTuteeCommandForTutee(validTutee, modelStub).execute();

        assertEquals(String.format(AddTuteeCommand.MESSAGE_SUCCESS, validTutee), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validTutee), modelStub.personsAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicatePersonException();
        Tutee validTutee = new TuteeBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddTuteeCommand.MESSAGE_DUPLICATE_PERSON);

        getAddTuteeCommandForTutee(validTutee, modelStub).execute();
    }

    @Test
    public void equals_validArgs_returnsTrue() {
        Tutee alice = new TuteeBuilder().withName("Alice").build();
        AddTuteeCommand addAliceCommand = new AddTuteeCommand(alice);

        // same object
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // objects with same value
        AddTuteeCommand addAliceCommandCopy = new AddTuteeCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));
    }

    @Test
    public void equals_invalidArgs_returnsFalse() {
        Tutee alice = new TuteeBuilder().withName("Alice").build();
        Tutee bob = new TuteeBuilder().withName("Bob").build();
        AddTuteeCommand addAliceCommand = new AddTuteeCommand(alice);
        AddTuteeCommand addBobCommand = new AddTuteeCommand(bob);

        // null
        assertFalse(addAliceCommand.equals(null));

        // invalid parameter data type
        assertFalse(addAliceCommand.equals(1));

        // different tutee
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * Generates a new AddTuteeCommand with the details of the given tutee.
     */
    private AddTuteeCommand getAddTuteeCommandForTutee(Tutee tutee, Model model) {
        AddTuteeCommand command = new AddTuteeCommand(tutee);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * A Model stub that always throw a DuplicatePersonException when trying to add a person.
     */
    private class ModelStubThrowingDuplicatePersonException extends ModelStub {
        @Override
        // A tutee is a person
        public void addPerson(Person person) throws DuplicatePersonException {
            throw new DuplicatePersonException();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Person> personsAdded = new ArrayList<>();

        @Override
        // A tutee is a person
        public void addPerson(Person person) throws DuplicatePersonException {
            requireNonNull(person);
            personsAdded.add(person);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
