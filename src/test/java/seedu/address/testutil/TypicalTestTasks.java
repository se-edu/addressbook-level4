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
                    .withEmail("alice@gmail.com").withPhone("85355255")
                    .withTags("friends").build();
            benson = new TaskBuilder().withName("Benson Meier").withAddress("311, Clementi Ave 2, #02-25")
                    .withEmail("johnd@gmail.com").withPhone("98765432")
                    .withTags("owesMoney", "friends").build();
            carl = new TaskBuilder().withName("Carl Kurz").withPhone("95352563").withEmail("heinz@yahoo.com").withAddress("wall street").build();
            daniel = new TaskBuilder().withName("Daniel Meier").withPhone("87652533").withEmail("cornelia@google.com").withAddress("10th street").build();
            elle = new TaskBuilder().withName("Elle Meyer").withPhone("9482224").withEmail("werner@gmail.com").withAddress("michegan ave").build();
            fiona = new TaskBuilder().withName("Fiona Kunz").withPhone("9482427").withEmail("lydia@gmail.com").withAddress("little tokyo").build();
            george = new TaskBuilder().withName("George Best").withPhone("9482442").withEmail("anna@google.com").withAddress("4th street").build();

            //Manually added
            hoon = new TaskBuilder().withName("Hoon Meier").withPhone("8482424").withEmail("stefan@mail.com").withAddress("little india").build();
            ida = new TaskBuilder().withName("Ida Mueller").withPhone("8482131").withEmail("hans@google.com").withAddress("chicago ave").build();
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
        } catch (UniqueTaskList.DuplicatePersonException e) {
            assert false : "not possible";
        }
    }

    public TestTask[] getTypicalPersons() {
        return new TestTask[]{alice, benson, carl, daniel, elle, fiona, george};
    }

    public ToDo getTypicalToDo(){
        ToDo ab = new ToDo();
        loadToDoWithSampleData(ab);
        return ab;
    }
}
