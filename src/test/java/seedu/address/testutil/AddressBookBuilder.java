package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniquePersonList;
import seedu.address.model.ToDo;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code ToDo ab = new AddressBookBuilder().withPerson("John", "Doe").withTag("Friend").build();}
 */
public class AddressBookBuilder {

    private ToDo toDo;

    public AddressBookBuilder(ToDo toDo){
        this.toDo = toDo;
    }

    public AddressBookBuilder withTask(Task task) throws UniquePersonList.DuplicatePersonException {
        toDo.addTask(task);
        return this;
    }

    public AddressBookBuilder withTag(String tagName) throws IllegalValueException {
        toDo.addTag(new Tag(tagName));
        return this;
    }

    public ToDo build(){
        return toDo;
    }
}
