package seedu.address.integration;

import java.util.Collections;

import org.junit.Test;

import seedu.address.logic.commands.ExitCommand;
import seedu.address.model.AddressBook;

public class ExitCommandTest extends AddressBookIntegrationTest {

    @Test
    public void execute_exit() {
        assertCommandSuccess("exit", ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT,
                new AddressBook(), Collections.emptyList());
    }
}
