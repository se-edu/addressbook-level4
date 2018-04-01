package seedu.address.testutil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

import com.calendarfx.model.Entry;
import com.calendarfx.model.Interval;

//@@author ChoChihTun
/**
 * A utility class containing a list of {@code Entry} objects to be used in tests.
 */
public class TypicalCalendarEntries {
    public static final String VALID_NAME = "Jason";
    public static final String VALID_DURATION = "1h30m";
    public static final String VALID_DESCRIPTION = "homework 1";

    private static final String VALID_STRING_START_DATE_TIME = "01/04/2018 11:00";
    private static final String VALID_STRING_END_DATE_TIME = "01/04/2018 12:30";
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu HH:mm")
            .withResolverStyle(ResolverStyle.STRICT);

    public static final LocalDateTime VALID_START_DATE_TIME =
            LocalDateTime.parse(VALID_STRING_START_DATE_TIME, formatter);
    public static final LocalDateTime VALID_END_DATE_TIME =
            LocalDateTime.parse(VALID_STRING_END_DATE_TIME, formatter);
    private static Entry validTuitionEntry;
    private static Entry validPersonalEntry;

    /**
     * Creates valid calendar entry
     *
     */
    public TypicalCalendarEntries() {
        Interval interval = new Interval(VALID_START_DATE_TIME, VALID_END_DATE_TIME);
        createTuitionEntry(interval);
        createPersonalEntry(interval);
        setEntryId();
    }

    /**
     * Matches ID of same entry in task constructor to remove unique entry ID
     *
     */
    private void setEntryId() {
        validTuitionEntry.setId("14");
        validPersonalEntry.setId("5");
    }

    /**
     * Creates a valid tuition calendar entry
     *
     * @param interval of the entry
     */
    private void createTuitionEntry(Interval interval) {
        validTuitionEntry = new Entry(VALID_NAME);
        validTuitionEntry.setInterval(interval);
    }

    /**
     * Creates a valid personal calendar entry
     *
     * @param interval of the entry
     */
    private void createPersonalEntry(Interval interval) {
        validPersonalEntry = new Entry(VALID_DESCRIPTION);
        validPersonalEntry.setInterval(interval);
    }

    public static Entry getTuitionEntry() {
        return validTuitionEntry;
    }

    public static Entry getPersonalEntry() {
        return validPersonalEntry;
    }
}
