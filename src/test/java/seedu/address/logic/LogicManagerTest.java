package seedu.address.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;


public class LogicManagerTest {

    private Model model;
    private Logic logic;

    @Before
    public void setUp() {
        model = new ModelManager();
        logic = new LogicManager(model);
    }

    @Test
    public void execute_invalid() {
        String invalidCommand = "       ";
        assertParseException(invalidCommand,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
    }

    /**
     * Executes the command, confirms that no exceptions are thrown and that the result message is correct.
     * Also confirms that {@code expectedModel} is as specified.
     * @see #assertCommandBehavior(Class, String, String, Model)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage, Model expectedModel) {
        assertCommandBehavior(null, inputCommand, expectedMessage, expectedModel);
    }

    /**
     * Executes the command, confirms that a ParseException is thrown and that the result message is correct.
     * @see #assertCommandBehavior(Class, String, String, Model)
     */
    private void assertParseException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     * @see #assertCommandBehavior(Class, String, String, Model)
     */
    private <T> void assertCommandFailure(String inputCommand, Class<T> expectedException, String expectedMessage) {
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        assertCommandBehavior(expectedException, inputCommand, expectedMessage, expectedModel);
    }

    /**
     * Executes the command, confirms that the result message is correct and that the expected exception is thrown,
     * and also confirms that the following two parts of the LogicManager object's state are as expected:<br>
     *      - the internal model manager data are same as those in the {@code expectedModel} <br>
     *      - {@code expectedModel}'s address book was saved to the storage file.
     */
    private <T> void assertCommandBehavior(Class<T> expectedException, String inputCommand,
                                           String expectedMessage, Model expectedModel) {

        try {
            CommandResult result = logic.execute(inputCommand);
            assertEquals(expectedException, null);
            assertEquals(expectedMessage, result.feedbackToUser);
        } catch (CommandException | ParseException e) {
            assertEquals(expectedException, e.getClass());
            assertEquals(expectedMessage, e.getMessage());
        }

        assertEquals(expectedModel, model);
    }

    @Test
    public void execute_unknownCommandWord() {
        String unknownCommand = "uicfhmowqewca";
        assertParseException(unknownCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_verifyHistory_success() throws Exception {
        String validCommand = "clear";
        logic.execute(validCommand);

        String invalidCommandParse = "   adds   Bob   ";
        try {
            logic.execute(invalidCommandParse);
            fail("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }

        String invalidCommandExecute = "delete 1"; // address book is of size 0; index out of bounds
        try {
            logic.execute(invalidCommandExecute);
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, ce.getMessage());
        }

        String expectedMessage = String.format(HistoryCommand.MESSAGE_SUCCESS,
                String.join("\n", validCommand, invalidCommandParse, invalidCommandExecute));
        assertCommandSuccess("history", expectedMessage, model);
    }
}
