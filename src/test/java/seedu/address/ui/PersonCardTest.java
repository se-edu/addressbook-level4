package seedu.address.ui;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;

import guitests.GuiRobot;
import guitests.guihandles.PersonCardHandle;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TestUtil;
import seedu.address.ui.testutil.UiPartRule;

public class PersonCardTest {

    @Rule
    public final UiPartRule uiPartRule = new UiPartRule();

    @Test
    public void display() throws Exception {
        GuiRobot guiRobot = new GuiRobot();

        // no tags
        Person johnDoe = new PersonBuilder().withName("John Doe").withPhone("95458425")
                .withEmail("johndoe@email.com").withAddress("4th Street").build();
        assertCardDisplay(1, johnDoe);
        guiRobot.pauseForHuman();

        // with tags
        Person janeDoe = new PersonBuilder().withName("Jane Doe").withPhone("91043245")
                .withEmail("janedoe@email.com").withAddress("6th Street").withTags("friends").build();
        assertCardDisplay(2, janeDoe);
        guiRobot.pauseForHuman();
    }

    /**
     * Asserts that the card displays the details correctly, given a valid person.
     *
     * @param validId of the person in the card list
     * @param validPerson contact details
     */
    private void assertCardDisplay(int validId, ReadOnlyPerson validPerson) throws Exception {

        PersonCard personCard = new PersonCard(validPerson, validId);

        uiPartRule.setUiPart(personCard);
        PersonCardHandle personCardHandle = new PersonCardHandle(personCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(validId) + ". ", personCardHandle.getId());

        // verify person details are displayed correctly
        assertEquals(validPerson.getName().toString(), personCardHandle.getFullName());
        assertEquals(validPerson.getPhone().toString(), personCardHandle.getPhone());
        assertEquals(validPerson.getAddress().toString(), personCardHandle.getAddress());
        assertEquals(validPerson.getEmail().toString(), personCardHandle.getEmail());

        // verify tags are displayed correctly
        assertEquals(TestUtil.getTagsAsStringsList(validPerson.getTags()), personCardHandle.getTags());

    }
}
