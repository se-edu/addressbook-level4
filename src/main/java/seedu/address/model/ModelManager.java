package seedu.address.model;

import javafx.collections.transformation.FilteredList;
import seedu.address.commons.LoggerManager;
import seedu.address.commons.StringUtil;
import seedu.address.events.model.LocalModelChangedEvent;
import seedu.address.main.ComponentManager;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.UniquePersonList.PersonNotFoundException;

import java.util.Set;
import java.util.logging.Logger;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager {
    private static final Logger logger = LoggerManager.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final FilteredList<Person> filteredPersons;

    /**
     * Initializes a ModelManager with the given AddressBook
     * AddressBook and its variables should not be null
     */
    public ModelManager(AddressBook src) {
        super();
        if (src == null) {
            logger.severe("Attempted to initialize with a null AddressBook");
            assert false;
        }
        logger.fine("Initializing with address book: " + src);

        addressBook = new AddressBook(src);
        filteredPersons = new FilteredList<>(addressBook.getPersons());
    }

    public ModelManager() {
        this(new AddressBook());
    }

    public static ReadOnlyAddressBook getDefaultAddressBook() {
        return new AddressBook();
    }

    /** Clears existing backing model and replaces with the provided new data. */
    public void resetData(ReadOnlyAddressBook newData) {
        addressBook.resetData(newData);
        indicateModelChanged();
    }

    /** Returns the AddressBook */
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }


    private void indicateModelChanged() {
        raise(new LocalModelChangedEvent(addressBook));
    }

    /** Deletes the given person. */
    public synchronized void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException {
        addressBook.removePerson(target);
        indicateModelChanged();
    }

    /** Adds the given person */
    public synchronized void addPerson(Person person) throws UniquePersonList.DuplicatePersonException {
        addressBook.addPerson(person);
        clearListFilter();
        indicateModelChanged();
    }


    public UnmodifiableObservableList<ReadOnlyPerson> getFilteredPersonList() {
        return new UnmodifiableObservableList<>(filteredPersons);
    }

    public void clearListFilter() {
        filteredPersons.setPredicate(null);
    }

    public void filterList(Expr expr) {
        filteredPersons.setPredicate(expr::satisfies);
    }

    public void filterList(Set<String> keywords){
        filterList(new PredExpr(new NameQualifier(keywords)));
    }

    interface Expr {
        boolean satisfies(ReadOnlyPerson person);
        String toString();
    }

    class PredExpr implements Expr {

        private final Qualifier qualifier;

        public PredExpr(Qualifier qualifier) {
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

    class NameQualifier implements Qualifier {
        private Set<String> nameKeyWords;

        public NameQualifier(Set<String> nameKeyWords) {
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
