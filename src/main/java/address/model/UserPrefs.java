package address.model;

import address.util.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs {

    public GuiSettings guiSettings;

    public GuiSettings getGuiSettings() {
        return guiSettings == null ? new GuiSettings() : guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        this.guiSettings = guiSettings;
    }
}
