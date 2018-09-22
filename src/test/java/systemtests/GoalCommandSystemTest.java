package systemtests;

import org.junit.Test;
import seedu.address.logic.commands.GoalCommand;
import seedu.address.model.Model;

public class GoalCommandSystemTest extends AddressBookSystemTest{
    @Test
    public void goal() {
        // Perform goal setting with a valid value
        double newGoal = 4.5;
        assertGoalSuccess(newGoal);
    }

    public void assertGoalSuccess(double goal) {
        String command = GoalCommand.COMMAND_WORD + " " + goal;
        Model expectedModel = getModel();
        String expectedResultMessage = String.format(GoalCommand.MESSAGE_SUCCESS, goal);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
    }
}
