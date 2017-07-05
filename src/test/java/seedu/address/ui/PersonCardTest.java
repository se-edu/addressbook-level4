package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
        assertCardDisplay(1, personWithNoTags);
        guiRobot.pauseForHuman();

        // with tags
        Person personWithTags = new PersonBuilder().build();
        assertCardDisplay(2, personWithTags);
        guiRobot.pauseForHuman();
    }

    @Test
    public void equals() throws Exception {
        Person person = new PersonBuilder().build();
        PersonCard personCard = new PersonCard(person, 0);

        // same person, same index -> returns true
        PersonCard copy = new PersonCard(person, 0);
        assertTrue(personCard.equals(copy));

        // same object -> returns true
        assertTrue(personCard.equals(personCard));

        // null -> returns false
        assertFalse(personCard.equals(null));

        // different types -> returns false
        assertFalse(personCard.equals(0));

        // different person, same index -> returns false
        Person differentPerson = new PersonBuilder().withName("differentName").build();
        assertFalse(personCard.equals(new PersonCard(differentPerson, 0)));

        // same person, different index -> returns false
        assertFalse(personCard.equals(new PersonCard(person, 1)));
    }

    /**
     * Asserts that the card in {@code validId} in the card list displays the contact
     * details of {@code validPerson}.
     */
    private void assertCardDisplay(int validId, ReadOnlyPerson validPerson) throws Exception {

        PersonCard personCard = new PersonCard(validPerson, validId);

        uiPartRule.setUiPart(personCard);
        PersonCardHandle personCardHandle = new PersonCardHandle(personCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(validId) + ". ", personCardHandle.getId());

        // verify person details are displayed correctly
        assertTrue(personCardHandle.isSamePerson(validPerson));
    }
}
