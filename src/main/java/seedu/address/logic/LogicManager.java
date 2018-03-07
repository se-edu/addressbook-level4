package seedu.address.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.LockCommand;
import seedu.address.logic.commands.UnlockCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private static boolean isKnocked = false;
    private static String password;
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);
    private final Model model;
    private final CommandHistory history;
    private final AddressBookParser addressBookParser;
    private final UndoRedoStack undoRedoStack;
    public LogicManager(Model model) {
        this.model = model;
        history = new CommandHistory();
        addressBookParser = new AddressBookParser();
        undoRedoStack = new UndoRedoStack();
        isKnocked = false;
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        try {
            Command command;
            CommandResult result = new CommandResult("");
            if (isKnocked) {
                command = addressBookParser.parseCommand(commandText);
                if (command instanceof UnlockCommand) {
                    UnlockCommand unknockCommand = (UnlockCommand) command;
                    if (unknockCommand.getPassword().compareTo(password) == 0) {
                        isKnocked = false;
                        result = new CommandResult(UnlockCommand.MESSAGE_SUCCESS);
                    } else {
                        result = new CommandResult("incorrect unlock password!");
                    }
                } else {
                    result = new CommandResult("Addressbook has been locked, please unlock it first!");
                }
            } else {
                command = addressBookParser.parseCommand(commandText);
                command.setData(model, history, undoRedoStack);
                result = command.execute();
                if (command instanceof LockCommand) {
                    isKnocked = true;
                    if (((LockCommand) command).getPassword() != null) {
                        password = ((LockCommand) command).getPassword();
                    } else {
                        password = "nopassword";
                    }
                }
                undoRedoStack.push(command);

            }
            return result;
        } finally {
            history.add(commandText);
        }
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static boolean isKnocked() {
        return isKnocked;
    }


}
