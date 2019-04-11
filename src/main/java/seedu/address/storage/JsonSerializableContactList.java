package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ContactList;
import seedu.address.model.ReadOnlyContactList;
import seedu.address.model.person.Person;

/**
 * An Immutable ContactList that is serializable to JSON format.
 */
@JsonRootName(value = "contactlist")
class JsonSerializableContactList {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableContactList} with the given persons.
     */
    @JsonCreator
    public JsonSerializableContactList(@JsonProperty("persons") List<JsonAdaptedPerson> persons) {
        this.persons.addAll(persons);
    }

    /**
     * Converts a given {@code ReadOnlyContactList} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableContactList}.
     */
    public JsonSerializableContactList(ReadOnlyContactList source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
    }

    /**
     * Converts this contact list into the model's {@code ContactList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public ContactList toModelType() throws IllegalValueException {
        ContactList contactList = new ContactList();
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (contactList.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            contactList.addPerson(person);
        }
        return contactList;
    }

}
