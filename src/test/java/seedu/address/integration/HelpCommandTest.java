package seedu.address.integration;

import static org.junit.Assert.assertTrue;

import java.util.Collections;

import org.junit.Test;

import seedu.address.logic.commands.HelpCommand;
import seedu.address.model.AddressBook;

public class HelpCommandTest extends AddressBookIntegrationTest {

    @Test
    public void execute_help() {
        assertCommandSuccess("help", HelpCommand.SHOWING_HELP_MESSAGE, new AddressBook(), Collections.emptyList());
        assertTrue(helpShown);
    }
}
