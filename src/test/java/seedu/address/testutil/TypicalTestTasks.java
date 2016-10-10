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
                    .withEmail("alice's description").withPhone("8535")
                    .withTags("friends").build();
            benson = new TaskBuilder().withName("Benson Meier").withAddress("311, Clementi Ave 2, #02-25")
                    .withEmail("johnd's description").withPhone("9876")
                    .withTags("owesMoney", "friends").build();
            carl = new TaskBuilder().withName("Carl Kurz").withPhone("9535").withEmail("heinz's description").withAddress("wall street").build();
            daniel = new TaskBuilder().withName("Daniel Meier").withPhone("8765").withEmail("cornelia's description").withAddress("10th street").build();
            elle = new TaskBuilder().withName("Elle Meyer").withPhone("9482").withEmail("werner's description").withAddress("michegan ave").build();
            fiona = new TaskBuilder().withName("Fiona Kunz").withPhone("9482").withEmail("lydia's description").withAddress("little tokyo").build();
            george = new TaskBuilder().withName("George Best").withPhone("9482").withEmail("anna's description").withAddress("4th street").build();

            //Manually added
            hoon = new TaskBuilder().withName("Hoon Meier").withPhone("8482").withEmail("stefan's description").withAddress("little india").build();
            ida = new TaskBuilder().withName("Ida Mueller").withPhone("8482").withEmail("hans's description").withAddress("chicago ave").build();
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
