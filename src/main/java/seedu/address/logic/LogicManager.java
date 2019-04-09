package seedu.address.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyExpenditureList;
import seedu.address.model.ReadOnlyTaskList;
import seedu.address.model.ReadOnlyWorkoutBook;
import seedu.address.model.person.Person;
import seedu.address.model.purchase.Purchase;
import seedu.address.model.task.Task;
import seedu.address.model.workout.Workout;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final CommandHistory history;
    private final AddressBookParser addressBookParser;
    private boolean addressBookModified;
    private boolean taskListModified;
    private boolean tickedTaskListModified;
    private boolean expenditureListModified;
    private boolean workoutBookModified;


    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        history = new CommandHistory();
        addressBookParser = new AddressBookParser();


        // Set addressBookModified to true whenever the models' address book is modified.
        model.getAddressBook().addListener(observable -> addressBookModified = true);
        model.getTaskList().addListener(observable -> taskListModified = true);
        model.getTickedTaskList().addListener(observable -> tickedTaskListModified = true);
        model.getExpenditureList().addListener(observable -> expenditureListModified = true);
        model.getWorkoutList().addListener(observable -> workoutBookModified = true);
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        addressBookModified = false;
        taskListModified = false;
        tickedTaskListModified = false;
        expenditureListModified = false;
        workoutBookModified = false;

        CommandResult commandResult;
        try {
            Command command = addressBookParser.parseCommand(commandText);
            commandResult = command.execute(model, history);
        } finally {
            history.add(commandText);
        }
        if (taskListModified) {
            logger.info("Task list modified, saving to file.");
            try {
                storage.saveTaskList(model.getTaskList());
            } catch (IOException ioe) {
                throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
            }
        }

        if (tickedTaskListModified) {
            logger.info("Ticked Task List modified. Saving to file. ");
        } //TODO

        if (expenditureListModified) {
            logger.info("Expenditure list modified, saving to file.");
            try {
                storage.saveExpenditureList(model.getExpenditureList());
            } catch (IOException ioe) {
                throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
            }
        }
        if (workoutBookModified) {

        }

        if (addressBookModified) {
            logger.info("Address book modified, saving to file.");
            try {
                storage.saveAddressBook(model.getAddressBook());
            } catch (IOException ioe) {
                throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
            }
        }

        return commandResult;
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return model.getAddressBook();
    }

    @Override
    public ReadOnlyTaskList getTaskList() {
        return model.getTaskList();
    }

    @Override
    public ReadOnlyTaskList getTickedTaskList() {
        return model.getTickedTaskList();
    }

    @Override
    public ReadOnlyExpenditureList getExpenditureList() {
        return model.getExpenditureList();
    }

    @Override
    public ReadOnlyWorkoutBook getWorkoutList() {
        return model.getWorkoutList();
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public ObservableList<Task> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }

    @Override
    public ObservableList<Task> getFilteredTickedTaskList() {
        return model.getFilteredTickedTaskList();
    }

    @Override
    public ObservableList<Purchase> getFilteredPurchaseList() {
        return model.getFilteredPurchaseList();
    }

    @Override
    public ObservableList<Workout> getFilteredWorkoutList() {
        return model.getFilteredWorkoutList();
    }

    @Override
    public ObservableList<String> getHistory() {
        return history.getHistory();
    }

    @Override
    public Path getAddressBookFilePath() {
        return model.getAddressBookFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    @Override
    public ReadOnlyProperty<Person> selectedPersonProperty() {
        return model.selectedPersonProperty();
    }

    @Override
    public ReadOnlyProperty<Task> selectedTaskProperty() {
        return model.selectedTaskProperty();
    }
    @Override
    public ReadOnlyProperty<Workout> selectedWorkoutProperty() {
        return model.selectedWorkoutProperty();
    }

    @Override
    public ReadOnlyProperty<Purchase> selectedPurchaseProperty() {
        return model.selectedPurchaseProperty();
    }

    @Override
    public void setSelectedPerson(Person person) {
        model.setSelectedPerson(person);
    }

    @Override
    public void setSelectedTask(Task task) {
        model.setSelectedTask(task);
    }

    @Override
    public void setSelectedPurchase(Purchase purchase) {
        model.setSelectedPurchase(purchase);
    }

    @Override
    public void setSelectedWorkout(Workout workout) {
        model.setSelectedWorkout(workout);
    }
}
