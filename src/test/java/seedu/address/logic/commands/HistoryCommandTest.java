package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;

import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public void execute() {
        assertCommandResult(historyCommand, HistoryCommand.MESSAGE_NO_HISTORY);

        history.add(ClearCommand.COMMAND_WORD);
        assertCommandResult(historyCommand, String.format(HistoryCommand.MESSAGE_SUCCESS, ClearCommand.COMMAND_WORD));

        String randomCommand = "randomCommand";
        String selectInput = "select 1";
        history.add(randomCommand);
        history.add(selectInput);

        String expectedMessage = String.format(HistoryCommand.MESSAGE_SUCCESS,
                Stream.of(ClearCommand.COMMAND_WORD, randomCommand, selectInput).collect(
                        Collectors.joining("\n")));

        assertCommandResult(historyCommand, expectedMessage);
    }

    /**
     * Asserts that the result message from the execution of {@code historyCommand} equals to {@code expectedMessage}
     */
    private void assertCommandResult(HistoryCommand historyCommand, String expectedMessage) {
        assertEquals(expectedMessage, historyCommand.execute().feedbackToUser);
    }
}
