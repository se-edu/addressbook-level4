package seedu.address.logic;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.ExecutionResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the entire execution process, including the parsing of input and execution of command.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    ExecutionResult execute(String commandText) throws CommandException, ParseException;

    /** Returns the filtered list of persons */
    ObservableList<ReadOnlyPerson> getFilteredPersonList();

}
