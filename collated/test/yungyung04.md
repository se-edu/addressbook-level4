# yungyung04
###### \java\seedu\address\logic\commands\AddPersonalTaskCommandTest.java
``` java
public class AddPersonalTaskCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullTask_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddPersonalTaskCommand(null);
    }

    @Test
    public void execute_taskAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonalTaskAdded modelStub = new ModelStubAcceptingPersonalTaskAdded();
        PersonalTask validTask = new TaskBuilder().buildPersonalTask();

        CommandResult commandResult = getAddPersonalTaskCommandForTask(validTask, modelStub).execute();

        assertEquals(String.format(AddPersonalTaskCommand.MESSAGE_SUCCESS, validTask), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validTask), modelStub.tasksAdded);
    }

    @Test
    public void execute_clashingTask_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingTimingClashException();
        PersonalTask validTask = new TaskBuilder().buildPersonalTask();

        thrown.expect(CommandException.class);
        thrown.expectMessage(MESSAGE_TASK_TIMING_CLASHES);

        getAddPersonalTaskCommandForTask(validTask, modelStub).execute();
    }

    @Test
    public void equals() {
        PersonalTask firstPersonalTask = new TaskBuilder().withDateTime(VALID_DATE_TIME_AMY).buildPersonalTask();
        PersonalTask secondPersonalTask = new TaskBuilder().withDateTime(VALID_DATE_TIME_BOB).buildPersonalTask();

        AddPersonalTaskCommand addFirstTask = new AddPersonalTaskCommand(firstPersonalTask);
        AddPersonalTaskCommand addFirstTaskCopy = new AddPersonalTaskCommand(firstPersonalTask);
        AddPersonalTaskCommand addSecondTask = new AddPersonalTaskCommand(secondPersonalTask);

        LocalDateTime tuitionDateTime = LocalDateTime.parse(VALID_DATE_TIME_AMY, FORMATTER);
        AddTuitionTaskCommand addTuitionTask = new AddTuitionTaskCommand(
                INDEX_FIRST_PERSON, tuitionDateTime, VALID_DURATION_AMY, VALID_TASK_DESC_AMY);

        // same object -> returns true
        assertTrue(addFirstTask.equals(addFirstTask));

        // same values -> returns true
        assertTrue(addFirstTask.equals(addFirstTaskCopy));

        // different types -> returns false
        assertFalse(addFirstTask.equals(1));

        // null -> returns false
        assertFalse(addFirstTask.equals(null));

        // different task type -> returns false
        assertFalse(addFirstTask.equals(addTuitionTask));

        // different detail -> returns false
        assertFalse(addFirstTask.equals(addSecondTask));
    }

    /**
     * Generates a new AddPersonalTaskCommand with the details of the given personal task.
     */
    private AddPersonalTaskCommand getAddPersonalTaskCommandForTask(PersonalTask task, Model model) {
        AddPersonalTaskCommand command = new AddPersonalTaskCommand(task);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * A Model stub that always throw a TimingClashException when trying to add a task.
     */
    private class ModelStubThrowingTimingClashException extends ModelStub {
        @Override
        public void addTask(Task task) throws TimingClashException {
            throw new TimingClashException(MESSAGE_TASK_TIMING_CLASHES);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    /**
     * A Model stub that always accept the task being added.
     */
    private class ModelStubAcceptingPersonalTaskAdded extends ModelStub {
        final ArrayList<Task> tasksAdded = new ArrayList<>();

        @Override
        public void addTask(Task task) throws TimingClashException {
            requireNonNull(task);
            tasksAdded.add(task);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
```
###### \java\seedu\address\logic\commands\AddTuitionTaskCommandTest.java
``` java
public class AddTuitionTaskCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook2(), new UserPrefs());
    private LocalDateTime taskDateTimeAmy = LocalDateTime.parse(VALID_DATE_TIME_AMY, FORMATTER);

    @Test
    public void constructor_nullTaskDetail_throwsNullPointerException() {
        //one of the other 3 task details is null.
        thrown.expect(NullPointerException.class);
        new AddTuitionTaskCommand(INDEX_FIRST_PERSON, taskDateTimeAmy, VALID_DURATION_AMY, null);
    }

    @Test
    public void execute_taskAcceptedByModel_addSuccessful() throws Exception {
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        AddTuitionTaskCommand addTuitionAmy = getAddTuitionTaskCommandForTask(
                INDEX_THIRD_PERSON, taskDateTimeAmy, VALID_DURATION_AMY, VALID_TASK_DESC_AMY);

        String expectedMessage = String.format(AddTuitionTaskCommand.MESSAGE_SUCCESS, TASK_AMY);
        expectedModel.addTask(TASK_AMY);

        assertCommandSuccess(addTuitionAmy, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        AddTuitionTaskCommand command = getAddTuitionTaskCommandForTask(outOfBoundIndex, taskDateTimeAmy,
                VALID_DURATION_AMY, VALID_TASK_DESC_AMY);
        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_clashingTask_throwsCommandException() throws Exception {
        thrown.expect(CommandException.class);
        thrown.expectMessage(MESSAGE_TASK_TIMING_CLASHES);

        getAddTuitionTaskCommandForTask(INDEX_THIRD_PERSON, taskDateTimeAmy, VALID_DURATION_AMY,
                VALID_TASK_DESC_AMY).execute();

        getAddTuitionTaskCommandForTask(INDEX_FIRST_PERSON, taskDateTimeAmy, VALID_DURATION_AMY,
                VALID_TASK_DESC_AMY).execute();
    }


    @Test
    public void equals() {
        LocalDateTime taskDateTimeBob = LocalDateTime.parse(VALID_DATE_TIME_BOB, FORMATTER);

        AddTuitionTaskCommand addTuitionAmy = getAddTuitionTaskCommandForTask(
                INDEX_THIRD_PERSON, taskDateTimeAmy, VALID_DURATION_AMY, VALID_TASK_DESC_AMY);
        AddTuitionTaskCommand addTuitionAmyCopy = getAddTuitionTaskCommandForTask(
                INDEX_THIRD_PERSON, taskDateTimeAmy, VALID_DURATION_AMY, VALID_TASK_DESC_AMY);
        AddTuitionTaskCommand addTuitionBob = new AddTuitionTaskCommand(
                INDEX_SECOND_PERSON, taskDateTimeBob, VALID_DURATION_BOB, VALID_TASK_DESC_BOB);

        // an AddPersonalTaskCommand object with same task details as addTuitionAmy
        AddPersonalTaskCommand addPersonalTask =
                new AddPersonalTaskCommand(new TaskBuilder(TASK_AMY).buildPersonalTask());

        // same value -> returns true
        assertTrue(addTuitionAmy.equals(addTuitionAmyCopy));

        // same object -> returns true
        assertTrue(addTuitionAmy.equals(addTuitionAmy));

        // different types -> returns false
        assertFalse(addTuitionAmy.equals(1));

        // null -> returns false
        assertFalse(addTuitionAmy.equals(null));

        // different task type -> returns false
        assertFalse(addTuitionAmy.equals(addPersonalTask));

        // different detail -> returns false
        assertFalse(addTuitionAmy.equals(addTuitionBob));
    }

    /**
     * Generates a new AddTuitionTaskCommand with the details of the given tuition task.
     */
    private AddTuitionTaskCommand getAddTuitionTaskCommandForTask(Index tuteeIndex, LocalDateTime taskDateTime,
                                                                  String duration, String description) {
        AddTuitionTaskCommand command = new AddTuitionTaskCommand(tuteeIndex, taskDateTime, duration, description);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
```
###### \java\seedu\address\logic\commands\DeleteTaskCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteTaskCommand}.
 */
public class DeleteTaskCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook1(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        Task taskToDelete = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        DeleteTaskCommand deleteTaskCommand = prepareCommand(INDEX_FIRST_TASK);

        String expectedMessage = String.format(DeleteTaskCommand.MESSAGE_SUCCESS, taskToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteTask(taskToDelete);

        assertCommandSuccess(deleteTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        DeleteTaskCommand deleteTaskCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteTaskCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showTaskAtIndex(model, INDEX_FIRST_TASK);

        Task taskToDelete = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        DeleteTaskCommand deleteTaskCommand = prepareCommand(INDEX_FIRST_TASK);

        String expectedMessage = String.format(DeleteTaskCommand.MESSAGE_SUCCESS, taskToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteTask(taskToDelete);
        showNoTask(expectedModel);

        assertCommandSuccess(deleteTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showTaskAtIndex(model, INDEX_FIRST_TASK);

        Index outOfBoundIndex = INDEX_SECOND_TASK;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getTaskList().size());

        DeleteTaskCommand deleteTaskCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteTaskCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Task taskToDelete = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        DeleteTaskCommand deleteTaskCommand = prepareCommand(INDEX_FIRST_TASK);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        // delete -> first task deleted
        deleteTaskCommand.execute();
        undoRedoStack.push(deleteTaskCommand);

        // undo -> reverts addressbook back to previous state and filtered task list to show all tasks
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first task deleted again
        expectedModel.deleteTask(taskToDelete);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        DeleteTaskCommand deleteTaskCommand = prepareCommand(outOfBoundIndex);

        // execution failed -> deleteTaskCommand not pushed into undoRedoStack
        assertCommandFailure(deleteTaskCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Deletes a {@code task} from a filtered list.
     * 2. Undo the deletion.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously deleted task in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the deletion. This ensures {@code RedoCommand} deletes the task object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameTaskDeleted() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        DeleteTaskCommand deleteTaskCommand = prepareCommand(INDEX_FIRST_TASK);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        showTaskAtIndex(model, INDEX_SECOND_TASK);
        Task taskToDelete = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        // delete -> deletes second task in unfiltered task list / first task in filtered task list
        deleteTaskCommand.execute();
        undoRedoStack.push(deleteTaskCommand);

        // undo -> reverts addressbook back to previous state and filtered task list to show all tasks
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.deleteTask(taskToDelete);
        assertNotEquals(taskToDelete, model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased()));
        // redo -> deletes same second task in unfiltered task list
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() throws Exception {
        DeleteTaskCommand deleteFirstCommand = prepareCommand(INDEX_FIRST_TASK);
        DeleteTaskCommand deleteSecondCommand = prepareCommand(INDEX_SECOND_TASK);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteTaskCommand deleteFirstCommandCopy = prepareCommand(INDEX_FIRST_TASK);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // one command preprocessed when previously equal -> returns false
        deleteFirstCommandCopy.preprocessUndoableCommand();
        assertFalse(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Returns a {@code DeleteTaskCommand} with the parameter {@code index}.
     */
    private DeleteTaskCommand prepareCommand(Index index) {
        DeleteTaskCommand deleteTaskCommand = new DeleteTaskCommand(index);
        deleteTaskCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteTaskCommand;
    }

    /**
     * Updates {@code model}'s filtered tasks list to show no one.
     */
    private void showNoTask(Model model) {
        model.updateFilteredTaskList(t -> false);

        assertTrue(model.getFilteredTaskList().isEmpty());
    }
}
```
###### \java\seedu\address\logic\commands\FindPersonCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code FindPersonCommand}.
 */
public class FindPersonCommandTest {
    private static final int INDEX_FIRST_ELEMENT = 0;
    private static final int INDEX_SECOND_ELEMENT = 1;

    private Model model = new ModelManager(getTypicalAddressBook2(), new UserPrefs());

    private final String[] firstNameKeywords = {VALID_NAME_BOB.split("\\s+")[INDEX_FIRST_ELEMENT],
            VALID_NAME_AMY.split("\\s+")[INDEX_SECOND_ELEMENT]};
    private final String[] secondNameKeywords = {VALID_NAME_BOB.split("\\s+")[INDEX_FIRST_ELEMENT]};

    private final FindPersonCommand findFirstName = new FindPersonCommand(CATEGORY_NAME, firstNameKeywords);
    private final FindPersonCommand findSecondName = new FindPersonCommand(CATEGORY_NAME, secondNameKeywords);

    @Test
    public void equals() {
        // same object -> returns true
        assertTrue(findFirstName.equals(findFirstName));

        // same values -> returns true
        FindPersonCommand findFirstCommandCopy = new FindPersonCommand(CATEGORY_NAME, firstNameKeywords);
        assertTrue(findFirstName.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstName.equals(1));

        // null -> returns false
        assertFalse(findFirstName.equals(null));

        // different person -> returns false
        assertFalse(findFirstName.equals(findSecondName));
    }

    @Test
    public void execute_findName_foundSuccessfully() {
        //multiple keywords
        findFirstName.setData(model, new CommandHistory(), new UndoRedoStack());
        String expectedMessage = String.format(FindPersonCommand.MESSAGE_SUCCESS + "\n"
                + MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        assertCommandSuccess(findFirstName, expectedMessage, Arrays.asList(AMYTUTEE, BOBTUTEE));

        //single keyword
        findSecondName.setData(model, new CommandHistory(), new UndoRedoStack());
        expectedMessage = String.format(FindPersonCommand.MESSAGE_SUCCESS + "\n" + MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        assertCommandSuccess(findSecondName, expectedMessage, Arrays.asList(BOBTUTEE));
    }

    @Test
    public void execute_findEducatonLevel_foundSuccessfully() {
        String[] educationLevelKeywords = {VALID_EDUCATION_LEVEL_AMY};
        FindPersonCommand findEducationLevel = new FindPersonCommand(CATEGORY_EDUCATION_LEVEL, educationLevelKeywords);
        findEducationLevel.setData(model, new CommandHistory(), new UndoRedoStack());
        String expectedMessage = String.format(FindPersonCommand.MESSAGE_SUCCESS + "\n"
                + MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        //Alice and Amy have the same education level
        assertCommandSuccess(findEducationLevel, expectedMessage, Arrays.asList(ALICETUTEE, AMYTUTEE));
    }

    @Test
    public void execute_findGrade_foundSuccessfully() {
        String[] gradeKeywords = {VALID_GRADE_AMY, VALID_GRADE_BOB};
        FindPersonCommand findGrade = new FindPersonCommand(CATEGORY_GRADE, gradeKeywords);
        findGrade.setData(model, new CommandHistory(), new UndoRedoStack());
        String expectedMessage = String.format(FindPersonCommand.MESSAGE_SUCCESS + "\n"
                + MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        assertCommandSuccess(findGrade, expectedMessage, Arrays.asList(AMYTUTEE, BOBTUTEE));
    }

    @Test
    public void execute_findSchool_foundSuccessfully() {
        String[] schoolKeywords = {VALID_SCHOOL_AMY.split("\\s+")[INDEX_FIRST_ELEMENT]};
        FindPersonCommand findSchool = new FindPersonCommand(CATEGORY_SCHOOL, schoolKeywords);
        findSchool.setData(model, new CommandHistory(), new UndoRedoStack());
        String expectedMessage = String.format(FindPersonCommand.MESSAGE_SUCCESS + "\n"
                + MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        assertCommandSuccess(findSchool, expectedMessage, Arrays.asList(AMYTUTEE));
    }

    @Test
    public void execute_findSubject_foundSuccessfully() {
        String[] subjectKeywords = {VALID_SUBJECT_BOB};
        FindPersonCommand findSubject = new FindPersonCommand(CATEGORY_SUBJECT, subjectKeywords);
        findSubject.setData(model, new CommandHistory(), new UndoRedoStack());
        String expectedMessage = String.format(FindPersonCommand.MESSAGE_SUCCESS + "\n"
                + MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        //Alice and Bob learn the same subject.
        assertCommandSuccess(findSubject, expectedMessage, Arrays.asList(ALICETUTEE, BOBTUTEE));
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<Person>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FindPersonCommand command, String expectedMessage, List<Person> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
```
###### \java\seedu\address\logic\commands\ListTuteeCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for ListTuteeCommand.
 */
public class ListTuteeCommandTest {

    private Model model;
    private Model expectedModel;
    private ListTuteeCommand listTuteeCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook2(), new UserPrefs());
        expectedModel = setExpectedModel(model);

        listTuteeCommand = new ListTuteeCommand();
        listTuteeCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_tuteeListIsNotFiltered_showsSameList() {
        assertCommandSuccess(listTuteeCommand, model, ListTuteeCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_tuteeListIsFiltered_showsEverything() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandSuccess(listTuteeCommand, model, ListTuteeCommand.MESSAGE_SUCCESS, expectedModel);
    }

    /**
     * Returns a model that has been filtered to show only tutees
     */
    private ModelManager setExpectedModel(Model model) {
        ModelManager modelManager = new ModelManager(model.getAddressBook(), new UserPrefs());
        modelManager.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_TUTEES);
        return modelManager;
    }

}
```
###### \java\seedu\address\logic\commands\SortPersonCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code SortPersonCommand}.
 */
public class SortPersonCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook2(), new UserPrefs());

    private final SortPersonCommand sortName = new SortPersonCommand(CATEGORY_NAME);

    @Test
    public void equals() {
        // same object -> returns true
        assertTrue(sortName.equals(sortName));

        // same values -> returns true
        SortPersonCommand sortNameCopy = new SortPersonCommand(CATEGORY_NAME);
        assertTrue(sortName.equals(sortNameCopy));

        // different types -> returns false
        assertFalse(sortName.equals(1));

        // null -> returns false
        assertFalse(sortName.equals(null));

        // different category -> returns false
        SortPersonCommand sortGrade = new SortPersonCommand(CATEGORY_GRADE);
        assertFalse(sortName.equals(sortGrade));
    }

    @Test
    public void execute_sortName_sortedSuccessfully() {
        sortName.setData(model, new CommandHistory(), new UndoRedoStack());
        String expectedMessage = String.format(SortPersonCommand.MESSAGE_SUCCESS);
        assertCommandSuccess(sortName, expectedMessage,
                Arrays.asList(ALICETUTEE, AMYTUTEE, BOBTUTEE, DANIEL));
    }

    @Test
    public void execute_sortEducatonLevel_sortedSuccessfully() {
        SortPersonCommand sortEducationLevel = new SortPersonCommand(CATEGORY_EDUCATION_LEVEL);
        sortEducationLevel.setData(model, new CommandHistory(), new UndoRedoStack());
        String expectedMessage = String.format(SortPersonCommand.MESSAGE_SUCCESS);
        assertCommandSuccess(sortEducationLevel, expectedMessage,
                Arrays.asList(BOBTUTEE, ALICETUTEE, AMYTUTEE, DANIEL));
    }

    @Test
    public void execute_sortGrade_sortedSuccessfully() {
        SortPersonCommand sortGrade = new SortPersonCommand(CATEGORY_GRADE);
        sortGrade.setData(model, new CommandHistory(), new UndoRedoStack());
        String expectedMessage = String.format(SortPersonCommand.MESSAGE_SUCCESS);
        assertCommandSuccess(sortGrade, expectedMessage,
                Arrays.asList(BOBTUTEE, AMYTUTEE, ALICETUTEE, DANIEL));
    }

    @Test
    public void execute_sortSchool_sortedSuccessfully() {
        SortPersonCommand sortSchool = new SortPersonCommand(CATEGORY_SCHOOL);
        sortSchool.setData(model, new CommandHistory(), new UndoRedoStack());
        String expectedMessage = String.format(SortPersonCommand.MESSAGE_SUCCESS);
        assertCommandSuccess(sortSchool, expectedMessage,
                Arrays.asList(AMYTUTEE, ALICETUTEE, BOBTUTEE, DANIEL));
    }

    @Test
    public void execute_sortSubject_sortedSuccessfully() {
        SortPersonCommand sortSubject = new SortPersonCommand(CATEGORY_SUBJECT);
        sortSubject.setData(model, new CommandHistory(), new UndoRedoStack());
        String expectedMessage = String.format(SortPersonCommand.MESSAGE_SUCCESS);
        assertCommandSuccess(sortSubject, expectedMessage,
                Arrays.asList(AMYTUTEE, ALICETUTEE, BOBTUTEE, DANIEL));
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<Person>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(SortPersonCommand command, String expectedMessage, List<Person> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
```
###### \java\seedu\address\logic\parser\FindPersonCommandParserTest.java
``` java
/**
 * Contains tests for {@code FindPersonCommandParser}.
 */
public class FindPersonCommandParserTest {
    private static final int INDEX_FIRST_ELEMENT = 0;
    public static final String VALID_FIRST_NAME_BOB = VALID_NAME_BOB.toLowerCase().split("\\s+")[INDEX_FIRST_ELEMENT];
    private FindPersonCommandParser parser = new FindPersonCommandParser();

    private final String[] nameKeywords = {VALID_FIRST_NAME_BOB};
    private final String[] educationLevelKeywords = {VALID_EDUCATION_LEVEL_AMY.toLowerCase()};
    private final String[] gradeKeywords = {VALID_GRADE_AMY.toLowerCase(), VALID_GRADE_BOB.toLowerCase()};
    private final String[] schoolKeywords = VALID_SCHOOL_AMY.toLowerCase().split("\\s+");
    private final String[] subjectKeywords = {VALID_SUBJECT_AMY.toLowerCase(), VALID_SUBJECT_BOB.toLowerCase()};

    private final String invalidCategory = "age";

    @Test
    public void parse_invalidArg_throwsParseException() {
        //empty input
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPersonCommand.MESSAGE_USAGE));

        //not enough arguments
        assertParseFailure(parser, CATEGORY_GRADE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPersonCommand.MESSAGE_USAGE));

        //invalid category
        assertParseFailure(parser, invalidCategory + " " + schoolKeywords[INDEX_FIRST_ELEMENT],
                String.format(MESSAGE_INVALID_FILTER_CATEGORY, FindPersonCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // filter by name using a single keyword
        FindPersonCommand expectedFindName = new FindPersonCommand(CATEGORY_NAME, nameKeywords);
        assertParseSuccess(parser, CATEGORY_NAME + " Bob", expectedFindName);

        // filter by education level using a single keyword
        FindPersonCommand expectedFindEducatonLevel =
                new FindPersonCommand(CATEGORY_EDUCATION_LEVEL, educationLevelKeywords);
        assertParseSuccess(parser,
                CATEGORY_EDUCATION_LEVEL + " " + VALID_EDUCATION_LEVEL_AMY, expectedFindEducatonLevel);

        // filter by grade using 2 different keywords
        FindPersonCommand expectedFindGrade = new FindPersonCommand(CATEGORY_GRADE, gradeKeywords);
        assertParseSuccess(parser, CATEGORY_GRADE + " " + VALID_GRADE_AMY
                + " " + VALID_GRADE_BOB, expectedFindGrade);

        // filter by school using multiple keywords from a single school
        FindPersonCommand expectedFindSchool = new FindPersonCommand(CATEGORY_SCHOOL, schoolKeywords);
        assertParseSuccess(parser, CATEGORY_SCHOOL + " " + VALID_SCHOOL_AMY, expectedFindSchool);

        // filter by subject using 2 different keywords
        FindPersonCommand expectedFindSubject = new FindPersonCommand(CATEGORY_SUBJECT, subjectKeywords);
        assertParseSuccess(parser, CATEGORY_SUBJECT + " " + VALID_SUBJECT_AMY
                + " " + VALID_SUBJECT_BOB, expectedFindSubject);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, CATEGORY_NAME + " \n\t  " + "Bob", expectedFindName);
    }
}
```
###### \java\seedu\address\logic\parser\ParserUtilTest.java
``` java
    @Test
    public void parseDateTime_invalidInput_throwsDateTimeParseException() {
        //null date and time
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseDateTime(null));

        //invalid date in non leap year
        Assert.assertThrows(DateTimeParseException.class, () -> ParserUtil
                .parseDateTime("29/02/2018 " + VALID_TIME));

        //invalid date in century year
        Assert.assertThrows(DateTimeParseException.class, () -> ParserUtil
                .parseDateTime("29/02/1900 " + VALID_TIME));

        //invalid date in month with 30 days
        Assert.assertThrows(DateTimeParseException.class, () -> ParserUtil
                .parseDateTime("31/04/2018 " + VALID_TIME));

        //invalid date in month with 31 days
        Assert.assertThrows(DateTimeParseException.class, () -> ParserUtil
                .parseDateTime("32/03/2018 " + VALID_TIME));

        //invalid hour
        Assert.assertThrows(DateTimeParseException.class, () -> ParserUtil
                .parseDateTime(VALID_DATE + " 25:00"));

        //invalid minute
        Assert.assertThrows(DateTimeParseException.class, () -> ParserUtil
                .parseDateTime(VALID_DATE + "12:60"));
    }

    @Test
    public void parseDateTime_validInput_parsedSuccessfully() {
        //beginning of the month
        LocalDateTime expectedDateTime = LocalDateTime.parse("01/10/2018 " + VALID_TIME, FORMATTER);
        assertEquals(expectedDateTime, parseDateTime("01/10/2018 " + VALID_TIME));

        //leap year
        expectedDateTime = LocalDateTime.parse("29/02/2020 " + VALID_TIME, FORMATTER);
        assertEquals(expectedDateTime, parseDateTime("29/02/2020 " + VALID_TIME));

        //month with 30 days
        expectedDateTime = LocalDateTime.parse("30/04/2020 " + VALID_TIME, FORMATTER);
        assertEquals(expectedDateTime, parseDateTime("30/04/2020 " + VALID_TIME));

        //month with 31 days
        expectedDateTime = LocalDateTime.parse("31/03/2020 " + VALID_TIME, FORMATTER);
        assertEquals(expectedDateTime, parseDateTime("31/03/2020 " + VALID_TIME));

        //valid time at boundary value
        expectedDateTime = LocalDateTime.parse(VALID_DATE + " 12:00", FORMATTER);
        assertEquals(expectedDateTime, parseDateTime(VALID_DATE + " 12:00"));
    }

    @Test
    public void parseDuration_invalidInput_throwsDateTimeParseException() {
        //null duration
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseDuration(null));

        //invalid duration
        Assert.assertThrows(DurationParseException.class, () -> ParserUtil
                .parseDuration(INVALID_DURATION));
    }

    @Test
    public void parseDuration_validInput_parsedSuccessfully() throws Exception {
        String expectedDuration = VALID_DURATION;
        assertEquals(expectedDuration, parseDuration(VALID_DURATION));
    }

    @Test
    public void parseDescription_noDescriptionWithinInput_returnsEmptyString() {
        //user input without description
        String[] validInputs = VALID_TASK_WITHOUT_DESCRIPTION.split("\\s+", MAXIMUM_AMOUNT_OF_PARAMETERS);
        String expectedDescription = "";
        assertEquals(expectedDescription, ParserUtil.parseDescription(validInputs, MAXIMUM_AMOUNT_OF_PARAMETERS));

        //user input with description
        validInputs = VALID_TASK_WITH_DESCRIPTION.split("\\s+", MAXIMUM_AMOUNT_OF_PARAMETERS);
        expectedDescription = VALID_DESCRIPTION;
        assertEquals(expectedDescription, ParserUtil.parseDescription(validInputs, MAXIMUM_AMOUNT_OF_PARAMETERS));
    }
}
```
###### \java\seedu\address\logic\parser\SortPersonCommandParserTest.java
``` java
/**
 * Contains tests for {@code SortPersonCommandParser}.
 */
public class SortPersonCommandParserTest {
    private SortPersonCommandParser parser = new SortPersonCommandParser();

    private final String invalidCategory = "age";

    @Test
    public void parse_invalidArg_throwsParseException() {
        //empty input
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortPersonCommand.MESSAGE_USAGE));

        //too many arguments
        assertParseFailure(parser, CATEGORY_GRADE + " " + CATEGORY_EDUCATION_LEVEL,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortPersonCommand.MESSAGE_USAGE));

        //invalid category
        assertParseFailure(parser, invalidCategory,
                String.format(MESSAGE_INVALID_SORTER_CATEGORY, SortPersonCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // sort by name
        SortPersonCommand expectedSortName = new SortPersonCommand(CATEGORY_NAME);
        assertParseSuccess(parser, CATEGORY_NAME, expectedSortName);

        // sort by education level
        SortPersonCommand expectedSortEducatonLevel = new SortPersonCommand(CATEGORY_EDUCATION_LEVEL);
        assertParseSuccess(parser, CATEGORY_EDUCATION_LEVEL, expectedSortEducatonLevel);

        // sort by grade
        SortPersonCommand expectedSortGrade = new SortPersonCommand(CATEGORY_GRADE);
        assertParseSuccess(parser, CATEGORY_GRADE, expectedSortGrade);

        // sort by school
        SortPersonCommand expectedSortSchool = new SortPersonCommand(CATEGORY_SCHOOL);
        assertParseSuccess(parser, CATEGORY_SCHOOL, expectedSortSchool);

        // sort by subject
        SortPersonCommand expectedSortSubject = new SortPersonCommand(CATEGORY_SUBJECT);
        assertParseSuccess(parser, CATEGORY_SUBJECT, expectedSortSubject);

        // multiple whitespaces before and after sort category
        assertParseSuccess(parser, "   \n\t" + CATEGORY_NAME + "\n\t", expectedSortName);
    }
}
```
###### \java\seedu\address\storage\XmlAdaptedPersonTest.java
``` java
    //=========== Tutee Related Tests =============================================================

    @Test
    public void toModelType_validTuteeDetails_returnsTutee() throws Exception {
        XmlAdaptedPerson tutee = new XmlAdaptedPerson(ALICETUTEE);
        assertEquals(ALICETUTEE, tutee.toModelType());
    }

    @Test
    public void toModelType_invalidTuteeName_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(INVALID_NAME, VALID_TUTEE_PHONE, VALID_TUTEE_EMAIL, VALID_TUTEE_ADDRESS,
                        VALID_TUTEE_SUBJECT, VALID_TUTEE_GRADE, VALID_TUTEE_EDUCATION_LEVEL,
                        VALID_TUTEE_SCHOOL, VALID_TUTEE_TAGS);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullTuteeName_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(null, VALID_TUTEE_PHONE, VALID_TUTEE_EMAIL, VALID_TUTEE_ADDRESS,
                        VALID_TUTEE_SUBJECT, VALID_TUTEE_GRADE, VALID_TUTEE_EDUCATION_LEVEL,
                        VALID_TUTEE_SCHOOL, VALID_TUTEE_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTuteePhone_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_TUTEE_NAME, INVALID_PHONE, VALID_TUTEE_EMAIL, VALID_TUTEE_ADDRESS,
                        VALID_TUTEE_SUBJECT, VALID_TUTEE_GRADE, VALID_TUTEE_EDUCATION_LEVEL,
                        VALID_TUTEE_SCHOOL, VALID_TUTEE_TAGS);
        String expectedMessage = Phone.MESSAGE_PHONE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullTuteePhone_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_TUTEE_NAME, null, VALID_TUTEE_EMAIL, VALID_TUTEE_ADDRESS,
                        VALID_TUTEE_SUBJECT, VALID_TUTEE_GRADE, VALID_TUTEE_EDUCATION_LEVEL,
                        VALID_TUTEE_SCHOOL, VALID_TUTEE_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTuteeEmail_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_TUTEE_NAME, VALID_TUTEE_PHONE, INVALID_EMAIL, VALID_TUTEE_ADDRESS,
                        VALID_TUTEE_SUBJECT, VALID_TUTEE_GRADE, VALID_TUTEE_EDUCATION_LEVEL,
                        VALID_TUTEE_SCHOOL, VALID_TUTEE_TAGS);
        String expectedMessage = Email.MESSAGE_EMAIL_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullTuteeEmail_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_TUTEE_NAME, VALID_TUTEE_PHONE, null, VALID_TUTEE_ADDRESS,
                        VALID_TUTEE_SUBJECT, VALID_TUTEE_GRADE, VALID_TUTEE_EDUCATION_LEVEL,
                        VALID_TUTEE_SCHOOL, VALID_TUTEE_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTuteeAddress_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_TUTEE_NAME, VALID_TUTEE_PHONE, VALID_TUTEE_EMAIL, INVALID_ADDRESS,
                        VALID_TUTEE_SUBJECT, VALID_TUTEE_GRADE, VALID_TUTEE_EDUCATION_LEVEL,
                        VALID_TUTEE_SCHOOL, VALID_TUTEE_TAGS);
        String expectedMessage = Address.MESSAGE_ADDRESS_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullTuteeAddress_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_TUTEE_NAME, VALID_TUTEE_PHONE, VALID_TUTEE_EMAIL, null,
                        VALID_TUTEE_SUBJECT, VALID_TUTEE_GRADE, VALID_TUTEE_EDUCATION_LEVEL,
                        VALID_TUTEE_SCHOOL, VALID_TUTEE_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidSubject_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_TUTEE_NAME, VALID_TUTEE_PHONE, VALID_TUTEE_EMAIL, VALID_TUTEE_ADDRESS,
                        INVALID_SUBJECT, VALID_TUTEE_GRADE, VALID_TUTEE_EDUCATION_LEVEL,
                        VALID_TUTEE_SCHOOL, VALID_TUTEE_TAGS);
        String expectedMessage = Subject.MESSAGE_SUBJECT_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullSubject_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_TUTEE_NAME, VALID_TUTEE_PHONE, VALID_TUTEE_EMAIL, VALID_TUTEE_ADDRESS,
                null, VALID_TUTEE_GRADE, VALID_TUTEE_EDUCATION_LEVEL,
                VALID_TUTEE_SCHOOL, VALID_TUTEE_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Subject.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidGrade_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_TUTEE_NAME, VALID_TUTEE_PHONE, VALID_TUTEE_EMAIL, VALID_TUTEE_ADDRESS,
                        VALID_TUTEE_SUBJECT, INVALID_GRADE, VALID_TUTEE_EDUCATION_LEVEL,
                        VALID_TUTEE_SCHOOL, VALID_TUTEE_TAGS);
        String expectedMessage = Grade.MESSAGE_GRADE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullGrade_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_TUTEE_NAME, VALID_TUTEE_PHONE, VALID_TUTEE_EMAIL, VALID_TUTEE_ADDRESS,
                VALID_TUTEE_SUBJECT, null, VALID_TUTEE_EDUCATION_LEVEL,
                VALID_TUTEE_SCHOOL, VALID_TUTEE_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Grade.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEducationLevel_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_TUTEE_NAME, VALID_TUTEE_PHONE, VALID_TUTEE_EMAIL, VALID_TUTEE_ADDRESS,
                        VALID_TUTEE_SUBJECT, VALID_TUTEE_GRADE, INVALID_EDUCATION_LEVEL,
                        VALID_TUTEE_SCHOOL, VALID_TUTEE_TAGS);
        String expectedMessage = EducationLevel.MESSAGE_EDUCATION_LEVEL_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEducationLevel_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_TUTEE_NAME, VALID_TUTEE_PHONE, VALID_TUTEE_EMAIL, VALID_TUTEE_ADDRESS,
                VALID_TUTEE_SUBJECT, VALID_TUTEE_GRADE, null,
                VALID_TUTEE_SCHOOL, VALID_TUTEE_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, EducationLevel.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidSchool_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_TUTEE_NAME, VALID_TUTEE_PHONE, VALID_TUTEE_EMAIL, VALID_TUTEE_ADDRESS,
                        VALID_TUTEE_SUBJECT, VALID_TUTEE_GRADE, VALID_TUTEE_EDUCATION_LEVEL,
                        INVALID_SCHOOL, VALID_TUTEE_TAGS);
        String expectedMessage = School.MESSAGE_SCHOOL_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullSchool_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_TUTEE_NAME, VALID_TUTEE_PHONE, VALID_TUTEE_EMAIL, VALID_TUTEE_ADDRESS,
                VALID_TUTEE_SUBJECT, VALID_TUTEE_GRADE, VALID_TUTEE_EDUCATION_LEVEL,
                null, VALID_TUTEE_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, School.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_tuteeHasInvalidTags_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TUTEE_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_TUTEE_NAME, VALID_TUTEE_PHONE, VALID_TUTEE_EMAIL, VALID_TUTEE_ADDRESS,
                VALID_TUTEE_SUBJECT, VALID_TUTEE_GRADE, VALID_TUTEE_EDUCATION_LEVEL,
                VALID_TUTEE_SCHOOL, invalidTags);
        Assert.assertThrows(IllegalValueException.class, person::toModelType);
    }
}
```
###### \java\seedu\address\testutil\TaskUtil.java
``` java

import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

import seedu.address.logic.commands.AddPersonalTaskCommand;
import seedu.address.model.Task;


/**
 * A utility class for Task.
 */
public class TaskUtil {
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/uuuu HH:mm")
            .withResolverStyle(ResolverStyle.STRICT);

    /**
     * Returns an add personal task command string for adding the {@code task}.
     */
    public static String getAddPersonalTaskCommand(Task task) {
        return AddPersonalTaskCommand.COMMAND_WORD + " " + getPersonalTaskDetails(task);
    }

    /**
     * Returns the part of command string for the given {@code task}'s details.
     */
    public static String getPersonalTaskDetails(Task task) {
        StringBuilder sb = new StringBuilder();
        sb.append(task.getStringTaskDateTime() + " ");
        sb.append(task.getDuration() + " ");
        sb.append(task.getDescription() + " ");
        return sb.toString();
    }
}
```
###### \java\systemtests\AddressBookSystemTest.java
``` java
    /**
     * Adds a tutee into the current model and expected model.
     */
    protected void addTutee(String command, Tutee tutee, Model expectedModel) {
        try {
            expectedModel.addPerson(tutee);
        } catch (DuplicatePersonException dpe) {
            System.out.println("a tutee with the same name exists in the expected model");
        }
        executeCommand(command);
    }
```
###### \java\systemtests\FindPersonCommandSystemTest.java
``` java
        /* Adding some tutees into the filtered person list to test whether Find Person command can find tutees */
        // adds AMYTUTEE
        expectedModel = getModel();
        command = AddTuteeCommand.COMMAND_WORD + "  " + NAME_DESC_AMY + "  " + PHONE_DESC_AMY + " "
                + EMAIL_DESC_AMY + "   " + ADDRESS_DESC_AMY + "   " + SUBJECT_DESC_AMY + " " + GRADE_DESC_AMY + " "
                + EDUCATION_LEVEL_DESC_AMY + " " + SCHOOL_DESC_AMY + " " + TAG_DESC_FRIEND + " ";
        addTutee(command, AMYTUTEE, expectedModel);

        //adds Bob whose subject and school are same as Amy's
        Tutee modifiedBobTutee = new TuteeBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withSubject(VALID_SUBJECT_AMY)
                .withGrade(VALID_GRADE_BOB).withEducationLevel(VALID_EDUCATION_LEVEL_BOB).withSchool(VALID_SCHOOL_AMY)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        command = AddTuteeCommand.COMMAND_WORD + "  " + NAME_DESC_BOB + "  " + PHONE_DESC_BOB + " "
                + EMAIL_DESC_BOB + "   " + ADDRESS_DESC_BOB + "   " + SUBJECT_DESC_AMY + " " + GRADE_DESC_BOB + " "
                + EDUCATION_LEVEL_DESC_BOB + " " + SCHOOL_DESC_AMY + " " + TAG_DESC_HUSBAND + " " + TAG_DESC_FRIEND;
        addTutee(command, modifiedBobTutee, expectedModel);

        /* Case: find education level of a tutee in address book -> 1 person found */
        ModelHelper.setFilteredList(expectedModel, AMYTUTEE);
        command = FindPersonCommand.COMMAND_WORD + " " + CATEGORY_EDUCATION_LEVEL + " "
                + AMYTUTEE.getEducationLevel().toString();
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find grade of a tutee using command alias in address book -> 1 person found */
        command = FindPersonCommand.COMMAND_ALIAS + " " + CATEGORY_GRADE + " "
                + AMYTUTEE.getGrade().toString();
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find school of a tutee in address book -> 2 persons found */
        ModelHelper.setFilteredList(expectedModel, AMYTUTEE, modifiedBobTutee);
        command = FindPersonCommand.COMMAND_WORD + " " + CATEGORY_SCHOOL + " "
                + AMYTUTEE.getSchool().toString();
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find subject of a tutee in address book -> 2 persons found */
        command = FindPersonCommand.COMMAND_WORD + " " + CATEGORY_SUBJECT + " "
                + AMYTUTEE.getSubject().toString();
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

```
###### \java\systemtests\ModelHelper.java
``` java
    /**
     * Updates {@code model}'s sorted list to display persons based on specified category.
     */
    public static void setSortedList(Model model, String category) {
        Comparator<Person> comparator = new PersonSortUtil().getComparator(category);
        model.sortFilteredPersonList(comparator);
    }
```
