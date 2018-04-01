package seedu.address.model.personal;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalCalendarEntries.VALID_DESCRIPTION;
import static seedu.address.testutil.TypicalCalendarEntries.VALID_DURATION;
import static seedu.address.testutil.TypicalCalendarEntries.VALID_START_DATE_TIME;
import static seedu.address.testutil.TypicalCalendarEntries.getPersonalEntry;

import org.junit.BeforeClass;
import org.junit.Test;

import com.calendarfx.model.Entry;

import seedu.address.testutil.TypicalCalendarEntries;

//@@author ChoChihTun
public class PersonalTaskTest {

    @BeforeClass
    public static void setupBeforeClass() {
        new TypicalCalendarEntries();
    }

    @Test
    public void constructor_validArgs_success() {
        PersonalTask personalTask = new PersonalTask(VALID_START_DATE_TIME,
                VALID_DURATION, VALID_DESCRIPTION);
        Entry actualEntry = personalTask.getEntry();
        Entry expectedEntry = getPersonalEntry();

        // To match the ID of the same entry
        actualEntry.setId("0");
        expectedEntry.setId("0");

        assertEquals(VALID_START_DATE_TIME, personalTask.getTaskDateTime());
        assertEquals(VALID_DURATION, personalTask.getDuration());
        assertEquals(VALID_DESCRIPTION, personalTask.getDescription());
        assertEquals(expectedEntry, actualEntry);
    }
}
