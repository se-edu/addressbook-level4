package seedu.todoList.model;

import javafx.collections.transformation.FilteredList;
import seedu.todoList.commons.core.ComponentManager;
import seedu.todoList.commons.core.LogsCenter;
import seedu.todoList.commons.core.UnmodifiableObservableList;
import seedu.todoList.commons.events.model.TodoListChangedEvent;
import seedu.todoList.commons.util.StringUtil;
import seedu.todoList.model.task.ReadOnlyTask;
import seedu.todoList.model.task.Task;
import seedu.todoList.model.task.UniqueTaskList;
import seedu.todoList.model.task.UniqueTaskList.taskNotFoundException;

import java.util.Set;
import java.util.logging.Logger;

/**
 * Represents the in-memory model of the Todo book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TodoList todoList;
    private final FilteredList<Task> filteredtasks;

    /**
     * Initializes a ModelManager with the given TodoList
     * TodoList and its variables should not be null
     */
    public ModelManager(TodoList src, UserPrefs userPrefs) {
        super();
        assert src != null;
        assert userPrefs != null;

        logger.fine("Initializing with Todo book: " + src + " and user prefs " + userPrefs);

        todoList = new TodoList(src);
        filteredtasks = new FilteredList<>(todoList.gettasks());
    }

    public ModelManager() {
        this(new TodoList(), new UserPrefs());
    }

    public ModelManager(ReadOnlyTodoList initialData, UserPrefs userPrefs) {
        todoList = new TodoList(initialData);
        filteredtasks = new FilteredList<>(todoList.gettasks());
    }

    @Override
    public void resetData(ReadOnlyTodoList newData) {
        todoList.resetData(newData);
        indicateTodoListChanged();
    }

    @Override
    public ReadOnlyTodoList getTodoList() {
        return todoList;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateTodoListChanged() {
        raise(new TodoListChangedEvent(todoList));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws taskNotFoundException {
        todoList.removeTask(target);
        indicateTodoListChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicatetaskException {
        todoList.addTask(task);
        updateFilteredListToShowAll();
        indicateTodoListChanged();
    }

    //=========== Filtered task List Accessors ===============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredtasks);
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredtasks.setPredicate(null);
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords){
        updateFilteredtaskList(new PredicateExpression(new NameQualifier(keywords)));
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredtasks.setPredicate(expression::satisfies);
    }

    //========== Inner classes/interfaces used for filtering ==================================================

    interface Expression {
        boolean satisfies(ReadOnlyTask task);
        String toString();
    }

    private class PredicateExpression implements Expression {

        private final Qualifier qualifier;

        PredicateExpression(Qualifier qualifier) {
            this.qualifier = qualifier;
        }

        @Override
        public boolean satisfies(ReadOnlyTask task) {
            return qualifier.run(task);
        }

        @Override
        public String toString() {
            return qualifier.toString();
        }
    }

    interface Qualifier {
        boolean run(ReadOnlyTask task);
        String toString();
    }

    private class NameQualifier implements Qualifier {
        private Set<String> nameKeyWords;

        NameQualifier(Set<String> nameKeyWords) {
            this.nameKeyWords = nameKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(task.getName().fullName, keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }

}
