package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import seedu.address.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private GuiSettings guiSettings = new GuiSettings();
    private Path contactListFilePath = Paths.get("data" , "contactlist.json");
    private Path taskListFilePath = Paths.get("data", "tasklist.json");
    private Path tickedTaskListFilePath = Paths.get("data", "tickedtasklist.json");
    private Path expenditureListFilePath = Paths.get("data" , "expenditurelist.json");
    private Path workoutBookFilePath = Paths.get("data" , "workoutbook.json");

    /**
     * Creates a {@code UserPrefs} with default values.
     */
    public UserPrefs() {}

    /**
     * Creates a {@code UserPrefs} with the prefs in {@code userPrefs}.
     */
    public UserPrefs(ReadOnlyUserPrefs userPrefs) {
        this();
        resetData(userPrefs);
    }

    /**
     * Resets the existing data of this {@code UserPrefs} with {@code newUserPrefs}.
     */
    public void resetData(ReadOnlyUserPrefs newUserPrefs) {
        requireNonNull(newUserPrefs);
        setGuiSettings(newUserPrefs.getGuiSettings());
        setContactListFilePath(newUserPrefs.getContactListFilePath());
        setTaskListFilePath(newUserPrefs.getTaskListFilePath());
        setTickedTaskListFilePath(newUserPrefs.getTickedTaskListFilePath());
        setExpenditureListFilePath(newUserPrefs.getExpenditureListFilePath());
        setWorkoutBookFilePath(newUserPrefs.getWorkoutBookFilePath());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public Path getContactListFilePath() {
        return contactListFilePath;
    }

    public void setContactListFilePath(Path contactListFilePath) {
        requireNonNull(contactListFilePath);
        this.contactListFilePath = contactListFilePath;
    }

    public Path getTaskListFilePath() {
        return taskListFilePath;
    }

    public void setTaskListFilePath(Path taskListFilePath) {
        requireNonNull(taskListFilePath);
        this.taskListFilePath = taskListFilePath;
    }
    public Path getTickedTaskListFilePath() {
        return tickedTaskListFilePath;
    }

    public void setTickedTaskListFilePath(Path tickedTaskListFilePath) {
        requireNonNull(tickedTaskListFilePath);
        this.tickedTaskListFilePath = tickedTaskListFilePath;
    }

    public Path getExpenditureListFilePath() {
        return expenditureListFilePath;
    }

    public void setExpenditureListFilePath(Path expenditureListFilePath) {
        requireNonNull(expenditureListFilePath);
        this.expenditureListFilePath = expenditureListFilePath;
    }

    public Path getWorkoutBookFilePath() {
        return workoutBookFilePath;
    }

    public void setWorkoutBookFilePath (Path workoutBookFilePath) {
        requireNonNull(workoutBookFilePath);
        this.workoutBookFilePath = workoutBookFilePath;
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

        return guiSettings.equals(o.guiSettings)
                && contactListFilePath.equals(o.contactListFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, contactListFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal data file location : " + contactListFilePath);
        return sb.toString();
    }

}
