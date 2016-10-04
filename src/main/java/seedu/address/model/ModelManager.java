package seedu.address.model;

import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.task.Task;
import seedu.address.model.task.ReadOnlyPerson;
import seedu.address.model.task.UniquePersonList;
import seedu.address.model.task.UniquePersonList.PersonNotFoundException;
import seedu.address.commons.events.model.ToDoChangedEvent;
import seedu.address.commons.core.ComponentManager;

import java.util.Set;
import java.util.logging.Logger;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final ToDo toDo;
    private final FilteredList<Task> filteredTasks;

    /**
     * Initializes a ModelManager with the given ToDo
     * ToDo and its variables should not be null
     */
    public ModelManager(ToDo src, UserPrefs userPrefs) {
        super();
        assert src != null;
        assert userPrefs != null;

        logger.fine("Initializing with address book: " + src + " and user prefs " + userPrefs);

        toDo = new ToDo(src);
        filteredTasks = new FilteredList<>(toDo.getTasks());
    }

    public ModelManager() {
        this(new ToDo(), new UserPrefs());
    }

    public ModelManager(ReadOnlyToDo initialData, UserPrefs userPrefs) {
        toDo = new ToDo(initialData);
        filteredTasks = new FilteredList<>(toDo.getTasks());
    }

    @Override
    public void resetData(ReadOnlyToDo newData) {
        toDo.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyToDo getToDo() {
        return toDo;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new ToDoChangedEvent(toDo));
    }

    @Override
    public synchronized void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException {
        toDo.removePerson(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws UniquePersonList.DuplicatePersonException {
        toDo.addTask(task);
        updateFilteredListToShowAll();
        indicateAddressBookChanged();
    }

    //=========== Filtered Task List Accessors ===============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyPerson> getFilteredPersonList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredTasks.setPredicate(null);
    }

    @Override
    public void updateFilteredPersonList(Set<String> keywords){
        updateFilteredPersonList(new PredicateExpression(new NameQualifier(keywords)));
    }

    private void updateFilteredPersonList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }

    //========== Inner classes/interfaces used for filtering ==================================================

    interface Expression {
        boolean satisfies(ReadOnlyPerson person);
        String toString();
    }

    private class PredicateExpression implements Expression {

        private final Qualifier qualifier;

        PredicateExpression(Qualifier qualifier) {
            this.qualifier = qualifier;
        }

        @Override
        public boolean satisfies(ReadOnlyPerson person) {
            return qualifier.run(person);
        }

        @Override
        public String toString() {
            return qualifier.toString();
        }
    }

    interface Qualifier {
        boolean run(ReadOnlyPerson person);
        String toString();
    }

    private class NameQualifier implements Qualifier {
        private Set<String> nameKeyWords;

        NameQualifier(Set<String> nameKeyWords) {
            this.nameKeyWords = nameKeyWords;
        }

        @Override
        public boolean run(ReadOnlyPerson person) {
            return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(person.getName().fullName, keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }

}
