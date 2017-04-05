package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

import seedu.address.commons.core.Messages;
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
 * An integration test class that tests
 * edit command's interaction with the Model.
 */
public class EditCommandIntegrationTest {

    private Model model = new ModelManager(new TypicalPersons().getTypicalAddressBook(), new UserPrefs());;
    private Parser parser = new Parser();

    @Test
    public void execute_validCommand_succeeds() throws Exception {
        int addressBookIndex = 0;
        Person editedPerson = new PersonBuilder().withName("Bobby").withPhone("91234567")
                                    .withEmail("bobby@example.com").withAddress("Block 123, Bobby Street 3")
                                    .withTags("husband").build();

        String userInput = PersonUtil.getEditCommand(addressBookIndex, editedPerson);
        Command command = prepareCommand(userInput);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);

        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        expectedAddressBook.updatePerson(addressBookIndex, editedPerson);

        List<ReadOnlyPerson> expectedShownList = expectedAddressBook.getPersonList();

        assertCommandSuccess(command, expectedMessage, expectedAddressBook, expectedShownList);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person alice = new TypicalPersons().alice; // Alice is the first person added to the typical address book
        int addressBookIndex = 1; // zero-based index for the second person
        String userInput = PersonUtil.getEditCommand(addressBookIndex, alice); // edit the second person to Alice
        Command command = prepareCommand(userInput);
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_invalidPersonIndex_throwsCommandException() {
        int outOfBoundIndex = new TypicalPersons().getTypicalPersons().length + 1;
        String userInput = "edit " + outOfBoundIndex + " Bobby";
        Command command = prepareCommand(userInput);
        assertCommandFailure(command, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    private Command prepareCommand(String userInput) {
        Command command = parser.parseCommand(userInput);
        command.setData(model);
        return command;
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the address book in the model matches {@code expectedAddressBook} <br>
     * - the filtered person list in the model matches {@code expectedFilteredList} <br>
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
     */
    private void assertCommandFailure(Command command, String expectedMessage) {
        try {
            command.execute();
            fail("expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }
}
