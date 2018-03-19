package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EDUCATION_LEVEL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EDUCATION_LEVEL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SCHOOL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SCHOOL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.tutee.Tutee;

/**
 * A utility class containing a list of {@code Tutee} objects to be used in tests.
 */
public class TypicalTutees {

    public static final Tutee ALICETUTEE = new TuteeBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("85355255").withSubject("mathematics").withGrade("C+").withEducationLevel("secondary")
            .withSchool("nanhua high school").withTags("friends").build();
    public static final Tutee CARLTUTEE = new TuteeBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("wall street").withSubject("history").withGrade("B")
            .withEducationLevel("secondary").withSchool("wall street high school").build();

    // Manually added
    public static final Tutee HOONTUTEE = new TuteeBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("little india").withSubject("economics").withGrade("A1")
            .withEducationLevel("secondary").withSchool("changi secondary school").build();
    public static final Tutee IDATUTEE = new TuteeBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave").withSubject("english").withGrade("B3")
            .withEducationLevel("secondary").withSchool("tanjong katong secondary school").build();

    // Manually added - Tutee's details found in {@code CommandTestUtil}
    public static final Tutee AMYTUTEE = new TuteeBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withSubject(VALID_SUBJECT_AMY)
            .withGrade(VALID_GRADE_AMY).withEducationLevel(VALID_EDUCATION_LEVEL_AMY).withSchool(VALID_SCHOOL_AMY)
            .withTags(VALID_TAG_FRIEND).build();
    public static final Tutee BOBTUTEE = new TuteeBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withSubject(VALID_SUBJECT_BOB)
            .withGrade(VALID_GRADE_BOB).withEducationLevel(VALID_EDUCATION_LEVEL_BOB).withSchool(VALID_SCHOOL_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalTutees() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            try {
                ab.addPerson(person);
            } catch (DuplicatePersonException e) {
                throw new AssertionError("not possible");
            }
        }
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICETUTEE, CARLTUTEE));
    }
}
