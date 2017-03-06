package seedu.address.testutil;

import guitests.guihandles.PersonCardHandle;
import seedu.address.model.person.ReadOnlyPerson;

/**
* Utility class for PersonCard tests.
*/
public class TestPersonCardUtil {
    /**
     * Returns true if {@code person} is the same as the person on {@code card}
     * The persons are considered to be equal if they have the same name, phone, email,
     * address, and tags.
     * Returns false otherwise.
     */
    public static boolean isPersonSameAsPersonOnCard(PersonCardHandle card, ReadOnlyPerson person) {
        return card.isSamePerson(person);
    }
}
