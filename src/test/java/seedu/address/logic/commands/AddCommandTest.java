package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.UniquePersonList.DuplicatePersonException;
import seedu.address.model.person.UniquePersonList.PersonNotFoundException;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.testutil.TestPerson;
import seedu.address.testutil.TypicalTestPersons;

public class AddCommandTest {

    private AddModelStub model;

    private TestPerson newPerson;
    private TestPerson duplicatePerson;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        TypicalTestPersons typicalTestPersons = new TypicalTestPersons();

        newPerson = typicalTestPersons.alice;
        duplicatePerson = typicalTestPersons.daniel;
        model = new AddModelStub(duplicatePerson);
    }

    @Test
    public void constructor_invalidName_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        new AddCommand("", "61239876", "valid.email@mail.com", "valid address", new HashSet<>());
    }

    @Test
    public void constructor_invalidPhone_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        new AddCommand("valid name", "", "valid.email@mail.com", "valid address", new HashSet<>());
    }

    @Test
    public void constructor_invalidEmail_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        new AddCommand("valid name", "61239876", "", "valid address", new HashSet<>());
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        new AddCommand("valid name", "61239876", "valid.email@mail.com", "", new HashSet<>());
    }

    @Test
    public void execute_newPersonNotInList_addSuccessful() throws Exception {
        AddCommand command = getAddCommandForPerson(newPerson);
        assertCommandBehaviour(String.format(AddCommand.MESSAGE_SUCCESS, newPerson), command);
    }

    @Test
    public void execute_duplicatePerson_throwsDuplicatePersonException() throws Exception {
        AddCommand command = getAddCommandForPerson(duplicatePerson);
        assertCommandBehaviour(AddCommand.MESSAGE_DUPLICATE_PERSON, command);
    }

    /**
     * Asserts that the command produced the correct message after execution.
     */
    public void assertCommandBehaviour(String expectedString, Command command) {
        CommandResult commandResult = command.execute();

        assertEquals(expectedString, commandResult.feedbackToUser);
    }

    /**
     * Generates a new AddCommand with the details of the given person.
     */
    private AddCommand getAddCommandForPerson(TestPerson testPerson) throws IllegalValueException {
        Set<String> tags = convertTagsToStringSet(testPerson.getTags());

        AddCommand command = new AddCommand(testPerson.getName().toString(),
                                            testPerson.getPhone().toString(),
                                            testPerson.getEmail().toString(),
                                            testPerson.getAddress().toString(),
                                            tags);

        command.setData(model);
        return command;
    }

    /**
     * Converts the list of tags into a set of strings.
     */
    private Set<String> convertTagsToStringSet(UniqueTagList uniqueTagList) {
        Set<String> stringSet = new HashSet<String>();
        uniqueTagList.forEach((t) -> stringSet.add(t.tagName));
        return stringSet;
    }

    /**
     * A model stub injected into the add command, for testing add's functionality.
     *
     * Only {@link #addPerson(Person)} is implemented for this stub.
     */
    private class AddModelStub implements Model {

        private TestPerson personInList;

        protected AddModelStub(TestPerson personInList) {
            this.personInList = personInList;
        }

        @Override
        public void resetData(ReadOnlyAddressBook newData) {
            // do nothing
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return null;
        }

        @Override
        public void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException {
            // do nothing
        }

        @Override
        public void addPerson(Person person) throws DuplicatePersonException {
            if (person.equals(personInList)) {
                throw new DuplicatePersonException();
            }
        }

        @Override
        public void updatePerson(int filteredPersonListIndex, ReadOnlyPerson editedPerson)
                throws DuplicatePersonException {
            // do nothing
        }

        @Override
        public UnmodifiableObservableList<ReadOnlyPerson> getFilteredPersonList() {
            return null;
        }

        @Override
        public void updateFilteredListToShowAll() {
            // do nothing
        }

        @Override
        public void updateFilteredPersonList(Set<String> keywords) {
            // do nothing
        }
    }
}
