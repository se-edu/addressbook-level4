package seedu.address.integration;

import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import org.junit.Test;

public class UnknownCommandTest extends AddressBookIntegrationTest {

    @Test
    public void execute_unknownCommandWord() {
        String unknownCommand = "uicfhmowqewca";
        assertCommandFailure(unknownCommand, MESSAGE_UNKNOWN_COMMAND);
    }
}
