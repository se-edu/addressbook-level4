package seedu.address.ui;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.view.CalendarView;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import seedu.address.model.Task;

//@@author ChoChihTun
/**
 * The Calendar Panel of the App.
 */
public class CalendarPanel extends UiPart<Region> {

    private static final String FXML = "CalendarPanel.fxml";
    private static final char DAY = 'd';
    private static final char WEEK = 'w';
    private static final char MONTH = 'm';
    private static final char YEAR = 'y';
    private static CalendarSource source = new CalendarSource("Schedule");
    private static Calendar calendar = new Calendar("Task");

    @FXML
    private static CalendarView calendarView = new CalendarView();


    public CalendarPanel() {
        super(FXML);
        calendarView.setRequestedTime(LocalTime.now());
        calendarView.setToday(LocalDate.now());
        calendarView.setTime(LocalTime.now());
        calendarView.setScaleX(0.95);
        calendarView.setScaleY(1.15);
        calendarView.setTranslateY(-40);
        calendarView.showDayPage();
        disableViews();
        setupCalendar();
    }

    /**
     * Initialises the calendar
     */
    private void setupCalendar() {
        source.getCalendars().add(calendar);
        calendarView.getCalendarSources().add(source);
    }

    /**
     * Removes unnecessary buttons from interface
     */
    private void disableViews() {
        calendarView.setShowAddCalendarButton(false);
        calendarView.setShowPrintButton(false);
        calendarView.setShowPageToolBarControls(false);
        calendarView.setShowSearchField(false);
    }

    /**
     * Changes the view page of the calendar
     * @param timeUnit the view page time unit to be changed into
     */
    public static void changeViewPage(char timeUnit) {
        switch(timeUnit) {
        case DAY:
            calendarView.showDayPage();
            return;
        case WEEK:
            calendarView.showWeekPage();
            return;
        case MONTH:
            calendarView.showMonthPage();
            return;
        case YEAR:
            calendarView.showYearPage();
            return;
        default:
            // Should never enter here
            assert (false);
        }
    }

    /**
     * Updates the calendar with the updated list of tasks
     *
     * @param filteredTasks updated list of tasks
     */
    public static void updateCalendar(List<Task> filteredTasks) {
        if (isFilteredTaskValid(filteredTasks)) {
            Calendar updatedCalendar = new Calendar("task");
            for (Task task : filteredTasks) {
                updatedCalendar.addEntry(task.getEntry());
            }
            source.getCalendars().clear();
            source.getCalendars().add(updatedCalendar);
        } else {
            // Latest task list provided should not have any task that clashes
            assert (false);
        }
    }

    /**
     * Checks if the given latest task list is valid
     *
     * @param taskList to be checked
     * @return true if there is no clash between tasks so task list is valid
     *         false if there is clash between tasks so task list is invalid
     */
    private static boolean isFilteredTaskValid(List<Task> taskList) {
        for (int i = 0; i < taskList.size(); i++) {
            Entry<?> taskEntryToBeChecked = taskList.get(i).getEntry();
            for (int j = i + 1; j < taskList.size(); j++) {
                Entry taskEntryToCheckAgainst = taskList.get(j).getEntry();
                if (taskEntryToBeChecked.intersects(taskEntryToCheckAgainst)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public CalendarView getRoot() {
        return calendarView;
    }

}
