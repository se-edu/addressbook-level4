package seedu.address.testutil.typicaladdressbook;

import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_TIME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DURATION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_DESC;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.Task;
import seedu.address.testutil.TaskBuilder;

//@@author yungyung04
/**
 * A utility class containing a list of {@code Task} objects to be used in tests.
 */
public class TypicalTasks {
    public static final Task TASK_ALICE = new TaskBuilder().withTuteeName("Alice Pauline")
            .withDateTime("01/10/2018 10:00").withDuration("2h0m").withDescription("Calculus page 24")
            .buildTuitionTask();
    public static final Task TASK_BENSON = new TaskBuilder().withTuteeName("Benson Meier")
            .withDateTime("01/10/2018 14:30").withDuration("2h0m").withDescription("Math exam")
            .buildTuitionTask();
    public static final Task TASK_CARL = new TaskBuilder().withTuteeName("Carl Kurtz")
            .withDateTime("31/12/2018 09:15").withDuration("1h20m").withoutDescription()
            .buildTuitionTask();
    public static final Task TASK_GROCERRY_SHOPPING = new TaskBuilder()
            .withDateTime("25/04/2017 14:30").withDuration("1h0m").withDescription("grocery shopping")
            .buildPersonalTask();
    public static final Task TASK_YOGA = new TaskBuilder()
            .withDateTime("28/02/2019 14:30").withDuration("3h0m").withDescription("yoga")
            .buildPersonalTask();

    // Tuition with same tutee but different timing
    public static final Task TASK_ALICE_SAME_DAY = new TaskBuilder().withTuteeName("Alice Pauline")
            .withDateTime("01/10/2018 17:00").withDuration("0h45m").buildTuitionTask();
    public static final Task TASK_ALICE_DIFFERENT_DAY = new TaskBuilder().withTuteeName("Alice Pauline")
            .withDateTime("30/09/2018 10:00").withDuration("2h0m").buildTuitionTask();

    //Tuition with time clash
    public static final Task TASK_DANIEL_CLASHES_ALICE = new TaskBuilder().withTuteeName("Daniel Meier")
            .withDateTime("01/10/2018 11:00").withDuration("2h0m").buildTuitionTask();

    //Personal task clashes tuition
    public static final Task TASK_GROCERRY_SHOPPING_CLASHES_ALICE = new TaskBuilder()
            .withDateTime("01/10/2018 10:00").withDuration("2h0m").withDescription("Calculus page 24")
            .buildPersonalTask();

    //Tuition which start right after another tuition ends
    public static final Task TASK_CARL_AFTER_ALICE = new TaskBuilder().withTuteeName("Carl Kurtz")
            .withDateTime("01/10/2018 12:00").withDuration("1h0m").buildTuitionTask();

    //Personal task which start right after another tuition ends
    public static final Task TASK_YOGA_AFTER_ALICE = new TaskBuilder()
            .withDateTime("01/10/2018 12:00").withDuration("3h0m").withDescription("yoga")
            .buildPersonalTask();

    // Manually added - Task details found in {@code CommandTestUtil}
    public static final Task TASK_AMY = new TaskBuilder().withTuteeName(VALID_NAME_AMY)
            .withDateTime(VALID_DATE_TIME).withDuration(VALID_DURATION).withDescription(VALID_TASK_DESC)
            .buildTuitionTask();

    private TypicalTasks() {} // prevents instantiation

    public static List<Task> getTypicalTasks() {
        return new ArrayList<>(Arrays.asList(TASK_ALICE, TASK_BENSON, TASK_CARL, TASK_GROCERRY_SHOPPING, TASK_YOGA));
    }
}
