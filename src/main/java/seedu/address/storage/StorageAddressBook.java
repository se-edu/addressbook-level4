package seedu.address.storage;

import seedu.address.exceptions.IllegalValueException;
import seedu.address.model.Tag;
import seedu.address.model.datatypes.ReadOnlyAddressBook;
import seedu.address.model.person.ReadOnlyPerson;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Serialisable immutable address book class for local storage
 */
@XmlRootElement(name = "addressbook")
public class StorageAddressBook implements ReadOnlyAddressBook {

    @XmlElement
    private List<AdaptedPerson> persons;
    @XmlElement
    private List<Tag> tags;

    {
        persons = new ArrayList<>();
        tags = new ArrayList<>();
    }

    /**
     * for marshalling 
     */
    public StorageAddressBook() {}

    /**
     * Conversion
     */
    public StorageAddressBook(ReadOnlyAddressBook src) {
        persons.addAll(src.getPersonList().stream().map(AdaptedPerson::new).collect(Collectors.toList()));
        tags = src.getTagList();
    }

    @Override
    public List<ReadOnlyPerson> getPersonList() {
        return persons.stream().map(p -> {
            try {
                return p.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                return null;
            }
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public List<Tag> getTagList() {
        return Collections.unmodifiableList(tags);
    }

}
