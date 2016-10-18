package guitests;

import org.junit.Test;

import seedu.address.logic.commands.MarkCommand;
import seedu.address.testutil.TestTask;

public class MarkCommandTest extends AddressBookGuiTest {

    @Test
    public void mark() {

        //mark the first in the list
        TestTask[] currentList = td.getTypicalTask();
        commandBox.runCommand("clear");
        commandBox.runCommand(td.hoon.getAddCommand());
        int targetIndex = 1;
        commandBox.runCommand("mark " + targetIndex);
        assertResultMessage(String.format(MarkCommand.MESSAGE_MARK_TASK_SUCCESS, td.hoon.getName(),
                td.hoon.getCompleted() == false ? "Completed" : "Incomplete"));
    }
}
