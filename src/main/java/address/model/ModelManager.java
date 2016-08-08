package address.model;

import address.events.model.LocalModelChangedEvent;
import address.exceptions.DuplicateTagException;
import address.main.ComponentManager;
import address.model.datatypes.AddressBook;
import address.model.datatypes.ReadOnlyAddressBook;
import address.model.datatypes.person.Person;
import address.model.datatypes.person.ReadOnlyPerson;
import address.model.datatypes.tag.Tag;
import address.util.AppLogger;
import address.util.Config;
import address.util.LoggerManager;
import address.util.collections.UnmodifiableObservableList;
import javafx.collections.ObservableList;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements ReadOnlyAddressBook {
    private static final AppLogger logger = LoggerManager.getLogger(ModelManager.class);

    private final AddressBook backingModel;

    public static final int GRACE_PERIOD_DURATION = 3;

    /**
     * Initializes a ModelManager with the given AddressBook
     * AddressBook and its variables should not be null
     */
    public ModelManager(AddressBook src, Config config) {
        super();
        if (src == null) {
            logger.fatal("Attempted to initialize with a null AddressBook");
            assert false;
        }
        logger.debug("Initializing with address book: {}", src);

        backingModel = new AddressBook(src);
    }

    public ModelManager(Config config) {
        this(new AddressBook(), config);
    }

    public ReadOnlyAddressBook getDefaultAddressBook() {
        return new AddressBook();
    }

    /**
     * Clears existing backing model and replaces with the provided new data.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        backingModel.resetData(newData);
    }

//// EXPOSING MODEL

    @Override
    public List<ReadOnlyPerson> getPersonList() {
        return backingModel.getPersonList();
    }

    @Override
    public List<Tag> getTagList() {
        return backingModel.getTagList();
    }

    @Override
    public UnmodifiableObservableList<ReadOnlyPerson> getPersonsAsReadOnlyObservableList() {
        return backingModel.getPersonsAsReadOnlyObservableList();
    }

    @Override
    public UnmodifiableObservableList<Tag> getTagsAsReadOnlyObservableList() {
        return backingModel.getTagsAsReadOnlyObservableList();
    }

    /**
     * @return reference to the tags list inside backing model
     */
    private ObservableList<Tag> backingTagList() {
        return backingModel.getTags();
    }

//// UI COMMANDS

    /**
     * Request to create a person. Simulates the change optimistically until remote confirmation, and provides a grace
     * period for cancellation, editing, or deleting.
     */
    public synchronized void createPersonThroughUI(Optional<ReadOnlyPerson> person) {
        Person toAdd;
        do { // make sure no id clashes.
            toAdd = new Person(Math.abs(UUID.randomUUID().hashCode()));
        } while (backingModel.getPersonList().contains(toAdd));
        toAdd.update(person.get());
        backingModel.addPerson(toAdd);
        updateBackingStorage();
    }

    /**
     * Request to update a person. Simulates the change optimistically until remote confirmation, and provides a grace
     * period for cancellation, editing, or deleting. TODO listen on Person properties and not manually raise events
     * @param target The Person to be changed.
     */
    public synchronized void editPersonThroughUI(ReadOnlyPerson target,
                                                 Optional<ReadOnlyPerson> editedTarget) {
        backingModel.findPerson(target).get().update(editedTarget.get());
        updateBackingStorage();
    }

    public void updateBackingStorage() {
        raise(new LocalModelChangedEvent(backingModel));
    }

    /**
     * Request to set the tags for a group of Persons. Simulates change optimistically until remote confirmation,
     * and provides a grace period for cancellation, editing, or deleting.
     * @param targets Persons to be retagged
     */
    public void retagPersonsThroughUI(Collection<? extends ReadOnlyPerson> targets,
                                      Optional<? extends Collection<Tag>> newTags) {

        targets.stream().forEach(p -> backingModel.findPerson(p).get().setTags(newTags.get()));
        updateBackingStorage();
    }

    /**
     * Request to delete a person. Simulates the change optimistically until remote confirmation, and provides a grace
     * period for cancellation, editing, or deleting.
     */
    public synchronized void deletePersonThroughUI(ReadOnlyPerson target) {
        try {
            backingModel.removePerson(target);
            updateBackingStorage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void deletePersonsThroughUI(List<ReadOnlyPerson> targets) {
        targets.forEach(backingModel::removePerson);
        updateBackingStorage();
    }

//// CREATE

    /**
     * Adds a tag to the model
     * @param tagToAdd
     * @throws DuplicateTagException when this operation would cause duplicates
     */
    public synchronized void addTagToBackingModel(Tag tagToAdd) throws DuplicateTagException {
        if (backingTagList().contains(tagToAdd)) {
            throw new DuplicateTagException(tagToAdd);
        }
        backingTagList().add(tagToAdd);
    }

//// UPDATE

    /**
     * Updates the details of a Tag object. Updates to Tag objects should be
     * done through this method to ensure the proper events are raised to indicate
     * a change to the model. TODO listen on Tag properties and not manually raise events here.
     *
     * @param original The Tag object to be changed.
     * @param updated The temporary Tag object containing new values.
     */
    public synchronized void updateTag(Tag original, Tag updated) throws DuplicateTagException {
        if (!original.equals(updated) && backingTagList().contains(updated)) {
            throw new DuplicateTagException(updated);
        }
        original.update(updated);
    }

//// DELETE

    /**
     * Deletes the tag from the model.
     * @param tagToDelete
     * @return true if there was a successful removal
     */
    public synchronized boolean deleteTag(Tag tagToDelete) {
        return backingTagList().remove(tagToDelete);
    }
}
