package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.item.Item;
import seedu.address.model.ledger.Ledger;
import seedu.address.model.member.Person;

/**
 * An Immutable AddressBook that is serializable to XML format
 */
@XmlRootElement(name = "addressbook")
public class XmlSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate member(s).";
    public static final String MESSAGE_DUPLICATE_ITEM = "Items list contains duplicate item(s).";
    public static final String MESSAGE_DUPLICATE_LEDGER = "Ledgers list contains duplicate ledger(s).";

    @XmlElement
    private List<XmlAdaptedPerson> persons;

    @XmlElement
    private List<XmlAdaptedItem> items;

    @XmlElement
    private List<XmlAdaptedLedger> ledgers;

    /**
     * Creates an empty XmlSerializableAddressBook.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableAddressBook() {
        persons = new ArrayList<>();
        items = new ArrayList<>();
        ledgers = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableAddressBook(ReadOnlyAddressBook src) {
        this();
        persons.addAll(src.getPersonList().stream().map(XmlAdaptedPerson::new).collect(Collectors.toList()));
        items.addAll(src.getItemList().stream().map(XmlAdaptedItem::new).collect(Collectors.toList()));
        ledgers.addAll(src.getLedgerList().stream().map(XmlAdaptedLedger::new).collect(Collectors.toList()));
    }

    /**
     * Converts this addressbook into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedPerson}.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        for (XmlAdaptedPerson p : persons) {
            Person person = p.toModelType();
            if (addressBook.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            addressBook.addPerson(person);
        }
        for (XmlAdaptedItem i : items) {
            Item item = i.toModelType();
            if (addressBook.hasItem(item)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ITEM);
            }
            addressBook.addItem(item);
        }
        for (XmlAdaptedLedger l : ledgers) {
            Ledger ledger = l.toModelType();
            if (addressBook.hasLedger(ledger)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_LEDGER);
            }
            addressBook.addLedger(ledger);
        }
        return addressBook;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableAddressBook)) {
            return false;
        }
        return persons.equals(((XmlSerializableAddressBook) other).persons)
                && items.equals(((XmlSerializableAddressBook) other).items)
                && ledgers.equals(((XmlSerializableAddressBook) other).ledgers);
    }
}
