package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.CommandHistory;
import seedu.address.model.ExpenditureList;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyContactList;
import seedu.address.model.ReadOnlyExpenditureList;
import seedu.address.model.ReadOnlyTaskList;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.ReadOnlyWorkoutBook;
import seedu.address.model.person.Person;
import seedu.address.model.purchase.Purchase;
import seedu.address.model.task.Task;
import seedu.address.model.workout.Workout;
import seedu.address.testutil.PurchaseBuilder;

public class AddPurchaseCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullPurchase_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddPurchaseCommand(null);
    }

    @Test
    public void execute_purchaseAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPurchaseAdded modelStub = new ModelStubAcceptingPurchaseAdded();
        Purchase validPurchase = new PurchaseBuilder().build();

        CommandResult commandResult = new AddPurchaseCommand(validPurchase).execute(modelStub, commandHistory);

        assertEquals(String.format(AddPurchaseCommand.MESSAGE_SUCCESS, validPurchase), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validPurchase), modelStub.purchasesAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void equals() {
        Purchase chickenrice = new PurchaseBuilder().withPurchaseName("chickenrice").build();
        Purchase prawnmee = new PurchaseBuilder().withPurchaseName("prawnmee").build();
        AddPurchaseCommand addChickenriceCommand = new AddPurchaseCommand(chickenrice);
        AddPurchaseCommand addPrawnmeeCommand = new AddPurchaseCommand(prawnmee);

        // same object -> returns true
        assertTrue(addChickenriceCommand.equals(addChickenriceCommand));

        // same values -> returns true
        AddPurchaseCommand addChickenriceCommandCopy = new AddPurchaseCommand(chickenrice);
        assertTrue(addChickenriceCommand.equals(addChickenriceCommandCopy));

        // different types -> returns false
        assertFalse(addChickenriceCommand.equals(1));

        // null -> returns false
        assertFalse(addChickenriceCommand.equals(null));

        // different person -> returns false
        assertFalse(addChickenriceCommand.equals(addPrawnmeeCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {

// -----------------------------UserPrefs and GuiSettings-------------------------------------
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

//-----------------------------Expenditure List------------------------------------

        @Override
        public void addPurchase(Purchase purchase) {

        }

        @Override
        public void setExpenditureList(ReadOnlyExpenditureList newData) {

        }

        @Override
        public ReadOnlyExpenditureList getExpenditureList() {
            return null;
        }


        @Override
        public ObservableList<Purchase> getFilteredPurchaseList() {
            return null;
        }

        @Override
        public void updateFilteredPurchaseList(Predicate<Purchase> predicate) {

        }

        @Override
        public void commitExpenditureList() {

        }

        @Override
        public ReadOnlyProperty<Purchase> selectedPurchaseProperty() {
            return null;
        }

        @Override
        public void setSelectedPurchase(Purchase purchase) {

        }

        @Override
        public Path getExpenditureListFilePath() {
            return null;
        }

        @Override
        public void setExpenditureListFilePath(Path expenditureListFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Purchase getSelectedPurchase() {
            return null;
        }

// --------------------------------Task List-------------------------------------

        @Override
        public ReadOnlyTaskList getTaskList() {
            return null;
        }

        @Override
        public ReadOnlyTaskList getTickedTaskList() {
            return null;
        }

        @Override
        public void addTask(Task task) {

        }

        @Override
        public void addTickedTaskList(Task task) {

        }

        @Override
        public boolean hasTask(Task task) {
            return false;
        }

        @Override
        public void deleteTask(Task task) {

        }

        @Override
        public void sortTask() {

        }

        @Override
        public void commitTaskList() {

        }

        @Override
        public void commitTickedTaskList() {

        }

        @Override
        public void updateFilteredTaskList(Predicate<Task> predicate) {

        }

        @Override
        public void updateFilteredTickedTaskList(Predicate<Task> predicate) {

        }

        @Override
        public ObservableList<Task> getFilteredTaskList() {
            return null;
        }

        @Override
        public ObservableList<Task> getFilteredTickedTaskList() {
            return null;
        }

        @Override
        public ReadOnlyProperty<Task> selectedTaskProperty() {
            return null;
        }

        @Override
        public void setSelectedTask(Task task) {

        }

        @Override
        public void setTask(Task target, Task editedTask) {

        }


// ---------------------------Workout Book----------------------------
@Override
public void addWorkout(Workout workout) {

}

        @Override
        public void commitWorkoutBook() {

        }

        @Override
        public void setSelectedWorkout(Workout workout) {

        }

        @Override
        public ReadOnlyProperty<Workout> selectedWorkoutProperty() {
            return null;
        }

        @Override
        public ObservableList<Workout> getFilteredWorkoutList() {
            return null;
        }

        @Override
        public ReadOnlyWorkoutBook getWorkoutList() {
            return null;
        }

        @Override
        public void updateFilteredWorkoutList(Predicate<Workout> predicate) {

        }

        @Override
        public void setWorkoutBook(ReadOnlyWorkoutBook workoutBook) {

        }

// ------------------------------- contact list---------------------------

        @Override
        public Path getContactListFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setContactListFilePath(Path contactListFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setContactList(ReadOnlyContactList contactList) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyContactList getContactList() {
            throw new AssertionError("This method should not be called.");
        }



        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }


        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }



        @Override
        public boolean canUndoContactList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoContactList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoContactList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoContactList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitContactList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyProperty<Person> selectedPersonProperty() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Person getSelectedPerson() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSelectedPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

    }


    /**
     * A Model stub that always accept the purchase being added.
     */
    private class ModelStubAcceptingPurchaseAdded extends ModelStub {
        final ArrayList<Purchase> purchasesAdded = new ArrayList<>();

        @Override
        public void addPurchase(Purchase purchase) {
            requireNonNull(purchase);
            purchasesAdded.add(purchase);
        }

        @Override
        public void commitExpenditureList() {
            // called by {@code AddPurchaseCommand#execute()}
        }

        @Override
        public ReadOnlyExpenditureList getExpenditureList() {
            return new ExpenditureList();
        }
    }
}
