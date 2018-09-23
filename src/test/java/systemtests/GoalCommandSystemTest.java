package systemtests;

import org.junit.Test;

import seedu.address.logic.commands.GoalCommand;
import seedu.address.model.Model;

/**
 * System test for Goal Command
 */
public class GoalCommandSystemTest extends AddressBookSystemTest {
    @Test
    public void goal() {
        /* Case: Set goal with valid value
         * -> goal command handled correctly
         */
        double newGoal = 4.5;
        assertGoalSuccess(newGoal);
    }

    /**
     * Assert that the given goal would result in a successful action.
     * @param goal
     */
    public void assertGoalSuccess(double goal) {
        String command = GoalCommand.COMMAND_WORD + " " + goal;
        Model expectedModel = getModel();
        String expectedResultMessage = String.format(GoalCommand.MESSAGE_SUCCESS, goal);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Assert given command would be successful
     * @param command
     * @param expectedModel
     * @param expectedResultMessage
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
    }
}
