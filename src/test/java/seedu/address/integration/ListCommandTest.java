package seedu.address.integration;

import java.util.List;

import org.junit.Test;

import seedu.address.logic.commands.ListCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.person.ReadOnlyPerson;

public class ListCommandTest extends AddressBookIntegrationTest {

    @Test
    public void execute_list_showsAllPersons() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        AddressBook expectedAB = helper.generateAddressBook(2);
        List<? extends ReadOnlyPerson> expectedList = expectedAB.getPersonList();

        // prepare address book state
        helper.addToModel(model, 2);

        assertCommandSuccess("list",
            ListCommand.MESSAGE_SUCCESS,
            expectedAB,
            expectedList);
    }
}
