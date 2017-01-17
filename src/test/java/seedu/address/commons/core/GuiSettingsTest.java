package seedu.address.commons.core;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class ConfigTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toString_defaultObject_stringReturned() {
        String defaultGuiSettingsAsString = "Width : 600\n" +
                "Height : 740\n" +
                "Position : null";

        assertEquals(defaultGuiSettingsAsString, new GuiSettings().toString());
    }

    @Test
    public void equalsMethod() {
        GuiSettings defaultGuiSettings = new GuiSettings();
        assertNotNull(defaultGuiSettings);
        assertTrue(defaultGuiSettings.equals(defaultGuiSettings));
    }


}
