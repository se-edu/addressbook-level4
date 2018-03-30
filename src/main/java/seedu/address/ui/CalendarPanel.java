package seedu.address.ui;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.model.Interval;
import com.calendarfx.view.CalendarView;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;

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
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu HH:mm")
            .withResolverStyle(ResolverStyle.STRICT);
    @FXML
    private static CalendarView calendarView = new CalendarView();


    public CalendarPanel() {
        super(FXML);
        calendarView.setRequestedTime(LocalTime.now());
        calendarView.setToday(LocalDate.now());
        calendarView.setTime(LocalTime.now());
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
            assert(false);
        }
    }

    /**
     * Test add task 1
     */
    public static void addTask1() {
        LocalDateTime startDateTime;
        startDateTime = LocalDateTime.parse("30/03/2018 22:30", formatter);
        LocalDateTime endDateTime = startDateTime.plusHours(1);
        Interval interval = new Interval(startDateTime, endDateTime);
        Entry entry = new Entry("Tuition1");
        entry.setInterval(interval);
        calendar.addEntry(entry);
    }

    /**
     * Test add task 2
     */
    public static void addTask2() {
        LocalDateTime startDateTime;
        startDateTime = LocalDateTime.parse("30/03/2018 22:30", formatter);
        LocalDateTime endDateTime = startDateTime.plusHours(1);
        Interval interval = new Interval(startDateTime, endDateTime);
        Entry entry = new Entry("Tuition2");
        entry.setInterval(interval);
        calendar.addEntry(entry);
    }

    /**
     * Deletes a task from the calendar's schedule
     *
     * @param task to be deleted
     */
    public static void deleteTask(Entry task) {
        calendar.removeEntry(task);
    }

    @Override
    public CalendarView getRoot() {
        return this.calendarView;
    }
}
