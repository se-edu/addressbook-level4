package seedu.address.logic;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PredicateBuilder;


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
    public void execute_selectIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand(SelectCommand.COMMAND_WORD);
    }

    @Test
    public void execute_select_jumpsToCorrectPerson() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Person> threePersons = helper.generatePersonList(3);

        Model expectedModel = new ModelManager(helper.generateAddressBook(threePersons), new UserPrefs());
        helper.addToModel(model, threePersons);

        assertCommandSuccess(SelectCommand.COMMAND_WORD + " 2",
                String.format(SelectCommand.MESSAGE_SELECT_PERSON_SUCCESS, 2), expectedModel);
        assertEquals(INDEX_SECOND_PERSON, targetedJumpIndex);
        assertEquals(model.getFilteredPersonList().get(1), threePersons.get(1));
    }


    @Test
    public void execute_deleteInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand(DeleteCommand.COMMAND_WORD, expectedMessage);
    }

    @Test
    public void execute_deleteIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand(DeleteCommand.COMMAND_WORD);
    }

    @Test
    public void execute_delete_removesCorrectPerson() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Person> threePersons = helper.generatePersonList(3);

        Model expectedModel = new ModelManager(helper.generateAddressBook(threePersons), new UserPrefs());
        expectedModel.deletePerson(threePersons.get(1));
        helper.addToModel(model, threePersons);

        assertCommandSuccess(DeleteCommand.COMMAND_WORD + " 2",
                String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, threePersons.get(1)), expectedModel);
    }


    @Test
    public void execute_find_invalidArgsFormat() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
        assertParseException(FindCommand.COMMAND_WORD + " ", expectedMessage);
    }

    @Test
    public void execute_find_onlyMatchesFullWordsInNames() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Person pTarget1 = new PersonBuilder().withName("bla bla KEY bla").build();
        Person pTarget2 = new PersonBuilder().withName("bla KEY bla bceofeia").build();
        Person p1 = new PersonBuilder().withName("KE Y").build();
        Person p2 = new PersonBuilder().withName("KEYKEYKEY sduauo").build();

        List<Person> fourPersons = helper.generatePersonList(p1, pTarget1, p2, pTarget2);
        Model expectedModel = new ModelManager(helper.generateAddressBook(fourPersons), new UserPrefs());
        expectedModel.updateFilteredPersonList(new PredicateBuilder().withNamePredicate("KEY").build());
        helper.addToModel(model, fourPersons);
        assertCommandSuccess(FindCommand.COMMAND_WORD + " KEY",
                Command.getMessageForPersonListShownSummary(expectedModel.getFilteredPersonList().size()),
                expectedModel);
    }

    @Test
    public void execute_find_isNotCaseSensitive() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Person p1 = new PersonBuilder().withName("bla bla KEY bla").build();
        Person p2 = new PersonBuilder().withName("bla KEY bla bceofeia").build();
        Person p3 = new PersonBuilder().withName("key key").build();
        Person p4 = new PersonBuilder().withName("KEy sduauo").build();

        List<Person> fourPersons = helper.generatePersonList(p3, p1, p4, p2);
        Model expectedModel = new ModelManager(helper.generateAddressBook(fourPersons), new UserPrefs());
        helper.addToModel(model, fourPersons);

        assertCommandSuccess(FindCommand.COMMAND_WORD + " KEY",
                Command.getMessageForPersonListShownSummary(expectedModel.getFilteredPersonList().size()),
                expectedModel);
    }

    @Test
    public void execute_find_matchesIfAnyKeywordPresent() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Person pTarget1 = new PersonBuilder().withName("bla bla KEY bla").build();
        Person pTarget2 = new PersonBuilder().withName("bla rAnDoM bla bceofeia").build();
        Person pTarget3 = new PersonBuilder().withName("key key").build();
        Person p1 = new PersonBuilder().withName("sduauo").build();

        List<Person> fourPersons = helper.generatePersonList(pTarget1, p1, pTarget2, pTarget3);
        Model expectedModel = new ModelManager(helper.generateAddressBook(fourPersons), new UserPrefs());
        expectedModel.updateFilteredPersonList(new PredicateBuilder().withNamePredicate("key", "rAnDoM").build());
        helper.addToModel(model, fourPersons);

        assertCommandSuccess(FindCommand.COMMAND_WORD + " key rAnDoM",
                Command.getMessageForPersonListShownSummary(expectedModel.getFilteredPersonList().size()),
                expectedModel);
    }

    @Test
    public void execute_verifyHistory_success() throws Exception {
        String validCommand = "clear";
        logic.execute(validCommand);

        String invalidCommandParse = "   adds   Bob   ";
        try {
            logic.execute(invalidCommandParse);
            fail("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }

        String invalidCommandExecute = "delete 1"; // address book is of size 0; index out of bounds
        try {
            logic.execute(invalidCommandExecute);
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, ce.getMessage());
        }

        String expectedMessage = String.format(HistoryCommand.MESSAGE_SUCCESS,
                String.join("\n", expectedCommands));
        assertEquals(expectedMessage, result.feedbackToUser);
    }
}
