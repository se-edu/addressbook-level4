package guitests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalPersons.INDEX_FIRST_PERSON;

import org.junit.Test;

import guitests.guihandles.PersonCardHandle;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.SelectCommand;

public class SelectCommandTest extends AddressBookGuiTest {


    @Test
    public void selectPerson_nonEmptyList() throws Exception {

        assertSelectionInvalid(Index.fromOneBased(10)); // invalid index
        assertNoCardSelected();

        assertSelectionSuccess(INDEX_FIRST_PERSON); // first person in the list
        Index personCount = Index.fromOneBased(td.getTypicalPersons().length);
        assertSelectionSuccess(personCount); // last person in the list
        Index middleIndex = Index.fromOneBased(personCount.getOneBased() / 2);
        assertSelectionSuccess(middleIndex); // a person in the middle of the list

        assertSelectionInvalid(Index.fromOneBased(personCount.getOneBased() + 1)); // invalid index
        assertCardSelected(middleIndex); // assert previous selection remains

        /* Testing other invalid indexes such as -1 should be done when testing the SelectCommand */
    }

    @Test
    public void selectPerson_emptyList() {
        runCommand(ClearCommand.COMMAND_WORD);
        assertListSize(0);
        assertSelectionInvalid(INDEX_FIRST_PERSON); //invalid index
    }

    private void assertSelectionInvalid(Index index) {
        runCommand(SelectCommand.COMMAND_WORD + " " + index.getOneBased());
        assertResultMessage("The person index provided is invalid");
    }

    private void assertSelectionSuccess(Index index) throws Exception {
        runCommand(SelectCommand.COMMAND_WORD + " " + index.getOneBased());
        assertResultMessage("Selected Person: " + index.getOneBased());
        assertCardSelected(index);
    }

    private void assertCardSelected(Index index) throws Exception {
        PersonCardHandle selectedCard = getPersonListPanel().getSelectedCardAsHandle().get();
        PersonCardHandle expectedCard = getPersonListPanel().getPersonCardHandle(index.getZeroBased());
        assertEquals(expectedCard.getId(), selectedCard.getId());
        assertEquals(expectedCard.getAddress(), selectedCard.getAddress());
        assertEquals(expectedCard.getEmail(), selectedCard.getEmail());
        assertEquals(expectedCard.getName(), selectedCard.getName());
        assertEquals(expectedCard.getPhone(), selectedCard.getPhone());
        assertEquals(expectedCard.getTags(), selectedCard.getTags());
        //TODO: confirm the correct page is loaded in the Browser Panel
    }

    private void assertNoCardSelected() {
        assertFalse(getPersonListPanel().getSelectedCardAsHandle().isPresent());
    }

}
