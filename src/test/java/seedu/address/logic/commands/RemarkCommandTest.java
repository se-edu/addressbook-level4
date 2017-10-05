package seedu.address.logic.commands;

import org.junit.Test;
import seedu.address.logic.commands.exceptions.CommandException;

import static org.junit.Assert.*;

public class RemarkCommandTest {
    @Test
    public void execute_remark_success() throws CommandException {
        try {
            CommandResult result = new RemarkCommand().executeUndoableCommand();
        } catch (CommandException e){
            assertEquals(e.getMessage(), "Lol");
        }
    }

}