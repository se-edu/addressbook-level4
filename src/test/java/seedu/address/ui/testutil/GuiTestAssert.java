package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;

import guitests.guihandles.PersonCardHandle;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    public static void assertCardEquals(PersonCardHandle expectedCard, PersonCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getAddress(), actualCard.getAddress());
        assertEquals(expectedCard.getEmail(), actualCard.getEmail());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getPhone(), actualCard.getPhone());
        assertEquals(expectedCard.getTags(), actualCard.getTags());
    }
}
