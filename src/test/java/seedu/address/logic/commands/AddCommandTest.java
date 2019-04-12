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
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ContactList;
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
import seedu.address.testutil.PersonBuilder;

public class AddCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddCommand(null);
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Person validPerson = new PersonBuilder().build();

        CommandResult commandResult = new AddCommand(validPerson).execute(modelStub, commandHistory);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validPerson), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validPerson), modelStub.personsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() throws Exception {
        Person validPerson = new PersonBuilder().build();
        AddCommand addCommand = new AddCommand(validPerson);
        ModelStub modelStub = new ModelStubWithPerson(validPerson);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_PERSON);
        addCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    public class ModelStub implements Model {
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
        public ReadOnlyTaskList getTaskList() {
            return null;
        }

        @Override
        public ReadOnlyTaskList getTickedTaskList() {
            return null;
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
        public void updateFilteredTaskList(Predicate<Task> predicate) {

        }

        @Override
        public void updateFilteredTickedTaskList(Predicate<Task> predicate) {

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

        @Override
        public Task getSelectedTask() {
            return null;
        }


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


        @Override
        public Purchase getSelectedPurchase() {
            return null;
        }

    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final Person person;

        ModelStubWithPerson(Person person) {
            requireNonNull(person);
            this.person = person;
        }

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return this.person.isSamePerson(person);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Person> personsAdded = new ArrayList<>();

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return personsAdded.stream().anyMatch(person::isSamePerson);
        }

        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
            personsAdded.add(person);
        }

        @Override
        public void commitContactList() {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public ReadOnlyContactList getContactList() {
            return new ContactList();
        }
    }

}
