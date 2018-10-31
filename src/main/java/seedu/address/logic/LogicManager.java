package seedu.address.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.TranscriptParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyTranscript;
import seedu.address.model.module.Module;
import seedu.address.model.person.Person;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final CommandHistory history;
    private final TranscriptParser transcriptParser;
    private final AddressBookParser addressBookParser;

    //@@author alexkmj
    public LogicManager(Model model) {
        this.model = model;
        history = new CommandHistory();
        transcriptParser = new TranscriptParser();
        addressBookParser = new AddressBookParser();
    }

    //@@author alexkmj
    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        try {
            if (commandText.trim().startsWith("c_")) {
                Command command = transcriptParser.parseCommand(commandText);
                return command.execute(model, history);
            }

            Command command = addressBookParser.parseCommand(commandText);
            return command.execute(model, history);
        } finally {
            history.add(commandText);
        }
    }

    //@@author alexkmj
    @Override
    public ObservableList<Module> getFilteredModuleList() {
        return model.getFilteredModuleList();
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public ReadOnlyTranscript getTranscript() {
        return model.getTranscript();
    }

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }

    @Override
    public ObservableList<Module> getCompletedModuleList() {
        return model.getCompletedModuleList();
    }

    @Override
    public ObservableList<Module> getIncompleteModuleList() {
        return model.getIncompleteModuleList();
    }


}
