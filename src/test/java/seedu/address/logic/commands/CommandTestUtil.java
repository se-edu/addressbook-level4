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
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book in the {@code model} remains unchanged <br>
     * - the filtered person list in the {@code model} remains unchanged
     */
    public static void assertCommandFailure(Command command, Model model, String expectedMessage, Model expectedModel) {
        try {
            command.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedModel, model);
        }
    }
}
