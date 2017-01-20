package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.testfx.framework.junit.ApplicationTest;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.testutil.TestPerson;
import seedu.address.testutil.TypicalTestPersons;

public class PersonCardTest extends ApplicationTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void personCard_nullPerson_throwsNullException() {
        thrown.expect(NullPointerException.class);
        new PersonCard(null, 0);
    }

    @Test
    public void personCard_validPerson_labelsShowCorrectly() {
        assertCardDisplay(0, new TypicalTestPersons().george);
        assertCardDisplay(1, new TypicalTestPersons().alice);
    }

    @Override
    public void start(Stage stage) throws Exception {
        // needed, otherwise we get an error saying toolkit is not initialized
    }

    /**
     * Asserts that the card displays the details correctly, given a valid person.
     */
    private void assertCardDisplay(int validId, TestPerson validPerson) {
        TestPersonCard personCard = new TestPersonCard(validPerson, validId);

        assertLabelContent(Integer.toString(validId) + ". ", personCard.getIdLabel());
        assertLabelContent(validPerson.getName().toString(), personCard.getNameLabel());
        assertLabelContent(validPerson.getPhone().toString(), personCard.getPhoneLabel());
        assertLabelContent(validPerson.getAddress().toString(), personCard.getAddressLabel());
        assertLabelContent(validPerson.getEmail().toString(), personCard.getEmailLabel());
        assertTagsContent(validPerson.getTags(), personCard.getTagPane().getChildren());
    }

    /**
     * Asserts that the card creates all the tag labels correctly, given a list of valid tags.
     */
    private void assertTagsContent(UniqueTagList expectedTags, List<Node> actualTagLabels) {
        Set<String> expectedTagsSet = new HashSet<String>();
        Set<String> actualTagsSet = new HashSet<String>();

        expectedTags.forEach((tag) -> expectedTagsSet.add(tag.tagName));
        actualTagLabels.forEach((node) -> actualTagsSet.add(((Label) node).getText()));

        assertTrue(expectedTagsSet.equals(actualTagsSet));
    }

    /**
     * Asserts that the label is displaying the correct content.
     */
    private void assertLabelContent(String expectedContent, Label actualLabel) {
        assertEquals(expectedContent, actualLabel.getText());
    }
}
