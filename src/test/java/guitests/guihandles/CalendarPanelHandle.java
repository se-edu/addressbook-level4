package guitests.guihandles;

import com.calendarfx.view.page.PageBase;

import javafx.scene.Node;
import seedu.address.ui.CalendarPanel;

//@@author ChoChihTun
/**
 * A handler for the {@code CalendarPanel} of the UI
 */
public class CalendarPanelHandle extends NodeHandle<Node> {

    public static final String CALENDAR_PANEL_ID = "#calendarPlaceholder";

    private CalendarPanel calendarPanel;

    public CalendarPanelHandle(Node calendarPanelNode) {
        super(calendarPanelNode);
        calendarPanel = new CalendarPanel();
    }

    public PageBase getDefaultCalendarViewPage() {
        return calendarPanel.getRoot().getDayPage();
    }

    public PageBase getWeekViewPage() {
        return calendarPanel.getRoot().getWeekPage();
    }

    public PageBase getMonthViewPage() {
        return calendarPanel.getRoot().getMonthPage();
    }

    public PageBase getYearViewPage() {
        return calendarPanel.getRoot().getYearPage();
    }

    public PageBase getCurrentCalendarViewPage() {
        return calendarPanel.getRoot().getSelectedPage();
    }
}
