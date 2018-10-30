package seedu.address.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.model.module.Module;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.util.ModuleBuilder;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.TypicalModules;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager modelManager = new ModelManager();

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasPerson(null);
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        modelManager.addPerson(ALICE);
        assertTrue(modelManager.hasPerson(ALICE));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredPersonList().remove(0);
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        AddressBook differentAddressBook = new AddressBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertTrue(modelManager.equals(new ModelManager(addressBook, differentUserPrefs)));
    }

    //@@author jeremiah-ang
    @Test
    public void getCompletedModuleListModifyThrowsException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getCompletedModuleList().remove(0);
    }

    @Test
    public void getIncompleteModuleListModifyThrowsException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getIncompleteModuleList().remove(0);
    }

    @Test
    public void getCompletedModuleListAllCompleted() {
        Module completed = TypicalModules.DATA_STRUCTURES;
        Module incomplete = new ModuleBuilder(TypicalModules.DISCRETE_MATH).noGrade().build();
        modelManager.addModule(completed);
        modelManager.addModule(incomplete);
        ObservableList<Module> completedModules = modelManager.getCompletedModuleList();
        boolean isAllCompleted = true;
        for (Module module : completedModules) {
            isAllCompleted = isAllCompleted && module.hasCompleted();
        }

        assertTrue(isAllCompleted);
    }

    @Test
    public void getIncompleteModuleListAllIncomplete() {
        Module completed = TypicalModules.DATA_STRUCTURES;
        Module incomplete = new ModuleBuilder(TypicalModules.DISCRETE_MATH).noGrade().build();
        modelManager.addModule(completed);
        modelManager.addModule(incomplete);
        ObservableList<Module> completedModules = modelManager.getIncompleteModuleList();
        boolean isAllCompleted = false;
        for (Module module : completedModules) {
            isAllCompleted = isAllCompleted || module.hasCompleted();
        }

        assertFalse(isAllCompleted);
    }
}
