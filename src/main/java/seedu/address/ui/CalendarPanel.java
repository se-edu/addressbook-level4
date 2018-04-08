package seedu.address.ui;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
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
            assert(false); // Should never enter here
        }
    }

    /**
     * Updates the calendar with the updated list of tasks
     *
     * @param filteredTasks updated list of tasks
     */
    public static void updateCalendar(List<Task> filteredTasks) {
        Calendar updatedCalendar = new Calendar("task");
        for (Task task : filteredTasks) {
            updatedCalendar.addEntry(task.getEntry());
        }
        source.getCalendars().clear();
        source.getCalendars().add(updatedCalendar);
    }

    @Override
    public CalendarView getRoot() {
        return this.calendarView;
    }

}
