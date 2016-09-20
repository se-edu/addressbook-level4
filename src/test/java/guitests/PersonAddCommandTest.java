package guitests;

import guitests.guihandles.PersonCardHandle;
import org.junit.Test;
import seedu.address.commands.AddPersonCommand;
import seedu.address.exceptions.IllegalValueException;

import static org.junit.Assert.assertEquals;

public class PersonAddCommandTest extends AddressBookGuiTest {

    @Test
    public void addPerson_singlePerson_successful() throws IllegalValueException {
        runCommand(td.hoon.getCommandString());
        PersonCardHandle johnCard = personListPanel.navigateToPerson(td.hoon.getName().fullName);
        assertMatching(td.hoon, johnCard);
    }

    @Test
    public void addPerson_duplicatePerson_showFeedback() throws IllegalValueException {
        runCommand(td.hoon.getCommandString());
        runCommand(td.hoon.getCommandString());
        assertEquals(AddPersonCommand.MESSAGE_DUPLICATE_PERSON, resultDisplay.getText());
    }

    @Test
    public void addPerson_multiplePerson_success() throws IllegalValueException {
        runCommand(td.hoon.getCommandString());
        runCommand(td.ida.getCommandString());

        final PersonCardHandle aliceCard = personListPanel.navigateToPerson(td.hoon);
        assertMatching(td.hoon, aliceCard);

        final PersonCardHandle bensonCard = personListPanel.navigateToPerson(td.ida);
        assertMatching(td.ida, bensonCard);
    }

    @Test
    public void addPerson_invalidCommand_fail() {
        runCommand("adds Johnny");

        assertEquals("Invalid command", resultDisplay.getText());
    }
}
