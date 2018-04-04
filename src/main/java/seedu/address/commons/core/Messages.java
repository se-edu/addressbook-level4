package seedu.address.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_INPUT_TYPES = "The input type specified is invalid \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_INVALID_TASK_DISPLAYED_INDEX = "The task index provided is invalid";
    public static final String MESSAGE_INVALID_TUTEE_INDEX = "The person index provided is not a tutee";
    public static final String MESSAGE_INVALID_FILTER_CATEGORY = "The filter category specified is invalid \n%1$s";
    public static final String MESSAGE_INVALID_SORTER_CATEGORY = "The sort category specified is invalid \n%1$s";
    public static final String MESSAGE_INVALID_DATE_TIME = "The input date and time is invalid";
    public static final String MESSAGE_INVALID_DURATION = "The duration format is invalid";
    public static final String MESSAGE_INVALID_INPUT_FORMAT = "The input format is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_TASK_LISTED_OVERVIEW = "%1$d tasks listed!";
    public static final String MESSAGE_INVALID_MONTH_RANGE_FORMAT = "The range of months follows an invalid format.\n"
            + "There should only be 2 different months mentioned\n and the first month mentioned must be an earlier"
            + " month than the second month";
    public static final String MESSAGE_TASK_TIMING_CLASHES = "This task clashes with another task";
}
