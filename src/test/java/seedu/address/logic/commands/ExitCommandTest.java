package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT;

import org.junit.Test;

import seedu.address.commons.events.ui.ExitAppRequestEvent;
import seedu.address.testutil.EventsCollector;

public class ExitCommandTest {

    @Test
    public void execute_exit_success() {
        EventsCollector eventCollector = new EventsCollector();

        CommandResult result = new ExitCommand().execute();
        assertEquals(MESSAGE_EXIT_ACKNOWLEDGEMENT, result.feedbackToUser);
        assertTrue(eventCollector.get(0) instanceof ExitAppRequestEvent);
    }
}
