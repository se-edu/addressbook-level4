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
            fail("expected IndexOutOfBoundsException was not thrown.");
        } catch (IndexOutOfBoundsException ioobe) {
            // expected behaviour
        }
    }

    @Test
    public void execute_nonEmptyHistory() {
        history.add(HistoryCommand.COMMAND_WORD);
        assertCommandResult(historyCommand, HistoryCommand.MESSAGE_NO_HISTORY);

        history.add(ClearCommand.COMMAND_WORD);
        assertCommandResult(historyCommand, String.format(HistoryCommand.MESSAGE_SUCCESS, HistoryCommand.COMMAND_WORD));

        String randomCommandString = "randomCommand";
        history.add(randomCommandString);
        history.add("select 1");

        assertCommandResult(historyCommand, String.format(HistoryCommand.MESSAGE_SUCCESS, HistoryCommand.COMMAND_WORD
                + "\n" + ClearCommand.COMMAND_WORD + "\n" + randomCommandString));
    }

    /**
     * Asserts that the result message from the execution of {@code historyCommand} equals to {@code expectedMessage}
     */
    private void assertCommandResult(HistoryCommand historyCommand, String expectedMessage) {
        assertEquals(historyCommand.execute().feedbackToUser, expectedMessage);
    }
}
