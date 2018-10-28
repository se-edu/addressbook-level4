package seedu.address.ui;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysModule;

//import org.junit.Test;

import guitests.guihandles.ModuleCardHandle;
import seedu.address.model.module.Module;
import seedu.address.model.util.ModuleBuilder;

public class ModuleCardTest extends GuiUnitTest {

    /**
     * TODO: REMOVE
     */
    //@Test
    public void display() {
        // no tags
        Module moduleWithNoTags = new ModuleBuilder().build();
        ModuleCard moduleCard = new ModuleCard(moduleWithNoTags);
        uiPartRule.setUiPart(moduleCard);
        assertCardDisplay(moduleCard, moduleWithNoTags, 1);

        // with tags
        Module moduleWithTags = new ModuleBuilder().build();
        moduleCard = new ModuleCard(moduleWithTags);
        uiPartRule.setUiPart(moduleCard);
        assertCardDisplay(moduleCard, moduleWithTags, 2);
    }

    /**
     * TODO: REMOVE
     */
    //@Test
    public void equals() {
        Module module = new ModuleBuilder().build();
        ModuleCard moduleCard = new ModuleCard(module);

        // same module, same index -> returns true
        ModuleCard copy = new ModuleCard(module);
        assertTrue(moduleCard.equals(copy));

        // same object -> returns true
        assertTrue(moduleCard.equals(moduleCard));

        // null -> returns false
        //assertFalse(moduleCard.equals(null));

        // different types -> returns false
        assertFalse(moduleCard.equals(0));

        // different module, same index -> returns false
        Module differentModule = new ModuleBuilder().withCode("differentName").build();
        assertFalse(moduleCard.equals(new ModuleCard(differentModule)));

        // same module, different index -> returns false
        assertFalse(moduleCard.equals(new ModuleCard(module)));
    }

    /**
     * Asserts that {@code moduleCard} displays the details of {@code expectedModule} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(ModuleCard moduleCard, Module expectedModule, int expectedId) {
        guiRobot.pauseForHuman();

        ModuleCardHandle moduleCardHandle = new ModuleCardHandle(moduleCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", moduleCardHandle.getId());

        // verify person details are displayed correctly
        assertCardDisplaysModule(expectedModule, moduleCardHandle);
    }
}
