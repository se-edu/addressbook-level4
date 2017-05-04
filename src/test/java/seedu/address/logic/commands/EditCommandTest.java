package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.util.IndexUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Parser;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;
import seedu.address.testutil.TypicalPersons;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private static final int ZERO_BASED_INDEX_FIRST_PERSON = 0;
    private static final int ZERO_BASED_INDEX_SECOND_PERSON =  ZERO_BASED_INDEX_FIRST_PERSON + 1;

    private Model model = new ModelManager(new TypicalPersons().getTypicalAddressBook(), new UserPrefs());
    private Parser parser = new Parser();

    /*
     * Unit Tests
     */
    @Test
    public void execute_notAllFieldsSpecified_success() throws Exception {
        Command command = prepareCommand("edit 2 t/sweetie t/bestie");

        ReadOnlyPerson personToEdit = model.getAddressBook().getPersonList().get(IndexUtil.oneToZeroIndex(2));
        Person editedPerson = new PersonBuilder(personToEdit).withTags("sweetie", "bestie").build();

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);

        assertCommandSuccess(command, expectedMessage);
    }

    @Test
    public void edit_clearTags_success() throws Exception {
        Command command = prepareCommand("edit 2 t/");

        ReadOnlyPerson personToEdit = model.getAddressBook().getPersonList().get(IndexUtil.oneToZeroIndex(2));
        Person editedPerson = new PersonBuilder(personToEdit).withTags().build();

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);

        assertCommandSuccess(command, expectedMessage);
    }

    @Test
    public void execute_invalidPersonIndex_throwsCommandException() {
        int oneBasedOutOfBoundIndex = model.getFilteredPersonList().size() + 1;
        String userInput = "edit " + oneBasedOutOfBoundIndex + " Bobby";
        Command command = prepareCommand(userInput);
        assertCommandFailure(command, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /*
     * Unit & Integration Test
     */
    @Test
    public void execute_validCommand_succeeds() throws Exception { {
        Person editedPerson = new PersonBuilder().withName("Bobby").withPhone("91234567")
                                    .withEmail("bobby@example.com").withAddress("Block 123, Bobby Street 3")
                                    .withTags("husband").build();

        String userInput = PersonUtil.getEditCommand(ZERO_BASED_INDEX_FIRST_PERSON, editedPerson);
        Command command = prepareCommand(userInput);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);

        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        expectedAddressBook.updatePerson(ZERO_BASED_INDEX_FIRST_PERSON, editedPerson);

        List<ReadOnlyPerson> expectedFilteredList = new ArrayList<>(model.getFilteredPersonList());

        assertCommandSuccess(command, expectedMessage, expectedAddressBook, expectedFilteredList);
    }

    /*
     * Integration Test
     */
    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person firstPerson = new Person(model.getFilteredPersonList().get(ZERO_BASED_INDEX_FIRST_PERSON));
        String userInput = PersonUtil.getEditCommand(ZERO_BASED_INDEX_SECOND_PERSON, firstPerson);
        Command command = prepareCommand(userInput);
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    private Command prepareCommand(String userInput) {
        Command command = parser.parseCommand(userInput);
        command.setData(model);
        return command;
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * This is used for unit tests.
     * @see #assertCommandSuccess(Command, String, ReadOnlyAddressBook, List)
     */
    private void assertCommandSuccess(Command command, String expectedMessage) throws CommandException {
        CommandResult result = command.execute();
        assertEquals(expectedMessage, result.feedbackToUser);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the address book in the model matches {@code expectedAddressBook} <br>
     * - the filtered person list in the model matches {@code expectedFilteredList} <br>
     * This is used for integration tests.
     * @see #assertCommandSuccess(Command, String)
     */
    private void assertCommandSuccess(Command command, String expectedMessage,
            ReadOnlyAddressBook expectedAddressBook,
            List<? extends ReadOnlyPerson> expectedFilteredList) throws CommandException {
        CommandResult result = command.execute();
        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(expectedAddressBook, model.getAddressBook());
        assertEquals(expectedFilteredList, model.getFilteredPersonList());
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book in the model remains unchanged <br>
     * - the filtered person list in the model remains unchanged <br>
     */
    private void assertCommandFailure(Command command, String expectedMessage) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        List<ReadOnlyPerson> expectedFilteredList = new ArrayList<>(model.getFilteredPersonList());
        try {
            command.execute();
            fail("expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedAddressBook, model.getAddressBook());
            assertEquals(expectedFilteredList, model.getFilteredPersonList());
        }
    }
}
