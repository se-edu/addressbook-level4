package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import seedu.address.model.tag.Tag;

public class LeadContactTest {

    @Test
    public void isSamePerson() {
        Set<Tag> tagTest = new HashSet<>();
        tagTest.add(new Tag("friends"));

        Contact test1 = new Contact(new Name("test"), new Phone("12345678"),
                new Email("what@gmail.com"), new Address("123 abc"), tagTest);
        Name name1 = test1.getName();
        Phone phone1 = test1.getPhone();
        Email email1 = test1.getEmail();
        Address address1 = test1.getAddress();
        Set<Tag> tags1 = test1.getTags();
        Type type = test1.getType();
        String string1 = test1.toString();

        Lead test2 = new Lead(new Name("test"), new Phone("12345678"),
                new Email("what@gmail.com"), new Address("123 abc"), tagTest);
        Name name2 = test2.getName();
        Phone phone2 = test2.getPhone();
        Email email2 = test2.getEmail();
        Address address2 = test2.getAddress();
        Set<Tag> tags2 = test2.getTags();
        Type type2 = test2.getType();
        String string2 = test2.toString();

        assertFalse(test1.equals(test2));
        assertFalse(type == type2);
        assertTrue(string1.equals(
                "test Type: Contact Phone: 12345678 Email: what@gmail.com Address: 123 abc Tags: [friends]"));
        assertTrue(string2.equals(
                "test Type: Lead Phone: 12345678 Email: what@gmail.com Address: 123 abc Tags: [friends]"));
    }
}
