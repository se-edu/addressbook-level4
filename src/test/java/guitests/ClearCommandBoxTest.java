package guitests;

import guitests.guihandles.PersonCardHandle;
import org.junit.Test;
import seedu.address.exceptions.IllegalValueException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ClearCommandBoxTest extends AddressBookGuiTest {

    @Test
    public void clearCommandBox_successful() throws IllegalValueException {
        commandBox.enterCommand(td.benson.getAddCommand());
        assertNotEquals(commandBox.getCommandInput(), "");
        mainGui.pressEnter();
        PersonCardHandle johnCard = personListPanel.navigateToPerson(td.benson.getName().fullName);
        assertMatching(td.benson, johnCard);
        assertEquals(commandBox.getCommandInput(), "");
    }

    @Test
    public void clearCommandBox_fail() throws IllegalValueException {
        commandBox.runCommand("invalid command");
        assertEquals(commandBox.getCommandInput(), "invalid command");
        mainGui.pressEnter();
        assertEquals(commandBox.getCommandInput(), "invalid command");
    }

}
