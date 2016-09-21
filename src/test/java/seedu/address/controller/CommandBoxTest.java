package seedu.address.controller;

import com.google.common.eventbus.Subscribe;
import org.junit.*;
import org.junit.rules.TemporaryFolder;

import seedu.address.events.EventManager;
import seedu.address.events.model.LocalModelChangedEvent;
import seedu.address.commands.*;
import seedu.address.events.ui.ShowHelpEvent;
import seedu.address.model.*;
import seedu.address.model.person.*;
import seedu.address.model.tag.*;
import seedu.address.parser.Parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static seedu.address.commons.Messages.*;
import static org.junit.Assert.*;

public class CommandBoxTest {

    /**
     * See https://github.com/junit-team/junit4/wiki/rules#temporaryfolder-rule
     */
    @Rule
    public TemporaryFolder saveFolder = new TemporaryFolder();

    private ModelManager model;
    private ResultDisplay resultDisplay;
    private CommandBox inputBox;

    // check for correct context in events raised
    private ReadOnlyAddressBook latestSavedAddressBook;
    private boolean helpShown;

    @Subscribe
    private void handleLocalModelChangedEvent(LocalModelChangedEvent lmce) {
        latestSavedAddressBook = new AddressBook(lmce.data);
    }

    @Subscribe
    private void handleShowHelpEvent(ShowHelpEvent she) {
        helpShown = true;
    }

    @Before
    public void setup() {
        model = new ModelManager(null); // ignore config
        resultDisplay = new ResultDisplay();
        inputBox = new CommandBox();
        inputBox.configure(new Parser(), resultDisplay, model);
        EventManager.getInstance().registerHandler(this);

        latestSavedAddressBook = new AddressBook(model.getAddressBook()); // last saved assumed to be up to date before.
        helpShown = false;
    }

    @After
    public void teardown() {
        EventManager.clearSubscribers();
    }

    @Test
    public void execute_invalid() throws Exception {
        String invalidCommand = "       ";
        assertCommandBehavior(invalidCommand,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
    }

    /**
     * Executes the command and confirms that the result message is correct.
     * Both the 'address book' and the 'last shown list' are expected to be empty.
     * @see #assertCommandBehavior(String, String, AddressBook, List)
     */
    private void assertCommandBehavior(String inputCommand, String expectedMessage) throws Exception {
        assertCommandBehavior(inputCommand, expectedMessage, new AddressBook(), Collections.emptyList());
    }

    /**
     * Executes the command and confirms that the result message is correct and
     * also confirms that the following three parts of the Logic object's state are as expected:<br>
     *      - the internal address book data are same as those in the {@code expectedAddressBook} <br>
     *      - the backing list shown by UI matches the {@code shownList} <br>
     *      - {@code expectedAddressBook} was saved to the storage file. <br>
     */
    private void assertCommandBehavior(String inputCommand,
                                       String expectedMessage,
                                       AddressBook expectedAddressBook,
                                       List<? extends ReadOnlyPerson> shownList) throws Exception {

        //Execute the command
        inputBox.processCommandInput(inputCommand);

        //Confirm the ui display elements should contain the right data
        assertEquals(expectedMessage, resultDisplay.getDisplayed());
        assertEquals(shownList, model.getFilteredPersonList());

        //Confirm the state of data (saved and in-memory) is as expected
        assertEquals(expectedAddressBook, model.getAddressBook());
        assertEquals(expectedAddressBook, latestSavedAddressBook);
    }


    @Test
    public void execute_unknownCommandWord() throws Exception {
        String unknownCommand = "uicfhmowqewca";
        assertCommandBehavior(unknownCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_help() throws Exception {
        assertCommandBehavior("help", HelpCommand.SHOWING_HELP_MESSAGE);
        assertTrue(helpShown);
    }

    @Test
    public void execute_exit() throws Exception {
        assertCommandBehavior("exit", ExitCommand.MESSAGE_EXIT_ACKNOWEDGEMENT);
    }

    @Test
    public void execute_clear() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        model.addPerson(helper.generatePerson(1, true));
        model.addPerson(helper.generatePerson(2, true));
        model.addPerson(helper.generatePerson(3, true));

        assertCommandBehavior("clear", ClearCommand.MESSAGE_SUCCESS, new AddressBook(), Collections.emptyList());
    }

    /**
     * A utility class to generate test data.
     */
    class TestDataHelper{

        Person adam() throws Exception {
            Name name = new Name("Adam Brown");
            Phone privatePhone = new Phone("111111", true);
            Email email = new Email("adam@gmail.com", false);
            Address privateAddress = new Address("111, alpha street", true);
            Tag tag1 = new Tag("tag1");
            Tag tag2 = new Tag("tag2");
            UniqueTagList tags = new UniqueTagList(tag1, tag2);
            return new Person(name, privatePhone, email, privateAddress, tags);
        }

        /**
         * Generates a valid person using the given seed.
         * Running this function with the same parameter values guarantees the returned person will have the same state.
         * Each unique seed will generate a unique Person object.
         *
         * @param seed used to generate the person data field values
         * @param isAllFieldsPrivate determines if private-able fields (phone, email, address) will be private
         */
        Person generatePerson(int seed, boolean isAllFieldsPrivate) throws Exception {
            return new Person(
                    new Name("Person " + seed),
                    new Phone("" + Math.abs(seed), isAllFieldsPrivate),
                    new Email(seed + "@email", isAllFieldsPrivate),
                    new Address("House of " + seed, isAllFieldsPrivate),
                    new UniqueTagList(new Tag("tag" + Math.abs(seed)), new Tag("tag" + Math.abs(seed + 1)))
            );
        }

        /** Generates the correct add command based on the person given */
        String generateAddCommand(Person p) {
            StringBuffer cmd = new StringBuffer(" ");

            cmd.append("add");

            cmd.append(p.getName().toString());
            cmd.append((p.getPhone().isPrivate() ? "pp/" : "p/") + p.getPhone());
            cmd.append((p.getEmail().isPrivate() ? "pe/" : "e/") + p.getEmail());
            cmd.append((p.getAddress().isPrivate() ? "pa/" : "a/") + p.getAddress());

            UniqueTagList tags = p.getTags();
            for(Tag t: tags){
                cmd.append("t/" + t.tagName);
            }

            return cmd.toString();
        }

        /**
         * Generates an AddressBook with auto-generated persons.
         * @param isPrivateStatuses flags to indicate if all contact details of respective persons should be set to
         *                          private.
         */
        AddressBook generateAddressBook(Boolean... isPrivateStatuses) throws Exception{
            AddressBook addressBook = new AddressBook();
            addToAddressBook(addressBook, isPrivateStatuses);
            return addressBook;
        }

        /**
         * Generates an AddressBook based on the list of Persons given.
         */
        AddressBook generateAddressBook(List<Person> persons) throws Exception{
            AddressBook addressBook = new AddressBook();
            addToAddressBook(addressBook, persons);
            return addressBook;
        }

        /**
         * Adds auto-generated Person objects to the given AddressBook
         * @param addressBook The AddressBook to which the Persons will be added
         * @param isPrivateStatuses flags to indicate if all contact details of generated persons should be set to
         *                          private.
         */
        void addToAddressBook(AddressBook addressBook, Boolean... isPrivateStatuses) throws Exception{
            addToAddressBook(addressBook, generatePersonList(isPrivateStatuses));
        }

        /**
         * Adds the given list of Persons to the given AddressBook
         */
        void addToAddressBook(AddressBook addressBook, List<Person> personsToAdd) throws Exception{
            for(Person p: personsToAdd){
                addressBook.addPerson(p);
            }
        }

        /**
         * Creates a list of Persons based on the give Person objects.
         */
        List<Person> generatePersonList(Person... persons) throws Exception{
            List<Person> personList = new ArrayList<>();
            for(Person p: persons){
                personList.add(p);
            }
            return personList;
        }

        /**
         * Generates a list of Persons based on the flags.
         * @param isPrivateStatuses flags to indicate if all contact details of respective persons should be set to
         *                          private.
         */
        List<Person> generatePersonList(Boolean... isPrivateStatuses) throws Exception{
            List<Person> persons = new ArrayList<>();
            int i = 1;
            for(Boolean p: isPrivateStatuses){
                persons.add(generatePerson(i++, p));
            }
            return persons;
        }

        /**
         * Generates a Person object with given name. Other fields will have some dummy values.
         */
        Person generatePersonWithName(String name) throws Exception {
            return new Person(
                    new Name(name),
                    new Phone("1", false),
                    new Email("1@email", false),
                    new Address("House of 1", false),
                    new UniqueTagList(new Tag("tag"))
            );
        }
    }
}
