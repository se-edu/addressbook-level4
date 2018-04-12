# ChoChihTun
###### \java\seedu\address\logic\commands\AddPersonalTaskCommand.java
``` java
    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.addTask(toAdd);
        } catch (TimingClashException tce) {
            throw new CommandException(tce.getMessage());
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

```
###### \java\seedu\address\logic\commands\AddTuitionTaskCommand.java
``` java
    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.addTask(toAdd);
        } catch (TimingClashException tce) {
            throw new CommandException(tce.getMessage());
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }

```
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
     * Creates an AddTuteeCommand to add the specified {@code Tutee}
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
        } catch (SameTimeUnitException stue) {
            throw new ParseException(stue.getMessage());
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

import seedu.address.commons.exceptions.DuplicateDataException;

/**
 * Signals that there is a clash of timing in the schedule or there is a duplicate task
 */
public class TimingClashException extends DuplicateDataException {

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

    private static final String HOUR_DELIMITER = "h";
    private static final String MINUTE_DELIMITER = "m";
    private static final String NULL_STRING = "";
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu HH:mm")
            .withResolverStyle(ResolverStyle.STRICT);

    private String description;
    private String duration;
    private LocalDateTime taskDateTime;
    private Entry entry;

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
        this.entry = createCalendarEntry();
    }

    /**
     * Creates an entry to be entered into the calendar
     *
     * @return Calendar entry
     */
    private Entry createCalendarEntry() {
        LocalDateTime endDateTime = getTaskEndTime();
        Interval interval = new Interval(taskDateTime, endDateTime);
        Entry entry = new Entry(description);
        entry.setInterval(interval);
        return entry;
    }

    /**
     * Returns the end time of the task
     */
    private LocalDateTime getTaskEndTime() {
        int hoursInDuration = parseHours();
        int minutesInDuration = parseMinutes();
        LocalDateTime endDateTime = taskDateTime.plusHours(hoursInDuration).plusMinutes(minutesInDuration);
        return endDateTime;
    }

    /**
     * Parses hour component out of duration
     *
     * @return number of hours in the duration
     */
    private int parseHours() {
        int indexOfHourDelimiter = duration.indexOf(HOUR_DELIMITER);
        return Integer.parseInt(duration.substring(0, indexOfHourDelimiter));
    }

    /**
     * Parses minute component out of duration
     *
     * @return number of minutes in the duration
     */
    private int parseMinutes() {
        int startOfMinutesIndex = duration.indexOf(HOUR_DELIMITER) + 1;
        int indexOfMinuteDelimiter = duration.indexOf(MINUTE_DELIMITER);
        return Integer.parseInt(duration.substring(startOfMinutesIndex, indexOfMinuteDelimiter));
    }

    public Entry getEntry() {
        return entry;
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

    @Override
    public String getStringTaskDateTime() {
        return taskDateTime.format(formatter);
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
 * Represents a tuition task that the tutee has
 */
public class TuitionTask implements Task {

    private static final String TUITION_TITLE = "Tuition with %1$s";
    private static final String HOUR_DELIMITER = "h";
    private static final String MINUTE_DELIMITER = "m";
    private static final String NULL_STRING = "";
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu HH:mm")
            .withResolverStyle(ResolverStyle.STRICT);

    private String tutee;
    private String description;
    private String duration;
    private LocalDateTime taskDateTime;
    private Entry entry;

    /**
     * Creates a tuition task
     *
     * @param tutee tutee involved in the task
     * @param taskDateTime date and time of the task
     * @param duration duration of the task
     * @param description description of the task
     */
    public TuitionTask(String tutee, LocalDateTime taskDateTime, String duration, String description) {
        this.tutee = tutee;
        this.taskDateTime = taskDateTime;
        this.duration = duration;
        this.description = description;
        this.entry = createCalendarEntry();
    }

    /**
     * Creates an entry to be entered into the calendar
     *
     * @return Calendar entry
     */
    private Entry createCalendarEntry() {
        LocalDateTime endDateTime = getTaskEndTime();
        Interval interval = new Interval(taskDateTime, endDateTime);
        Entry entry = new Entry(getTuitionTitle());
        entry.setInterval(interval);
        return entry;
    }

    /**
     * Returns the end time of the task
     */
    private LocalDateTime getTaskEndTime() {
        int hoursInDuration = parseHours();
        int minutesInDuration = parseMinutes();
        LocalDateTime endDateTime = taskDateTime.plusHours(hoursInDuration).plusMinutes(minutesInDuration);
        return endDateTime;
    }

    /**
     * Parses hour component out of duration
     *
     * @return number of hours in the duration
     */
    private int parseHours() {
        int indexOfHourDelimiter = duration.indexOf(HOUR_DELIMITER);
        return Integer.parseInt(duration.substring(0, indexOfHourDelimiter));
    }

    /**
     * Parses minute component out of duration
     *
     * @return number of minutes in the duration
     */
    private int parseMinutes() {
        int indexOfFirstMinuteDigit = duration.indexOf(HOUR_DELIMITER) + 1;
        int indexOfMinuteDelimiter = duration.indexOf(MINUTE_DELIMITER);
        return Integer.parseInt(duration.substring(indexOfFirstMinuteDigit, indexOfMinuteDelimiter));
    }

    public Entry getEntry() {
        return entry;
    }

    public LocalDateTime getTaskDateTime() {
        return taskDateTime;
    }

    public String getPerson() {
        return tutee;
    }

    public String getDescription() {
        return description;
    }

    public String getDuration() {
        return duration;
    }

    @Override
    public String getStringTaskDateTime() {
        return taskDateTime.format(formatter);
    }

```
###### \java\seedu\address\model\tutee\Tutee.java
``` java
/**
 * Represents a Tutee in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Tutee extends Person {
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

        if (!(other instanceof Tutee)) {
            return false;
        }

        Tutee otherPerson = (Tutee) other;
        return otherPerson.getName().equals(this.getName())
                && otherPerson.getPhone().equals(this.getPhone())
                && otherPerson.getEmail().equals(this.getEmail())
                && otherPerson.getAddress().equals(this.getAddress())
                && otherPerson.getEducationLevel().equals(this.getEducationLevel())
                && otherPerson.getGrade().equals(this.getGrade())
                && otherPerson.getSchool().equals(this.getSchool())
                && otherPerson.getSubject().equals(this.getSubject());
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
###### \java\seedu\address\model\UniqueTaskList.java
``` java
    /**
     * Adds a task to the list.
     *
     * @throws TimingClashException if there is a clash in timing with an existing task
     */
    public void add(Task toAdd) throws TimingClashException {
        requireNonNull(toAdd);
        if (isTimeClash(toAdd.getTaskDateTime(), toAdd.getDuration())) {
            throw new TimingClashException(MESSAGE_TASK_TIMING_CLASHES);
        }
        internalList.add(toAdd);
    }
```
###### \java\seedu\address\model\UniqueTaskList.java
``` java
    /**
     * Checks for any clashes in the task timing in schedule
     *
     * @param startDateTime start date and time of new task
     * @param duration duration of new task
     */
    private boolean isTimeClash(LocalDateTime startDateTime, String duration) {
        LocalDateTime taskEndTime = getTaskEndTime(duration, startDateTime);

        for (Task recordedTask : internalList) {
            LocalDateTime startTimeOfRecordedTask = recordedTask.getTaskDateTime();
            String durationOfRecordedTask = recordedTask.getDuration();
            LocalDateTime endTimeOfRecordedTask = getTaskEndTime(durationOfRecordedTask, startTimeOfRecordedTask);
            boolean isClash = !(taskEndTime.isBefore(startTimeOfRecordedTask)
                    || startDateTime.isAfter(endTimeOfRecordedTask))
                    && !(taskEndTime.equals(startTimeOfRecordedTask)
                    || startDateTime.equals(endTimeOfRecordedTask));
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
        int indexOfHourDelimiter = duration.indexOf(HOUR_DELIMITER);
        int indexOfMinuteDelimiter = duration.indexOf(MINUTE_DELIMITER);
        int indexOfFirstDigitInMinute = indexOfHourDelimiter + 1;
        int hoursInDuration = Integer.parseInt(duration.substring(0, indexOfHourDelimiter));
        int minutesInDuration = Integer.parseInt(duration.substring(indexOfFirstDigitInMinute, indexOfMinuteDelimiter));

        LocalDateTime taskEndTime;
        taskEndTime = startDateTime.plusHours(hoursInDuration).plusMinutes(minutesInDuration);
        return taskEndTime;
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
    private static CalendarSource source = new CalendarSource("Schedule");
    private static Calendar calendar = new Calendar("Task");

    @FXML
    private static CalendarView calendarView = new CalendarView();


    public CalendarPanel() {
        super(FXML);
        calendarView.setRequestedTime(LocalTime.now());
        calendarView.setToday(LocalDate.now());
        calendarView.setTime(LocalTime.now());
        calendarView.setScaleX(0.95);
        calendarView.setScaleY(1.15);
        calendarView.setTranslateY(-40);
        calendarView.showDayPage();
        disableViews();
        setupCalendar();
    }

    /**
     * Initialises the calendar
     */
    private void setupCalendar() {
        source.getCalendars().add(calendar);
        calendarView.getCalendarSources().add(source);
    }

    /**
     * Removes unnecessary buttons from interface
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
            // Should never enter here
            assert (false);
        }
    }

    /**
     * Updates the calendar with the updated list of tasks
     *
     * @param filteredTasks updated list of tasks
     */
    public static void updateCalendar(List<Task> filteredTasks) {
        if (isFilteredTaskListValid(filteredTasks)) {
            Calendar updatedCalendar = new Calendar("task");
            for (Task task : filteredTasks) {
                updatedCalendar.addEntry(task.getEntry());
            }
            source.getCalendars().clear();
            source.getCalendars().add(updatedCalendar);
        } else {
            // Latest task list provided or loaded from storage should not have any task that clashes
            assert (false);
        }
    }

    /**
     * Checks if the given latest task list is valid
     *
     * @param taskList to be checked
     * @return true if there is no clash between tasks so task list is valid
     *         false if there is clash between tasks so task list is invalid
     */
    private static boolean isFilteredTaskListValid(List<Task> taskList) {
        for (int i = 0; i < taskList.size(); i++) {
            Entry<?> taskEntryToBeChecked = taskList.get(i).getEntry();
            if (isTaskTimingClash(taskList, i, taskEntryToBeChecked)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the given task clashes with any task in the list
     *
     * @param taskList list of tasks to check against
     * @param index index of the given task
     * @param taskEntryToBeChecked the given task entry
     * @return true if given task does not clash with any task in the list
     *         false if given task clashes with another task in the list
     */
    private static boolean isTaskTimingClash(List<Task> taskList, int index, Entry<?> taskEntryToBeChecked) {
        for (int j = index + 1; j < taskList.size(); j++) {
            Entry taskEntryToCheckAgainst = taskList.get(j).getEntry();
            if (taskEntryToBeChecked.intersects(taskEntryToCheckAgainst)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public CalendarView getRoot() {
        return calendarView;
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
<fx:root type="javafx.stage.Stage" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1"
         minWidth="450" minHeight="600">
  <icons>
    <Image url="@/images/address_book_32.png" />
  </icons>
  <scene>
    <Scene>
      <stylesheets>
        <URL value="@TuitionConnectTheme.css" />
        <URL value="@Extensions.css" />
      </stylesheets>

      <VBox>
          <MenuBar fx:id="menuBar" prefHeight="0.0" prefWidth="1422.0" VBox.vgrow="NEVER">
              <Menu mnemonicParsing="false" text="File">
                  <MenuItem mnemonicParsing="false" onAction="#handleExit" text="Exit" />
              </Menu>
              <Menu mnemonicParsing="false" text="Help">
                  <MenuItem fx:id="helpMenuItem" mnemonicParsing="false" onAction="#handleHelp" text="Help" />
              </Menu>
          </MenuBar>
          <SplitPane prefHeight="689.0" prefWidth="1360.0">
              <items>
                  <VBox prefHeight="687.0" prefWidth="667.0">
                      <children>

                          <StackPane fx:id="commandBoxPlaceholder" prefHeight="0.0" prefWidth="1420.0" styleClass="pane-with-border" VBox.vgrow="NEVER">
                              <padding>
                                  <Insets bottom="5" left="10" right="10" top="10" />
                              </padding>
                          </StackPane>

                          <StackPane fx:id="resultDisplayPlaceholder" maxHeight="311.0" minHeight="46.0" prefHeight="106.0" prefWidth="1420.0" styleClass="pane-with-border" VBox.vgrow="NEVER">
                              <padding>
                                  <Insets bottom="5.0" left="5.0" right="5.0" top="5.0" />
                              </padding>
                          </StackPane>

                          <SplitPane id="splitPane" fx:id="splitPane" dividerPositions="0.1, 0.1" prefHeight="541.0" prefWidth="573.0" VBox.vgrow="ALWAYS">

                              <VBox fx:id="personList" minWidth="264.0" prefHeight="488.0" prefWidth="584.0" SplitPane.resizableWithParent="false">
                                  <padding>
                                      <Insets bottom="5" left="5" top="5" />
                                  </padding>
                                  <StackPane fx:id="personListPanelPlaceholder" prefHeight="555.0" prefWidth="85.0" VBox.vgrow="ALWAYS" />
                              </VBox>

                              <VBox fx:id="taskList" minWidth="300" prefHeight="570.0" prefWidth="407.0" SplitPane.resizableWithParent="false">
                                  <padding>
                                      <Insets bottom="5" right="5" top="5" />
                                  </padding>
                                  <StackPane fx:id="taskListPanelPlaceholder" prefHeight="555.0" prefWidth="381.0" VBox.vgrow="ALWAYS" />
                              </VBox>

                              <StackPane fx:id="calendarPlaceholder" minWidth="775.0" prefHeight="570.0" prefWidth="775.0">
                                  <padding>
                                      <Insets bottom="10" left="-5" right="-5" top="10" />
                                  </padding>
                              </StackPane>
                          </SplitPane>
                      </children>
                  </VBox>
              </items>
          </SplitPane>

          <StackPane fx:id="statusbarPlaceholder" VBox.vgrow="NEVER" />
      </VBox>
    </Scene>
  </scene>
</fx:root>
```
###### \resources\view\TuitionConnectTheme.css
``` css
.background {
    -fx-background-color: white;
    background-color: white; /* Used in the default.html file */
}

.label {
    -fx-font-size: 10pt;
    -fx-font-family: "Andale Mono";
    -fx-text-fill: black;
    -fx-opacity: 0.9;
}

.label-bright {
    -fx-font-size: 10pt;
    -fx-font-family: "Andale Mono";
    -fx-text-fill: black;
    -fx-opacity: 1;
}

.label-header {
    -fx-font-size: 30pt;
    -fx-font-family: "Andale Mono";
    -fx-text-fill: black;
    -fx-opacity: 1;
}

.text-field {
    -fx-font-size: 12pt;
    -fx-font-family: "Andale Mono";
}

.tab-pane {
    -fx-padding: 0 0 0 1;
}

.tab-pane .tab-header-area {
    -fx-padding: 0 0 0 0;
    -fx-min-height: 0;
    -fx-max-height: 0;
}

.table-view {
    -fx-base: black;
    -fx-control-inner-background: black;
    -fx-background-color: black;
    -fx-table-cell-border-color: transparent;
    -fx-table-header-border-color: transparent;
    -fx-padding: 5;
}

.table-view .column-header-background {
    -fx-background-color: transparent;
}

.table-view .column-header, .table-view .filler {
    -fx-size: 35;
    -fx-border-width: 0 0 1 0;
    -fx-background-color: transparent;
    -fx-border-color:
        transparent
        transparent
        derive(-fx-base, 80%)
        transparent;
    -fx-border-insets: 0 10 1 0;
}

.table-view .column-header .label {
    -fx-font-size: 20pt;
    -fx-font-family: "Andale Mono";
    -fx-text-fill: white;
    -fx-alignment: center-left;
    -fx-opacity: 1;
}

.table-view:focused .table-row-cell:filled:focused:selected {
    -fx-background-color: -fx-focus-color;
}

.split-pane:horizontal .split-pane-divider {
    -fx-background-color: white;
    -fx-border-color: transparent transparent transparent white;
}

.split-pane {
    -fx-background-color: white;
}

.list-view {
    -fx-background-insets: 0;
    -fx-padding: 0;
    -fx-background-color: derive(#1d1d1d, 20%);
}

.list-cell {
    -fx-label-padding: 0 0 0 0;
    -fx-graphic-text-gap : 0;
    -fx-padding: 0 0 0 0;
}

.list-cell:filled:even {
    -fx-background-color: #c9d8ef;
}

.list-cell:filled:odd {
    -fx-background-color: #c9d8ef;
}

.list-cell:filled:selected {
    -fx-background-color: #1f3351;
}

.list-cell:filled:selected #cardPane {
    -fx-border-color: black;
    -fx-border-width: 3px;
}

.list-cell .label {
    -fx-text-fill: white;
}

.cell_big_label { /* Name */
    -fx-font-family: "Segoe UI Semibold";
    -fx-font-size: 16px;
    -fx-text-fill: white;
}

.cell_small_label { /* Details */
    -fx-font-family: "Segoe UI";
    -fx-font-size: 13px;
    -fx-text-fill: white;
}

.anchor-pane {
     -fx-background-color: #c9bbbb;
}

.pane-with-border {
     -fx-background-color: black;
     -fx-border-color: transparent;
     -fx-border-top-width: 1px;
}

.status-bar {
    -fx-background-color: white;
    -fx-text-fill: black;
}

.result-display { /* Command result */
    -fx-background-color: transparent;
    -fx-font-family: "Andale Mono";
    -fx-font-size: 13pt;
    -fx-text-fill: black;
}

.result-display .label {
    -fx-text-fill: black !important;
}

.status-bar .label {
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: white;
}

.status-bar-with-border {
    -fx-background-color: derive(#1d1d1d, 30%);
    -fx-border-color: derive(#1d1d1d, 25%);
    -fx-border-width: 1px;
}

.status-bar-with-border .label {
    -fx-text-fill: white;
}

.grid-pane {
    -fx-background-color: white;
    -fx-border-color: black;
    -fx-border-width: 1px;
}

.grid-pane .anchor-pane {
    -fx-background-color: #113756;
}

.context-menu {
    -fx-background-color: #22529e;
}

.context-menu .label {
    -fx-text-fill: white;
}

.menu-bar {
    -fx-background-color: #113756;
}

.menu-bar .label {
    -fx-font-size: 14pt;
    -fx-font-family: "Andale Mono";
    -fx-text-fill: white;
    -fx-opacity: 0.9;
}

.menu .left-container {
    -fx-background-color: black;
}

/*
 * Metro style Push Button
 * Author: Pedro Duque Vieira
 * http://pixelduke.wordpress.com/2012/10/23/jmetro-windows-8-controls-on-java/
 */
.button {
    -fx-padding: 5 22 5 22;
    -fx-border-color: #e2e2e2;
    -fx-border-width: 2;
    -fx-background-radius: 0;
    -fx-background-color: #1d1d1d;
    -fx-font-family: "Andale Mono", Helvetica, Arial, sans-serif;
    -fx-font-size: 11pt;
    -fx-text-fill: black;
    -fx-background-insets: 0 0 0 0, 0, 1, 2;
}

.button:hover {
    -fx-background-color: #3a3a3a;
}

.button:pressed, .button:default:hover:pressed {
  -fx-background-color: white;
  -fx-text-fill: black;
}

.button:focused {
    -fx-border-color: white, white;
    -fx-border-width: 1, 1;
    -fx-border-style: solid, segments(1, 1);
    -fx-border-radius: 0, 0;
    -fx-border-insets: 1 1 1 1, 0;
}

.button:disabled, .button:default:disabled {
    -fx-opacity: 0.4;
    -fx-background-color: #1d1d1d;
    -fx-text-fill: black;
}

.button:default {
    -fx-background-color: -fx-focus-color;
    -fx-text-fill: black;
}

.button:default:hover {
    -fx-background-color: black;
}

.dialog-pane {
    -fx-background-color: black;
}

.dialog-pane > *.button-bar > *.container {
    -fx-background-color: black;
}

.dialog-pane > *.label.content {
    -fx-font-size: 14px;
    -fx-font-weight: bold;
    -fx-text-fill: white;
}

.dialog-pane:header *.header-panel {
    -fx-background-color: black;
}

.dialog-pane:header *.header-panel *.label {
    -fx-font-size: 18px;
    -fx-font-style: italic;
    -fx-fill: white;
    -fx-text-fill: white;
}

.scroll-bar { /* Scroll bar column background color */
    -fx-background-color: #ced8dd;
}

.scroll-bar .thumb { /* Scroll bar background color */
    -fx-background-color: #939a9e;
    -fx-background-insets: 3;
}

.scroll-bar .increment-button, .scroll-bar .decrement-button {
    -fx-background-color: transparent;
    -fx-padding: 0 0 0 0;
}

.scroll-bar .increment-arrow, .scroll-bar .decrement-arrow {
    -fx-shape: " ";
}

.scroll-bar:vertical .increment-arrow, .scroll-bar:vertical .decrement-arrow {
    -fx-padding: 1 8 1 8;
}

.scroll-bar:horizontal .increment-arrow, .scroll-bar:horizontal .decrement-arrow {
    -fx-padding: 8 1 8 1;
}

#cardPane {
    -fx-background-color: #37598e;
    -fx-border-width: 0;
}

#commandTypeLabel {
    -fx-font-size: 11px;
    -fx-text-fill: black;
}

#commandTextField { /* Command box */
    -fx-background-color: transparent #383838 transparent #383838;
    -fx-background-insets: 0;
    -fx-border-color: black;
    -fx-border-insets: 0;
    -fx-border-width: 2.1;
    -fx-font-family: "Andale Mono";
    -fx-font-size: 13pt;
    -fx-text-fill: black;
    -fx-prompt-text-fill: black;
}

#filterField, #personListPanel, #personWebpage {
    -fx-effect: innershadow(gaussian, black, 10, 0, 0, 0);
}

#resultDisplay .content {
    -fx-background-color: #c9bbbb;
    -fx-background-radius: 0;
}

#tags {
    -fx-hgap: 7;
    -fx-vgap: 3;
}

#tags .label {
    -fx-text-fill: white;
    -fx-background-color: gray;
    -fx-padding: 1 3 1 3;
    -fx-border-radius: 2;
    -fx-background-radius: 15;
    -fx-font-size: 11;
}

#calendarPlaceholder {
    -fx-background-color: white;
}

#commandBoxPlaceholder {
    -fx-background-color: white;
}

#resultDisplayPlaceholder {
    -fx-background-color: white;
}

#statusbarPlaceholder {
    -fx-background-color: white;
}

#personListPanelPlaceholder {
    -fx-background-color: white;
}

#taskListPanelPlaceholder {
    -fx-background-color: white;
}
```
