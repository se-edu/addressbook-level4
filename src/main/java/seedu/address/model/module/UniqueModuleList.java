package seedu.address.model.module;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.model.module.exceptions.DuplicateModuleException;
import seedu.address.model.module.exceptions.ModuleNotFoundException;

//@@author alexkmj
/**
 * A list of modules that enforces uniqueness between its elements and does not allow nulls.
 * <p>
 * A module is considered unique by comparing {@code moduleA.equals(moduleB)}.
 * <p>
 * As such, adding and updating of modules uses {@code moduleA.equals(moduleB)} for equality so as
 * to ensure that the module being added or updated is unique in terms of identity in the
 * UniqueModuleList.
 */
public class UniqueModuleList implements Iterable<Module> {

    /**
     * Creates an observable list of module.
     * See {@link Module}.
     */
    private final ObservableList<Module> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent module as the given argument.
     * See {@link Module}.
     *
     * @param toCheck the module that is being checked against
     * @return true if list contains equivalent module
     */
    public boolean contains(Module toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameModule);
    }

    /**
     * Returns true if the list contains multiple instances of module with the given code.
     */
    public boolean hasMultipleInstances(Code code) {
        requireNonNull(code);
        return internalList.stream()
                .filter(target -> target.getCode().equals(code))
                .count() > 1;
    }

    /**
     * Adds a module to the list.
     * <p>
     * The {@link Module} must not have already exist in the list.
     *
     * @param toAdd the module that would be added into the list
     */
    public void add(Module toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateModuleException();
        }
        internalList.add(toAdd);
    }

    /**
     * Adds all module in a list to the list
     */
    public boolean addAll(Collection<Module> modules) {
        return internalList.addAll(modules);
    }

    /**
     * Replaces the module {@code target} in the list with {@code editedModule}.
     * <p>
     * {@code target} must exist in the list. The {@link Module} identity of {@code editedModule}
     * must not be the same as another existing module in the list.
     *
     * @param target the module to be replaced
     * @param editedModule the modue that replaces the old module
     */
    public void setModule(Module target, Module editedModule) {
        requireAllNonNull(target, editedModule);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ModuleNotFoundException();
        }

        if (!target.isSameModule(editedModule) && contains(editedModule)) {
            throw new DuplicateModuleException();
        }

        internalList.set(index, editedModule);
    }

    /**
     * Replaces the {@link #internalList} of this UniqueModuleList with the internalList of the
     * replacement.
     *
     * @param replacement the UniqueModuleList object that contains the internalList that is
     * replacing the old internalList
     */
    public void setModules(UniqueModuleList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code modules}. {@code modules} must not contain
     * duplicate modules.
     *
     * @param modules the list of module that would replace the old list
     */
    public void setModules(List<Module> modules) {
        requireAllNonNull(modules);
        if (!modulesAreUnique(modules)) {
            throw new DuplicateModuleException();
        }

        internalList.setAll(modules);
    }

    /**
     * Removes the equivalent module from the list.
     * <p>
     * The {@link Module} must exist in the list.
     *
     * @param module the code that the module to be removed contains
     */
    public void remove(Module module) {
        requireNonNull(module);

        if (!internalList.remove(module)) {
            throw new ModuleNotFoundException();
        }
    }

    /**
     * Removes the equivalent module from the list.
     * <p>
     * The {@link Module} must exist in the list.
     *
     * @param filter the predicate used to filter the modules to be removed
     */
    public void remove(Predicate<Module> filter) {
        boolean successful = internalList.removeIf(filter);

        if (!successful) {
            throw new ModuleNotFoundException();
        }
    }

    /**
     * Removes all module in a list from the list
     */
    public boolean removeAll(Collection<Module> modules) {
        return internalList.removeAll(modules);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     *
     * @return backing list as an unmodifiable {@code ObservableList}
     */
    public ObservableList<Module> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    /**
     * Returns true if {@code modules} contains only unique modules.
     *
     * @param modules the module list that is being checked
     * @return true if modules are unique and false if modules are not unique
     */
    private boolean modulesAreUnique(List<Module> modules) {
        return modules.size() == modules.parallelStream()
                .distinct()
                .count();
    }

    //@@author jeremiah-ang
    /**
     * Returns the list of filtered Module based on the given predicate
     *
     * @param predicate
     * @return filtered list
     */
    public ObservableList<Module> getFilteredModules(Predicate<Module> predicate) {
        return internalList.filtered(predicate);
    }

    /**
     * Finds Module that isSameModule as moduleToFind
     * @param moduleToFind
     * @return the Module that matches; null if not matched
     */
    public Module find(Module moduleToFind) {
        for (Module module: internalList) {
            if (module.isSameModule(moduleToFind)) {
                return module;
            }
        }
        return null;
    }

    /**
     * Finds the first instance of the module that has the same moduleCodeToFind
     * @param moduleCodeToFind
     * @return module that return true; null if not matched
     */
    public Module find(Code moduleCodeToFind) {
        for (Module module: internalList) {
            if (module.getCode().equals(moduleCodeToFind)) {
                return module;
            }
        }
        throw new ModuleNotFoundException();
    }

    //@@author
    /**
     * Returns the iterator of the internal list.
     *
     * @return iterator of the internal list
     */
    @Override
    public Iterator<Module> iterator() {
        return internalList.iterator();
    }

    /**
     * Compares the internal list of both UniqueModuleList object.
     * <p>
     * This defines a notion of equality between two UniqueModuleList objects.
     *
     * @param other Code object compared against this object
     * @return true if both are the same object or contains the same value
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueModuleList // instanceof handles nulls
                && internalList.equals(((UniqueModuleList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
