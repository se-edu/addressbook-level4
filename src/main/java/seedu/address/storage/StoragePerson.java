package seedu.address.storage;

import seedu.address.model.datatypes.person.ReadOnlyPerson;
import seedu.address.model.datatypes.tag.Tag;
import seedu.address.commons.XmlUtil;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Serialisable person class for local storage
 */
@XmlRootElement(name = "person")
public class StoragePerson implements ReadOnlyPerson {

    @XmlElement
    private int id;
    @XmlElement
    private String firstName;
    @XmlElement
    private String lastName;

    @XmlElement
    private String githubUsername;
    @XmlElement
    private String street;
    @XmlElement
    private String postalCode;
    @XmlElement
    private String city;

    @XmlElement
    @XmlJavaTypeAdapter(XmlUtil.LocalDateAdapter.class)
    private LocalDate birthday;
    @XmlElement
    private List<Tag> tags;

    {
        tags = new ArrayList<>();
    }

    /**
     * for xml/json marshalling
     */
    public StoragePerson() {}

    /**
     * for conversion
     */
    public StoragePerson(ReadOnlyPerson src) {
        id = src.getId();
        firstName = src.getFirstName();
        lastName = src.getLastName();
        githubUsername = src.getGithubUsername();
        street = src.getStreet();
        postalCode = src.getPostalCode();
        city = src.getCity();
        birthday = src.getBirthday();
        tags.addAll(src.getTagList());
    }
    
    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    public String getGithubUsername() {
        return githubUsername;
    }

    @Override
    public String getStreet() {
        return street;
    }

    @Override
    public String getPostalCode() {
        return postalCode;
    }

    @Override
    public String getCity() {
        return city;
    }

    @Override
    public LocalDate getBirthday() {
        return birthday;
    }

    @Override
    public List<Tag> getTagList() {
        return Collections.unmodifiableList(tags);
    }

    @Override
    public boolean hasName(String firstName, String lastName) {
        return this.firstName.equals(firstName) && this.lastName.equals(lastName);
    }
}
