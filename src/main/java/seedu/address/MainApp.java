package seedu.address;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Version;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.ConfigUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.model.ContactList;
import seedu.address.model.ExpenditureList;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyContactList;
import seedu.address.model.ReadOnlyExpenditureList;
import seedu.address.model.ReadOnlyTaskList;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.ReadOnlyWorkoutBook;
import seedu.address.model.TaskList;
import seedu.address.model.UserPrefs;
import seedu.address.model.WorkoutBook;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.storage.ContactListStorage;
import seedu.address.storage.ExpenditureListStorage;
import seedu.address.storage.JsonContactListStorage;
import seedu.address.storage.JsonExpenditureListStorage;
import seedu.address.storage.JsonTaskListStorage;
import seedu.address.storage.JsonTickedTaskListStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.JsonWorkoutBookStorage;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;
import seedu.address.storage.TaskListStorage;
import seedu.address.storage.TickedTaskListStorage;
import seedu.address.storage.UserPrefsStorage;
import seedu.address.storage.WorkoutBookStorage;
import seedu.address.ui.Ui;
import seedu.address.ui.UiManager;




/**
 * The main entry point to the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(1, 3, 4, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;
    private Optional<ReadOnlyTaskList> taskListOptional;
    private Optional<ReadOnlyExpenditureList> expListOptional;
    private Optional<ReadOnlyWorkoutBook> workoutBookOptional;


    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing LIFE data ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        ContactListStorage contactListStorage = new JsonContactListStorage(userPrefs.getContactListFilePath());
        TaskListStorage taskListStorage = new JsonTaskListStorage(userPrefs.getTaskListFilePath());
        TickedTaskListStorage tickedTaskListStorage =
                new JsonTickedTaskListStorage(userPrefs.getTickedTaskListFilePath());
        ExpenditureListStorage expenditureListStorage =
                new JsonExpenditureListStorage(userPrefs.getExpenditureListFilePath());
        WorkoutBookStorage workoutBookStorage = new JsonWorkoutBookStorage(userPrefs.getWorkoutBookFilePath());
        storage = new StorageManager(contactListStorage, userPrefsStorage, taskListStorage,
                expenditureListStorage, workoutBookStorage, tickedTaskListStorage);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s contact list and {@code userPrefs}. <br>
     * The data from the sample contact list will be used instead if {@code storage}'s contact list is not found,
     * or an empty contact list will be used instead if errors occur when reading {@code storage}'s contact list.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        Optional<ReadOnlyContactList> contactListOptional;
        Optional<ReadOnlyTaskList> taskListOptional;
        Optional<ReadOnlyExpenditureList> expenditureListOptional;
        Optional<ReadOnlyTaskList> tickedTaskListOptional;
        ReadOnlyContactList initialData;
        ReadOnlyTaskList initialTasks;
        ReadOnlyTaskList initialTickedTasks;
        ReadOnlyExpenditureList initialPurchases;
        ReadOnlyWorkoutBook initialWorkout;

        try {
            contactListOptional = storage.readContactList();
            if (!contactListOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample ContactList");
            }
            initialData = contactListOptional.orElseGet(SampleDataUtil::getSampleContactList);

        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty ContactList");
            initialData = new ContactList();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty ContactList");
            initialData = new ContactList();
        }

        try {
            taskListOptional = storage.readTaskList();
            if (!taskListOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample ContactList");
            }
            initialTasks = taskListOptional.orElseGet(SampleDataUtil::getSampleTaskList);

        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty task List");
            initialTasks = new TaskList();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty task List");
            initialTasks = new TaskList();
        }

        try {
            tickedTaskListOptional = storage.readTickedTaskList();
            if (!tickedTaskListOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a empty ticked task list");
            }
            initialTickedTasks = tickedTaskListOptional.orElseGet(SampleDataUtil::getSampleTaskList);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty Ticked task List");
            initialTickedTasks = new TaskList();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty Ticked task List");
            initialTickedTasks = new TaskList();
        }


        try {
            expenditureListOptional = storage.readExpenditureList();
            if (!expenditureListOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample ExpenditureList");
            }
            initialPurchases = expenditureListOptional.orElseGet(SampleDataUtil::getSampleExpenditureList);

        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty ExpenditureList");
            initialPurchases = new ExpenditureList();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty ExpenditureList");
            initialPurchases = new ExpenditureList();
        }
        try {
            workoutBookOptional = storage.readWorkoutBook();
            if (!workoutBookOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample WorkoutList");
            }
            initialWorkout = workoutBookOptional.orElseGet(SampleDataUtil::getSampleWorkoutBook);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty WorkoutList");
            initialWorkout = new WorkoutBook();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty WorkoutList");
            initialWorkout = new WorkoutBook();
        }

        return new ModelManager(initialData, userPrefs, initialTasks, initialPurchases, initialWorkout);
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    /**
     * Returns a {@code Config} using the file at {@code configFilePath}. <br>
     * The default file path {@code Config#DEFAULT_CONFIG_FILE} will be used instead
     * if {@code configFilePath} is null.
     */
    protected Config initConfig(Path configFilePath) {
        Config initializedConfig;
        Path configFilePathUsed;

        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        if (configFilePath != null) {
            logger.info("Custom Config file specified " + configFilePath);
            configFilePathUsed = configFilePath;
        }

        logger.info("Using config file : " + configFilePathUsed);

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataConversionException e) {
            logger.warning("Config file at " + configFilePathUsed + " is not in the correct format. "
                    + "Using default config properties");
            initializedConfig = new Config();
        }

        //Update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
        return initializedConfig;
    }

    /**
     * Returns a {@code UserPrefs} using the file at {@code storage}'s user prefs file path,
     * or a new {@code UserPrefs} with default configuration if errors occur when
     * reading from the file.
     */
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        Path prefsFilePath = storage.getUserPrefsFilePath();
        logger.info("Using prefs file : " + prefsFilePath);

        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataConversionException e) {
            logger.warning("UserPrefs file at " + prefsFilePath + " is not in the correct format. "
                    + "Using default user prefs");
            initializedPrefs = new UserPrefs();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty ContactList");
            initializedPrefs = new UserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting ContactList " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping LIFE application ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
