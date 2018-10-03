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

    //-----------------------------------------------
    private Path addressBookFilePath = Paths.get("data" , "addressbook.xml");
    private Path expensesListFilePath = Paths.get("data" , "expenses.xml");
    private Path scheduleListFilePath = Paths.get("data" , "schedulelist.xml");

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

    //-----------------------------------------------
    public Path getAddressBookFilePath() {
        return addressBookFilePath;
    }
    public Path getExpensesListFilePath() {
        return expensesListFilePath; }
    public Path getScheduleListFilePath() {
        return scheduleListFilePath;
    }

    public void setAddressBookFilePath(Path addressBookFilePath) {
        this.addressBookFilePath = addressBookFilePath;
    }
    public void setExpensesListFilePath(Path expensesListFilePath) {
        this.expensesListFilePath = expensesListFilePath; }
    public void setScheduleListFilePath(Path scheduleListFilePath) {
        this.scheduleListFilePath = scheduleListFilePath;
    }

    //-----------------------------------------------
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
                && Objects.equals(addressBookFilePath, o.addressBookFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, addressBookFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings.toString());
        sb.append("\nLocal data file location : " + addressBookFilePath);
        return sb.toString();
    }

}
