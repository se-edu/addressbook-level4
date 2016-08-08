package seedu.address.storage;

import seedu.address.model.datatypes.ReadOnlyAddressBook;
import seedu.address.model.datatypes.person.ReadOnlyPerson;
import seedu.address.model.datatypes.tag.Tag;

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
    private List<StoragePerson> persons;
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
        persons.addAll(src.getPersonList().stream().map(StoragePerson::new).collect(Collectors.toList()));
        tags = src.getTagList();
    }

    @Override
    public List<ReadOnlyPerson> getPersonList() {
        return Collections.unmodifiableList(persons);
    }

    @Override
    public List<Tag> getTagList() {
        return Collections.unmodifiableList(tags);
    }

}
