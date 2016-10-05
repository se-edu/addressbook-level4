package seedu.todoList.model;

import javafx.collections.ObservableList;
import seedu.todoList.model.tag.Tag;
import seedu.todoList.model.tag.UniqueTagList;
import seedu.todoList.model.task.ReadOnlyTask;
import seedu.todoList.model.task.Task;
import seedu.todoList.model.task.UniquetaskList;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Wraps all data at the Todo-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class TodoList implements ReadOnlyTodoList {

    private final UniquetaskList tasks;
    private final UniqueTagList tags;

    {
        tasks = new UniquetaskList();
        tags = new UniqueTagList();
    }

    public TodoList() {}

    /**
     * tasks and Tags are copied into this Todobook
     */
    public TodoList(ReadOnlyTodoList toBeCopied) {
        this(toBeCopied.getUniquetaskList(), toBeCopied.getUniqueTagList());
    }

    /**
     * tasks and Tags are copied into this Todobook
     */
    public TodoList(UniquetaskList tasks, UniqueTagList tags) {
        resetData(tasks.getInternalList(), tags.getInternalList());
    }

    public static ReadOnlyTodoList getEmptyTodoList() {
        return new TodoList();
    }

//// list overwrite operations

    public ObservableList<Task> gettasks() {
        return tasks.getInternalList();
    }

    public void settasks(List<Task> tasks) {
        this.tasks.getInternalList().setAll(tasks);
    }

    public void setTags(Collection<Tag> tags) {
        this.tags.getInternalList().setAll(tags);
    }

    public void resetData(Collection<? extends ReadOnlyTask> newtasks, Collection<Tag> newTags) {
        settasks(newtasks.stream().map(Task::new).collect(Collectors.toList()));
        setTags(newTags);
    }

    public void resetData(ReadOnlyTodoList newData) {
        resetData(newData.gettaskList(), newData.getTagList());
    }

//// task-level operations

    /**
     * Adds a task to the Todo book.
     * Also checks the new task's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the task to point to those in {@link #tags}.
     *
     * @throws UniquetaskList.DuplicatetaskException if an equivalent task already exists.
     */
    public void addtask(Task p) throws UniquetaskList.DuplicatetaskException {
        syncTagsWithMasterList(p);
        tasks.add(p);
    }

    /**
     * Ensures that every tag in this task:
     *  - exists in the master list {@link #tags}
     *  - points to a Tag object in the master list
     */
    private void syncTagsWithMasterList(Task task) {
        final UniqueTagList taskTags = task.getTags();
        tags.mergeFrom(taskTags);

        // Create map with values = tag object references in the master list
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        for (Tag tag : tags) {
            masterTagObjects.put(tag, tag);
        }

        // Rebuild the list of task tags using references from the master list
        final Set<Tag> commonTagReferences = new HashSet<>();
        for (Tag tag : taskTags) {
            commonTagReferences.add(masterTagObjects.get(tag));
        }
        task.setTags(new UniqueTagList(commonTagReferences));
    }

    public boolean removetask(ReadOnlyTask key) throws UniquetaskList.taskNotFoundException {
        if (tasks.remove(key)) {
            return true;
        } else {
            throw new UniquetaskList.taskNotFoundException();
        }
    }

//// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

//// util methods

    @Override
    public String toString() {
        return tasks.getInternalList().size() + " tasks, " + tags.getInternalList().size() +  " tags";
        // TODO: refine later
    }

    @Override
    public List<ReadOnlyTask> gettaskList() {
        return Collections.unmodifiableList(tasks.getInternalList());
    }

    @Override
    public List<Tag> getTagList() {
        return Collections.unmodifiableList(tags.getInternalList());
    }

    @Override
    public UniquetaskList getUniquetaskList() {
        return this.tasks;
    }

    @Override
    public UniqueTagList getUniqueTagList() {
        return this.tags;
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TodoList // instanceof handles nulls
                && this.tasks.equals(((TodoList) other).tasks)
                && this.tags.equals(((TodoList) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(tasks, tags);
    }
}
