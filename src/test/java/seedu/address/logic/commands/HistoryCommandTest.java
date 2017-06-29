package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class HistoryCommandTest {
    private HistoryCommand historyCommand;
    private CommandHistory history;

    @Before
    public void setUp() {
        Model model = new ModelManager();
        history = new CommandHistory();
        historyCommand = new HistoryCommand();
        historyCommand.setData(model, history);
    }

    @Test
    public void execute() {
        assertCommandResult(historyCommand, HistoryCommand.MESSAGE_NO_HISTORY);

        String command1 = "clear";
        history.add(command1);
        assertCommandResult(historyCommand, String.format(HistoryCommand.MESSAGE_SUCCESS, command1));

        String command2 = "randomCommand";
        String command3 = "select 1";
        history.add(command2);
        history.add(command3);

        String expectedMessage = String.format(HistoryCommand.MESSAGE_SUCCESS,
                String.join("\n", command1, command2, command3));

        assertCommandResult(historyCommand, expectedMessage);
    }

    /**
     * Asserts that the result message from the execution of {@code historyCommand} equals to {@code expectedMessage}
     */
    private void assertCommandResult(HistoryCommand historyCommand, String expectedMessage) {
        assertEquals(expectedMessage, historyCommand.execute().feedbackToUser);
    }
}
