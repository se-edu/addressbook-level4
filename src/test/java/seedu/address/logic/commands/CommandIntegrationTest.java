package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Parser;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.TypicalTestPersons;

/**
 * An Integration Test class for each command's interaction
 * with the Model and other Logic components (e.g. parser).
 */
public abstract class CommandIntegrationTest {

    protected Model model;
    protected Parser parser;

    @Before
    public void setUp() {
        ReadOnlyAddressBook typicalAddressBook = new TypicalTestPersons().getTypicalAddressBook();
        model = new ModelManager(typicalAddressBook, new UserPrefs());
        parser = new Parser();
    }

    protected Command prepareCommand(String userInput) {
        Command cmd = parser.parseCommand(userInput);
        cmd.setData(model);
        return cmd;
    }

    /**
     * Executes {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the internal address book data are same as those in the {@code expectedAddressBook} <br>
     * - the filtered person list shown by UI matches the {@code expectedShownList} <br>
     */
    protected void assertCommandSuccess(Command command,
                                        String expectedMessage,
                                        ReadOnlyAddressBook expectedAddressBook,
                                        List<? extends ReadOnlyPerson> expectedShownList) {
        try {
            CommandResult result = command.execute();
            assertEquals(expectedMessage, result.feedbackToUser);

            // Verify the state of address book is as expected
            assertEquals(expectedAddressBook, model.getAddressBook());

            // Verify the ui display elements should contain the right data
            assertEquals(expectedShownList, model.getFilteredPersonList());
        } catch (Exception e) {
            fail("unexpected exception: " + e.getMessage());
        }
    }

    /**
     * Executes {@code command}, confirms that <br>
     * - a CommandException is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     */
    protected void assertCommandFailure(Command command, String expectedMessage) {
        try {
            command.execute();
            fail("expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

}
