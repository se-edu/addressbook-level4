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
import seedu.address.model.tag.UniqueTagList;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TestPerson;

public class AddCommandTest {

    private static final String VALID_NAME = "valid name";
    private static final String VALID_PHONE = "61239876";
    private static final String VALID_EMAIL = "valid.email@mail.com";
    private static final String VALID_ADDRESS = "valid address";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    /*
     * Constructor tests ensure that any invalid values actually throws an exception.
     *
     * It does not test the validation logic of the values, that is the job of the unit
     * test of Name, Phone, Email, and Address.
     */

    @Test
    public void constructor_invalidName_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        new AddCommand("", VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, new UniqueTagList());
    }

    @Test
    public void constructor_invalidPhone_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        new AddCommand(VALID_NAME, "", VALID_EMAIL, VALID_ADDRESS, new UniqueTagList());
    }

    @Test
    public void constructor_invalidEmail_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        new AddCommand(VALID_NAME, VALID_PHONE, "", VALID_ADDRESS, new UniqueTagList());
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        new AddCommand(VALID_NAME, VALID_PHONE, VALID_EMAIL, "", new UniqueTagList());
    }

    @Test
    public void constructor_nullName_throwsAssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        new AddCommand(null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, new UniqueTagList());
    }

    @Test
    public void constructor_nullPhone_throwsAssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        new AddCommand(VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS, new UniqueTagList());
    }

    @Test
    public void constructor_nullEmail_throwsAssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        new AddCommand(VALID_NAME, VALID_PHONE, null, VALID_ADDRESS, new UniqueTagList());
    }

    @Test
    public void constructor_nullAddress_throwsAssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        new AddCommand(VALID_NAME, VALID_PHONE, VALID_EMAIL, null, new UniqueTagList());
    }

    @Test
    public void constructor_nullTags_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        new AddCommand(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, null);
    }

    @Test
    public void execute_newPersonNotInList_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        TestPerson validPerson = createValidPerson();

        CommandResult commandResult = getAddCommandForPerson(validPerson, modelStub).execute();

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validPerson), commandResult.feedbackToUser);
        assertTrue(modelStub.isAddPersonMethodCalledWith(validPerson));
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() throws Exception {
        Model modelStub = new ModelStubThrowingDuplicatePersonException();
        TestPerson validPerson = createValidPerson();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_PERSON);

        getAddCommandForPerson(validPerson, modelStub).execute();
    }

    /**
     * Creates a person with valid contact details.
     */
    private TestPerson createValidPerson() throws IllegalValueException {
        return new PersonBuilder().withName(VALID_NAME).withPhone(VALID_PHONE).withEmail(VALID_EMAIL)
                .withAddress(VALID_ADDRESS).build();
    }

    /**
     * Generates a new AddCommand with the details of the given person.
     */
    private AddCommand getAddCommandForPerson(TestPerson person, Model model) throws IllegalValueException {
        AddCommand command = new AddCommand(person.getName().toString(), person.getPhone().toString(),
                person.getEmail().toString(), person.getAddress().toString(), person.getTags());

        command.setData(model);
        return command;
    }

    /**
     * A Model stub that always throw a DuplicatePersonException when trying to add a person.
     */
    private class ModelStubThrowingDuplicatePersonException implements Model {
        public void addPerson(Person person) throws DuplicatePersonException {
            throw new DuplicatePersonException();
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
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingPersonAdded implements Model {
        private Person personAdded = null;

        public void addPerson(Person person) throws DuplicatePersonException {
            if (personAdded != null) {
                fail("This method should not be called twice.");
            }

            personAdded = person;
        }

        public boolean isAddPersonMethodCalledWith(TestPerson person) {
            return personAdded.equals(person);
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

}
