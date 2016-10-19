package guitests;

import org.junit.Test;

import seedu.address.logic.commands.DoneCommand;
import seedu.address.testutil.TestTask;

public class MarkCommandTest extends AddressBookGuiTest {

    @Test
    public void mark() {

        //mark the first in the list
        TestTask[] currentList = td.getTypicalTask();
        commandBox.runCommand("clear");
        commandBox.runCommand(td.hoon.getAddCommand());
        int targetIndex = 1;
        commandBox.runCommand("done " + targetIndex);
        assertResultMessage(String.format(DoneCommand.MESSAGE_DONE_TASK_SUCCESS, td.hoon.getName(),
                td.hoon.getCompleted() == false ? "Completed" : "Incomplete"));
    }
}
