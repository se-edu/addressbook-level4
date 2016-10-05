package seedu.Todo.testutil;

import seedu.todoList.commons.exceptions.IllegalValueException;
import seedu.todoList.model.TodoList;
import seedu.todoList.model.task.*;

/**
 *
 */
public class TypicalTesttasks {

    public static Testtask alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTesttasks() {
        try {
            alice =  new taskBuilder().withName("Alice Pauline").withTodo("123, Jurong West Ave 6, #08-111")
                    .withEmail("alice@gmail.com").withPhone("85355255")
                    .withTags("friends").build();
            benson = new taskBuilder().withName("Benson Meier").withTodo("311, Clementi Ave 2, #02-25")
                    .withEmail("johnd@gmail.com").withPhone("98765432")
                    .withTags("owesMoney", "friends").build();
            carl = new taskBuilder().withName("Carl Kurz").withPhone("95352563").withEmail("heinz@yahoo.com").withTodo("wall street").build();
            daniel = new taskBuilder().withName("Daniel Meier").withPhone("87652533").withEmail("cornelia@google.com").withTodo("10th street").build();
            elle = new taskBuilder().withName("Elle Meyer").withPhone("9482224").withEmail("werner@gmail.com").withTodo("michegan ave").build();
            fiona = new taskBuilder().withName("Fiona Kunz").withPhone("9482427").withEmail("lydia@gmail.com").withTodo("little tokyo").build();
            george = new taskBuilder().withName("George Best").withPhone("9482442").withEmail("anna@google.com").withTodo("4th street").build();

            //Manually added
            hoon = new taskBuilder().withName("Hoon Meier").withPhone("8482424").withEmail("stefan@mail.com").withTodo("little india").build();
            ida = new taskBuilder().withName("Ida Mueller").withPhone("8482131").withEmail("hans@google.com").withTodo("chicago ave").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTodoListWithSampleData(TodoList ab) {

        try {
            ab.addtask(new Task(alice));
            ab.addtask(new Task(benson));
            ab.addtask(new Task(carl));
            ab.addtask(new Task(daniel));
            ab.addtask(new Task(elle));
            ab.addtask(new Task(fiona));
            ab.addtask(new Task(george));
        } catch (UniquetaskList.DuplicatetaskException e) {
            assert false : "not possible";
        }
    }

    public Testtask[] getTypicaltasks() {
        return new Testtask[]{alice, benson, carl, daniel, elle, fiona, george};
    }

    public TodoList getTypicalTodoList(){
        TodoList ab = new TodoList();
        loadTodoListWithSampleData(ab);
        return ab;
    }
}
