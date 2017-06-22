package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.HelpCommand.SHOWING_HELP_MESSAGE;

import org.junit.Test;

import seedu.address.commons.events.ui.ShowHelpRequestEvent;
import seedu.address.testutil.EventsCollector;

public class HelpCommandTest {

    @Test
    public void execute_help_success() {
        EventsCollector eventCollector = new EventsCollector();

        CommandResult result = new HelpCommand().execute();
        assertEquals(SHOWING_HELP_MESSAGE, result.feedbackToUser);
        assertTrue(eventCollector.getMostRecent() instanceof ShowHelpRequestEvent);
        assertTrue(eventCollector.getSize() == 1);
    }
}
