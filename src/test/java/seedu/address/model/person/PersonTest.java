package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonTest {

    private Person person1 = ALICE;
    private Person person2 = BOB;

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(person1.isSamePerson(person1));

        // null -> returns false
        assertFalse(person1.isSamePerson(null));

        // different person -> returns false
        assertFalse(person1.isSamePerson(person2));

        // same attributes except for name -> returns false
        Person person3 = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(person1.isSamePerson(person3));

        // same attributes except for phone -> returns true
        person3 = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertTrue(person1.isSamePerson(person3));

        // same attributes except for email -> returns true
        person3 = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertTrue(person1.isSamePerson(person3));
    }

    @Test
    public void equals() {
        // same object -> returns true
        assertTrue(person1.equals(person1));

        // null -> returns false
        assertFalse(person1.equals(null));

        // different types -> returns false
        assertFalse(person1.equals(5));

        // different person -> returns false
        assertFalse(person1.equals(person2));

        // same attributes except for name -> returns false
        Person person3 = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(person1.equals(person3));

        // same attributes except for phone -> returns false
        person3 = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(person1.equals(person3));

        // same attributes except for email -> returns false
        person3 = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(person1.equals(person3));

        // same attributes except for address -> returns false
        person3 = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(person1.equals(person3));
    }
}
