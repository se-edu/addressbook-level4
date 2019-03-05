package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.*;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;

import static java.util.Objects.requireNonNull;

public class FilterCommand extends Command{

    public static final String COMMAND_WORD = "filter";

    /**
     * Explanation of process numbers:
     * 0 -> clear
     * 1 -> or
     * 2 -> and
     */

    private final int processNum;
    private final String filterConditions;
    private String name;
    private String phone;
    private String email;
    private String[] tagList;
    private String address;
    
    private boolean isFilterCleared;

    public static final String MESSAGE_USAGE = COMMAND_WORD + " clear/or/and " + "[prefix/text/prefix] \n"
            + "Examples: \n"
            + COMMAND_WORD + " or  " + "p/91234567/p " + "e/johndoe@example.com/e" + " --> SAVES THE FILTER. IF ONE OF THE FILTER TYPES MATCH, IT PRINTS IT! \n"
            + COMMAND_WORD + " and " + "p/91234567/p " + "e/johndoe@example.com/e" + " --> SAVES THE FILTER. IF ALL OF THE FILTER TYPES MATCH, IT PRINTS IT! \n"
            + COMMAND_WORD + " clear \n" + " --> Clears all the filtering made previously.";

    public static final String MESSAGE_FILTER_PERSON_SUCCESS = "The Address Book is filtered.";
    public static final String MESSAGE_CLEAR_FILTER_PERSON_SUCCESS = "The Address Book is cleared from all the filters.";
    public static final String MESSAGE_NOT_FILTERED = "Filtering is not successful!";


    public FilterCommand(String filteringConditions, String[] criterion, int processNumber) {

        requireNonNull(filteringConditions);
        filterConditions = filteringConditions;
        processNum = processNumber;
        name = criterion[0];
        phone = criterion[1];
        email = criterion[2];
        address = criterion[3];
        isFilterCleared = false;

        if(criterion[4] != null) {
            String tags = criterion[4].trim().replaceAll(" +", " ");
            tagList = tags.split(" ");
        }
        else {
            tagList = null;
        }
    }

    private void ProcessCommand(Model model){

        // or statement will be processed
        if(processNum == 1)  model.filterOr(name, phone, email, address, tagList);

        // and statement will be processed
        else if(processNum == 2)  model.filterAnd(name, phone, email, address, tagList);

        // clear statement will be processed
        else  model.clearFilter();
    }


    @Override
    public CommandResult execute(Model model, CommandHistory history) {

        try {
            ProcessCommand(model);
            
            if(isFilterCleared) {
                isFilterCleared = false;
                return new CommandResult(MESSAGE_CLEAR_FILTER_PERSON_SUCCESS);
            }

            else {
                return new CommandResult(MESSAGE_FILTER_PERSON_SUCCESS);
            }
        }
        catch(Exception e) {
            return new CommandResult(MESSAGE_NOT_FILTERED);
        }
    }

}
