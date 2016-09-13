package guitests;

import guitests.guihandles.PersonCardHandle;
import org.junit.Test;
import seedu.address.commands.AddPersonCommand;
import seedu.address.exceptions.IllegalValueException;

import static org.junit.Assert.assertEquals;

public class PersonAddCommandTest extends GuiTestBase {

    @Test
    public void addPerson_singlePerson_successful() throws IllegalValueException {
        personListPanel.enterCommandAndApply(td.hoon.getCommandString());
        PersonCardHandle johnCard = personListPanel.navigateToPerson(td.hoon.getName().fullName);
        assertMatching(td.hoon, johnCard);
    }

    @Test
    public void addPerson_duplicatePerson_showFeedback() throws IllegalValueException {
        personListPanel.enterCommandAndApply(td.hoon.getCommandString());
        personListPanel.enterCommandAndApply(td.hoon.getCommandString());
        assertEquals(AddPersonCommand.MESSAGE_DUPLICATE_PERSON, headerStatusBar.getText());
    }

    @Test
    public void addPerson_multiplePerson_success() throws IllegalValueException {
        personListPanel.enterCommandAndApply(td.hoon.getCommandString());
        personListPanel.enterCommandAndApply(td.ida.getCommandString());

        final PersonCardHandle aliceCard = personListPanel.navigateToPerson(td.hoon);
        assertMatching(td.hoon, aliceCard);

        final PersonCardHandle bensonCard = personListPanel.navigateToPerson(td.ida);
        assertMatching(td.ida, bensonCard);
    }

    @Test
    public void addPerson_invalidCommand_fail() {
        personListPanel.enterCommandAndApply("adds Johnny");

        assertEquals("Invalid command", headerStatusBar.getText());
    }
}
