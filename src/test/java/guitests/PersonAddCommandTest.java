package guitests;

import guitests.guihandles.PersonCardHandle;
import org.junit.Test;
import seedu.address.commands.AddPersonCommand;
import seedu.address.exceptions.IllegalValueException;

import static org.junit.Assert.assertEquals;

public class PersonAddCommandTest extends GuiTestBase {

    @Test
    public void testAddPerson_singlePerson_successful() throws IllegalValueException {
        personListPanel.enterCommandAndApply(td.benson.getCommandString());
        PersonCardHandle johnCard = personListPanel.navigateToPerson(td.benson.getName().fullName);
        assertMatching(td.benson, johnCard);
    }

    @Test
    public void testAddPerson_duplicatePerson_showFeedback() throws IllegalValueException {
        personListPanel.enterCommandAndApply(td.benson.getCommandString());
        personListPanel.enterCommandAndApply(td.benson.getCommandString());
        assertEquals(AddPersonCommand.MESSAGE_DUPLICATE_PERSON, headerStatusBar.getText());
    }

    @Test
    public void testAddPerson_multiplePerson_success() throws IllegalValueException {
        personListPanel.enterCommandAndApply(td.alice.getCommandString());
        personListPanel.enterCommandAndApply(td.benson.getCommandString());

        final PersonCardHandle aliceCard = personListPanel.navigateToPerson(td.alice);
        assertMatching(td.alice, aliceCard);

        final PersonCardHandle bensonCard = personListPanel.navigateToPerson(td.benson);
        assertMatching(td.benson, bensonCard);
    }

    @Test
    public void testAddPerson_invalidCommand_fail() {
        personListPanel.enterCommandAndApply("adds Johnny");

        assertEquals("Invalid command", headerStatusBar.getText());
    }
}
