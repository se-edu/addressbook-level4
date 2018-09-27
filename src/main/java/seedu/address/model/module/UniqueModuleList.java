package seedu.address.model.module;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.model.module.exceptions.DuplicateModuleException;
import seedu.address.model.module.exceptions.ModuleNotFoundException;

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

        if (!target.equals(editedModule) && contains(editedModule)) {
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
     * @param toRemove the module to be removed from the list
     */
    public void remove(Module toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ModuleNotFoundException();
        }
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
