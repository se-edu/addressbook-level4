package address.testutil;

import address.model.datatypes.AddressBook;
import address.model.datatypes.person.Person;
import address.model.datatypes.tag.Tag;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").withTag("Friend").build();}
 */
public class AddressBookBuilder {

    private AddressBook addressBook;
    private int idCounter = 1;

    public AddressBookBuilder(){
        addressBook = new AddressBook();
    }

    public AddressBookBuilder(AddressBook addressBook){
        this.addressBook = addressBook;
    }

    public AddressBookBuilder withPerson(String firstName, String lastName){
        addressBook.addPerson(new Person(firstName, lastName, idCounter++));
        return this;
    }

    public AddressBookBuilder withTag(String tagName){
        addressBook.addTag(new Tag(tagName));
        return this;
    }

    public AddressBook build(){
        return addressBook;
    }
}
