package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.EditCommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.testutil.EditCommandTestUtil.VALID_TAG_HUSBAND;

import org.junit.Test;

import guitests.guihandles.PersonCardHandle;
import seedu.address.commons.util.IndexUtil;
import seedu.address.logic.commands.EditCommand;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.PersonBuilder;

public class EditCommandTest extends AddressBookGuiTest {

    // The list of persons in the person list panel is expected to match this list.
    // This list is updated with every successful call to assertEditSuccess().
    private Person[] expectedPersonsList = td.getTypicalPersons();

    @Test
    public void edit_oneFieldSpecified_success() throws Exception {
        String detailsToEdit = PREFIX_TAG + VALID_TAG_HUSBAND + " " + PREFIX_TAG + VALID_TAG_FRIEND;
        int addressBookIndex = 2;

        Person personToEdit = expectedPersonsList[IndexUtil.oneToZeroIndex(addressBookIndex)];
        Person editedPerson = new PersonBuilder(personToEdit).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();

        assertEditSuccess(addressBookIndex, detailsToEdit, editedPerson);
    }

    @Test
    public void edit_invalidValue_failure() {
        assertEditFailure(1, PREFIX_PHONE + "abc", Phone.MESSAGE_PHONE_CONSTRAINTS);
    }

    /**
     * Checks whether the {@code personListPanel} has been modified.
     *
     * @param addressBookIndex index of person to edit in the address book
     * @param invalidInput details to edit the person with as input to the edit command
     */
    private void assertEditFailure(int addressBookIndex, String invalidInput, String expectedMessage) {
        commandBox.runCommand(EditCommand.COMMAND_WORD + " " + addressBookIndex + " " + invalidInput);

        // confirm the card remains unmodified
        ReadOnlyPerson person = expectedPersonsList[IndexUtil.oneToZeroIndex(addressBookIndex)];
        PersonCardHandle editedCard = personListPanel.navigateToPerson(person.getName().fullName);
        assertMatching(person, editedCard);

        // confirm the list remains unmodified
        assertTrue(personListPanel.isListMatching(expectedPersonsList));

        assertResultMessage(expectedMessage);
    }

    /**
     * Checks whether the edited person has the correct updated details.
     *
     * @param addressBookIndex index of person to edit in the address book.
     * @param detailsToEdit details to edit the person with as input to the edit command
     * @param editedPerson the expected person after editing the person's details
     */
    private void assertEditSuccess(int addressBookIndex, String detailsToEdit, Person editedPerson) {
        commandBox.runCommand(EditCommand.COMMAND_WORD + " " + addressBookIndex + " " + detailsToEdit);

        // confirm the new card contains the right data
        PersonCardHandle editedCard = personListPanel.navigateToPerson(editedPerson.getName().fullName);
        assertMatching(editedPerson, editedCard);

        // confirm the list now contains all previous persons plus the person with updated details
        expectedPersonsList[IndexUtil.oneToZeroIndex(addressBookIndex)] = editedPerson;
        assertTrue(personListPanel.isListMatching(expectedPersonsList));
        assertResultMessage(String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson));
    }
}
