package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import guitests.guihandles.PersonCardHandle;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.testutil.TestPerson;
import seedu.address.testutil.TypicalTestPersons;

public class PersonCardTest extends GuiUnitTest {

    @Test
    public void personCard_validPersonWithNoTags_labelsShowCorrectly() {
        assertCardDisplay(0, new TypicalTestPersons().george);
    }

    @Test
    public void personCard_validPersonWithTags_labelsShowCorrectly() {
        assertCardDisplay(1, new TypicalTestPersons().alice);
    }

    /**
     * Asserts that the card displays the details correctly, given a valid person.
     */
    private void assertCardDisplay(int validId, TestPerson validPerson) {
        PersonCard personCard = new PersonCard(validPerson, validId);
        getTestApp().addUiPart(personCard);
        PersonCardHandle personCardHandle = new PersonCardHandle(getGuiRobot(), getTestApp().getStage(),
                                                                    personCard.getRoot());

        assertEquals(Integer.toString(validId) + ". ", personCardHandle.getId());
        assertEquals(validPerson.getName().toString(), personCardHandle.getFullName());
        assertEquals(validPerson.getPhone().toString(), personCardHandle.getPhone());
        assertEquals(validPerson.getAddress().toString(), personCardHandle.getAddress());
        assertEquals(validPerson.getEmail().toString(), personCardHandle.getEmail());
        assertTagsContent(validPerson.getTags(), personCardHandle.getTags());
    }

    /**
     * Asserts that the card creates all the tag labels correctly, given a list of valid tags.
     */
    private void assertTagsContent(UniqueTagList expectedTags, List<String> actualTags) {
        Set<String> expectedTagsSet = new HashSet<String>();
        Set<String> actualTagsSet = new HashSet<String>();

        expectedTags.forEach((tag) -> expectedTagsSet.add(tag.tagName));
        actualTags.forEach((tag) -> actualTagsSet.add(tag));
        assertTrue(expectedTagsSet.equals(actualTagsSet));
    }
}
