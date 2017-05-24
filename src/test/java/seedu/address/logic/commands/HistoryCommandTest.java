package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.History;
import seedu.address.logic.HistoryManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class HistoryCommandTest {
    private HistoryCommand historyCommand;
    private History history;

    @Before
    public void setUp() {
        Model model = new ModelManager();
        history = new HistoryManager();
        historyCommand = new HistoryCommand();
        historyCommand.setData(model, history);
    }

    @Test
    public void execute_emptyHistory_throwsIndexOutOfBoundsException() {
        try {
            historyCommand.execute();
            fail();
        } catch (IndexOutOfBoundsException ioobe) {
            // expected behaviour
        }
    }

    @Test
    public void execute_nonEmptyHistory() {
        String historyString = "history";
        String clearString = "clear";
        String randomCommandString = "randomCommand";

        history.add(historyString);
        assertCommandResult(historyCommand, HistoryCommand.MESSAGE_NO_HISTORY);

        history.add(clearString);
        assertCommandResult(historyCommand, String.format(HistoryCommand.MESSAGE_SUCCESS, "history"));

        history.add(randomCommandString);
        history.add("select 1");

        assertCommandResult(historyCommand, String.format(
                HistoryCommand.MESSAGE_SUCCESS, historyString + "\n" + clearString + "\n" + randomCommandString));
    }

    /**
     * Asserts that the result message from the execution of {@code historyCommand} equals to {@code expectedMessage}
     */
    private void assertCommandResult(HistoryCommand historyCommand, String expectedMessage) {
        assertEquals(historyCommand.execute().feedbackToUser, expectedMessage);
    }
}
