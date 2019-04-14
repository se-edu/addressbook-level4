package seedu.address.logic;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINEDATE_DESC_ONE;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINETIME_DESC_ONE;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PRICE_DESC_PRAWNMEE;
import static seedu.address.logic.commands.CommandTestUtil.PURCHASENAME_DESC_PRAWNMEE;
import static seedu.address.logic.commands.CommandTestUtil.TASKNAME_DESC_ONE;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPurchases.PRAWNMEE;
import static seedu.address.testutil.TypicalTasks.TASKONE;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddPurchaseCommand;
import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyContactList;
import seedu.address.model.ReadOnlyExpenditureList;
import seedu.address.model.ReadOnlyTaskList;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.purchase.Purchase;
import seedu.address.model.task.Task;
import seedu.address.storage.JsonContactListStorage;
import seedu.address.storage.JsonExpenditureListStorage;
import seedu.address.storage.JsonTaskListStorage;
import seedu.address.storage.JsonTickedTaskListStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.JsonWorkoutBookStorage;
import seedu.address.storage.StorageManager;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PurchaseBuilder;
import seedu.address.testutil.TaskBuilder;


public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    private Model model = new ModelManager();
    private Logic logic;

    @Before
    public void setUp() throws Exception {
        JsonContactListStorage contactListStorage = new JsonContactListStorage(temporaryFolder.newFile().toPath());
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.newFile().toPath());
        JsonTaskListStorage taskListStorage = new JsonTaskListStorage(temporaryFolder.newFile().toPath());
        JsonTickedTaskListStorage tickedTaskListStorage =
                new JsonTickedTaskListStorage(temporaryFolder.newFile().toPath());
        JsonExpenditureListStorage expenditureListStorage =
                new JsonExpenditureListStorage(temporaryFolder.newFile().toPath());
        JsonWorkoutBookStorage workoutBookStorage = new JsonWorkoutBookStorage(temporaryFolder.newFile().toPath());
        StorageManager storage = new StorageManager(contactListStorage, userPrefsStorage,
                taskListStorage, expenditureListStorage, workoutBookStorage, tickedTaskListStorage);
        logic = new LogicManager(model, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
        assertHistoryCorrect(invalidCommand);
    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteCommand = "delete 9";
        String deleteTaskCommand = "deletetask 9";
        assertCommandException(deleteCommand, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        assertHistoryCorrect(deleteCommand);
        assertCommandException(deleteTaskCommand, MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

    }

    @Test
    public void execute_validCommand_success() {
        String listCommand = ListCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand, ListCommand.MESSAGE_SUCCESS, model);
        assertHistoryCorrect(listCommand);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() throws Exception {
        // Setup LogicManager with JsonContactListIoExceptionThrowingStub
        JsonContactListStorage contactListStorage =
                new JsonContactListIoExceptionThrowingStub(temporaryFolder.newFile().toPath());
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.newFile().toPath());
        JsonTaskListStorage taskListStorage =
                new JsonTaskListIoExceptionThrowingStub(temporaryFolder.newFile().toPath()); //TODO
        JsonTickedTaskListStorage tickedTaskListStorage =
                new JsonTickedTaskListStorage(temporaryFolder.newFile().toPath());
        JsonExpenditureListStorage expenditureListStorage =
                new JsonExpenditureListIoExceptionThrowingStub(temporaryFolder.newFile().toPath());
        JsonWorkoutBookStorage workoutBookStorage = new JsonWorkoutBookStorage(temporaryFolder.newFile().toPath());

        StorageManager storage = new StorageManager(contactListStorage, userPrefsStorage,
                taskListStorage, expenditureListStorage, workoutBookStorage, tickedTaskListStorage);
        logic = new LogicManager(model, storage);

        // Execute add command
        String addCommand = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY;

        Person expectedPerson = new PersonBuilder(AMY).withTags().build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addPerson(expectedPerson);
        expectedModel.commitContactList();
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertCommandBehavior(CommandException.class, addCommand, expectedMessage, expectedModel);


        String addTaskCommand = AddTaskCommand.COMMAND_WORD + TASKNAME_DESC_ONE + DEADLINETIME_DESC_ONE
                + DEADLINEDATE_DESC_ONE;
        Task expectedTask = new TaskBuilder(TASKONE).withTags().build();
        expectedModel.addTask(expectedTask);
        expectedModel.commitTaskList();
        assertCommandBehavior(CommandException.class, addTaskCommand, expectedMessage, expectedModel);
        assertHistoryCorrect(addTaskCommand, addCommand);


    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        logic.getFilteredPersonList().remove(0);
    }

    @Test
    public void execute_exp_storageThrowsIoException_throwsCommandException() throws Exception {
        // Setup LogicManager with JsonContactListIoExceptionThrowingStub
        JsonContactListStorage contactListStorage =
                new JsonContactListIoExceptionThrowingStub(temporaryFolder.newFile().toPath());
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.newFile().toPath());
        JsonTaskListStorage taskListStorage =
                new JsonTaskListStorage(temporaryFolder.newFile().toPath()); //TODO
        JsonTickedTaskListStorage tickedTaskListStorage =
                new JsonTickedTaskListStorage(temporaryFolder.newFile().toPath());
        JsonExpenditureListStorage expenditureListStorage =
                new JsonExpenditureListIoExceptionThrowingStub(temporaryFolder.newFile().toPath());
        JsonWorkoutBookStorage workoutBookStorage = new JsonWorkoutBookStorage(temporaryFolder.newFile().toPath());

        StorageManager storage = new StorageManager(contactListStorage, userPrefsStorage,
                taskListStorage, expenditureListStorage, workoutBookStorage, tickedTaskListStorage);
        logic = new LogicManager(model, storage);

        // Execute addpurchase command
        String addpurchaseCommand = AddPurchaseCommand.COMMAND_WORD + PURCHASENAME_DESC_PRAWNMEE + PRICE_DESC_PRAWNMEE;
        Purchase expectedPurchase = new PurchaseBuilder(PRAWNMEE).withTags().build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addPurchase(expectedPurchase);
        expectedModel.commitExpenditureList();
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertCommandBehavior(CommandException.class, addpurchaseCommand, expectedMessage, expectedModel);
        assertHistoryCorrect(addpurchaseCommand);
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
        Model expectedModel = new ModelManager(model.getContactList(), new UserPrefs(),
                model.getTaskList(), model.getExpenditureList(), model.getWorkoutList());
        assertCommandBehavior(expectedException, inputCommand, expectedMessage, expectedModel);
    }

    /**
     * Executes the command, confirms that the result message is correct and that the expected exception is thrown,
     * and also confirms that the following two parts of the LogicManager object's state are as expected:<br>
     *      - the internal model manager data are same as those in the {@code expectedModel} <br>
     *      - {@code expectedModel}'s contact list was saved to the storage file.
     */
    private void assertCommandBehavior(Class<?> expectedException, String inputCommand,
                                           String expectedMessage, Model expectedModel) {

        try {
            CommandResult result = logic.execute(inputCommand);
            assertEquals(expectedException, null);
            assertEquals(expectedMessage, result.getFeedbackToUser());
        } catch (CommandException | ParseException e) {
            assertEquals(expectedException, e.getClass());
            assertEquals(expectedMessage, e.getMessage());
        }

        assertEquals(expectedModel, model);
    }

    /**
     * Asserts that the result display shows all the {@code expectedCommands} upon the execution of
     * {@code HistoryCommand}.
     */
    private void assertHistoryCorrect(String... expectedCommands) {
        try {
            CommandResult result = logic.execute(HistoryCommand.COMMAND_WORD);
            String expectedMessage = String.format(
                    HistoryCommand.MESSAGE_SUCCESS, String.join("\n", expectedCommands));
            assertEquals(expectedMessage, result.getFeedbackToUser());
        } catch (ParseException | CommandException e) {
            throw new AssertionError("Parsing and execution of HistoryCommand.COMMAND_WORD should succeed.", e);
        }
    }

    /**
     * A stub class to throw an {@code IOException} when the save method for contact list is called.
     */
    private static class JsonContactListIoExceptionThrowingStub extends JsonContactListStorage {
        private JsonContactListIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveContactList(ReadOnlyContactList contactList, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }

    /**
     * A stub class to throw an {@code IOException} when the save method for expenditure list is called.
     */
    private static class JsonExpenditureListIoExceptionThrowingStub extends JsonExpenditureListStorage {
        private JsonExpenditureListIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveExpenditureList(ReadOnlyExpenditureList expenditureList, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }


    /**
     * A stub class to throw an {@code IOException} when the save method is called.
     */
    private static class JsonTaskListIoExceptionThrowingStub extends JsonTaskListStorage {
        private JsonTaskListIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveTaskList(ReadOnlyTaskList taskList, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
}
