package commons;

import address.model.datatypes.AddressBook;
import address.model.datatypes.ReadOnlyAddressBook;
import address.model.datatypes.person.Person;
import address.model.datatypes.person.ReadOnlyPerson;
import address.model.datatypes.tag.Tag;
import address.storage.StorageAddressBook;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Tests JSON Read and Write
 */
public class JsonUtilTest {

    @Test
    public void jsonUtil_readJsonStringToObjectInstance_correctObject() throws IOException {
        String jsonString = "{\n" +
                "  \"persons\" : [ {\n" +
                "    \"id\" : 1,\n" +
                "    \"firstName\" : \"First\",\n" +
                "    \"lastName\" : \"Last\",\n" +
                "    \"street\" : \"\",\n" +
                "    \"postalCode\" : \"123456\",\n" +
                "    \"city\" : \"Singapore\",\n" +
                "    \"githubUsername\" : \"FirstLast\",\n" +
                "    \"birthday\" : \"1980-03-18\",\n" +
                "    \"tags\" : [ {\n" +
                "      \"name\" : \"Tag\"\n" +
                "    } ],\n" +
                "    \"birthday\" : \"1980-03-18\"\n" +
                "  } ],\n" +
                "  \"tags\" : [ {\n" +
                "    \"name\" : \"Tag\"\n" +
                "  } ]\n" +
                "}";
        ReadOnlyAddressBook addressBook = JsonUtil.fromJsonString(jsonString, StorageAddressBook.class);
        assertEquals(1, addressBook.getPersonList().size());
        assertEquals(1, addressBook.getTagList().size());

        ReadOnlyPerson person = addressBook.getPersonList().get(0);
        Tag tag = addressBook.getTagList().get(0);

        assertEquals(1, person.getId());
        assertEquals("First", person.getFirstName());
        assertEquals("Last", person.getLastName());
        assertEquals("Singapore", person.getCity());
        assertEquals("123456", person.getPostalCode());
        assertEquals(tag, person.getTagList().get(0));
        assertEquals("Tag", person.getTagList().get(0).getName());
        assertEquals(LocalDate.of(1980, 3, 18), person.getBirthday());
        assertEquals("FirstLast", person.getGithubUsername());
    }

    @Test
    public void jsonUtil_writeThenReadObjectToJson_correctObject() throws IOException {
        // Write
        Tag sampleTag = new Tag("Tag");
        Person samplePerson = new Person("First", "Last", 1);
        samplePerson.setCity("Singapore");
        samplePerson.setPostalCode("123456");
        List<Tag> tag = new ArrayList<>();
        tag.add(sampleTag);
        samplePerson.setTags(tag);
        samplePerson.setBirthday(LocalDate.of(1980, 3, 18));
        samplePerson.setGithubUsername("FirstLast");

        AddressBook addressBook = new AddressBook();
        addressBook.setPersons(Arrays.asList(samplePerson));
        addressBook.setTags(Arrays.asList(sampleTag));

        String jsonString = JsonUtil.toJsonString(new StorageAddressBook(addressBook));

        // Read
        ReadOnlyAddressBook addressBookRead = JsonUtil.fromJsonString(jsonString, StorageAddressBook.class);
        assertEquals(1, addressBookRead.getPersonList().size());
        assertEquals(1, addressBookRead.getTagList().size());

        ReadOnlyPerson person = addressBookRead.getPersonList().get(0);
        Tag tagRead = addressBookRead.getTagList().get(0);

        assertEquals(1, person.getId());
        assertEquals("First", person.getFirstName());
        assertEquals("Last", person.getLastName());
        assertEquals("Singapore", person.getCity());
        assertEquals("123456", person.getPostalCode());
        assertEquals(tagRead, person.getTagList().get(0));
        assertEquals("Tag", person.getTagList().get(0).getName());
        assertEquals(LocalDate.of(1980, 3, 18), person.getBirthday());
        assertEquals("FirstLast", person.getGithubUsername());
    }
}
