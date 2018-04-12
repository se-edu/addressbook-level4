package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.typicaladdressbook.TypicalAddressBookCompiler.getTypicalAddressBook2;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

//@@author yungyung04
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
