package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.UniquePersonList.DuplicatePersonException;
import seedu.address.model.person.UniquePersonList.PersonNotFoundException;
import seedu.address.testutil.PersonBuilder;

public class AddCommandTest {

    private static final String VALID_NAME = "valid name";
    private static final String VALID_PHONE = "61239876";
    private static final String VALID_EMAIL = "valid.email@mail.com";
    private static final String VALID_ADDRESS = "valid address";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullParameters_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        new AddCommand(null);
    }

    @Test
    public void execute_newPersonNotInList_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Person validPerson = createValidPerson();

        CommandResult commandResult = getAddCommandForPerson(validPerson, modelStub).execute();

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validPerson), commandResult.feedbackToUser);
        assertTrue(modelStub.isAddPersonMethodCalledWith(validPerson));
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicatePersonException();
        Person validPerson = createValidPerson();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_PERSON);

        getAddCommandForPerson(validPerson, modelStub).execute();
    }

    /**
     * Creates a person with valid contact details.
     */
    private Person createValidPerson() throws IllegalValueException {
        return new PersonBuilder().withName(VALID_NAME).withPhone(VALID_PHONE).withEmail(VALID_EMAIL)
                .withAddress(VALID_ADDRESS).build();
    }

    /**
     * Generates a new AddCommand with the details of the given person.
     */
    private AddCommand getAddCommandForPerson(Person person, Model model) throws IllegalValueException {
        AddCommand command = new AddCommand(person);
        command.setData(model);
        return command;
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addPerson(Person person) throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyAddressBook newData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updatePerson(int filteredPersonListIndex, ReadOnlyPerson editedPerson)
                throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public UnmodifiableObservableList<ReadOnlyPerson> getFilteredPersonList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredListToShowAll() {
            fail("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Set<String> keywords) {
            fail("This method should not be called.");
        }
    }

    /**
     * A Model stub that always throw a DuplicatePersonException when trying to add a person.
     */
    private class ModelStubThrowingDuplicatePersonException extends ModelStub {
        @Override
        public void addPerson(Person person) throws DuplicatePersonException {
            throw new DuplicatePersonException();
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        private Person personAdded = null;

        @Override
        public void addPerson(Person person) throws DuplicatePersonException {
            if (personAdded != null) {
                fail("This method should not be called twice.");
            }

            personAdded = person;
        }

        public boolean isAddPersonMethodCalledWith(Person person) {
            return personAdded.equals(person);
        }
    }

}
