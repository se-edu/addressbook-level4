package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.Person;

public class FindCommandTest extends AddressBookGuiTest {

    @Test
    public void find_nonEmptyList() throws Exception {
        assertFindResult(FindCommand.COMMAND_WORD + " Mark"); // no results
        assertFindResult(FindCommand.COMMAND_WORD + " Meier", td.benson, td.daniel); // multiple results

        //find after deleting one result
        getCommandBox().runCommand(DeleteCommand.COMMAND_WORD + " 1");
        assertFindResult(FindCommand.COMMAND_WORD + " Meier", td.daniel);
    }

    @Test
    public void find_emptyList() {
        getCommandBox().runCommand(ClearCommand.COMMAND_WORD);
        assertFindResult(FindCommand.COMMAND_WORD + " Jean"); // no results
    }

    @Test
    public void find_invalidCommand_fail() {
        getCommandBox().runCommand(FindCommand.COMMAND_WORD + "george");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertFindResult(String command, Person... expectedHits) {
        getCommandBox().runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " persons listed!");
        assertTrue(getPersonListPanel().isListMatching(expectedHits));
    }
}
