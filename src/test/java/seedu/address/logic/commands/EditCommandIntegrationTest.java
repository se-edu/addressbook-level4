package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Parser;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;
import seedu.address.testutil.TypicalPersons;

/**
 * An integration test class that tests edit command's interaction with the Model.
 */
public class EditCommandIntegrationTest {

    private static final String VALID_ADDRESS_ALICE = "Block 321, Alice Street 1";
    private static final String VALID_EMAIL_ALICE = "alice@yahoo.com";
    private static final String VALID_NAME_ALICE = "Alice";
    private static final String VALID_PHONE_ALICE = "91111111";
    private static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    private static final String VALID_EMAIL_BOB = "bobby@example.com";
    private static final String VALID_PHONE_BOB = "91234567";
    private static final int ZERO_BASED_INDEX_FIRST_PERSON = 0;
    private static final int ZERO_BASED_INDEX_SECOND_PERSON =  ZERO_BASED_INDEX_FIRST_PERSON + 1;

    private Model model = new ModelManager(new TypicalPersons().getTypicalAddressBook(), new UserPrefs());
    private Parser parser = new Parser();

    @Test
    public void execute_validCommand_succeeds() throws Exception {
        Person editedPerson = new PersonBuilder().withName("Bobby").withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                                    .withTags("husband").build();

        String userInput = PersonUtil.getEditCommand(ZERO_BASED_INDEX_FIRST_PERSON, editedPerson);
        Command command = prepareCommand(userInput);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);

        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        expectedAddressBook.updatePerson(ZERO_BASED_INDEX_FIRST_PERSON, editedPerson);
        FilteredList<ReadOnlyPerson> expectedFilteredList = new FilteredList<>(expectedAddressBook.getPersonList());

        assertCommandSuccess(command, expectedMessage, expectedAddressBook, expectedFilteredList);
    }

    @Test
    public void edit_multipleValuesOneField_success() throws Exception {
        String userInput = "edit 1 " + VALID_NAME_ALICE + " " + PREFIX_PHONE.getPrefix() + VALID_PHONE_ALICE + " "
                + PREFIX_PHONE.getPrefix() + VALID_PHONE_BOB;
        Command command = prepareCommand(userInput);
        Person firstPerson = new Person(model.getFilteredPersonList().get(ZERO_BASED_INDEX_FIRST_PERSON));
        Person editedPerson = new PersonBuilder(firstPerson).withName(VALID_NAME_ALICE).withPhone(VALID_PHONE_BOB)
                .build();

        String fieldWithMultipleValues = "Phone";
        String expectedMessage = String.format(EditCommand.MESSAGE_MULTIPLE_VALUES_WARNING, fieldWithMultipleValues,
                fieldWithMultipleValues) + String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);

        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        expectedAddressBook.updatePerson(ZERO_BASED_INDEX_FIRST_PERSON, editedPerson);

        List<ReadOnlyPerson> expectedFilteredList = new ArrayList<>(model.getFilteredPersonList());

        assertCommandSuccess(command, expectedMessage, expectedAddressBook, expectedFilteredList);
    }

    @Test
    public void edit_multipleValuesTwoFields_success() throws Exception {
        String userInput = "edit 1 " + VALID_NAME_ALICE + " " + PREFIX_EMAIL.getPrefix() + VALID_EMAIL_ALICE + " "
                + PREFIX_ADDRESS.getPrefix() + VALID_ADDRESS_ALICE + " " + PREFIX_EMAIL.getPrefix() + VALID_EMAIL_BOB
                + " " + PREFIX_ADDRESS.getPrefix() + VALID_ADDRESS_BOB;
        Command command = prepareCommand(userInput);
        Person firstPerson = new Person(model.getFilteredPersonList().get(ZERO_BASED_INDEX_FIRST_PERSON));
        Person editedPerson = new PersonBuilder(firstPerson).withName(VALID_NAME_ALICE).withAddress(VALID_ADDRESS_BOB)
                .withEmail(VALID_EMAIL_BOB).build();

        String fieldWithMultipleValues = "Email and Address";
        String expectedMessage = String.format(EditCommand.MESSAGE_MULTIPLE_VALUES_WARNING, fieldWithMultipleValues,
                fieldWithMultipleValues) + String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);

        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        expectedAddressBook.updatePerson(ZERO_BASED_INDEX_FIRST_PERSON, editedPerson);

        List<ReadOnlyPerson> expectedFilteredList = new ArrayList<>(model.getFilteredPersonList());

        assertCommandSuccess(command, expectedMessage, expectedAddressBook, expectedFilteredList);
    }

    @Test
    public void edit_multipleValuesThreeFields_success() throws Exception {
        String userInput = "edit 1 " + VALID_NAME_ALICE + " " + PREFIX_EMAIL.getPrefix() + VALID_EMAIL_ALICE + " "
                + PREFIX_ADDRESS.getPrefix() + VALID_ADDRESS_ALICE + " " + PREFIX_EMAIL.getPrefix() + VALID_EMAIL_BOB
                + " " + PREFIX_ADDRESS.getPrefix() + VALID_ADDRESS_BOB + " " + PREFIX_PHONE.getPrefix()
                + VALID_PHONE_ALICE + " " + PREFIX_PHONE.getPrefix() + VALID_PHONE_BOB;
        Command command = prepareCommand(userInput);
        Person firstPerson = new Person(model.getFilteredPersonList().get(ZERO_BASED_INDEX_FIRST_PERSON));
        Person editedPerson = new PersonBuilder(firstPerson).withName(VALID_NAME_ALICE).withAddress(VALID_ADDRESS_BOB)
                .withEmail(VALID_EMAIL_BOB).withPhone(VALID_PHONE_BOB).build();

        String fieldWithMultipleValues = "Phone, Email and Address";
        String expectedMessage = String.format(EditCommand.MESSAGE_MULTIPLE_VALUES_WARNING, fieldWithMultipleValues,
                fieldWithMultipleValues) + String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);

        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        expectedAddressBook.updatePerson(ZERO_BASED_INDEX_FIRST_PERSON, editedPerson);

        List<ReadOnlyPerson> expectedFilteredList = new ArrayList<>(model.getFilteredPersonList());

        assertCommandSuccess(command, expectedMessage, expectedAddressBook, expectedFilteredList);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person firstPerson = new Person(model.getFilteredPersonList().get(ZERO_BASED_INDEX_FIRST_PERSON));
        String userInput = PersonUtil.getEditCommand(ZERO_BASED_INDEX_SECOND_PERSON, firstPerson);
        Command command = prepareCommand(userInput);
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_invalidPersonIndex_throwsCommandException() {
        int oneBasedOutOfBoundIndex = model.getFilteredPersonList().size() + 1;
        String userInput = EditCommand.COMMAND_WORD + " " + oneBasedOutOfBoundIndex + " Bobby";
        Command command = prepareCommand(userInput);
        assertCommandFailure(command, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    private Command prepareCommand(String userInput) {
        Command command = parser.parseCommand(userInput);
        command.setData(model);
        return command;
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the address book in the model matches {@code expectedAddressBook} <br>
     * - the filtered person list in the model matches {@code expectedFilteredList} <br>
     */
    private void assertCommandSuccess(Command command, String expectedMessage,
            ReadOnlyAddressBook expectedAddressBook,
            List<? extends ReadOnlyPerson> expectedFilteredList) throws CommandException {
        CommandResult result = command.execute();
        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(expectedAddressBook, model.getAddressBook());
        assertEquals(expectedFilteredList, model.getFilteredPersonList());
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book in the model remains unchanged <br>
     * - the filtered person list in the model remains unchanged <br>
     */
    private void assertCommandFailure(Command command, String expectedMessage) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        List<ReadOnlyPerson> expectedFilteredList = new ArrayList<>(model.getFilteredPersonList());
        try {
            command.execute();
            fail("expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedAddressBook, model.getAddressBook());
            assertEquals(expectedFilteredList, model.getFilteredPersonList());
        }
    }
}
