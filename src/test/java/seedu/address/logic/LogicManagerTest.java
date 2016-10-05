package seedu.Todo.logic;

import com.google.common.eventbus.Subscribe;

import seedu.todoList.commons.core.EventsCenter;
import seedu.todoList.commons.events.model.TodoListChangedEvent;
import seedu.todoList.commons.events.ui.JumpToListRequestEvent;
import seedu.todoList.commons.events.ui.ShowHelpRequestEvent;
import seedu.todoList.logic.Logic;
import seedu.todoList.logic.LogicManager;
import seedu.todoList.logic.commands.*;
import seedu.todoList.model.Model;
import seedu.todoList.model.ModelManager;
import seedu.todoList.model.ReadOnlyTodoList;
import seedu.todoList.model.TodoList;
import seedu.todoList.model.tag.Tag;
import seedu.todoList.model.tag.UniqueTagList;
import seedu.todoList.model.task.*;
import seedu.todoList.storage.StorageManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.todoList.commons.core.Messages.*;

public class LogicManagerTest {

    /**
     * See https://github.com/junit-team/junit4/wiki/rules#temporaryfolder-rule
     */
    @Rule
    public TemporaryFolder saveFolder = new TemporaryFolder();

    private Model model;
    private Logic logic;

    //These are for checking the correctness of the events raised
    private ReadOnlyTodoList latestSavedTodoList;
    private boolean helpShown;
    private int targetedJumpIndex;

    @Subscribe
    private void handleLocalModelChangedEvent(TodoListChangedEvent abce) {
        latestSavedTodoList = new TodoList(abce.data);
    }

    @Subscribe
    private void handleShowHelpRequestEvent(ShowHelpRequestEvent she) {
        helpShown = true;
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent je) {
        targetedJumpIndex = je.targetIndex;
    }

    @Before
    public void setup() {
        model = new ModelManager();
        String tempTodoListFile = saveFolder.getRoot().getPath() + "TempTodoList.xml";
        String tempPreferencesFile = saveFolder.getRoot().getPath() + "TempPreferences.json";
        logic = new LogicManager(model, new StorageManager(tempTodoListFile, tempPreferencesFile));
        EventsCenter.getInstance().registerHandler(this);

        latestSavedTodoList = new TodoList(model.getTodoList()); // last saved assumed to be up to date before.
        helpShown = false;
        targetedJumpIndex = -1; // non yet
    }

    @After
    public void teardown() {
        EventsCenter.clearSubscribers();
    }

    @Test
    public void execute_invalid() throws Exception {
        String invalidCommand = "       ";
        assertCommandBehavior(invalidCommand,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
    }

    /**
     * Executes the command and confirms that the result message is correct.
     * Both the 'Todo book' and the 'last shown list' are expected to be empty.
     * @see #assertCommandBehavior(String, String, ReadOnlyTodoList, List)
     */
    private void assertCommandBehavior(String inputCommand, String expectedMessage) throws Exception {
        assertCommandBehavior(inputCommand, expectedMessage, new TodoList(), Collections.emptyList());
    }

    /**
     * Executes the command and confirms that the result message is correct and
     * also confirms that the following three parts of the LogicManager object's state are as expected:<br>
     *      - the internal Todo book data are same as those in the {@code expectedTodoList} <br>
     *      - the backing list shown by UI matches the {@code shownList} <br>
     *      - {@code expectedTodoList} was saved to the storage file. <br>
     */
    private void assertCommandBehavior(String inputCommand, String expectedMessage,
                                       ReadOnlyTodoList expectedTodoList,
                                       List<? extends ReadOnlyTask> expectedShownList) throws Exception {

        //Execute the command
        CommandResult result = logic.execute(inputCommand);

        //Confirm the ui display elements should contain the right data
        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(expectedShownList, model.getFilteredtaskList());

        //Confirm the state of data (saved and in-memory) is as expected
        assertEquals(expectedTodoList, model.getTodoList());
        assertEquals(expectedTodoList, latestSavedTodoList);
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
        assertCommandBehavior("exit", ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT);
    }

    @Test
    public void execute_clear() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        model.addtask(helper.generatetask(1));
        model.addtask(helper.generatetask(2));
        model.addtask(helper.generatetask(3));

        assertCommandBehavior("clear", ClearCommand.MESSAGE_SUCCESS, new TodoList(), Collections.emptyList());
    }


    @Test
    public void execute_add_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
        assertCommandBehavior(
                "add wrong args wrong args", expectedMessage);
        assertCommandBehavior(
                "add Valid Name 12345 e/valid@email.butNoPhonePrefix a/valid, Todo", expectedMessage);
        assertCommandBehavior(
                "add Valid Name p/12345 valid@email.butNoPrefix a/valid, Todo", expectedMessage);
        assertCommandBehavior(
                "add Valid Name p/12345 e/valid@email.butNoTodoPrefix valid, Todo", expectedMessage);
    }

    @Test
    public void execute_add_invalidtaskData() throws Exception {
        assertCommandBehavior(
                "add []\\[;] p/12345 e/valid@e.mail a/valid, Todo", Name.MESSAGE_NAME_CONSTRAINTS);
        assertCommandBehavior(
                "add Valid Name p/not_numbers e/valid@e.mail a/valid, Todo", Phone.MESSAGE_PHONE_CONSTRAINTS);
        assertCommandBehavior(
                "add Valid Name p/12345 e/notAnEmail a/valid, Todo", Email.MESSAGE_EMAIL_CONSTRAINTS);
        assertCommandBehavior(
                "add Valid Name p/12345 e/valid@e.mail a/valid, Todo t/invalid_-[.tag", Tag.MESSAGE_TAG_CONSTRAINTS);

    }

    @Test
    public void execute_add_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.adam();
        TodoList expectedAB = new TodoList();
        expectedAB.addtask(toBeAdded);

        // execute command and verify result
        assertCommandBehavior(helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded),
                expectedAB,
                expectedAB.gettaskList());

    }

    @Test
    public void execute_addDuplicate_notAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.adam();
        TodoList expectedAB = new TodoList();
        expectedAB.addtask(toBeAdded);

        // setup starting state
        model.addtask(toBeAdded); // task already in internal Todo book

        // execute command and verify result
        assertCommandBehavior(
                helper.generateAddCommand(toBeAdded),
                AddCommand.MESSAGE_DUPLICATE_task,
                expectedAB,
                expectedAB.gettaskList());

    }


    @Test
    public void execute_list_showsAlltasks() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        TodoList expectedAB = helper.generateTodoList(2);
        List<? extends ReadOnlyTask> expectedList = expectedAB.gettaskList();

        // prepare Todo book state
        helper.addToModel(model, 2);

        assertCommandBehavior("list",
                ListCommand.MESSAGE_SUCCESS,
                expectedAB,
                expectedList);
    }


    /**
     * Confirms the 'invalid argument index number behaviour' for the given command
     * targeting a single task in the shown list, using visible index.
     * @param commandWord to test assuming it targets a single task in the last shown list based on visible index.
     */
    private void assertIncorrectIndexFormatBehaviorForCommand(String commandWord, String expectedMessage) throws Exception {
        assertCommandBehavior(commandWord , expectedMessage); //index missing
        assertCommandBehavior(commandWord + " +1", expectedMessage); //index should be unsigned
        assertCommandBehavior(commandWord + " -1", expectedMessage); //index should be unsigned
        assertCommandBehavior(commandWord + " 0", expectedMessage); //index cannot be 0
        assertCommandBehavior(commandWord + " not_a_number", expectedMessage);
    }

    /**
     * Confirms the 'invalid argument index number behaviour' for the given command
     * targeting a single task in the shown list, using visible index.
     * @param commandWord to test assuming it targets a single task in the last shown list based on visible index.
     */
    private void assertIndexNotFoundBehaviorForCommand(String commandWord) throws Exception {
        String expectedMessage = MESSAGE_INVALID_task_DISPLAYED_INDEX;
        TestDataHelper helper = new TestDataHelper();
        List<Task> taskList = helper.generatetaskList(2);

        // set AB state to 2 tasks
        model.resetData(new TodoList());
        for (Task p : taskList) {
            model.addtask(p);
        }

        assertCommandBehavior(commandWord + " 3", expectedMessage, model.getTodoList(), taskList);
    }

    @Test
    public void execute_selectInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("select", expectedMessage);
    }

    @Test
    public void execute_selectIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("select");
    }

    @Test
    public void execute_select_jumpsToCorrecttask() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threetasks = helper.generatetaskList(3);

        TodoList expectedAB = helper.generateTodoList(threetasks);
        helper.addToModel(model, threetasks);

        assertCommandBehavior("select 2",
                String.format(SelectCommand.MESSAGE_SELECT_task_SUCCESS, 2),
                expectedAB,
                expectedAB.gettaskList());
        assertEquals(1, targetedJumpIndex);
        assertEquals(model.getFilteredtaskList().get(1), threetasks.get(1));
    }


    @Test
    public void execute_deleteInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("delete", expectedMessage);
    }

    @Test
    public void execute_deleteIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("delete");
    }

    @Test
    public void execute_delete_removesCorrecttask() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threetasks = helper.generatetaskList(3);

        TodoList expectedAB = helper.generateTodoList(threetasks);
        expectedAB.removetask(threetasks.get(1));
        helper.addToModel(model, threetasks);

        assertCommandBehavior("delete 2",
                String.format(DeleteCommand.MESSAGE_DELETE_task_SUCCESS, threetasks.get(1)),
                expectedAB,
                expectedAB.gettaskList());
    }


    @Test
    public void execute_find_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
        assertCommandBehavior("find ", expectedMessage);
    }

    @Test
    public void execute_find_onlyMatchesFullWordsInNames() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generatetaskWithName("bla bla KEY bla");
        Task pTarget2 = helper.generatetaskWithName("bla KEY bla bceofeia");
        Task p1 = helper.generatetaskWithName("KE Y");
        Task p2 = helper.generatetaskWithName("KEYKEYKEY sduauo");

        List<Task> fourtasks = helper.generatetaskList(p1, pTarget1, p2, pTarget2);
        TodoList expectedAB = helper.generateTodoList(fourtasks);
        List<Task> expectedList = helper.generatetaskList(pTarget1, pTarget2);
        helper.addToModel(model, fourtasks);

        assertCommandBehavior("find KEY",
                Command.getMessageFortaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    @Test
    public void execute_find_isNotCaseSensitive() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task p1 = helper.generatetaskWithName("bla bla KEY bla");
        Task p2 = helper.generatetaskWithName("bla KEY bla bceofeia");
        Task p3 = helper.generatetaskWithName("key key");
        Task p4 = helper.generatetaskWithName("KEy sduauo");

        List<Task> fourtasks = helper.generatetaskList(p3, p1, p4, p2);
        TodoList expectedAB = helper.generateTodoList(fourtasks);
        List<Task> expectedList = fourtasks;
        helper.addToModel(model, fourtasks);

        assertCommandBehavior("find KEY",
                Command.getMessageFortaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    @Test
    public void execute_find_matchesIfAnyKeywordPresent() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generatetaskWithName("bla bla KEY bla");
        Task pTarget2 = helper.generatetaskWithName("bla rAnDoM bla bceofeia");
        Task pTarget3 = helper.generatetaskWithName("key key");
        Task p1 = helper.generatetaskWithName("sduauo");

        List<Task> fourtasks = helper.generatetaskList(pTarget1, p1, pTarget2, pTarget3);
        TodoList expectedAB = helper.generateTodoList(fourtasks);
        List<Task> expectedList = helper.generatetaskList(pTarget1, pTarget2, pTarget3);
        helper.addToModel(model, fourtasks);

        assertCommandBehavior("find key rAnDoM",
                Command.getMessageFortaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }


    /**
     * A utility class to generate test data.
     */
    class TestDataHelper{

        Task adam() throws Exception {
            Name name = new Name("Adam Brown");
            Phone privatePhone = new Phone("111111");
            Email email = new Email("adam@gmail.com");
            Todo privateTodo = new Todo("111, alpha street");
            Tag tag1 = new Tag("tag1");
            Tag tag2 = new Tag("tag2");
            UniqueTagList tags = new UniqueTagList(tag1, tag2);
            return new Task(name, privatePhone, email, privateTodo, tags);
        }

        /**
         * Generates a valid task using the given seed.
         * Running this function with the same parameter values guarantees the returned task will have the same state.
         * Each unique seed will generate a unique task object.
         *
         * @param seed used to generate the task data field values
         */
        Task generatetask(int seed) throws Exception {
            return new Task(
                    new Name("task " + seed),
                    new Phone("" + Math.abs(seed)),
                    new Email(seed + "@email"),
                    new Todo("House of " + seed),
                    new UniqueTagList(new Tag("tag" + Math.abs(seed)), new Tag("tag" + Math.abs(seed + 1)))
            );
        }

        /** Generates the correct add command based on the task given */
        String generateAddCommand(Task p) {
            StringBuffer cmd = new StringBuffer();

            cmd.append("add ");

            cmd.append(p.getName().toString());
            cmd.append(" p/").append(p.getPhone());
            cmd.append(" e/").append(p.getEmail());
            cmd.append(" a/").append(p.getTodo());

            UniqueTagList tags = p.getTags();
            for(Tag t: tags){
                cmd.append(" t/").append(t.tagName);
            }

            return cmd.toString();
        }

        /**
         * Generates an TodoList with auto-generated tasks.
         */
        TodoList generateTodoList(int numGenerated) throws Exception{
            TodoList TodoList = new TodoList();
            addToTodoList(TodoList, numGenerated);
            return TodoList;
        }

        /**
         * Generates an TodoList based on the list of tasks given.
         */
        TodoList generateTodoList(List<Task> tasks) throws Exception{
            TodoList TodoList = new TodoList();
            addToTodoList(TodoList, tasks);
            return TodoList;
        }

        /**
         * Adds auto-generated task objects to the given TodoList
         * @param TodoList The TodoList to which the tasks will be added
         */
        void addToTodoList(TodoList TodoList, int numGenerated) throws Exception{
            addToTodoList(TodoList, generatetaskList(numGenerated));
        }

        /**
         * Adds the given list of tasks to the given TodoList
         */
        void addToTodoList(TodoList TodoList, List<Task> tasksToAdd) throws Exception{
            for(Task p: tasksToAdd){
                TodoList.addtask(p);
            }
        }

        /**
         * Adds auto-generated task objects to the given model
         * @param model The model to which the tasks will be added
         */
        void addToModel(Model model, int numGenerated) throws Exception{
            addToModel(model, generatetaskList(numGenerated));
        }

        /**
         * Adds the given list of tasks to the given model
         */
        void addToModel(Model model, List<Task> tasksToAdd) throws Exception{
            for(Task p: tasksToAdd){
                model.addtask(p);
            }
        }

        /**
         * Generates a list of tasks based on the flags.
         */
        List<Task> generatetaskList(int numGenerated) throws Exception{
            List<Task> tasks = new ArrayList<>();
            for(int i = 1; i <= numGenerated; i++){
                tasks.add(generatetask(i));
            }
            return tasks;
        }

        List<Task> generatetaskList(Task... tasks) {
            return Arrays.asList(tasks);
        }

        /**
         * Generates a task object with given name. Other fields will have some dummy values.
         */
        Task generatetaskWithName(String name) throws Exception {
            return new Task(
                    new Name(name),
                    new Phone("1"),
                    new Email("1@email"),
                    new Todo("House of 1"),
                    new UniqueTagList(new Tag("tag"))
            );
        }
    }
}
