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
        
        String guiSettingsAsString = "Width : 600\n" +
                "Height : 740\n" +
                "Position : (0,0)";
        
        assertEquals(guiSettingsAsString, new GuiSettings(600, 740, 0, 0).toString());
    }

    @Test
    public void equalsMethod() {
        GuiSettings defaultGuiSettings = new GuiSettings();
        assertNotNull(defaultGuiSettings);
        assertTrue(defaultGuiSettings.equals(defaultGuiSettings));
    }


}
