package seedu.address.ui;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationRule;

import guitests.GuiRobot;
import guitests.guihandles.PersonCardHandle;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TestPerson;
import seedu.address.ui.testutil.GuiUnitTestApp;

public class PersonCardTest {

    private GuiUnitTestApp testApp;

    @Rule
    public ApplicationRule applicationRule = new ApplicationRule((stage) -> testApp = GuiUnitTestApp.spawnApp(stage));

    @Test
    public void display() throws Exception {
        testApp.setStageWidth(200);
        testApp.setStageHeight(120);

        // no tags
        TestPerson johnDoe = new PersonBuilder().withName("John Doe").withPhone("95458425")
                .withEmail("johndoe@email.com").withAddress("4th Street").build();
        assertCardDisplay(1, johnDoe);
        testApp.clearUiParts();

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
        testApp.addUiPart(personCard);
        PersonCardHandle personCardHandle = new PersonCardHandle(new GuiRobot(), testApp.getStage(),
                personCard.getRoot());

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
}
