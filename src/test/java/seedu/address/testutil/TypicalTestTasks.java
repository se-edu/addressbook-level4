package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ToDo;
import seedu.address.model.task.*;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestTasks() {
        try {
            alice =  new TaskBuilder().withName("Alice Pauline").withAddress("123, Jurong West Ave 6, #08-111")
                    .withDescription("alice's description").withTime("12-10-2016")
                    .withTags("friends").withPeriod("10:00AM").build();
            benson = new TaskBuilder().withName("Benson Meier").withAddress("311, Clementi Ave 2, #02-25")
                    .withDescription("johnd's description").withTime("12-10-2016")
                    .withTags("owesMoney", "friends").withPeriod("10:00AM").build();
            carl = new TaskBuilder().withName("Carl Kurz").withTime("12-10-2016")
                    .withDescription("heinz's description").withAddress("wall street").withPeriod("10:00AM").build();
            daniel = new TaskBuilder().withName("Daniel Meier").withTime("12-10-2016")
                    .withDescription("cornelia's description").withAddress("10th street").withPeriod("10:00AM").build();
            elle = new TaskBuilder().withName("Elle Meyer").withTime("12-10-2016")
                    .withDescription("werner's description").withAddress("michegan ave").withPeriod("10:00AM").build();
            fiona = new TaskBuilder().withName("Fiona Kunz").withTime("12-10-2016")
                    .withDescription("lydia's description").withAddress("little tokyo").withPeriod("10:00AM").build();
            george = new TaskBuilder().withName("George Best").withTime("12-10-2016")
                    .withDescription("anna's description").withAddress("4th street").withPeriod("10:00AM").build();

            //Manually added
            hoon = new TaskBuilder().withName("Hoon Meier").withTime("12-10-2016").withDescription("stefan's description")
                    .withAddress("little india").withPeriod("10:00AM").build();
            ida = new TaskBuilder().withName("Ida Mueller").withTime("12-10-2016").withDescription("hans's description")
                    .withAddress("chicago ave").withPeriod("10:00AM").build();

        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadToDoWithSampleData(ToDo ab) {

        try {
            ab.addTask(new Task(alice));
            ab.addTask(new Task(benson));
            ab.addTask(new Task(carl));
            ab.addTask(new Task(daniel));
            ab.addTask(new Task(elle));
            ab.addTask(new Task(fiona));
            ab.addTask(new Task(george));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "not possible";
        }
    }

    public TestTask[] getTypicalTask() {
        return new TestTask[]{alice, benson, carl, daniel, elle, fiona, george};
    }

    public ToDo getTypicalToDo(){
        ToDo ab = new ToDo();
        loadToDoWithSampleData(ab);
        return ab;
    }
}
