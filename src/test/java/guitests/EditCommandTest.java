package guitests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalPersons;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;
import static seedu.address.ui.testutil.GuiTestAssert.assertListMatching;
import static seedu.address.ui.testutil.GuiTestAssert.assertResultMessage;

import java.util.List;

import org.junit.Test;

import guitests.guihandles.PersonCardHandle;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

// TODO: reduce GUI tests by transferring some tests to be covered by lower level tests.
public class EditCommandTest extends AddressBookGuiTest {

    @Test
    public void edit_allFieldsSpecified_success() {
        String detailsToEdit = PREFIX_NAME + "Bobby " + PREFIX_PHONE + "91234567 "
                + PREFIX_EMAIL + "bobby@example.com "
                + PREFIX_ADDRESS + "Block 123, Bobby Street 3 "
                + PREFIX_TAG + "husband";
        Index addressBookIndex = INDEX_FIRST_PERSON;

        Person editedPerson = new PersonBuilder().withName("Bobby").withPhone("91234567")
                .withEmail("bobby@example.com").withAddress("Block 123, Bobby Street 3").withTags("husband").build();

        assertEditSuccess(addressBookIndex, addressBookIndex, detailsToEdit, editedPerson);
    }

    @Test
    public void edit_notAllFieldsSpecified_success() {
        String detailsToEdit = PREFIX_TAG + "sweetie "
                + PREFIX_TAG + "bestie";
        Index addressBookIndex = INDEX_SECOND_PERSON;

        ReadOnlyPerson personToEdit = getTypicalPersons().get(addressBookIndex.getZeroBased());
        Person editedPerson = new PersonBuilder(personToEdit).withTags("sweetie", "bestie").build();

        assertEditSuccess(addressBookIndex, addressBookIndex, detailsToEdit, editedPerson);
    }

    @Test
    public void edit_clearTags_success() {
        String detailsToEdit = PREFIX_TAG.getPrefix();
        Index addressBookIndex = INDEX_SECOND_PERSON;

        ReadOnlyPerson personToEdit = getTypicalPersons().get(addressBookIndex.getZeroBased());
        Person editedPerson = new PersonBuilder(personToEdit).withTags().build();

        assertEditSuccess(addressBookIndex, addressBookIndex, detailsToEdit, editedPerson);
    }

    @Test
    public void edit_findThenEdit_success() {
        runCommand(FindCommand.COMMAND_WORD + " Carl");

        String detailsToEdit = PREFIX_NAME + "Carrle";
        Index addressBookIndex = INDEX_THIRD_PERSON;

        ReadOnlyPerson personToEdit = getTypicalPersons().get(addressBookIndex.getZeroBased());
        Person editedPerson = new PersonBuilder(personToEdit).withName("Carrle").build();

        assertEditSuccess(INDEX_FIRST_PERSON, addressBookIndex, detailsToEdit, editedPerson);
    }

    @Test
    public void edit_missingPersonIndex_failure() {
        runCommand(EditCommand.COMMAND_WORD + " " + PREFIX_NAME + "Bobby");
        assertResultMessage(getResultDisplay(),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
    }

    @Test
    public void edit_invalidPersonIndex_failure() {
        runCommand(EditCommand.COMMAND_WORD + " 8 " + PREFIX_NAME + "Bobby");
        assertResultMessage(getResultDisplay(), Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void edit_noFieldsSpecified_failure() {
        runCommand(EditCommand.COMMAND_WORD + " 1");
        assertResultMessage(getResultDisplay(), EditCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void edit_invalidValues_failure() {
        runCommand(EditCommand.COMMAND_WORD + " 1 " + PREFIX_NAME + "*&");
        assertResultMessage(getResultDisplay(), Name.MESSAGE_NAME_CONSTRAINTS);

        runCommand(EditCommand.COMMAND_WORD + " 1 " + PREFIX_PHONE + "abcd");
        assertResultMessage(getResultDisplay(), Phone.MESSAGE_PHONE_CONSTRAINTS);

        runCommand(EditCommand.COMMAND_WORD + " 1 " + PREFIX_EMAIL + "yahoo!!!");
        assertResultMessage(getResultDisplay(), Email.MESSAGE_EMAIL_CONSTRAINTS);

        runCommand(EditCommand.COMMAND_WORD + " 1 " + PREFIX_ADDRESS.getPrefix());
        assertResultMessage(getResultDisplay(), Address.MESSAGE_ADDRESS_CONSTRAINTS);

        runCommand(EditCommand.COMMAND_WORD + " 1 " + PREFIX_TAG + "*&");
        assertResultMessage(getResultDisplay(), Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    @Test
    public void edit_duplicatePerson_failure() {
        runCommand(EditCommand.COMMAND_WORD + " 3 "
                + PREFIX_PHONE + "85355255 "
                + PREFIX_EMAIL + "alice@example.com "
                + PREFIX_NAME + "Alice Pauline "
                + PREFIX_ADDRESS + "123, Jurong West Ave 6, #08-111 "
                + PREFIX_TAG + "friends");
        assertResultMessage(getResultDisplay(), EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    /**
     * Checks whether the edited person has the correct updated details.
     *
     * @param filteredPersonListIndex index of person to edit in filtered list
     * @param addressBookIndex index of person to edit in the address book.
     *      Must refer to the same person as {@code filteredPersonListIndex}
     * @param detailsToEdit details to edit the person with as input to the edit command
     * @param editedPerson the expected person after editing the person's details
     */
    private void assertEditSuccess(Index filteredPersonListIndex, Index addressBookIndex,
                                    String detailsToEdit, Person editedPerson) {
        runCommand(EditCommand.COMMAND_WORD + " "
                + filteredPersonListIndex.getOneBased() + " " + detailsToEdit);

        // confirm the new card contains the right data
        getPersonListPanel().navigateToCard(editedPerson);
        PersonCardHandle editedCard = getPersonListPanel().getPersonCardHandle(editedPerson);
        assertCardDisplaysPerson(editedPerson, editedCard);

        // confirm the list now contains all previous persons plus the person with updated details
        List<ReadOnlyPerson> expectedPersons = getTypicalPersons();
        expectedPersons.set(addressBookIndex.getZeroBased(), editedPerson);
        assertListMatching(getPersonListPanel(), expectedPersons);
        assertResultMessage(getResultDisplay(), String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson));
    }
}
