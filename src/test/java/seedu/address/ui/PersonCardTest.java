package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.PersonCardHandle;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.PersonBuilder;

public class PersonCardTest extends GuiUnitTest {

    @Test
    public void display() throws Exception {
        // no tags
        Person personWithNoTags = new PersonBuilder().withTags(new String[0]).build();
        assertCardDisplay(personWithNoTags, 1);

        // with tags
        Person personWithTags = new PersonBuilder().build();
        assertCardDisplay(personWithTags, 2);
    }

    /**
     * Asserts that the card in {@code validId} in the card list displays the contact
     * details of {@code validPerson}.
     */
    private void assertCardDisplay(ReadOnlyPerson validPerson, int validId) throws Exception {

        PersonCard personCard = new PersonCard(validPerson, validId);

        uiPartRule.setUiPart(personCard);
        guiRobot.pauseForHuman();

        PersonCardHandle personCardHandle = new PersonCardHandle(personCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(validId) + ". ", personCardHandle.getId());

        // verify person details are displayed correctly
        assertTrue(personCardHandle.isSamePerson(validPerson));
    }
}
