package guitests;

import org.junit.Test;
import seedu.address.testutil.TestPerson;
import seedu.address.testutil.TestUtil;

import static org.junit.Assert.assertTrue;

public class ListCommandTest extends AddressBookGuiTest {

    @Test
    public void list() {
        assertListSuccess(td.getTypicalPersons());

        //list after showing empty sub list
        runCommand("find Mark");
        assertListSize(0);
        assertListSuccess(td.getTypicalPersons());

        //list after showing non-empty sub list
        runCommand("find Meier");
        assertListSize(2);
        assertListSuccess(td.getTypicalPersons());

        //list after deleting person
        runCommand("delete 1");
        TestPerson[] expectedList = TestUtil.removePersonsFromList(td.getTypicalPersons(), td.alice);
        assertListSuccess(expectedList);

        //list after adding person
        runCommand(td.alice.getAddCommand());
        assertListSuccess(TestUtil.addPersonsToList(expectedList, td.alice));

        //list when address book is empty
        runCommand("clear");
        assertListSuccess();

    }


    private void assertListSuccess(TestPerson... expectedList) {
        runCommand("list");
        assertTrue(personListPanel.isListMatching(expectedList));
        assertResultMessage("Listed all persons");
    }

}
