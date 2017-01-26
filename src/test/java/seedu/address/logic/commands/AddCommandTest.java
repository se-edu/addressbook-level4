package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList.DuplicatePersonException;
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
        new AddCommand("", VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, new HashSet<>());
    }

    @Test
    public void constructor_invalidPhone_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        new AddCommand(VALID_NAME, "", VALID_EMAIL, VALID_ADDRESS, new HashSet<>());
    }

    @Test
    public void constructor_invalidEmail_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        new AddCommand(VALID_NAME, VALID_PHONE, "", VALID_ADDRESS, new HashSet<>());
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        new AddCommand(VALID_NAME, VALID_PHONE, VALID_EMAIL, "", new HashSet<>());
    }

    @Test
    public void execute_newPersonNotInList_addSuccessful() throws Exception {
        UniquePersonModelStub modelStub = new UniquePersonModelStub();
        TestPerson validPerson = new PersonBuilder()
                .withName(VALID_NAME)
                .withPhone(VALID_PHONE)
                .withEmail(VALID_EMAIL)
                .withAddress(VALID_ADDRESS)
                .build();

        CommandResult commandResult = getAddCommandForPerson(validPerson, modelStub).execute();

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validPerson), commandResult.feedbackToUser);
    }

    @Test
    public void execute_duplicatePerson_throwsDuplicatePersonException() throws Exception {
        DuplicatePersonModelStub modelStub = new DuplicatePersonModelStub();
        TestPerson validPerson = new PersonBuilder()
                .withName(VALID_NAME)
                .withPhone(VALID_PHONE)
                .withEmail(VALID_EMAIL)
                .withAddress(VALID_ADDRESS)
                .build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_PERSON);

        getAddCommandForPerson(validPerson, modelStub).execute();
    }

    /**
     * Generates a new AddCommand with the details of the given person.
     */
    private AddCommand getAddCommandForPerson(TestPerson person, ModelManager modelStub) throws IllegalValueException {
        Set<String> tags = person.getTagsAsStringsSet();

        AddCommand command = new AddCommand(person.getName().toString(), person.getPhone().toString(),
                person.getEmail().toString(), person.getAddress().toString(),
                tags);

        command.setData(modelStub);
        return command;
    }

    /**
     * A model stub injected into the add command, that always reject person being added
     * by treating it as a duplicate.
     *
     * The stub inherits from ModelManager instead of implementing the Model interface so that we do not have
     * too many empty methods.
     */
    private class DuplicatePersonModelStub extends ModelManager {
        public void addPerson(Person person) throws DuplicatePersonException {
            throw new DuplicatePersonException();
        }
    }

    /**
     * A model stub injected into the add command, that always accept the person being added
     * by treating it as a unique person.
     *
     * The stub inherits from ModelManager instead of implementing the Model interface so that we do not have
     * too many empty methods.
     */
    private class UniquePersonModelStub extends ModelManager {
        public void addPerson(Person person) throws DuplicatePersonException {
            // always accept
        }
    }
}
