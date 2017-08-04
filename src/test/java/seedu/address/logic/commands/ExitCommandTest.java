package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT;

import org.junit.After;
import org.junit.Test;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ExitAppRequestEvent;
import seedu.address.testutil.EventsCollector;

public class ExitCommandTest {

    @After
    public void tearDown() {
        EventsCenter.clearSubscribers();
    }

    @Test
    public void execute_exit_success() {
        EventsCollector eventCollector = new EventsCollector();

        CommandResult result = new ExitCommand().execute();
        assertEquals(MESSAGE_EXIT_ACKNOWLEDGEMENT, result.feedbackToUser);
        assertTrue(eventCollector.getMostRecent() instanceof ExitAppRequestEvent);
        assertTrue(eventCollector.getSize() == 1);
    }
}
