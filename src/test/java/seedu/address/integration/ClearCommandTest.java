package seedu.address.integration;

import java.util.Collections;

import org.junit.Test;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.model.AddressBook;

public class ClearCommandTest extends AddressBookIntegrationTest {

    @Test
    public void execute_clear() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        model.addPerson(helper.generatePerson(1));
        model.addPerson(helper.generatePerson(2));
        model.addPerson(helper.generatePerson(3));

        assertCommandSuccess("clear", ClearCommand.MESSAGE_SUCCESS, new AddressBook(), Collections.emptyList());
    }
}
