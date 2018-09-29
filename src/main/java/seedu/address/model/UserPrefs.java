package seedu.address.model;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import seedu.address.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs {

    private GuiSettings guiSettings;
    //TODO: REMOVE
    private Path addressBookFilePath = Paths.get("data" , "addressbook.xml");
    private Path transcriptFilePath = Paths.get("data" , "transcript.xml");

    public UserPrefs() {
        setGuiSettings(500, 500, 0, 0);
    }

    public GuiSettings getGuiSettings() {
        return guiSettings == null ? new GuiSettings() : guiSettings;
    }

    public void updateLastUsedGuiSetting(GuiSettings guiSettings) {
        this.guiSettings = guiSettings;
    }

    public void setGuiSettings(double width, double height, int x, int y) {
        guiSettings = new GuiSettings(width, height, x, y);
    }

    public Path getTranscriptFilePath() {
        return transcriptFilePath;
    }

    public void setTranscriptFilePath(Path transcriptFilePath) {
        this.transcriptFilePath = transcriptFilePath;
    }

    // TODO: REMOVE
    public Path getAddressBookFilePath() {
        return addressBookFilePath;
    }

    // TODO: REMOVE
    public void setAddressBookFilePath(Path addressBookFilePath) {
        this.addressBookFilePath = addressBookFilePath;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UserPrefs)) { //this handles null as well.
            return false;
        }

        UserPrefs o = (UserPrefs) other;

        return Objects.equals(guiSettings, o.guiSettings)
                && Objects.equals(addressBookFilePath, o.addressBookFilePath) //TODO: REMOVE
                && Objects.equals(transcriptFilePath, o.transcriptFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, transcriptFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings.toString());
        sb.append("\nLocal data file location : " + transcriptFilePath);
        return sb.toString();
    }
}
