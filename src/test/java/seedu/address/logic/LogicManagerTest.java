package seedu.address.logic;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import org.junit.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 *
 */
public class LogicManagerTest {

    private Model model = new ModelManager();
    private Logic logic = new LogicManager(model);

    @Test
    public void execute_invalidCommandFormat_throwsParseException() throws Exception {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
        assertHistoryCorrect(invalidCommand);
    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() throws Exception {
        String deleteCommand = "delete 9";
        assertCommandException(deleteCommand, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        assertHistoryCorrect(deleteCommand);
    }

    @Test
    public void execute_validCommand_success() throws Exception {
        String listCommand = ListCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand, ListCommand.MESSAGE_SUCCESS, model);
        assertHistoryCorrect(listCommand);
    }

    /**
     * Executes the command, confirms that no exceptions are thrown and that the result message is correct.
     * Also confirms that {@code expectedModel} is as specified.
     * @see #assertCommandBehavior(Class, String, String, Model)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage, Model expectedModel) {
        assertCommandBehavior(null, inputCommand, expectedMessage, expectedModel);
    }

    /**
     * Executes the command, confirms that a ParseException is thrown and that the result message is correct.
     * @see #assertCommandBehavior(Class, String, String, Model)
     */
    private void assertParseException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     * @see #assertCommandBehavior(Class, String, String, Model)
     */
    private void assertCommandException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, CommandException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     * @see #assertCommandBehavior(Class, String, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<?> expectedException, String expectedMessage) {
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        assertCommandBehavior(expectedException, inputCommand, expectedMessage, expectedModel);
    }

    /**
     * Executes the command, confirms that the result message is correct and that the expected exception is thrown,
     * and also confirms that the following two parts of the LogicManager object's state are as expected:<br>
     *      - the internal model manager data are same as those in the {@code expectedModel} <br>
     *      - {@code expectedModel}'s address book was saved to the storage file.
     */
    private void assertCommandBehavior(Class<?> expectedException, String inputCommand,
                                           String expectedMessage, Model expectedModel) {

        try {
            CommandResult result = logic.execute(inputCommand);
            assertEquals(expectedException, null);
            assertEquals(expectedMessage, result.feedbackToUser);
        } catch (CommandException | ParseException e) {
            assertEquals(expectedException, e.getClass());
            assertEquals(expectedMessage, e.getMessage());
        }

        assertEquals(expectedModel, model);
    }

    @Test
    public void execute_find_isNotCaseSensitive() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Person p1 = helper.generatePersonWithName("bla bla KEY bla");
        Person p2 = helper.generatePersonWithName("bla KEY bla bceofeia");
        Person p3 = helper.generatePersonWithName("key key");
        Person p4 = helper.generatePersonWithName("KEy sduauo");

        List<Person> fourPersons = helper.generatePersonList(p3, p1, p4, p2);
        AddressBook expectedAb = helper.generateAddressBook(fourPersons);
        List<Person> expectedList = fourPersons;
        helper.addToModel(model, fourPersons);

        assertCommandSuccess("find KEY",
                Command.getMessageForPersonListShownSummary(expectedList.size()),
                expectedAb,
                expectedList);
    }

    @Test
    public void execute_find_matchesIfAnyKeywordPresent() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Person pTarget1 = helper.generatePersonWithName("bla bla KEY bla");
        Person pTarget2 = helper.generatePersonWithName("bla rAnDoM bla bceofeia");
        Person pTarget3 = helper.generatePersonWithName("key key");
        Person p1 = helper.generatePersonWithName("sduauo");

        List<Person> fourPersons = helper.generatePersonList(pTarget1, p1, pTarget2, pTarget3);
        AddressBook expectedAb = helper.generateAddressBook(fourPersons);
        List<Person> expectedList = helper.generatePersonList(pTarget1, pTarget2, pTarget3);
        helper.addToModel(model, fourPersons);

        assertCommandSuccess("find key rAnDoM",
                Command.getMessageForPersonListShownSummary(expectedList.size()),
                expectedAb,
                expectedList);
    }


    /**
     * A utility class to generate test data.
     */
    class TestDataHelper {

        /**
         *
         */
        Person adam() throws Exception {
            Name name = new Name("Adam Brown");
            Phone privatePhone = new Phone("111111");
            Email email = new Email("adam@example.com");
            Address privateAddress = new Address("111, alpha street");

            return new Person(name, privatePhone, email, privateAddress,
                    getTagSet("tag1", "longertag2"));
        }

        /**
         * Generates a valid person using the given seed.
         * Running this function with the same parameter values guarantees the returned person will have the same state.
         * Each unique seed will generate a unique Person object.
         *
         * @param seed used to generate the person data field values
         */
        Person generatePerson(int seed) throws Exception {
            return new Person(
                    new Name("Person " + seed),
                    new Phone("" + Math.abs(seed)),
                    new Email(seed + "@email"),
                    new Address("House of " + seed),
                    getTagSet("tag" + Math.abs(seed), "tag" + Math.abs(seed + 1)));
        }

        /** Generates the correct add command based on the person given */
        String generateAddCommand(Person p) {
            StringBuffer cmd = new StringBuffer();

            cmd.append("add ");

            cmd.append(p.getName().toString());
            cmd.append(" e/").append(p.getEmail());
            cmd.append(" p/").append(p.getPhone());
            cmd.append(" a/").append(p.getAddress());

            Set<Tag> tags = p.getTags();
            for (Tag t: tags) {
                cmd.append(" t/").append(t.tagName);
            }

            return cmd.toString();
        }

        /**
         * Generates an AddressBook with auto-generated persons.
         */
        AddressBook generateAddressBook(int numGenerated) throws Exception {
            AddressBook addressBook = new AddressBook();
            addToAddressBook(addressBook, numGenerated);
            return addressBook;
        }

        /**
         * Generates an AddressBook based on the list of Persons given.
         */
        AddressBook generateAddressBook(List<Person> persons) throws Exception {
            AddressBook addressBook = new AddressBook();
            addToAddressBook(addressBook, persons);
            return addressBook;
        }

        /**
         * Adds auto-generated Person objects to the given AddressBook
         * @param addressBook The AddressBook to which the Persons will be added
         */
        void addToAddressBook(AddressBook addressBook, int numGenerated) throws Exception {
            addToAddressBook(addressBook, generatePersonList(numGenerated));
        }

        /**
         * Adds the given list of Persons to the given AddressBook
         */
        void addToAddressBook(AddressBook addressBook, List<Person> personsToAdd) throws Exception {
            for (Person p: personsToAdd) {
                addressBook.addPerson(p);
            }
        }

        /**
         * Adds auto-generated Person objects to the given model
         * @param model The model to which the Persons will be added
         */
        void addToModel(Model model, int numGenerated) throws Exception {
            addToModel(model, generatePersonList(numGenerated));
        }

        /**
         * Adds the given list of Persons to the given model
         */
        void addToModel(Model model, List<Person> personsToAdd) throws Exception {
            for (Person p: personsToAdd) {
                model.addPerson(p);
            }
        }

        /**
         * Generates a list of Persons based on the flags.
         */
        List<Person> generatePersonList(int numGenerated) throws Exception {
            List<Person> persons = new ArrayList<>();
            for (int i = 1; i <= numGenerated; i++) {
                persons.add(generatePerson(i));
            }
            return persons;
        }

        List<Person> generatePersonList(Person... persons) {
            return Arrays.asList(persons);
        }

        /**
         * Generates a Person object with given name. Other fields will have some dummy values.
         */
        Person generatePersonWithName(String name) throws Exception {
            return new Person(
                    new Name(name),
                    new Phone("1"),
                    new Email("1@email"),
                    new Address("House of 1"),
                    getTagSet("tag"));
        }
    }
}
