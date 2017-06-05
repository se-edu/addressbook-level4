package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the address book and the filtered person list in the {@code actualModel} matches that of {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) throws CommandException {
        CommandResult result = command.execute();
        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(expectedModel, actualModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book and the filtered person list in the {@code actualModel} is same as {@code expectedModel}
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        try {
            command.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedModel, actualModel);
        }
    }
}
