package seedu.address.ui;

import java.time.LocalDate;
import java.time.LocalTime;

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

    @FXML
    private static CalendarView calendarView = new CalendarView();

    public CalendarPanel() {
        super(FXML);
        calendarView.setRequestedTime(LocalTime.now());
        calendarView.setToday(LocalDate.now());
        calendarView.setTime(LocalTime.now());
        calendarView.showDayPage();
        disableViews();
    }

    /**
     * Remove unnecessary buttons from interface
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

    @Override
    public CalendarView getRoot() {
        return this.calendarView;
    }
}
