# yungyung04
###### \java\seedu\address\logic\commands\AddPersonalTaskCommand.java
``` java
/**
 * Adds a personal task into the schedule.
 */
public class AddPersonalTaskCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addtask";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a personal task into the schedule.\n"
            + "Parameters: "
            + "Date(dd/mm/yyyy) "
            + "Start time(hh:mm) "
            + "Duration(XXhXXm) "
            + "Description( anything; leading and trailing whitespaces will be trimmed )\n"
            + "Example: " + COMMAND_WORD + " "
            + "10/12/2018 "
            + "12:30 "
            + "1h30m "
            + "Yoga";
    public static final String MESSAGE_SUCCESS = "Task added: %1$s";

    private final PersonalTask toAdd;

    /**
     * Creates an AddPersonalTaskCommand to add the specified {@code Task}.
     */
    public AddPersonalTaskCommand(PersonalTask task) {
        requireNonNull(task);
        toAdd = task;
    }

```
###### \java\seedu\address\logic\commands\AddPersonalTaskCommand.java
``` java
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddPersonalTaskCommand // instanceof handles nulls
                && toAdd.equals(((AddPersonalTaskCommand) other).toAdd));
    }
}
```
###### \java\seedu\address\logic\commands\AddTuitionTaskCommand.java
``` java

/**
 * Adds a tuition (task) into the schedule.
 */
public class AddTuitionTaskCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addtuition";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a tuition (task) into the schedule.\n"
            + "Parameters: "
            + "tutee_index"
            + "Date(dd/mm/yyyy) "
            + "Start time(hh:mm) "
            + "Duration(XXhXXm) "
            + "Description( anything; leading and trailing whitespaces will be trimmed )\n"
            + "Example: " + COMMAND_WORD + " "
            + "1 "
            + "10/12/2018 "
            + "12:30 "
            + "1h30m "
            + "Calculus homework page 24";

    public static final String MESSAGE_SUCCESS = "New tuition task added.";

    private final Index targetIndex;
    private final LocalDateTime taskdateTime;
    private final String duration;
    private final String description;

    private TuitionTask toAdd;
    private String associatedTutee;

    /**
     * Creates an AddTuition to add the specified {@code Task} which is associated to {@code Tutee}.
     */
    public AddTuitionTaskCommand(Index targetIndex, LocalDateTime taskDateTime, String duration, String description) {
        requireNonNull(taskDateTime);
        requireNonNull(duration);
        requireNonNull(description);
        this.targetIndex = targetIndex;
        this.taskdateTime = taskDateTime;
        this.duration = duration;
        this.description = description;
    }

```
###### \java\seedu\address\logic\commands\AddTuitionTaskCommand.java
``` java
    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        associatedTutee = getAssociatedTutee().getName().fullName;
        toAdd = new TuitionTask(associatedTutee, taskdateTime, duration, description);
    }

    /**
     * Returns the {@code Tutee} object that is pointed by the index as shown in the last displayed conatct list.
     */
    private Tutee getAssociatedTutee() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        requireNonNull(lastShownList.get(targetIndex.getZeroBased()));
        Person associatedPerson = lastShownList.get(targetIndex.getZeroBased());
        if (!(associatedPerson instanceof Tutee)) {
            throw new CommandException(Messages.MESSAGE_INVALID_TUTEE_INDEX);
        }
        return (Tutee) lastShownList.get(targetIndex.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddTuitionTaskCommand // instanceof handles nulls
                && targetIndex.equals(((AddTuitionTaskCommand) other).targetIndex))
                && taskdateTime.equals(((AddTuitionTaskCommand) other).taskdateTime)
                && duration.equals(((AddTuitionTaskCommand) other).duration)
                && description.equals(((AddTuitionTaskCommand) other).description);

    }
}
```
###### \java\seedu\address\logic\commands\DeleteTaskCommand.java
``` java
/**
 * Deletes a task from the schedule.
 */
public class DeleteTaskCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "deletetask";

    public static final String MESSAGE_USAGE =
            COMMAND_WORD + ": Deletes a tuition or personal task from the schedule.\n"
            + "Parameters: "
            + "index of Task"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Deleted task : %1$s";

    private final Index targetIndex;
    private Task toDelete;

    public DeleteTaskCommand(Index indexOfTask) {
        targetIndex = indexOfTask;
    }

    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(toDelete);
        try {
            model.deleteTask(toDelete);
        } catch (TaskNotFoundException tnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, toDelete.toString()));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        toDelete = getAssociatedTask();
    }

    private Task getAssociatedTask() throws CommandException {
        List<Task> lastShownTaskList = model.getFilteredTaskList();

        if (targetIndex.getZeroBased() >= lastShownTaskList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        return lastShownTaskList.get(targetIndex.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteTaskCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteTaskCommand) other).targetIndex))
                && Objects.equals(this.toDelete, ((DeleteTaskCommand) other).toDelete);
    }
}
```
###### \java\seedu\address\logic\commands\FindPersonCommand.java
``` java
/**
 * Finds and lists all persons in contact list based on the specified filter category.
 */
public class FindPersonCommand extends Command {
    public static final String COMMAND_WORD = "findpersonby";
    public static final String COMMAND_ALIAS = "f";

    public static final String MESSAGE_SUCCESS = "Find is successful.";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": lists all person that suit the specified category\n"
            + "Parameters: filter_category keyword\n"
            + "Choice of filter_categories: "
            + CATEGORY_NAME + ", "
            + CATEGORY_EDUCATION_LEVEL + ", "
            + CATEGORY_GRADE + ", "
            + CATEGORY_SCHOOL + ", "
            + CATEGORY_SUBJECT + "\n"
            + "Example: " + COMMAND_WORD + " " + CATEGORY_GRADE + " A";

    private final String category;
    private final String[] keywords;
    private Predicate<Person> personPredicate;
    private Predicate<Task> taskPredicate;

    public FindPersonCommand(String category, String[] keywords) {
        this.category = category;
        this.keywords = keywords;
    }

    @Override
    public CommandResult execute() {
        switch (category) {
        case CATEGORY_NAME:
            personPredicate = new NameContainsKeywordsPredicate(Arrays.asList(keywords));
            model.updateFilteredPersonList(personPredicate);
            break;
        case CATEGORY_EDUCATION_LEVEL:
            personPredicate = new EducationLevelContainsKeywordsPredicate(Arrays.asList(keywords));
            model.updateFilteredPersonList(personPredicate);
            break;
        case CATEGORY_GRADE:
            personPredicate = new GradeContainsKeywordsPredicate(Arrays.asList(keywords));
            model.updateFilteredPersonList(personPredicate);
            break;
        case CATEGORY_SCHOOL:
            personPredicate = new SchoolContainsKeywordsPredicate(Arrays.asList(keywords));
            model.updateFilteredPersonList(personPredicate);
            break;
        case CATEGORY_SUBJECT:
            personPredicate = new SubjectContainsKeywordsPredicate(Arrays.asList(keywords));
            model.updateFilteredPersonList(personPredicate);
            break;
        default:
            // invalid category should be detected in parser instead
            assert (false);
        }
        return new CommandResult(MESSAGE_SUCCESS + "\n"
                + getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindPersonCommand // instanceof handles nulls
                && category.equals(((FindPersonCommand) other).category)
                && hasSameValue(keywords, ((FindPersonCommand) other).keywords));
    }

    /**
     * Returns true if both the given arrays of String contain the same elements.
     */
    private boolean hasSameValue(String[] firstKeywords, String[] secondKeywords) {
        if (firstKeywords.length != secondKeywords.length) {
            return false;
        }

        for (int i = 0; i < firstKeywords.length; i++) {
            if (!firstKeywords[i].equals(secondKeywords[i])) {
                return false;
            }
        }
        return true;
    }
}
```
###### \java\seedu\address\logic\commands\FindTaskCommand.java
``` java
/**
 * Finds and lists all tasks in the task list based on the specified filter category.
 */
public class FindTaskCommand extends Command {
    public static final String COMMAND_WORD = "findtaskby";

    public static final String MESSAGE_SUCCESS = "Find is successful.";

    public static final String INPUT_TYPE_BETWEEN = "between";
    public static final String INPUT_TYPE_NAMELY = "namely";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": lists all tasks that suit the specified category\n"
            + "Parameters: CATEGORY FIND_TYPE KEYWORDS\n"
            + "1st Example: " + COMMAND_WORD + " " + CATEGORY_MONTH + " " + INPUT_TYPE_BETWEEN + " April October\n"
            + "2nd Example: " + COMMAND_WORD + " " + CATEGORY_MONTH + " " + INPUT_TYPE_NAMELY
            + " 2 05 Aug December now";

    private final String category;
    private final String[] keywords;
    private Predicate<Task> taskPredicate;

    public FindTaskCommand(String category, String[] keywords) {
        this.category = category;
        this.keywords = keywords;
    }

    @Override
    public CommandResult execute() {
        switch (category) {
        case CATEGORY_MONTH:
            taskPredicate = new MonthContainsKeywordsPredicate(Arrays.asList(keywords));
            model.updateFilteredTaskList(taskPredicate);
            break;
        default:
            // invalid category should be detected in parser instead
            assert (false);
        }
        return new CommandResult(MESSAGE_SUCCESS + "\n"
                + getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindTaskCommand // instanceof handles nulls
                && category.equals(((FindTaskCommand) other).category)
                && hasSameValue(keywords, ((FindTaskCommand) other).keywords));
    }

    /**
     * Returns true if both the given arrays of String contain the same elements.
     */
    private boolean hasSameValue(String[] firstKeywords, String[] secondKeywords) {
        if (firstKeywords.length != secondKeywords.length) {
            return false;
        }

        for (int i = 0; i < firstKeywords.length; i++) {
            if (!firstKeywords[i].equals(secondKeywords[i])) {
                return false;
            }
        }
        return true;
    }
}
```
###### \java\seedu\address\logic\commands\ListTuteeCommand.java
``` java
/**
 * Lists all tutees in the application to the user.
 */
public class ListTuteeCommand extends Command {

    public static final String COMMAND_WORD = "listtutee";

    public static final String MESSAGE_SUCCESS = "Listed all tutees";

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_TUTEES);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### \java\seedu\address\logic\commands\SortPersonCommand.java
``` java
/**
 * Sorts all persons from the last shown list lexicographically according to the specified sorting category.
 * Since tutee contains specific information such as grade,
 * a Person who is not a tutee will be listed last when such information is selected to be the sorting category.
 */
public class SortPersonCommand extends Command {
    public static final String COMMAND_WORD = "sortpersonby";
    public static final String COMMAND_ALIAS = "spb";

    public static final String MESSAGE_SUCCESS = "sorted list of persons successfully";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": sorts all visible persons lexicographically according to the specified sorting category.\n"
            + "Persons who are not Tutees will be listed last when a tutee detail is the selected category "
            + "(refer to User Guide)\n"
            + "Parameter: sort_category\n"
            + "Choice of sort_categories: "
            + CATEGORY_NAME + ", "
            + CATEGORY_EDUCATION_LEVEL + ", "
            + CATEGORY_GRADE + ", "
            + CATEGORY_SCHOOL + ", "
            + CATEGORY_SUBJECT + "\n"
            + "Example: " + COMMAND_WORD + " " + CATEGORY_GRADE;

    private final String category;
    private final Comparator<Person> comparator;

    public SortPersonCommand(String category) {
        this.category = category;
        comparator = new PersonSortUtil().getComparator(category);
    }

    @Override
    public CommandResult execute() {
        model.sortFilteredPersonList(comparator);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortPersonCommand // instanceof handles nulls
                && category.equals(((SortPersonCommand) other).category));
    }
}
```
###### \java\seedu\address\logic\commands\SortTaskCommand.java
``` java
/**
 * Sorts all tasks from the last shown list according to the specified sorting category in an increasing order
 */
public class SortTaskCommand extends Command {
    public static final String COMMAND_WORD = "sorttaskby";
    public static final String COMMAND_ALIAS = "stb";
    public static final String MESSAGE_SUCCESS = "sorted list of tasks successfully";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + "Sorts all visible tasks according to the specified sorting category in an increasing order\n"
            + "Parameter: sort_category\n"
            + "Choice of sort_categories: "
            + CATEGORY_MONTH + ", "
            + CATEGORY_DATE_TIME + "\n"
            + "Example: " + COMMAND_WORD + " " + CATEGORY_MONTH;

    private final String category;
    private final Comparator<Task> comparator;

    public SortTaskCommand(String category) {
        requireNonNull(category);
        this.category = category;
        comparator = new TaskSortUtil().getComparator(category);
    }

    @Override
    public CommandResult execute() {
        requireNonNull(comparator);
        model.sortFilteredTaskList(comparator);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortTaskCommand // instanceof handles nulls
                && category.equals(((SortTaskCommand) other).category));
    }
}
```
###### \java\seedu\address\logic\parser\AddPersonalTaskCommandParser.java
``` java

/**
 * Parses input arguments and creates a new AddPersonalTaskCommand object.
 */
public class AddPersonalTaskCommandParser implements Parser<AddPersonalTaskCommand> {

    private static final String INPUT_FORMAT_VALIDATION_REGEX = "(\\d{2}/\\d{2}/\\d{4})\\s\\d{2}:\\d{2}\\s"
            + "\\d{1,2}h\\d{1,2}m.*";
    private static final int MAXIMUM_AMOUNT_OF_TASK_PARAMETER = 4;
    private static final int INDEX_OF_DATE = 0;
    private static final int INDEX_OF_TIME = 1;
    private static final int INDEX_OF_DURATION = 2;

    /**
     * Parses the given {@code String} of arguments in the context of the AddPersonalTaskCommand
     * and returns an AddPersonalTaskCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddPersonalTaskCommand parse(String task) throws ParseException {
        if (!task.trim().matches(INPUT_FORMAT_VALIDATION_REGEX)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPersonalTaskCommand.MESSAGE_USAGE));
        }

        String[] arguments = task.trim().split("\\s+", MAXIMUM_AMOUNT_OF_TASK_PARAMETER);
        try {
            LocalDateTime taskDateTime =
                    ParserUtil.parseDateTime(arguments[INDEX_OF_DATE] + " " + arguments[INDEX_OF_TIME]);
            String duration = ParserUtil.parseDuration(arguments[INDEX_OF_DURATION]);
            String description = ParserUtil.parseDescription(arguments, MAXIMUM_AMOUNT_OF_TASK_PARAMETER);

            return new AddPersonalTaskCommand(new PersonalTask(taskDateTime, duration, description));
        } catch (DateTimeParseException dtpe) {
            throw new ParseException(MESSAGE_INVALID_DATE_TIME + "\n"
                    + AddPersonalTaskCommand.MESSAGE_USAGE);
        } catch (DurationParseException dpe) {
            throw new ParseException(MESSAGE_INVALID_DURATION + "\n"
                    + AddPersonalTaskCommand.MESSAGE_USAGE);
        }
    }
}
```
###### \java\seedu\address\logic\parser\AddTuitionTaskCommandParser.java
``` java
/**
 * Parses input arguments and creates a new AddTuitionTaskCommand object
 */
public class AddTuitionTaskCommandParser implements Parser<AddTuitionTaskCommand> {

    private static final String INPUT_FORMAT_VALIDATION_REGEX = "\\d+\\s(\\d{2}/\\d{2}/\\d{4})\\s\\d{2}:\\d{2}\\s"
            + "\\d{1,2}h\\d{1,2}m.*";
    private static final int MAXIMUM_AMOUNT_OF_TASK_PARAMETER = 5;
    private static final int INDEX_OF_PERSON_INDEX = 0;
    private static final int INDEX_OF_DATE = 1;
    private static final int INDEX_OF_TIME = 2;
    private static final int INDEX_OF_DURATION = 3;

    /**
     * Parses the given {@code String} of arguments in the context of the AddTuitionTaskCommand
     * and returns an AddTuitionTaskCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddTuitionTaskCommand parse(String args) throws ParseException {
        if (!args.trim().matches(INPUT_FORMAT_VALIDATION_REGEX)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTuitionTaskCommand.MESSAGE_USAGE));
        }

        String[] arguments = args.trim().split("\\s+", MAXIMUM_AMOUNT_OF_TASK_PARAMETER);
        try {
            Index personIndex = ParserUtil.parseIndex(arguments[INDEX_OF_PERSON_INDEX]);
            LocalDateTime taskDateTime =
                    ParserUtil.parseDateTime(arguments[INDEX_OF_DATE] + " " + arguments[INDEX_OF_TIME]);
            String duration = ParserUtil.parseDuration(arguments[INDEX_OF_DURATION]);
            String description = ParserUtil.parseDescription(arguments, MAXIMUM_AMOUNT_OF_TASK_PARAMETER);

            return new AddTuitionTaskCommand(personIndex, taskDateTime, duration, description);
        } catch (DateTimeParseException dtpe) {
            throw new ParseException(MESSAGE_INVALID_DATE_TIME);
        } catch (DurationParseException dpe) {
            throw new ParseException(MESSAGE_INVALID_DURATION);
        } catch (IllegalValueException ive) {
            throw new ParseException(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
    }
}
```
###### \java\seedu\address\logic\parser\DeleteTaskCommandParser.java
``` java
/**
 * Parses input arguments and creates a new DeleteTaskCommand object
 */
public class DeleteTaskCommandParser implements Parser<DeleteTaskCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteTaskCommand
     * and returns an DeleteTaskCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteTaskCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteTaskCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTaskCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\logic\parser\exceptions\InvalidBoundariesException.java
``` java
/**
 * Signals that the given keywords cannot serve as valid boundaries
 */
public class InvalidBoundariesException extends Exception {
    public InvalidBoundariesException() {};
}
```
###### \java\seedu\address\logic\parser\FindPersonCommandParser.java
``` java
/**
 * Parses input arguments and creates a new FindPersonCommand object
 */
public class FindPersonCommandParser implements Parser<FindPersonCommand> {

    private static final int EXPECTED_AMOUNT_OF_PARAMETERS = 2;
    private static final int INDEX_OF_FILTER_CATEGORY = 0;
    private static final int INDEX_OF_KEYWORDS = 1;

    private List<String> validCategories =
            new ArrayList<>(Arrays.asList(CATEGORY_NAME, CATEGORY_EDUCATION_LEVEL, CATEGORY_GRADE,
                    CATEGORY_SCHOOL, CATEGORY_SUBJECT));

    /**
     * Parses the given {@code String} of arguments in the context of the FindPersonCommand
     * and returns a FindPersonCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindPersonCommand parse(String args) throws ParseException {
        String[] arguments = args.trim().toLowerCase().split("\\s+", EXPECTED_AMOUNT_OF_PARAMETERS);

        if (arguments.length < EXPECTED_AMOUNT_OF_PARAMETERS) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPersonCommand.MESSAGE_USAGE));
        }

        String filterCategory = arguments[INDEX_OF_FILTER_CATEGORY];
        String[] keywords = arguments[INDEX_OF_KEYWORDS].split("\\s+");

        if (!validCategories.contains(filterCategory)) {
            throw new ParseException(String.format(MESSAGE_INVALID_FILTER_CATEGORY, FindPersonCommand.MESSAGE_USAGE));
        }

        return new FindPersonCommand(filterCategory, keywords);
    }
}
```
###### \java\seedu\address\logic\parser\FindTaskCommandParser.java
``` java
/**
 * Parses input arguments and creates a new FindTaskCommand object
 */
public class FindTaskCommandParser implements Parser<FindTaskCommand> {

    private static final int EXPECTED_AMOUNT_OF_PARAMETERS = 3;
    private static final int INDEX_OF_FILTER_CATEGORY = 0;
    private static final int INDEX_OF_INPUT_TYPE = 1;
    private static final int INDEX_OF_KEYWORDS = 2;
    private static final int INDEX_OF_FIRST_KEYWORD = 0;
    private static final int INDEX_OF_SECOND_KEYWORD = 1;
    private static final int INVALID_MONTH = -1;
    private static final int MONTH_WITH_MMM_FORMAT_CHARACTER_LENGTH = 3;
    private static final int REQUIRED_AMOUNT_OF_BOUNDARIES = 2;
    private static final int MONTH_WITH_MM_FORMAT_CHARACTER_LENGTH = 2;
    private static final int AMOUNT_OF_MONTHS = 12;
    private static final String INPUT_TYPE_NAMELY = "namely";
    private static final String INPUT_TYPE_BETWEEN = "between";
    private static final DateTimeFormatter FORMATTER_MONTH_MM = new DateTimeFormatterBuilder().parseCaseInsensitive()
            .appendPattern("MM").toFormatter(Locale.ENGLISH);
    private static final DateTimeFormatter FORMATTER_MONTH_MMM = new DateTimeFormatterBuilder().parseCaseInsensitive()
            .appendPattern("MMM").toFormatter(Locale.ENGLISH);
    private static final DateTimeFormatter FORMATER_MONTH_MMMM = new DateTimeFormatterBuilder().parseCaseInsensitive()
            .appendPattern("MMMM").toFormatter(Locale.ENGLISH);

    private List<String> validCategories = new ArrayList<>(Arrays.asList(CATEGORY_MONTH));
    private List<String> validInputTypes = new ArrayList<>(Arrays.asList(INPUT_TYPE_NAMELY, INPUT_TYPE_BETWEEN));

    /**
     * Parses the given {@code String} of arguments in the context of the FindTaskCommand
     * and returns a FindTaskCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindTaskCommand parse(String args) throws ParseException {
        String[] arguments = args.trim().toLowerCase().split("\\s+", EXPECTED_AMOUNT_OF_PARAMETERS);
        if (arguments.length < EXPECTED_AMOUNT_OF_PARAMETERS) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTaskCommand.MESSAGE_USAGE));
        }

        String filterCategory = arguments[INDEX_OF_FILTER_CATEGORY];
        if (!validCategories.contains(filterCategory)) {
            throw new ParseException(String.format(MESSAGE_INVALID_FILTER_CATEGORY, FindTaskCommand.MESSAGE_USAGE));
        }

        String inputType = arguments[INDEX_OF_INPUT_TYPE];
        if (!validInputTypes.contains(inputType)) {
            throw new ParseException(String.format(MESSAGE_INVALID_INPUT_TYPES, FindTaskCommand.MESSAGE_USAGE));
        }

        String[] keywords = arguments[INDEX_OF_KEYWORDS].split("\\s+");

        try {
            switch (filterCategory) {
            case CATEGORY_MONTH:
                keywords = parseMonthKeywords(inputType, keywords);
                break;
            default:
                assert (false); // should never be called
            }
        } catch (DateTimeParseException dtpe) {
            throw new ParseException(MESSAGE_INVALID_KEYWORD_GIVEN);
        } catch (InvalidBoundariesException ibe) {
            throw new ParseException(MESSAGE_INVALID_MONTH_RANGE_FORMAT);
        }
        return new FindTaskCommand(filterCategory, keywords);
    }

    /**
     * Parses month keywords into the required form for the purpose of creating a FindTaskCommand
     * @throws ParseException if the given input type is not recognized.
     * @throws DateTimeParseException if any of the keywords given is an invalid month
     * @throws InvalidBoundariesException if the given keywords are invalid boundary values
     */
    private String[] parseMonthKeywords(String inputType, String[] keywords) throws DateTimeParseException,
            InvalidBoundariesException {
        int[] months;
        String[] convertedKeywords = NaturalLanguageIdentifier.getInstance()
                .convertNaturalLanguagesIntoMonths(keywords);
        months = parseMonthsAsIntegers(convertedKeywords);
        if (inputType.equals(INPUT_TYPE_BETWEEN)) {
            if (!hasValidMonthBoundaries(months)) {
                throw new InvalidBoundariesException();
            }
            months = getAllMonthsBetweenBoundaries(months[INDEX_OF_FIRST_KEYWORD], months[INDEX_OF_SECOND_KEYWORD]);
        }
        convertedKeywords = convertIntoStrings(months);
        return convertedKeywords;
    }

    /**
     * Converts an array of integer into an array of String with the same value.
     */
    private String[] convertIntoStrings(int[] integers) {
        String[] strings = new String[integers.length];
        for (int i = 0; i < integers.length; i++) {
            strings[i] = Integer.toString(integers[i]);
        }
        return strings;
    }

    /**
     * Returns all months given two month boundaries.
     */
    private int[] getAllMonthsBetweenBoundaries(int lowerBoundary, int upperBoundary) {
        int monthDifference;
        int[] monthsWithinRange;

        if (lowerBoundary < upperBoundary) {
            monthDifference = upperBoundary - lowerBoundary + 1;
            monthsWithinRange = new int[monthDifference];
            for (int i = 0; i < monthDifference; i++) {
                monthsWithinRange[i] = lowerBoundary + i;
            }
        } else {
            monthDifference = upperBoundary + AMOUNT_OF_MONTHS + 1 - lowerBoundary;
            monthsWithinRange = new int[monthDifference];
            for (int i = 0; i < monthDifference; i++) {
                if (lowerBoundary + i <= AMOUNT_OF_MONTHS) {
                    monthsWithinRange[i] = lowerBoundary + i;
                } else {
                    monthsWithinRange[i] = lowerBoundary + i - AMOUNT_OF_MONTHS;
                }
            }
        }
        return monthsWithinRange;
    }

    /**
     * Returns true if the given months are valid boundaries.
     */
    private boolean hasValidMonthBoundaries(int[] months) {
        return months.length == REQUIRED_AMOUNT_OF_BOUNDARIES
                && months[INDEX_OF_FIRST_KEYWORD] != months[INDEX_OF_SECOND_KEYWORD];
    }

    /**
     * Parses given {@code String[]} of months into their integer representation.
     * @throws DateTimeParseException if any of the given month is invalid.
     */
    private int[] parseMonthsAsIntegers(String[] keywords) throws DateTimeParseException {
        int[] months = new int[keywords.length];
        for (int i = 0; i < keywords.length; i++) {
            months[i] = parseMonthAsInteger(keywords[i]);
        }
        return months;
    }

    /**
     * Parses given {@code String} of month into its integer representation.
     * @throws DateTimeParseException if the given month is invalid.
     */
    private int parseMonthAsInteger(String monthString) throws DateTimeParseException {
        TemporalAccessor accessor;
        int month = INVALID_MONTH;
        if (monthString.length() < MONTH_WITH_MM_FORMAT_CHARACTER_LENGTH) {
            accessor = FORMATTER_MONTH_MM.parse("0" + monthString);
            month = accessor.get(ChronoField.MONTH_OF_YEAR);
        } else if (monthString.length() == MONTH_WITH_MM_FORMAT_CHARACTER_LENGTH) {
            accessor = FORMATTER_MONTH_MM.parse(monthString);
            month = accessor.get(ChronoField.MONTH_OF_YEAR);
        } else if (monthString.length() == MONTH_WITH_MMM_FORMAT_CHARACTER_LENGTH) {
            accessor = FORMATTER_MONTH_MMM.parse(monthString);
            month = accessor.get(ChronoField.MONTH_OF_YEAR);
        } else if (monthString.length() > MONTH_WITH_MMM_FORMAT_CHARACTER_LENGTH) {
            accessor = FORMATER_MONTH_MMMM.parse(monthString);
            month = accessor.get(ChronoField.MONTH_OF_YEAR);
        }
        return month;
    }
}
```
###### \java\seedu\address\logic\parser\NaturalLanguageIdentifier.java
``` java
/**
 * Provides utilities to recognize and translate natural language from user input into processable values
 */
public class NaturalLanguageIdentifier {
    public static final String NATURAL_NOW = "now";
    public static final String NATURAL_TODAY = "today";
    public static final String NATURAL_CURRENT = "current";
    public static final String NATURAL_LAST = "last";
    public static final String NATURAL_THIS = "this";
    public static final String NATURAL_NEXT = "next";
    public static final String NATURAL_MONTH = "month";
    public static final String NATURAL_LAST_MONTH = NATURAL_LAST + " " + NATURAL_MONTH;
    public static final String NATURAL_THIS_MONTH = NATURAL_THIS + " " + NATURAL_MONTH;
    public static final String NATURAL_NEXT_MONTH = NATURAL_NEXT + " " + NATURAL_MONTH;
    public static final String NATURAL_CURRENT_MONTH = NATURAL_CURRENT + " " + NATURAL_MONTH;

    private static List<String> twoWordedNaturalLanguages = new ArrayList<>(Arrays.asList(
            NATURAL_LAST_MONTH, NATURAL_THIS_MONTH, NATURAL_NEXT_MONTH, NATURAL_CURRENT_MONTH));

    private static NaturalLanguageIdentifier naturalLanguageIdentifier = null;
    private LocalDateTime currentDateTime = null;

    /**
     * Constructs a NaturalLanguageIdentifier object which stores the current date and time.
     */
    private NaturalLanguageIdentifier() {
        currentDateTime = LocalDateTime.now();
    }

    /**
     * Returns an instance of NaturalLanguageIdentifier object
     */
    public static NaturalLanguageIdentifier getInstance() {
        if (naturalLanguageIdentifier == null) {
            naturalLanguageIdentifier = new NaturalLanguageIdentifier();
        }
        return naturalLanguageIdentifier;
    }

    /**
     * Converts any keywords that are recognizable as month-related natural languages into their month representation.
     */
    public String[] convertNaturalLanguagesIntoMonths(String[] keywords) {
        String[] mergedKeywords = mergeTwoWordedNaturalLanguage(keywords);
        for (int i = 0; i < mergedKeywords.length; i++) {
            mergedKeywords[i] = getMonthAsString(mergedKeywords[i]);
        }
        return mergedKeywords;
    }

    /**
     * Converts natural language into its month representation if possible.
     */
    public String getMonthAsString(String userInput) {
        String result;
        switch (userInput) {
        case NATURAL_TODAY:
        case NATURAL_NOW:
        case NATURAL_CURRENT_MONTH:
        case NATURAL_THIS_MONTH:
            result = currentDateTime.getMonth().name();
            break;
        case NATURAL_LAST_MONTH:
            result = currentDateTime.minusMonths(1).getMonth().name();
            break;
        case NATURAL_NEXT_MONTH:
            result = currentDateTime.plusMonths(1).getMonth().name();
            break;
        default:
            result = userInput;
        }
        return result;
    }

    /**
     * Merges 2 adjoin strings if they can form a valid natural language.
     * Keywords are case-sensitive.
     */
    public static String[] mergeTwoWordedNaturalLanguage(String[] keywords) {
        if (keywords.length <= 1) {
            return keywords;
        }

        ArrayList<String> mergedKeywords = new ArrayList<>();
        for (int i = 0; i < keywords.length - 1; i++) {
            if (isMergeable(keywords[i], keywords[i + 1])) {
                mergedKeywords.add(keywords[i] + " " + keywords[i + 1]);
                i++;
            } else {
                mergedKeywords.add(keywords[i]);
            }
        }
        return mergedKeywords.toArray(new String[mergedKeywords.size()]);
    }

    /**
     * Checks whether 2 given words can form a valid natural language.
     */
    private static boolean isMergeable(String prefix, String suffix) {
        return twoWordedNaturalLanguages.contains(prefix + " " + suffix);
    }

}
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code String dateTime} into an {@code LocalDateTime}.
     *
     * @throws DateTimeParseException if the given {@code stringDateTime} is invalid.
     */
    public static LocalDateTime parseDateTime(String stringDateTime) throws DateTimeParseException {
        requireNonNull(stringDateTime);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu HH:mm")
                .withResolverStyle(ResolverStyle.STRICT);
        return LocalDateTime.parse(stringDateTime, formatter);
    }

    /**
     * Returns a valid duration
     *
     * @throws DurationParseException if the given {@code duration} is invalid.
     */
    public static String parseDuration(String duration) throws DurationParseException {
        requireNonNull(duration);
        String durationValidationRegex = "([0-9]|1[0-9]|2[0-3])h([0-5][0-9]|[0-9])m";
        if (!duration.matches(durationValidationRegex)) {
            throw new DurationParseException(MESSAGE_INVALID_DURATION);
        }
        return duration;
    }

    /**
     * Returns a valid task description.
     * If description does not exist, returns an empty String.
     */
    public static String parseDescription(String[] userInputs, int numberOfParametersWhenDescriptionExist) {
        requireNonNull(userInputs);
        requireNonNull(numberOfParametersWhenDescriptionExist);
        if (isEmptyDescription(userInputs, numberOfParametersWhenDescriptionExist)) {
            return EMPTY_STRING;
        } else {
            String description = getLastElement(userInputs);
            return description;
        }
    }

    /**
     * Returns the last element of an array of Strings.
     */
    private static String getLastElement(String[] userInputs) {
        return userInputs[userInputs.length - 1];
    }

    /**
     * Returns true if the given task arguments contain a task description.
     */
    private static boolean isEmptyDescription(String[] arguments, int numberOfParametersWhenDescriptionExist) {
        return arguments.length < numberOfParametersWhenDescriptionExist;
    }
}
```
###### \java\seedu\address\logic\parser\SortTaskCommandParser.java
``` java
/**
 * Parses input arguments and creates a new SortTaskCommand object
 */
public class SortTaskCommandParser implements Parser<SortTaskCommand> {

    private static final String SORT_CATEGORY_VALIDATION_REGEX = "\\p{Alpha}+";

    private List<String> validCategories =
            new ArrayList<>(Arrays.asList(CATEGORY_MONTH, CATEGORY_DATE_TIME));

    /**
     * Parses the given {@code String} of arguments in the context of the SortTaskCommand
     * and returns a SortPersonCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortTaskCommand parse(String args) throws ParseException {
        String sortCategory = args.trim().toLowerCase();

        if (!sortCategory.matches(SORT_CATEGORY_VALIDATION_REGEX)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortPersonCommand.MESSAGE_USAGE));
        }
        if (!validCategories.contains(sortCategory)) {
            throw new ParseException(String.format(MESSAGE_INVALID_SORTER_CATEGORY, SortPersonCommand.MESSAGE_USAGE));
        }
        return new SortTaskCommand(sortCategory);
    }
}
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void sortFilteredPersonList(Comparator<Person> comparator) {
        sortedPersons.setComparator(comparator);
    }

    @Override
    public void sortFilteredTaskList(Comparator<Task> comparator) {
        sortedTasks.setComparator(comparator);
    }

```
###### \java\seedu\address\model\personal\PersonalTask.java
``` java
    @Override
    public String toString() {
        if (hasDescription()) {
            return "Personal task with description " + description + " on "
                    + Integer.toString(taskDateTime.getDayOfMonth()) + " "
                    + taskDateTime.getMonth().name() + " " + Integer.toString(taskDateTime.getYear());
        } else {
            return "Personal task without description on " + Integer.toString(taskDateTime.getDayOfMonth())
                    + " " + taskDateTime.getMonth().name() + " " + Integer.toString(taskDateTime.getYear());
        }
    }

    /**
     * Returns true if the tuition task contains a non-empty description.
     */
    private boolean hasDescription() {
        return !description.equals(NULL_STRING);
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonalTask // instanceof handles nulls
                && taskDateTime.equals(((PersonalTask) other).taskDateTime)
                && duration.equals(((PersonalTask) other).duration)
                && description.equals(((PersonalTask) other).description));
    }
}
```
###### \java\seedu\address\model\task\exceptions\TaskNotFoundException.java
``` java
/**
 * Signals that the operation is unable to find the specified task.
 */
public class TaskNotFoundException extends Exception {}
```
###### \java\seedu\address\model\task\MonthContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code Task}'s month matches any of the {@code int month} given.
 */
public class MonthContainsKeywordsPredicate implements Predicate<Task> {
    private final List<String> keywords;

    public MonthContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Task task) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(
                        Integer.toString(task.getTaskDateTime().getMonthValue()), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.address.model.task.MonthContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords
                .equals(((seedu.address.model.task.MonthContainsKeywordsPredicate) other).keywords)); // state check
    }
}
```
###### \java\seedu\address\model\task\TaskSortUtil.java
``` java
/**
 * Provides utilities for sorting a list of Tasks.
 */
public class TaskSortUtil {
    public static final String CATEGORY_DATE_TIME = "datetime";
    public static final String CATEGORY_MONTH = "month";
    public static final String CATEGORY_DURATION = "duration";
    public static final int NEGATIVE_DIGIT = -1;
    public static final int POSITIVE_DIGIT = 1;

    /**
     * Returns the apppropriate Task comparator given the sorting category
    */
    public Comparator<Task> getComparator(String sortCategory) {
        Comparator<Task> comparator = null;

        switch (sortCategory) {
        case CATEGORY_MONTH:
            comparator = getMonthComparator();
            break;
        case CATEGORY_DATE_TIME:
            comparator = getDateTimeComparator();
            break;
        default:
            assert (false); //invalid sortCategory should be identified in parser.
        }
        return comparator;
    }

    /**
     * Returns a comparator which is useful for sorting tasks based on the month sequence in an increasing order.
     */
    private Comparator<Task> getMonthComparator() {
        return new Comparator<Task>() {
            @Override
            public int compare(Task task1, Task task2) {
                int month1 = task1.getTaskDateTime().getMonthValue();
                int month2 = task2.getTaskDateTime().getMonthValue();

                if (month1 != month2) {
                    return compareByMonth(month1, month2);
                } else {
                    return compareByTime(task1.getTaskDateTime(), task2.getTaskDateTime());
                }
            }
        };
    }

    /**
    Returns a comparator which is useful for sorting tasks based on the date and time sequence in an increasing order.
    */
    private Comparator<Task> getDateTimeComparator() {
        return new Comparator<Task>() {
            @Override
            public int compare(Task task1, Task task2) {
                return compareByTime(task1.getTaskDateTime(), task2.getTaskDateTime());
            }
        };
    }

    /**
     * Compares the 2 given months and returns an integer according to their sequence in standard Gregorian calendar.
     */
    private int compareByMonth(int month1, int month2) {
        if (month1 < month2) {
            return NEGATIVE_DIGIT;
        } else {
            return POSITIVE_DIGIT;
        }
    }
    /**
     * Compares the 2 given {@code LocalDateTime} and
     * Returns an integer according to their sequence in standard Gregorian calendar.
     */
    private int compareByTime(LocalDateTime dateTime1, LocalDateTime dateTime2) {
        assert (!dateTime1.isEqual(dateTime2)); //time should be different due to thrown exception when task is added
        if (dateTime1.isBefore(dateTime2)) {
            return NEGATIVE_DIGIT;
        } else {
            return POSITIVE_DIGIT;
        }
    }
}
```
###### \java\seedu\address\model\tutee\TuitionTask.java
``` java
    @Override
    public String toString() {
        if (hasDescription()) {
            return "Tuition task with description " + description + " on "
                    + Integer.toString(taskDateTime.getDayOfMonth()) + " " + taskDateTime.getMonth().name()
                    + " " + Integer.toString(taskDateTime.getYear());
        } else {
            return "Tuition task without description on " + Integer.toString(taskDateTime.getDayOfMonth())
                    + " " + taskDateTime.getMonth().name() + " " + Integer.toString(taskDateTime.getYear());
        }
    }

    /**
     * Returns true if the tuition task contains a non-empty description.
     */
    private boolean hasDescription() {
        return !description.equals(NULL_STRING);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TuitionTask // instanceof handles nulls
                && tutee.equals(((TuitionTask) other).tutee)
                && taskDateTime.equals(((TuitionTask) other).taskDateTime)
                && duration.equals(((TuitionTask) other).duration)
                && description.equals(((TuitionTask) other).description));
    }

    public String getTuitionTitle() {
        return String.format(TUITION_TITLE, tutee);
    }
}
```
###### \java\seedu\address\storage\XmlAdaptedPerson.java
``` java
        if (isTutee(personTags)) {
            if (this.subject == null) {
                throw new IllegalValueException(
                        String.format(MISSING_FIELD_MESSAGE_FORMAT, Subject.class.getSimpleName()));
            }
            if (!Subject.isValidSubject(this.subject)) {
                throw new IllegalValueException(Subject.MESSAGE_SUBJECT_CONSTRAINTS);
            }
            final Subject subject = new Subject(this.subject);

            if (this.grade == null) {
                throw new IllegalValueException(
                        String.format(MISSING_FIELD_MESSAGE_FORMAT, Grade.class.getSimpleName()));
            }
            if (!Grade.isValidGrade(this.grade)) {
                throw new IllegalValueException(Grade.MESSAGE_GRADE_CONSTRAINTS);
            }
            final Grade grade = new Grade(this.grade);

            if (this.educationLevel == null) {
                throw new IllegalValueException(
                        String.format(MISSING_FIELD_MESSAGE_FORMAT, EducationLevel.class.getSimpleName()));
            }
            if (!EducationLevel.isValidEducationLevel(this.educationLevel)) {
                throw new IllegalValueException(EducationLevel.MESSAGE_EDUCATION_LEVEL_CONSTRAINTS);
            }
            final EducationLevel educationLevel = new EducationLevel(this.educationLevel);

            if (this.school == null) {
                throw new IllegalValueException(
                        String.format(MISSING_FIELD_MESSAGE_FORMAT, School.class.getSimpleName()));
            }
            if (!School.isValidSchool(this.school)) {
                throw new IllegalValueException(School.MESSAGE_SCHOOL_CONSTRAINTS);
            }
            final School school = new School(this.school);

            return new Tutee(name, phone, email, address, subject, grade, educationLevel, school, tags);
        } else {
            return new Person(name, phone, email, address, tags);
        }
    }
```
