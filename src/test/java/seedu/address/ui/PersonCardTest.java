package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.GuiRobot;
import guitests.guihandles.PersonCardHandle;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.PersonBuilder;

public class PersonCardTest extends GuiUnitTest {

    @Test
    public void display() throws Exception {
        // no tags
        Person personWithNoTags = new PersonBuilder().withTags(new String[0]).build();
        assertCardDisplay(1, personWithNoTags);

        // with tags
        Person personWithTags = new PersonBuilder().build();
        assertCardDisplay(2, personWithTags);
    }

    /**
     * Asserts that the card in {@code validId} in the card list displays the contact
     * details of {@code validPerson}.
     */
    private void assertCardDisplay(int validId, ReadOnlyPerson validPerson) throws Exception {
        PersonCard personCard = new PersonCard(validPerson, validId);
        GuiRobot guiRobot = new GuiRobot();

        uiPartRule.setUiPart(personCard);
        guiRobot.pauseForHuman();

        PersonCardHandle personCardHandle = new PersonCardHandle(personCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(validId) + ". ", personCardHandle.getId());

        // verify person details are displayed correctly
        assertTrue(personCardHandle.isSamePerson(validPerson));
    }
}
