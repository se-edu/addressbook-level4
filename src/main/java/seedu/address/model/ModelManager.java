package seedu.address.model;

import seedu.address.events.model.LocalModelChangedEvent;
import seedu.address.exceptions.DuplicateTagException;
import seedu.address.main.ComponentManager;
import seedu.address.model.datatypes.AddressBook;
import seedu.address.model.datatypes.ReadOnlyAddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.UniquePersonList;
import seedu.address.util.AppLogger;
import seedu.address.util.Config;
import seedu.address.util.LoggerManager;
import seedu.address.util.collections.UnmodifiableObservableList;
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
        toAdd = new Person(person.get().getName(), person.get().getPhone(), person.get().getEmail(),
                               person.get().getAddress(), person.get().getTags());
        backingModel.addPerson(toAdd);
        updateBackingStorage();
    }


    public void updateBackingStorage() {
        raise(new LocalModelChangedEvent(backingModel));
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
        targets.forEach(p -> {
            try {
                backingModel.removePerson(p);
            } catch (UniquePersonList.PersonNotFoundException e) {
                e.printStackTrace(); //TODO: handle exception
            }
        });
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

    public synchronized void addPerson(Person person) {
        backingModel.addPerson(person);
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
