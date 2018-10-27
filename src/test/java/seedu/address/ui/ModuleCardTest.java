package seedu.address.ui;

import guitests.guihandles.ModuleCardHandle;
import org.junit.Test;
import seedu.address.model.module.Module;
import seedu.address.model.util.ModuleBuilder;

import static org.junit.Assert.*;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysModule;

public class ModuleCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Module moduleWithNoTags = new ModuleBuilder().withCode(new String("CS2103")).build();
        ModuleCard moduleCard = new ModuleCard(moduleWithNoTags);
        uiPartRule.setUiPart(moduleCard);
        assertCardDisplay(moduleCard, moduleWithNoTags, 1);

        // with tags
        Module moduleWithTags = new ModuleBuilder().build();
        moduleCard = new ModuleCard(moduleWithTags);
        uiPartRule.setUiPart(moduleCard);
        assertCardDisplay(moduleCard, moduleWithTags, 2);
    }

    @Test
    public void equals() {
        Module module = new ModuleBuilder().build();
        ModuleCard moduleCard = new ModuleCard(module);

        // same module, same index -> returns true
        ModuleCard copy = new ModuleCard(module);
        assertTrue(moduleCard.equals(copy));

        // same object -> returns true
        assertTrue(moduleCard.equals(moduleCard));

        // null -> returns false
        assertFalse(moduleCard.equals(null));

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
