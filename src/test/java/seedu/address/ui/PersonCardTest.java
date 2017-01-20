package seedu.address.ui;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import guitests.GuiRobot;
import guitests.guihandles.GuiHandle;
import guitests.guihandles.PersonCardHandle;
import javafx.scene.layout.Region;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TestPerson;
import seedu.address.ui.testutil.GuiUnitTest;

public class PersonCardTest extends GuiUnitTest {

    @Test
    public void display() throws Exception {
        testApp.setStageWidth(200);
        testApp.setStageHeight(120);

        // no tags
        TestPerson johnDoe = new PersonBuilder().withName("John Doe").withPhone("95458425")
                .withEmail("johndoe@email.com").withAddress("4th Street").build();
        assertCardDisplay(1, johnDoe);
        clearUiParts();

        // with tags
        TestPerson janeDoe = new PersonBuilder().withName("Jane Doe").withPhone("91043245")
                .withEmail("janedoe@email.com").withAddress("6th Street").withTags("friends").build();
        assertCardDisplay(2, janeDoe);
    }

    /**
     * Asserts that the card displays the details correctly, given a valid person.
     *
     * @param validId of the person in the card list
     * @param validPerson contact details
     */
    private void assertCardDisplay(int validId, TestPerson validPerson) throws Exception {
        PersonCard personCard = new PersonCard(validPerson, validId);
        PersonCardHandle personCardHandle = (PersonCardHandle) addUiPart(personCard);

        // verify id is displayed correctly
        assertEquals(Integer.toString(validId) + ". ", personCardHandle.getId());

        // verify person details are displayed correctly
        assertEquals(validPerson.getName().toString(), personCardHandle.getFullName());
        assertEquals(validPerson.getPhone().toString(), personCardHandle.getPhone());
        assertEquals(validPerson.getAddress().toString(), personCardHandle.getAddress());
        assertEquals(validPerson.getEmail().toString(), personCardHandle.getEmail());

        // verify tags are displayed correctly
        assertEquals(validPerson.getTagsAsStringsList(), personCardHandle.getTags());
    }

    @Override
    protected GuiHandle getGuiHandle(UiPart<Region> part) {
        return new PersonCardHandle(new GuiRobot(), testApp.getStage(), part.getRoot());
    }
}
