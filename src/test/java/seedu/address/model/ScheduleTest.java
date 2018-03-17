package seedu.address.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.Test;

import seedu.address.model.person.TuitionTask;
import seedu.address.model.personal.PersonalTask;

public class ScheduleTest {
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu HH:mm");

    /**
     * Generates a list of existing tasks
     */
    private static void createTaskList() {
        Schedule.taskList.add(new TuitionTask(
                "Anne", LocalDateTime.parse("11/01/2011 22:00", formatter), "1h30m", "tuition 1"));
        Schedule.taskList.add(new PersonalTask(
                LocalDateTime.parse("15/01/2011 22:00", formatter), "2h30m", "personal task 1"));
        Schedule.taskList.add(new PersonalTask(
                LocalDateTime.parse("13/01/2011 11:00", formatter), "1h0m", "personal task 2"));
    }

    @Test
    public void isTaskClash_clashes_false() {
        createTaskList();

        // New task is on another day
        assertFalse(Schedule.isTaskClash(LocalDateTime.parse("17/01/2011 11:00", formatter), "1h0m"));

        // New task ends right before start of an existing task
        assertFalse(Schedule.isTaskClash(LocalDateTime.parse("11/01/2011 11:00", formatter), "1h0m"));

        // New task starts right after the end of an existing task
        assertFalse(Schedule.isTaskClash(LocalDateTime.parse("16/01/2011 00:30", formatter), "2h0m"));
    }

    @Test
    public void isTaskClash_clashes_true() {
        createTaskList();

        // New task starts at the same time as an existing task
        assertTrue(Schedule.isTaskClash(LocalDateTime.parse("11/01/2011 22:00", formatter), "2h0m"));

        // New task starts during an existing task
        assertTrue(Schedule.isTaskClash(LocalDateTime.parse("15/01/2011 22:30", formatter), "2h0m"));

        // New task ends at the same time as an existing task
        assertTrue(Schedule.isTaskClash(LocalDateTime.parse("13/01/2011 11:30", formatter), "0h30m"));

        // New task ends during an existing task
        assertTrue(Schedule.isTaskClash(LocalDateTime.parse("13/01/2011 10:00", formatter), "1h30m"));

        // New task is within an existing task completely
        assertTrue(Schedule.isTaskClash(LocalDateTime.parse("15/01/2011 22:30", formatter), "1h30m"));

        // Existing task is within the new task completely
        assertTrue(Schedule.isTaskClash(LocalDateTime.parse("11/01/2011 21:00", formatter), "4h0m"));

    }

}
