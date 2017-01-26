package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList.DuplicatePersonException;
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
    public void constructor_noName_throwsAssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        new AddCommand(null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, new UniqueTagList());
    }

    @Test
    public void constructor_noPhone_throwsAssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        new AddCommand(VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS, new UniqueTagList());
    }

    @Test
    public void constructor_noEmail_throwsAssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        new AddCommand(VALID_NAME, VALID_PHONE, null, VALID_ADDRESS, new UniqueTagList());
    }

    @Test
    public void constructor_noAddress_throwsAssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        new AddCommand(VALID_NAME, VALID_PHONE, VALID_EMAIL, null, new UniqueTagList());
    }

    @Test
    public void constructor_noTags_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        new AddCommand(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, null);
    }

    @Test
    public void execute_newPersonNotInList_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        TestPerson validPerson = createValidPerson();

        CommandResult commandResult = getAddCommandForPerson(validPerson, modelStub).execute();

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validPerson), commandResult.feedbackToUser);
        assertTrue(modelStub.isAddPersonMethodCalled());
    }

    @Test
    public void execute_duplicatePerson_throwsDuplicatePersonException() throws Exception {
        ModelManager modelStub = new ModelStubThrowingDuplicatePersonException();
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
    private class ModelStubThrowingDuplicatePersonException extends ModelManager {
        public void addPerson(Person person) throws DuplicatePersonException {
            throw new DuplicatePersonException();
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelManager {
        private boolean addPersonMethodCalled = false;

        public void addPerson(Person person) throws DuplicatePersonException {
            addPersonMethodCalled = true;
        }

        public boolean isAddPersonMethodCalled() {
            return addPersonMethodCalled;
        }
    }

}
