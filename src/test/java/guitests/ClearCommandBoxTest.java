package guitests;

import guitests.guihandles.PersonCardHandle;
import org.junit.Test;
import seedu.address.exceptions.IllegalValueException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ClearCommandBoxTest extends AddressBookGuiTest {

    @Test
    public void clearCommandBox_successful() throws IllegalValueException {
        personListPanel.enterCommand(td.benson.getCommandString());
        assertNotEquals(personListPanel.getCommandInput(), "");
        mainGui.pressEnter();
        PersonCardHandle johnCard = personListPanel.navigateToPerson(td.benson.getName().fullName);
        assertMatching(td.benson, johnCard);
        assertEquals(personListPanel.getCommandInput(), "");
    }

    @Test
    public void clearCommandBox_fail() throws IllegalValueException {
        personListPanel.enterCommand("invalid command");
        assertEquals(personListPanel.getCommandInput(), "invalid command");
        mainGui.pressEnter();
        assertEquals(personListPanel.getCommandInput(), "invalid command");
    }

}
