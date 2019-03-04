package seedu.address.logic.commands;


import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.sortMethods.SortAlphabetical;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.logic.parser.SortWord;

import static java.util.Objects.requireNonNull;

import java.util.List;
/**
import static seedu.address.logic.parser.CliSyntax.SORTIT_AGE;
import static seedu.address.logic.parser.CliSyntax.SORTIT_ALPHABETICAL;
 */

/**
 * Sorts all persons in the address book and lists to the user.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts all persons in address book "
            + "according to the specified keyword and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD \n"
            + "Example: " + COMMAND_WORD + " age";

    public static final String MESSAGE_SUCCESS = "Sorted all persons";

    private final SortWord method;

    private List<Person> sortedPersons;

    //private final SortResultPredicate predicate;

    public SortCommand(SortWord method) {
        this.method = method;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        String MESSAGE_SUCCESS = "Sorted all persons by " + method.toString();
        //Maybe use switch statement here?
        if (this.method.getSortWord().equals("alphabetical")) {
            SortAlphabetical sorted = new SortAlphabetical(lastShownList);
            sortedPersons = sorted.getList();
        }


        for (Person personToDelete : sortedPersons) {
            model.deletePerson(personToDelete);
        }
        for (Person newPerson : sortedPersons) {
            System.out.println("Should be adding");
            model.addPerson(newPerson);
        }
        model.commitAddressBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
