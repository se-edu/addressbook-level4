package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.address.logic.commands.GoalCommand;
import seedu.address.model.Model;


/**
 * System test for Goal Command
 */
public class GoalCommandSystemTest extends AddressBookSystemTest {
    @Test
    public void setGoalSuccess() {
        /* Case: Set goal with valid value
         * -> goal command handled correctly
         */
        double newGoal = 4.5;
        assertGoalSuccess(newGoal);

        newGoal = 5.0;
        assertGoalSuccess(newGoal);
    }

    @Test
    public void setGoalFailure() {
        /* Case: Set goal with valid value
         * -> goal command handled correctly
         */
        double newGoal = -1;
        assertGoalFailure(newGoal);
    }

    /**
     * Assert that the given goal would result in a failure action.
     * @param goal
     */
    private void assertGoalFailure(double goal) {
        String expectedResultMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, GoalCommand.MESSAGE_USAGE);
        assertCommandFailure(getCommandString(goal), getModel(), expectedResultMessage);
    }

    /**
     * Assert that the given goal would result in a successful action.
     * @param goal
     */
    public void assertGoalSuccess(double goal) {
        String expectedResultMessage = String.format(GoalCommand.MESSAGE_SUCCESS, goal);
        assertCommandSuccess(getCommandString(goal), getModel(), expectedResultMessage);
    }

    private String getCommandString(double goal) {
        return GoalCommand.COMMAND_WORD + " " + goal;
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

    private void assertCommandFailure(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
    }
}
