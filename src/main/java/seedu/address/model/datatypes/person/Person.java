package seedu.address.model.datatypes.person;

import seedu.address.model.datatypes.UniqueData;
import seedu.address.model.datatypes.tag.Tag;
import seedu.address.util.collections.UnmodifiableObservableList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * Data-model implementation class representing the "Person" domain object.
 * id is immutable, so this class can be safely used as map keys and set elements.
 *
 * As far as possible, avoid working directly with this class.
 * Instead, use and declare the minimum required superclass/interface.
 *
 * Eg. A GUI element controller that only needs access to the Person's properties should declare the received Person
 * as an ReadOnlyPerson -- since it does not need the functionality in the other superclasses/interfaces.
 */
public class Person extends UniqueData implements ReadOnlyPerson {

    private final int id;

    private final StringProperty firstName;
    private final StringProperty lastName;

    private final StringProperty githubUsername;
    private final StringProperty street;
    private final StringProperty postalCode;
    private final StringProperty city;

    private final SimpleObjectProperty<LocalDate> birthday;
    private final ObservableList<Tag> tags;
    private final SimpleBooleanProperty isDeleted;

    // defaults
    {
        firstName = new SimpleStringProperty("");
        lastName = new SimpleStringProperty("");

        street = new SimpleStringProperty("");
        postalCode = new SimpleStringProperty("");
        city = new SimpleStringProperty("");
        githubUsername = new SimpleStringProperty("");

        birthday = new SimpleObjectProperty<>();

        tags = FXCollections.observableArrayList();
        isDeleted = new SimpleBooleanProperty(false);
    }

    /**
     * id-less person data container
     */
    public static Person createPersonDataContainer() {
        return new Person(0);
    }

    public Person(int id) {
        this.id = id;
    }

    /**
     * Constructor with firstName and lastName parameters.
     * Other parameters are set to defaults.
     */
    public Person(String firstName, String lastName, int id) {
        this(id);
        setFirstName(firstName);
        setLastName(lastName);
    }

    /**
     * Deep copy constructor. <strong>Also copies id.</strong>
     * @see Person#update(ReadOnlyPerson)
     */
    public Person(ReadOnlyPerson toBeCopied) {
        this(toBeCopied.getId());
        update(toBeCopied);
    }

    /**
     * Does not update own id with argument's id.
     * @return self (calling this from a Person returns a Person instead of just a WritablePerson)
     */
    public Person update(ReadOnlyPerson newDataSource) {
        setFirstName(newDataSource.getFirstName());
        setLastName(newDataSource.getLastName());

        setStreet(newDataSource.getStreet());
        setPostalCode(newDataSource.getPostalCode());
        setCity(newDataSource.getCity());
        setGithubUsername(newDataSource.getGithubUsername());

        setBirthday(newDataSource.getBirthday());
        setTags(newDataSource.getTagList());
        setIsDeleted(false); // TODO: change when isDeleted is fully implemented
        return this;
    }

    // TODO: consider using reflection to access all isassignablefrom(Property) returning methods for maintainability
    /**
     * Passes matching property field pairs (paired between self and another ReadOnlyPerson) as arguments to a callback.
     * The callback is called once for each property field in the ObservabePerson class.
     *
     * @param other the ReadOnlyPerson whose property fields make up the second parts of the property pairs
     * @param action called for every property field: action(self:property, other:same_property)
     *               first argument is from self, second is from the "other" parameter
     */
    public void forEachPropertyFieldPairWith(Person other, BiConsumer<? super Property, ? super Property> action) {
        action.accept(firstName, other.firstName);
        action.accept(lastName, other.lastName);
        action.accept(githubUsername, other.githubUsername);

        action.accept(street, other.street);
        action.accept(postalCode, other.postalCode);
        action.accept(city, other.city);

        action.accept(birthday, other.birthday);
    }

//// id

    @JsonProperty("id")
    @Override
    public int getId() {
        return id;
    }

//// NAME

    @JsonProperty("firstName")
    @Override
    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    @Override
    public ReadOnlyStringProperty firstNameProperty() {
        return firstName;
    }

    @JsonProperty("lastName")
    @Override
    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    @Override
    public ReadOnlyStringProperty lastNameProperty() {
        return lastName;
    }

//// GITHUB USERNAME

    @JsonProperty("githubUsername")
    public String getGithubUsername() {
        return githubUsername.get();
    }

    public void setGithubUsername(String githubUsername) {
        this.githubUsername.set(githubUsername);
    }

    public ReadOnlyStringProperty githubUsernameProperty() {
        return githubUsername;
    }

//// STREET

    @JsonProperty("street")
    @Override
    public String getStreet() {
        return street.get();
    }

    public void setStreet(String street) {
        this.street.set(street);
    }

    @Override
    public ReadOnlyStringProperty streetProperty() {
        return street;
    }

//// POSTAL CODE

    @JsonProperty("postalCode")
    @Override
    public String getPostalCode() {
        return postalCode.get();
    }

    public void setPostalCode(String postalCode) {
        this.postalCode.set(postalCode);
    }

    @Override
    public ReadOnlyStringProperty postalCodeProperty() {
        return postalCode;
    }

//// CITY

    @JsonProperty("city")
    @Override
    public String getCity() {
        return city.get();
    }

    public void setCity(String city) {
        this.city.set(city);
    }

    @Override
    public ReadOnlyStringProperty cityProperty() {
        return city;
    }

//// BIRTHDAY

    @JsonProperty("birthday")
    @Override
    public LocalDate getBirthday() {
        return birthday.get();
    }

    @JsonSetter("birthday")
    public void setBirthday(LocalDate birthday) {
        this.birthday.set(birthday);
    }

    @Override
    public ReadOnlyObjectProperty<LocalDate> birthdayProperty() {
        return birthday;
    }

//// TAGS

    @Override
    public List<Tag> getTagList() {
        return Collections.unmodifiableList(tags);
    }

    @Override
    public UnmodifiableObservableList<Tag> getObservableTagList() {
        return new UnmodifiableObservableList<>(tags);
    }

    @Override
    public boolean hasName(String firstName, String lastName) {
        return this.firstName.get().equals(firstName) && this.lastName.get().equals(lastName);
    }

    @JsonProperty("tags")
    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(Collection<Tag> tags) {
        this.tags.clear();
        this.tags.addAll(tags);
    }

    @JsonProperty("isDeleted")
    public boolean isDeleted() {
        return isDeleted.get();
    }

    public SimpleBooleanProperty isDeletedProperty() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted.set(isDeleted);
    }

    //// OTHER LOGIC

    /**
     * Compares id
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (other == null) return false;
        if (Person.class.isAssignableFrom(other.getClass())) {
            final ReadOnlyPerson otherP = (ReadOnlyPerson) other;
            return getId() == otherP.getId();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Person [" + idString() + "][" + fullName()
                + "][Street:" + getStreet()
                + "][City:" + getCity()
                + "][Postal code:" + getPostalCode()
                + "][Birthday:" + birthdayString()
                + "][GitHub Username:" + getGithubUsername()
                + "][" + tagsString() + "]";
    }

    public Person copy() {
        Person copy = new Person(this.getFirstName(), this.getLastName(), this.getId());
        copy.setBirthday(this.getBirthday());
        copy.setCity(this.getCity());
        copy.setPostalCode(this.getPostalCode());
        copy.setStreet(this.getStreet());
        copy.setGithubUsername(this.getGithubUsername());
        copy.setTags(this.getTags());
        return copy;
    }

    public static String collateNames(List<ReadOnlyPerson> list) {
        StringBuilder sb = new StringBuilder();
        list.stream().forEach(p -> sb.append(p.fullName() + ", "));
        return sb.toString().substring(0, sb.toString().length() - 2);
    }

}
