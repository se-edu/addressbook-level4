package seedu.address.model.task;

import static java.util.Objects.requireNonNull;
import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.xml.internal.ws.api.message.ExceptionHasMessage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.task.exceptions.DuplicateTaskException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.task.exceptions.TaskNotFoundException;
import sun.rmi.runtime.Log;


public class UniqueTaskList implements Iterable<Task>{

    private final ObservableList<Task> internalList = FXCollections.observableArrayList();
    private final ObservableList<Task> internalUnmodifiableList = FXCollections.unmodifiableObservableList(internalList);
    private final Logger logger = LogsCenter.getLogger(getClass());

    public boolean contains(Task toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameTask);
    }
    public void add(Task toAdd) {
        requireNonNull(toAdd);
//        if (contains(toAdd)) {
//            throw new DuplicatePersonException();
//        }
        internalList.add(toAdd);
        logger.log(Level.FINE, "PROCESSINGT THROUGH UNIQUE TASK LIST");
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Task> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Task> iterator() {
        return internalList.iterator();
    }

    public void setTask(Task target, Task editedTask) {
        requireAllNonNull(target, editedTask);

        int index = internalList.indexOf(target);
        if (index == -1) {
            Logger.getLogger("task not found");
        }
        if (!editedTask.isSameTask(editedTask) &&
        contains(editedTask)){
            Logger.getLogger("Duplicate task");
        }
        internalList.set(index, editedTask);
    }

    public void setTasks(List<Task> tasks) {
        requireAllNonNull(tasks);
        if (!tasksAreUnique(tasks)) {
//            logger.info("FUCKING DUPLICATE BITCH");
            throw new DuplicateTaskException();
        }
        internalList.setAll(tasks);
    }

    public void setTasks(UniqueTaskList replacement){
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    public void remove(Task toRemove){
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)){
            throw new TaskNotFoundException();
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueTaskList // instanceof handles nulls
                && internalList.equals(((UniqueTaskList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    private boolean tasksAreUnique(List<Task> tasks){
        for (int i=0;i<tasks.size()-1;i++){
            for (int j=i+1;j<tasks.size();j++){
                if (tasks.get(i).isSameTask(tasks.get(j))){
                    return false;
                }
            }
        }
        return true;
    }


}
