package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.UniquePersonList.DuplicatePersonException;
import seedu.address.model.tag.UniqueTagList;

public class AddCommandTest {

    private static final String VALID_NAME = "valid name";
    private static final String VALID_PHONE = "61239876";
    private static final String VALID_EMAIL = "valid.email@mail.com";
    private static final String VALID_ADDRESS = "valid address";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

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
        ModelStub modelStub = new ModelStub();
        Person validPerson = new Person(new Name(VALID_NAME), new Phone(VALID_PHONE), new Email(VALID_EMAIL),
                                        new Address(VALID_ADDRESS), new UniqueTagList());

        AddCommand command = getAddCommandForPerson(validPerson, modelStub);
        CommandResult commandResult = command.execute();

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validPerson), commandResult.feedbackToUser);
        assertTrue(modelStub.isPersonAdded(validPerson));
    }

    @Test
    public void execute_duplicatePerson_throwsDuplicatePersonException() throws Exception {
        ModelStub modelStub = new ModelStub();
        Person validPerson = new Person(new Name(VALID_NAME), new Phone(VALID_PHONE), new Email(VALID_EMAIL),
                                        new Address(VALID_ADDRESS), new UniqueTagList());

        modelStub.addPerson(validPerson);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_PERSON);

        AddCommand command = getAddCommandForPerson(validPerson, modelStub);
        command.execute();
    }

    /**
     * Generates a new AddCommand with the details of the given person.
     */
    private AddCommand getAddCommandForPerson(Person person, ModelStub modelStub) throws IllegalValueException {
        Set<String> tags = new HashSet<>();
        person.getTags().forEach((t) -> tags.add(t.tagName));

        AddCommand command = new AddCommand(person.getName().toString(), person.getPhone().toString(),
                                            person.getEmail().toString(), person.getAddress().toString(),
                                            tags);

        command.setData(modelStub);
        return command;
    }

    /**
     * A model stub injected into the add command, for testing add's functionality.
     *
     * We do not want the correctness of {@link ModelManager#addPerson(Person)} to affect the unit testing
     * of AddCommand. Therefore, it is overridden to simplify the logic.
     */
    private class ModelStub extends ModelManager {

        private List<Person> personInList;

        public ModelStub() {
            personInList = new ArrayList<>();
        }

        @Override
        public void addPerson(Person person) throws DuplicatePersonException {
            for (Person existingPerson : personInList) {
                if (existingPerson.equals(person)) {
                    throw new DuplicatePersonException();
                }
            }

            personInList.add(person);
        }

        /**
         * Checks whether the particular person is added.
         */
        public boolean isPersonAdded(Person person) {
            return personInList.contains(person);
        }
    }
}
