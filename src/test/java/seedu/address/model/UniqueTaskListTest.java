package seedu.address.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.ExpectedException;

import seedu.address.model.person.exceptions.TimingClashException;
import seedu.address.model.personal.PersonalTask;
import seedu.address.model.tutee.TuitionTask;
import seedu.address.testutil.Assert;
import systemtests.SystemTestSetupHelper;

//@@author ChoChihTun
public class UniqueTaskListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu HH:mm")
            .withResolverStyle(ResolverStyle.STRICT);
    private UniqueTaskList uniqueTaskList = new UniqueTaskList();

    @BeforeClass
    public static void setupBeforeClass() {
        SystemTestSetupHelper.initialize();
    }

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueTaskList uniqueTaskList = new UniqueTaskList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueTaskList.asObservableList().remove(0);
    }

    @Test
    public void addNewTask_clashes_throwsTimingClashException() {
        try {
            createTaskList();
        } catch (TimingClashException e) {
            throw new AssertionError("Should not have any clashed timing");
        }

        // New task starts at the same time as an existing task
        Assert.assertThrows(TimingClashException.class, () ->
                uniqueTaskList.add(new PersonalTask(
                        LocalDateTime.parse("11/01/2011 22:00", formatter), "2h0m", "Homework 1")));

        // New task starts during an existing task
        Assert.assertThrows(TimingClashException.class, () ->
                uniqueTaskList.add(new PersonalTask(
                        LocalDateTime.parse("15/01/2011 22:30", formatter), "2h0m", "Homework 2")));

        // New task ends at the same time as an existing task
        Assert.assertThrows(TimingClashException.class, () ->
                uniqueTaskList.add(new PersonalTask(
                        LocalDateTime.parse("13/01/2011 11:30", formatter), "0h30m", "Homework 3")));

        // New task ends during an existing task
        Assert.assertThrows(TimingClashException.class, () ->
                uniqueTaskList.add(new PersonalTask(
                        LocalDateTime.parse("13/01/2011 10:00", formatter), "1h30m", "Homework 4")));

        // New task is within an existing task completely
        Assert.assertThrows(TimingClashException.class, () ->
                uniqueTaskList.add(new TuitionTask(
                        "Anne", LocalDateTime.parse("15/01/2011 22:30", formatter), "1h30m", "Assignment")));

        // Existing task is within the new task completely
        Assert.assertThrows(TimingClashException.class, () ->
                uniqueTaskList.add(new TuitionTask(
                        "Ben", LocalDateTime.parse("11/01/2011 21:00", formatter), "4h0m", "Revision")));
    }

    /**
     * Generates a list of existing tasks
     */
    private void createTaskList() throws TimingClashException {
        uniqueTaskList.add(new TuitionTask("Anne",
                LocalDateTime.parse("11/01/2011 22:00", formatter), "1h30m", "tuition 1"));
        uniqueTaskList.add(new PersonalTask(
                LocalDateTime.parse("15/01/2011 22:00", formatter), "2h30m", "personal task 1"));
        uniqueTaskList.add(new PersonalTask(
                LocalDateTime.parse("13/01/2011 11:00", formatter), "1h0m", "personal task 2"));
    }
}
