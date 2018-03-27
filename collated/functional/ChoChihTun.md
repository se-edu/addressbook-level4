# ChoChihTun
###### \java\seedu\address\logic\commands\AddTuteeCommand.java
``` java
/**
 * Adds a tutee to the address book
 */
public class AddTuteeCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "addtutee";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a tutee to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_SUBJECT + "SUBJECT "
            + PREFIX_GRADE + "GRADE "
            + PREFIX_EDUCATION_LEVEL + "EDUCATION LEVEL "
            + PREFIX_SCHOOL + "SCHOOL "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_SUBJECT + "Economics "
            + PREFIX_GRADE + "B+ "
            + PREFIX_EDUCATION_LEVEL + "junior college "
            + PREFIX_SCHOOL + "Victoria Junior College "
            + PREFIX_TAG + "priority "
            + PREFIX_TAG + "owesMoney";

    public static final String MESSAGE_SUCCESS = "New tutee added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";

    private final Tutee toAdd;

    /**
     * Creates an AddTuteeCommand to add the specified {@code Person}
     */
    public AddTuteeCommand(Tutee tutee) {
        requireNonNull(tutee);
        toAdd = tutee;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addPerson(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicatePersonException e) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddTuteeCommand // instanceof handles nulls
                && toAdd.equals(((AddTuteeCommand) other).toAdd));
    }
}

```
###### \java\seedu\address\logic\commands\ChangeCommand.java
``` java
/**
 * Changes the Calendar Time Unit View of the Application
 */
public class ChangeCommand extends Command {
    public static final String COMMAND_WORD = "change";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the calendar view "
            + "Parameters: "
            + "TIME_UNIT (Only d, w, m or y) "
            + "Example: " + COMMAND_WORD + " d";
    public static final String MESSAGE_CONSTRAINT = "Time unit can only be d, w, m or y for day, week, month and year"
            + " respectively";

    public static final String MESSAGE_SUCCESS = "Calendar view changed";
    public static final String MESSAGE_SAME_VIEW = "Calendar is already in the requested view";
    private static final int INDEX_OF_TIME_UNIT = 0;
    private static final String INITIAL_TIME_UNIT = "d";

    private static String timeUnit = INITIAL_TIME_UNIT;

    /**
     * Creates an ChangeCommand to change calendar into the specified view page time unit {@code timeUnit}
     */
    public ChangeCommand(String timeUnit) {
        requireNonNull(timeUnit);
        this.timeUnit = timeUnit;
    }

    public static String getTimeUnit() {
        return timeUnit;
    }

    @Override
    public CommandResult execute() {
        CalendarPanel.changeViewPage(timeUnit.charAt(INDEX_OF_TIME_UNIT));
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ChangeCommand // instanceof handles nulls
                && timeUnit.equals(((ChangeCommand) other).timeUnit));
    }

}
```
###### \java\seedu\address\logic\parser\AddTuteeCommandParser.java
``` java
/**
 * Parses input arguments and creates a new AddTuteeCommand object
 */
public class AddTuteeCommandParser implements Parser<AddTuteeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddTuteeCommand
     * and returns an AddTuteeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddTuteeCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_SUBJECT, PREFIX_GRADE, PREFIX_EDUCATION_LEVEL, PREFIX_SCHOOL, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_SUBJECT, PREFIX_GRADE, PREFIX_EDUCATION_LEVEL, PREFIX_SCHOOL)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTuteeCommand.MESSAGE_USAGE));
        }

        try {
            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).get();
            Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE)).get();
            Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL)).get();
            Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS)).get();
            Subject subject = ParserUtil.parseSubject(argMultimap.getValue(PREFIX_SUBJECT)).get();
            Grade grade = ParserUtil.parseGrade(argMultimap.getValue(PREFIX_GRADE)).get();
            EducationLevel educationLevel = ParserUtil.parseEducationLevel(
                    argMultimap.getValue(PREFIX_EDUCATION_LEVEL)).get();
            School school = ParserUtil.parseSchool(argMultimap.getValue(PREFIX_SCHOOL)).get();
            Set<Tag> tagList = new HashSet<>();
            tagList.add(new Tag("Tutee"));
            tagList.addAll(ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG)));

            Tutee person = new Tutee(name, phone, email, address, subject, grade, educationLevel, school, tagList);
            return new AddTuteeCommand(person);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
```
###### \java\seedu\address\logic\parser\ChangeCommandParser.java
``` java
/**
 * Parses input arguments and creates a new ChangeCommand object
 */
public class ChangeCommandParser implements Parser<ChangeCommand> {
    private static final String DAY = "d";
    private static final String WEEK = "w";
    private static final String MONTH = "m";
    private static final String YEAR = "y";

    /**
     * Parses the given {@code String} of arguments in the context of the ChangeCommand
     * and returns an ChangeCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ChangeCommand parse(String args) throws ParseException {
        try {
            String timeUnit = ParserUtil.parseTimeUnit(args);
            return new ChangeCommand(timeUnit);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeCommand.MESSAGE_USAGE));
        } catch (SameTimeUnitException svpe) {
            throw new ParseException(svpe.getMessage());
        }
    }

    /**
     * Checks if the user input view page time unit is valid
     *
     * @param trimmedTimeUnit to be checked
     * @return true if view page time unit is valid
     *          false if the view page time unit is invalid
     */
    public static boolean isValidTimeUnit(String trimmedTimeUnit) {
        return (trimmedTimeUnit.equals(DAY)
            || trimmedTimeUnit.equals(WEEK)
            || trimmedTimeUnit.equals(MONTH)
            || trimmedTimeUnit.equals(YEAR));
    }

    /**
     * Checks if the new view page time unit clashes with the current time unit
     *
     * @param timeUnit to be checked
     * @return true if the view page time unit clashes with the current time unit
     *          false if there is no clash
     */
    public static boolean isTimeUnitClash(String timeUnit) {
        String currentViewPage = ChangeCommand.getTimeUnit();
        return (timeUnit.equals(currentViewPage));
    }
}
```
###### \java\seedu\address\logic\parser\exceptions\SameTimeUnitException.java
``` java
/**
 * Signals that the input calendar view page time unit clashes with current time unit
 */
public class SameTimeUnitException extends Exception {
    public SameTimeUnitException(String message) {
        super(message);
    }

}
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code String subject} into an {@code Subject}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code subject} is invalid.
     */
    public static Subject parseSubject(String subject) throws IllegalValueException {
        requireNonNull(subject);
        String trimmedSubject = subject.trim();
        if (!Subject.isValidSubject(trimmedSubject)) {
            throw new IllegalValueException(Subject.MESSAGE_SUBJECT_CONSTRAINTS);
        }
        return new Subject(trimmedSubject);
    }

    /**
     * Parses a {@code Optional<String> subject} into an {@code Optional<Subject>} if {@code subject} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Subject> parseSubject(Optional<String> subject) throws IllegalValueException {
        requireNonNull(subject);
        return subject.isPresent() ? Optional.of(parseSubject(subject.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String educationLevel} into an {@code EducationLevel}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code educationLevel} is invalid.
     */
    public static EducationLevel parseEducationLevel(String educationLevel) throws IllegalValueException {
        requireNonNull(educationLevel);
        String trimmedEducationLevel = educationLevel.trim();
        if (!EducationLevel.isValidEducationLevel(trimmedEducationLevel)) {
            throw new IllegalValueException(EducationLevel.MESSAGE_EDUCATION_LEVEL_CONSTRAINTS);
        }
        return new EducationLevel(trimmedEducationLevel);
    }

    /**
     * Parses a {@code Optional<String> educationLevel} into an {@code Optional<EducationLevel>}
     * if {@code educationLevel} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<EducationLevel> parseEducationLevel(Optional<String> educationLevel)
            throws IllegalValueException {
        requireNonNull(educationLevel);
        return educationLevel.isPresent() ? Optional.of(parseEducationLevel(educationLevel.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String school} into an {@code School}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code school} is invalid.
     */
    public static School parseSchool(String school) throws IllegalValueException {
        requireNonNull(school);
        String trimmedSchool = school.trim();
        if (!School.isValidSchool(trimmedSchool)) {
            throw new IllegalValueException(School.MESSAGE_SCHOOL_CONSTRAINTS);
        }
        return new School(trimmedSchool);
    }

    /**
     * Parses a {@code Optional<String> school} into an {@code Optional<School>} if {@code school} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<School> parseSchool(Optional<String> school) throws IllegalValueException {
        requireNonNull(school);
        return school.isPresent() ? Optional.of(parseSchool(school.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String grade} into an {@code Grade}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code grade} is invalid.
     */
    public static Grade parseGrade(String grade) throws IllegalValueException {
        requireNonNull(grade);
        String trimmedGrade = grade.trim();
        if (!Grade.isValidGrade(trimmedGrade)) {
            throw new IllegalValueException(Grade.MESSAGE_GRADE_CONSTRAINTS);
        }
        return new Grade(trimmedGrade);
    }

    /**
     * Parses a {@code Optional<String> grade} into an {@code Optional<Grade>} if {@code grade} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Grade> parseGrade(Optional<String> grade) throws IllegalValueException {
        requireNonNull(grade);
        return grade.isPresent() ? Optional.of(parseGrade(grade.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String timeUnit} into an {@code String} and returns it.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code timeUnit} is invalid.
     */
    public static String parseTimeUnit(String timeUnit) throws IllegalValueException, SameTimeUnitException {
        requireNonNull(timeUnit);
        String trimmedTimeUnit = timeUnit.trim();
        if (!ChangeCommandParser.isValidTimeUnit(trimmedTimeUnit)) {
            throw new IllegalValueException(ChangeCommand.MESSAGE_CONSTRAINT);
        }
        if (ChangeCommandParser.isTimeUnitClash(trimmedTimeUnit)) {
            throw new SameTimeUnitException(ChangeCommand.MESSAGE_SAME_VIEW);
        }
        return trimmedTimeUnit;
    }
}
```
###### \java\seedu\address\model\person\exceptions\DurationParseException.java
``` java
/**
 * Signals that the input duration format is invalid
 */
public class DurationParseException extends Exception {
    public DurationParseException(String message) {
        super(message);
    }
}
```
###### \java\seedu\address\model\person\exceptions\TimingClashException.java
``` java
/**
 * Signals that there is a clash of timing in the schedule
 */
public class TimingClashException extends Exception {

    public TimingClashException(String message) {
        super(message);
    }
}
```
###### \java\seedu\address\model\personal\PersonalTask.java
``` java
/**
 * Represents the personal task that the user has
 */
public class PersonalTask implements Task {

    public static final String MESSAGE_TASK_CONSTRAINT =
                    "Date can only contain numbers in the format of dd/mm/yyyy\n"
                    + ", Time must in the format of HH:mm\n"
                    + " and Duration must be in hours.";

    private String description;
    private String duration;
    private LocalDateTime taskDateTime;

    /**
     * Creates a personal task
     *
     * @param taskDateTime date and time of the task
     * @param duration duration of the task
     * @param description description of the task
     */
    public PersonalTask(LocalDateTime taskDateTime, String duration, String description) {
        this.taskDateTime = taskDateTime;
        this.duration = duration;
        this.description = description;
    }

    public LocalDateTime getTaskDateTime() {
        return taskDateTime;
    }

    public String getDescription() {
        return description;
    }

    public String getDuration() {
        return duration;
    }
}
```
###### \java\seedu\address\model\Schedule.java
``` java
/**
 * Wraps the data of all existing tasks.
 */
public class Schedule {

    protected static ArrayList<Task> taskList = new ArrayList<>();
    /**
     * Returns a list of all existing tasks.
     */
    public static ArrayList<Task> getTaskList() {
        return taskList;
    }

    /**
     * Checks for any clashes in the task timing in schedule
     *
     *  @return true if there is a clash
     *          false if there is no clash
     */
    public static boolean isTaskClash(LocalDateTime taskDateTime, String duration) {
        LocalDateTime taskEndTime = getTaskEndTime(duration, taskDateTime);

        for (Task recordedTask : taskList) {
            LocalDateTime startTimeOfTaskInSchedule = recordedTask.getTaskDateTime();
            String durationOfTaskInSchedule = recordedTask.getDuration();
            LocalDateTime endTimeOfTaskInSchedule = getTaskEndTime(durationOfTaskInSchedule, startTimeOfTaskInSchedule);
            boolean isClash = !(taskEndTime.isBefore(startTimeOfTaskInSchedule)
                    || taskDateTime.isAfter(endTimeOfTaskInSchedule))
                    && !(taskEndTime.equals(startTimeOfTaskInSchedule)
                    || taskDateTime.equals(endTimeOfTaskInSchedule));
            if (isClash) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns date and time when the task ends
     */
    private static LocalDateTime getTaskEndTime(String duration, LocalDateTime startDateTime) {
        int indexOfHourDelimiter = duration.indexOf("h");
        int indexOfMinuteDelimiter = duration.indexOf("m");
        int indexOfFirstMinuteDigit = indexOfHourDelimiter + 1;
        int hoursInDuration = Integer.parseInt(duration.substring(0, indexOfHourDelimiter));
        int minutesInDuration = Integer.parseInt(duration.substring(indexOfFirstMinuteDigit, indexOfMinuteDelimiter));

        LocalDateTime taskEndTime;
        taskEndTime = startDateTime.plusHours(hoursInDuration).plusMinutes(minutesInDuration);
        return taskEndTime;
    }

}
```
###### \java\seedu\address\model\tutee\EducationLevel.java
``` java
/**
 * Represents a Tutee's education level in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidEducationLevel(String)}
 */
public class EducationLevel {

    public static final String MESSAGE_EDUCATION_LEVEL_CONSTRAINTS =
            "Education level should only be either primary, secondary or junior college, and it should not be blank";
    public static final String EDUCATION_LEVEL_VALIDATION_REGEX = "(?i)\\b(primary|secondary|(junior\\scollege))\\b";

    public final String educationLevel;

    /**
     * Constructs a {@code education level}.
     *
     * @param educationLevel A valid education level.
     */
    public EducationLevel(String educationLevel) {
        requireNonNull(educationLevel);
        checkArgument(isValidEducationLevel(educationLevel), MESSAGE_EDUCATION_LEVEL_CONSTRAINTS);
        this.educationLevel = educationLevel;
    }

    /**
     * Returns true if a given string is a valid education level.
     */
    public static boolean isValidEducationLevel(String test) {
        return test.matches(EDUCATION_LEVEL_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return educationLevel;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EducationLevel // instanceof handles nulls
                && this.educationLevel.equals(((EducationLevel) other).educationLevel)); // state check
    }

    @Override
    public int hashCode() {
        return educationLevel.hashCode();
    }
}
```
###### \java\seedu\address\model\tutee\Grade.java
``` java
/**
 * Represents a Tutee's subject grade
 * Guarantees: immutable; is valid as declared in {@link #isValidGrade(String)}
 */
public class Grade {

    public static final String MESSAGE_GRADE_CONSTRAINTS =
            "Grade should start with alphabetic characters and followed by any character or blank, "
            + "and it should not be blank";
    public static final String GRADE_VALIDATION_REGEX = "[\\p{Alpha}].??";

    public final String grade;

    /**
     * Constructs a {@code Grade}.
     *
     * @param grade A valid grade.
     */
    public Grade(String grade) {
        requireNonNull(grade);
        checkArgument(isValidGrade(grade), MESSAGE_GRADE_CONSTRAINTS);
        this.grade = grade;
    }

    /**
     * Returns true if a given string is a valid grade.
     */
    public static boolean isValidGrade(String test) {
        return test.matches(GRADE_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return grade;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Grade // instanceof handles nulls
                && this.grade.equals(((Grade) other).grade)); // state check
    }

    @Override
    public int hashCode() {
        return grade.hashCode();
    }
}
```
###### \java\seedu\address\model\tutee\School.java
``` java
/**
 * Represents a Tutee's school
 * Guarantees: immutable; is valid as declared in {@link #isValidSchool(String)}
 */
public class School {

    public static final String MESSAGE_SCHOOL_CONSTRAINTS =
            "School should only contain alphabetic characters and spaces, and it should not be blank";
    public static final String SCHOOL_VALIDATION_REGEX = "[\\p{Alpha}][\\p{Alpha} ]*";

    public final String school;

    /**
     * Constructs a {@code School}.
     *
     * @param school A valid school.
     */
    public School(String school) {
        requireNonNull(school);
        checkArgument(isValidSchool(school), MESSAGE_SCHOOL_CONSTRAINTS);
        this.school = school;
    }

    /**
     * Returns true if a given string is a valid school.
     */
    public static boolean isValidSchool(String test) {
        return test.matches(SCHOOL_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return school;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof School // instanceof handles nulls
                && this.school.equals(((School) other).school)); // state check
    }

    @Override
    public int hashCode() {
        return school.hashCode();
    }
}
```
###### \java\seedu\address\model\tutee\Subject.java
``` java
/**
 * Represents a Tutee's subject
 * Guarantees: immutable; is valid as declared in {@link #isValidSubject(String)}
 */
public class Subject {

    public static final String MESSAGE_SUBJECT_CONSTRAINTS =
            "Subject should only contain alphabetic characters and spaces, and it should not be blank";
    public static final String SUBJECT_VALIDATION_REGEX = "[\\p{Alpha}][\\p{Alpha} ]*";

    public final String subject;

    /**
     * Constructs a {@code Subject}.
     *
     * @param subject A valid subject.
     */
    public Subject(String subject) {
        requireNonNull(subject);
        checkArgument(isValidSubject(subject), MESSAGE_SUBJECT_CONSTRAINTS);
        this.subject = subject;
    }

    /**
     * Returns true if a given string is a valid subject.
     */
    public static boolean isValidSubject(String test) {
        return test.matches(SUBJECT_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return subject;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Subject // instanceof handles nulls
                && this.subject.equals(((Subject) other).subject)); // state check
    }

    @Override
    public int hashCode() {
        return subject.hashCode();
    }
}
```
###### \java\seedu\address\model\tutee\TuitionTask.java
``` java
/**
 * Represents a tuition task that the person has
 */
public class TuitionTask implements Task {


    public static final String MESSAGE_TASK_CONSTRAINT =
                    "Task can only be tuition\n"
                    + ", the person involved must already be inside the contact list\n"
                    + ", Date can only contain numbers in the format of dd/mm/yyyy\n"
                    + ", Time must in the format of HH:mm\n"
                    + " and Duration must be the format of 01h30m";

    private String person;
    private String description;
    private String duration;
    private LocalDateTime taskDateTime;

    /**
     * Creates a tuition task
     *
     * @param person person involves in the task
     * @param taskDateTime date and time of the task
     * @param duration duration of the task
     * @param description description of the task
     */
    public TuitionTask(String person, LocalDateTime taskDateTime, String duration, String description) {
        this.person = person;
        this.taskDateTime = taskDateTime;
        this.duration = duration;
        this.description = description;
    }

    public LocalDateTime getTaskDateTime() {
        return taskDateTime;
    }

    public String getPerson() {
        return person;
    }

    public String getDescription() {
        return description;
    }

    public String getDuration() {
        return duration;
    }
}
```
###### \java\seedu\address\model\tutee\Tutee.java
``` java
/**
 * Represents a Tutee in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Tutee extends Person {
    private TuitionSchedule schedule;
    private Subject subject;
    private Grade grade;
    private EducationLevel educationLevel;
    private School school;

    /**
     * Every field must be present and not null.
     */
    public Tutee(Name name, Phone phone, Email email, Address address, Subject subject,
                 Grade grade, EducationLevel educationLevel, School school, Set<Tag> tags) {
        super(name, phone, email, address, tags);
        this.subject = subject;
        this.grade = grade;
        this.educationLevel = educationLevel;
        this.school = school;
        this.schedule = new TuitionSchedule(name.toString());
    }

    public TuitionSchedule getTuitionSchedule() {
        return schedule;
    }

    public Subject getSubject() {
        return subject;
    }

    public Grade getGrade() {
        return grade;
    }

    public EducationLevel getEducationLevel() {
        return educationLevel;
    }

    public School getSchool() {
        return school;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return otherPerson.getName().equals(this.getName())
                && otherPerson.getPhone().equals(this.getPhone())
                && otherPerson.getEmail().equals(this.getEmail())
                && otherPerson.getAddress().equals(this.getAddress());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, subject, grade, educationLevel, school, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Address: ")
                .append(getAddress())
                .append(" Subject: ")
                .append(getSubject())
                .append(" Grade ")
                .append(getGrade())
                .append(" Education Level: ")
                .append(getEducationLevel())
                .append(" School: ")
                .append(getSchool())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
```
###### \java\seedu\address\ui\CalendarPanel.java
``` java
/**
 * The Calendar Panel of the App.
 */
public class CalendarPanel extends UiPart<Region> {

    private static final String FXML = "CalendarPanel.fxml";
    private static final char DAY = 'd';
    private static final char WEEK = 'w';
    private static final char MONTH = 'm';
    private static final char YEAR = 'y';

    @FXML
    private static CalendarView calendarView = new CalendarView();

    public CalendarPanel() {
        super(FXML);
        calendarView.setRequestedTime(LocalTime.now());
        calendarView.setToday(LocalDate.now());
        calendarView.setTime(LocalTime.now());
        calendarView.showDayPage();
        disableViews();
    }

    /**
     * Remove unnecessary buttons from interface
     */
    private void disableViews() {
        calendarView.setShowAddCalendarButton(false);
        calendarView.setShowPrintButton(false);
        calendarView.setShowPageToolBarControls(false);
        calendarView.setShowSearchField(false);
    }

    /**
     * Changes the view page of the calendar
     * @param timeUnit the view page time unit to be changed into
     */
    public static void changeViewPage(char timeUnit) {
        switch(timeUnit) {
        case DAY:
            calendarView.showDayPage();
            return;
        case WEEK:
            calendarView.showWeekPage();
            return;
        case MONTH:
            calendarView.showMonthPage();
            return;
        case YEAR:
            calendarView.showYearPage();
            return;
        default:
            assert(false);
        }
    }

    @Override
    public CalendarView getRoot() {
        return this.calendarView;
    }
}
```
###### \resources\view\CalendarPanel.fxml
``` fxml
<StackPane xmlns="http://javafx.com/javafx/8.0.121">
    <children>
        <BorderPane prefHeight="200.0" prefWidth="200.0" />
    </children>
</StackPane>
```
###### \resources\view\MainWindow.fxml
``` fxml
          <StackPane fx:id="calendarPlaceholder" prefWidth="540">
            <padding>
              <Insets bottom="10" left="10" right="10" top="10" />
            </padding>
          </StackPane>
         </SplitPane>
```
