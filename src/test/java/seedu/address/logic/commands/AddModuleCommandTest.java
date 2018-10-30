package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyTranscript;
import seedu.address.model.Transcript;
import seedu.address.model.capgoal.CapGoal;
import seedu.address.model.module.Code;
import seedu.address.model.module.Grade;
import seedu.address.model.module.Module;
import seedu.address.model.person.Person;
import seedu.address.model.util.ModuleBuilder;

public class AddModuleCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructorNullModuleThrowsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddModuleCommand(null);
    }

    @Test
    public void executeModuleAcceptedByModelAddSuccessful() throws Exception {
        ModelStubAcceptingModuleAdded modelStub = new ModelStubAcceptingModuleAdded();
        Module validModule = new ModuleBuilder().build();

        CommandResult commandResult = new AddModuleCommand(validModule)
                .execute(modelStub, commandHistory);

        assertEquals(String.format(AddModuleCommand.MESSAGE_SUCCESS, validModule),
                commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validModule), modelStub.modulesAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void executeDuplicateModuleThrowsCommandException() throws Exception {
        Module validModule = new ModuleBuilder().build();
        AddModuleCommand addCommand = new AddModuleCommand(validModule);
        ModelStub modelStub = new ModelStubWithModule(validModule);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddModuleCommand.MESSAGE_DUPLICATE_MODULE);
        addCommand.execute(modelStub, commandHistory);
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addModule(Module module) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyTranscript newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyTranscript getTranscript() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasModule(Module module) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasMultipleInstances(Code code) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteModule(Module target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteModule(Predicate<Module> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateModule(Module target, Module editedModule) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Module> getFilteredModuleList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredModuleList(Predicate<Module> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoTranscript() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoTranscript() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoTranscript() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoTranscript() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitTranscript() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public CapGoal getCapGoal() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void updateCapGoal(double capGoal) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public double getCap() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
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
        public Module findModule(Module moduleToFind) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Module findModule(Code moduleCodeToFind) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Module> getCompletedModuleList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Module adjustModule(Module targetModule, Grade adjustGrade) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Module> getIncompleteModuleList() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single module.
     */
    private class ModelStubWithModule extends ModelStub {
        private final Module module;

        ModelStubWithModule(Module module) {
            requireNonNull(module);
            this.module = module;
        }

        @Override
        public boolean hasModule(Module module) {
            requireNonNull(module);
            return this.module.isSameModule(module);
        }
    }

    /**
     * A Model stub that always accept the module being added.
     */
    private class ModelStubAcceptingModuleAdded extends ModelStub {
        public final ArrayList<Module> modulesAdded = new ArrayList<>();

        @Override
        public boolean hasModule(Module module) {
            requireNonNull(module);
            return modulesAdded.stream().anyMatch(module::isSameModule);
        }

        @Override
        public void addModule(Module module) {
            requireNonNull(module);
            modulesAdded.add(module);
        }

        @Override
        public void commitTranscript() {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public ReadOnlyTranscript getTranscript() {
            return new Transcript();
        }
    }

}
