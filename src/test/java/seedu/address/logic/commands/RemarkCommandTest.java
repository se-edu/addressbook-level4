package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 *
 */
public class RemarkCommandTest {
    @Test
    public void execute_remark_success() throws CommandException {
        try {
            CommandResult result = new RemarkCommand(5, "lmao").executeUndoableCommand();
        } catch (CommandException e) {
            assertEquals(e.getMessage(), "5 lmao");
        }
    }

}
