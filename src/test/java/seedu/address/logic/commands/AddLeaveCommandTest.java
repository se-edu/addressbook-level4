package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.LeaveList;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyLeaveList;
import seedu.address.model.leave.Leave;
import seedu.address.model.person.Person;
import seedu.address.testutil.LeaveBuilder;

public class AddLeaveCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddLeaveCommand(null);
    }

    @Test
    public void execute_leaveAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingLeaveAdded modelStub = new ModelStubAcceptingLeaveAdded();
        Leave validLeave = new LeaveBuilder().build();

        CommandResult commandResult = new AddLeaveCommand(validLeave).execute(modelStub, commandHistory);

        assertEquals(String.format(AddLeaveCommand.MESSAGE_SUCCESS, validLeave), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validLeave), modelStub.leavesAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateLeave_throwsCommandException() throws Exception {
        Leave validLeave = new LeaveBuilder().build();
        AddLeaveCommand addLeaveCommand = new AddLeaveCommand(validLeave);
        ModelStub modelStub = new ModelStubWithLeave(validLeave);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddLeaveCommand.MESSAGE_DUPLICATE_LEAVE);
        addLeaveCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Leave leave1 = new LeaveBuilder().withNric("S9514222A").withDate("12/03/2012").build();
        Leave leave2 = new LeaveBuilder().withNric("S9513222E").withDate("12/04/2018").build();
        AddLeaveCommand addLeaveCommand1 = new AddLeaveCommand(leave1);
        AddLeaveCommand addLeaveCommand2 = new AddLeaveCommand(leave2);

        // same object -> returns true
        assertTrue(addLeaveCommand1.equals(addLeaveCommand1));

        // different types -> returns false
        assertFalse(addLeaveCommand1.equals(1));

        // null -> returns false
        assertFalse(addLeaveCommand1.equals(null));

        // different leave -> returns false
        assertFalse(addLeaveCommand1.equals(addLeaveCommand2));
    }


    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addLeave(Leave leave) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetData2(ReadOnlyLeaveList newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyLeaveList getLeaveList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasLeave(Leave leave) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updatePerson(Person target, Person editedPerson) {
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
        public boolean canUndoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitLeaveList() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithLeave extends ModelStub {
        private final Leave leave;

        ModelStubWithLeave(Leave leave) {
            requireNonNull(leave);
            this.leave = leave;
        }

        @Override
        public boolean hasLeave(Leave leave) {
            requireNonNull(leave);
            return this.leave.isSameRequest(leave);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingLeaveAdded extends ModelStub {
        final ArrayList<Leave> leavesAdded = new ArrayList<>();

        @Override
        public boolean hasLeave(Leave leave) {
            requireNonNull(leave);
            return leavesAdded.stream().anyMatch(leave::isSameRequest);
        }

        @Override
        public void addLeave(Leave leave) {
            requireNonNull(leave);
            leavesAdded.add(leave);
        }

        @Override
        public void commitLeaveList() {
            // called by {@code AddLeaveCommand#execute()}
        }

        @Override
        public ReadOnlyLeaveList getLeaveList() {
            return new LeaveList();
        }
    }



}
