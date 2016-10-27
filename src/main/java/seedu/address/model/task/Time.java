package seedu.address.model.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.Parser;

//@@author A0121261Y
/**
 * Represents a Task's Date in the SmartyDo.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Time implements Comparable<Time> {

    public static final String MESSAGE_DATE_CONSTRAINTS = "Task Dates should be in valid UK-format "
            + "DD/MMM/YYYY or DD/MM/YYYY or DD.MM.YYYY or DD.MMM.YYY or DD-MM-YYYY or DD-MMM-YYYY";
    /**
     * Date validation in UK format, includes checks for valid date during leap years.
     * Supported Formats: dd/mmm/yyyy, dd-mmm-yyyy, dd/mm/yyyy, dd-mm-yyyy, dd.mm.yyyy
     *
     * @author Alok Chaudhary, Filbert(with the help of Java regex converter)
     */
    public static final String DATE_VALIDATION_REGEX = "(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]|"
            + "(?:Jan|Mar|May|Jul|Aug|Oct|Dec)))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2]|"
            + "(?:Jan|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec))\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^"
            + "(?:29(\\/|-|\\.)(?:0?2|(?:Feb))\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|"
            + "(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9]|"
            + "(?:Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep))|(?:1[0-2]|(?:Oct|Nov|Dec)))"
            + "\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})";

    public static final String TIME_PARSE_FORMAT_CHOICE_12HR = "[h:mma]" + "[h.mma]" + "[hmma]";
    public static final String TIME_PARSE_FORMAT_CHOICE_24HR = "[k:mm]" + "[k.mm]" + "[kkmm]";

    public static final String[] DATE_PARSE_FORMAT_UNTIMED_CHOICE = {"[d-M-uuuu]","[d-MMM-uuuu]",
            "[d.M.uuuu]","[d.MMM.uuuu]", "[d/M/uuuu]","[d/MMM/uuuu]","[uuuu-M-dd]","[d-M-yy]",
            "[d-M-yy]", "[d-MMM-yy]", "[d.M.yy]", "[d.MMM.yy]", "[d/MMM/yy]", "[d/M/yy]"};

    public static final String DATE_TIME_PRINT_FORMAT = "dd-MMM-uuuu h:mma";
    public static final String DATE_PRINT_FORMAT = "dd-MMM-uuuu";
    public static final String XML_DATE_TIME_OPTIONAL_FORMAT = "uuuu-MM-dd HH:mm";
    public static final String TIME_PRINT_FORMAT = "h:mma";

    public final String value; //value to store date in UK format
    private LocalDateTime startDate; //US format by java YYYY-MM-DD
    private Optional<LocalDateTime> endDate;
    private boolean isUntimed;

    /**
     * Validates given date.
     *
     * @param a string consisting of only the date i.e dd-MMM-YYYY no time
     * @return an untimed date for untimed task
     * @throws IllegalValueException if given time string is invalid.
     */

    public Time(String date) throws IllegalValueException {
        assert date != null;
        date = fixStoredDataForTest(date);
        date = date.toUpperCase(); // fix for strings that bypass parser from other components..
        assert (isValidDate(date)); // if this fails, you have used the wrong constructor
        endDate = Optional.empty();
        isUntimed = true;
        DateTimeFormatter formatter = setDateFormatter();
        date = fixMonthForJavaFormat(date);
        this.startDate = LocalDate.parse(date, formatter).atTime(LocalTime.now().truncatedTo(ChronoUnit.MINUTES));
        value = timeToUkFormat();
    }
    /**
     * Java only accepts camel case dates, this method converts text month into camel case
     *
     * @param date
     * @return date with month in camel case
     */

    private String fixMonthForJavaFormat(String date) {
        int capsMonth = -1;
        date = date.toLowerCase().trim();
        if (Character.isAlphabetic(date.charAt(2))) { //for 1st character in date with 2 digits 1 delimiter,
            capsMonth = 2;
        } else if (Character.isAlphabetic(date.charAt(3))) { // 1st character in date with 1 digit 1 delimiter
            capsMonth = 3;
        }
        if (capsMonth != -1){
            String fixedMonth = date.substring(0,capsMonth) + Character.toString(date.charAt(capsMonth)).toUpperCase()+date.substring(capsMonth + 1);
            return fixedMonth;
        } else {
            return date;
        }
    }

    /*
     * For formatting to date when receiving for LogicTest Manager
     *
     * @param date format given by LogictestManager
     */
    private String fixStoredDataForTest(String date) {
        date = date.replaceAll("(Optional\\[)", " ");
        date = date.replaceAll("\\]", " ");
        date = date.trim();
        return date;
    }

    /**
     * Validates given date and deadline.
     *
     * @param a string consisting of only the date and a single time
     * @return a deadline for timed task
     * @throws IllegalValueException if given time string is invalid.
     */
    public Time(String startDate, String startTime) throws IllegalValueException {
        assert startDate != null;
        assert startTime != null;
        startTime = startTime.toUpperCase();
        endDate = Optional.empty();
        isUntimed = false;
        startDate = startDate.toUpperCase(); // fix for strings that bypass parser from other components.
        startDate = fixMonthForJavaFormat(startDate);
        DateTimeFormatter dateFormatter = setDateFormatter();
        DateTimeFormatter timeFormatter = setTimeFormatter();
        LocalDate localDate = LocalDate.parse(startDate, dateFormatter);
        LocalTime localTime = LocalTime.parse(startTime, timeFormatter);
        this.startDate = localDate.atTime(localTime);
        value = timeToUkFormat();
    }
    /**
     * Validates given date and TimeRange.
     *
     * @param a string consisting of only the date and a start and end time
     * @return a task with Time Range.
     * @throws IllegalValueException if given time string is invalid.
     */
    public Time(String startDate, String startTime, String endTime) throws IllegalValueException {
        assert startDate != null;
        assert startTime != null;
        assert endTime != null;
/*        if (!startDate.isEmpty()&&!isValidDate(startDate)) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        }*/

        isUntimed = false;
        startDate = startDate.toUpperCase(); // fix for strings that bypass parser from other components.
        startDate = fixMonthForJavaFormat(startDate);
        startTime = startTime.toUpperCase();
        endTime = endTime.toUpperCase();
        DateTimeFormatter formatter = setDateFormatter();
        DateTimeFormatter timeFormatter = setTimeFormatter();
        LocalDate localDate = LocalDate.parse(startDate, formatter);
        LocalTime localstartTime = LocalTime.parse(startTime, timeFormatter);
        LocalTime localendTime = LocalTime.parse(endTime, timeFormatter);
        this.startDate = localDate.atTime(localstartTime);
        this.endDate = Optional.ofNullable(localDate.atTime(localendTime));
        value = timeToUkFormat();
    }

    /*
     * To retrieve information of Untimed and Deadline Task from previous session
     *
     * @param, from XmlAdaptedTask storage of Untimed and Deadline Tasks
     * @returns imported formatted details of Untimed and Deadline Task
     */
    public Time (String startDate, boolean isUntimed) {
        assert startDate != null;
        this.startDate = LocalDateTime.parse(startDate, DateTimeFormatter.ofPattern(DATE_TIME_PRINT_FORMAT));
        endDate = Optional.empty();
        this.isUntimed = isUntimed;
        value = timeToUkFormat();
    }

    /*
     * To retrieve information of TimeRange Task from previous session
     *
     * @param, from xmlAdapted Task of TimeRange Type task.
     * @returns, imported formatted details of TimeRange Task
     */
    public Time (String startDate, String endDate, boolean isUntimed) {
        endDate = fixStoredDataXml(endDate);
        this.startDate = LocalDateTime.parse(startDate, DateTimeFormatter.ofPattern(DATE_TIME_PRINT_FORMAT));
        this.endDate = Optional.of(LocalDateTime.parse(endDate, DateTimeFormatter.ofPattern(XML_DATE_TIME_OPTIONAL_FORMAT)));
        this.isUntimed = isUntimed;
        value = timeToUkFormat();
    }

    /*
     * To remove the delimiters java uses to store a Optional date time
     *
     * @param a string which bypass parser validation due to receiving from system containing [Optional]date time
     * @return a formatted date time
     */
    private String fixStoredDataXml(String endDate) {
        endDate = endDate.replaceAll("[^\\d-:]", " ");
        endDate = endDate.trim();
        return endDate;
    }

    /*
     * Initialize the date formatter for parsing different types of date formats.
     * @returns formatter for LocalDate
     */
    private DateTimeFormatter setDateFormatter() {

        DateTimeFormatterBuilder formatterBuilder = new DateTimeFormatterBuilder();
        formatterBuilder.append(DateTimeFormatter.ofPattern(""));
        for (String format : DATE_PARSE_FORMAT_UNTIMED_CHOICE) {
            formatterBuilder.append(DateTimeFormatter.ofPattern(format));
        }
        DateTimeFormatter formatter = formatterBuilder.toFormatter(Locale.UK);
        return formatter;
    }


    /*
     * Initialize the date formatter for parsing different types of date formats.
     * @returns a formatter for LocalTime.
     */
    private DateTimeFormatter setTimeFormatter() {
        DateTimeFormatterBuilder formatterBuilder = new DateTimeFormatterBuilder();
        formatterBuilder.append(DateTimeFormatter.ofPattern(TIME_PARSE_FORMAT_CHOICE_12HR));
        formatterBuilder.append(DateTimeFormatter.ofPattern(TIME_PARSE_FORMAT_CHOICE_24HR));
        DateTimeFormatter formatter = formatterBuilder.toFormatter();
        return formatter;
    }

   //Store date as UK-format string
    public String timeToUkFormat() {
        if (isUntimed) {
            return startDate.format(DateTimeFormatter.ofPattern(DATE_PRINT_FORMAT));
        } else {
            return startDate.format(DateTimeFormatter.ofPattern(DATE_TIME_PRINT_FORMAT));
        }
    }

    /**
     * TODO: Change validation to comparing valid time Range
     *       Parsing of valid date arguments is in parser.
     * Returns true if a given string is a valid task time.
     */
    public static boolean isValidDate(String test) {
        return test.matches(Parser.DATE_VALIDATION_FORMAT);

    }

    public boolean getUntimedStatus() {
        return isUntimed;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PRINT_FORMAT);
        String text = startDate.format(formatter);
        return text;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Time // instanceof handles nulls
                && this.value.equals(((Time) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    public Optional<LocalDateTime> getEndDate() {
        return endDate;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public String getStartDateString() {
        if (isUntimed) {
            return startDate.toLocalDate().format(DateTimeFormatter.ofPattern(DATE_PRINT_FORMAT));
        } else {
            return startDate.format(DateTimeFormatter.ofPattern(DATE_TIME_PRINT_FORMAT));
        }

    }

    public String getEndTimeString() {
        if (!endDate.isPresent()) {
            return null;
        }
        return endDate.get().toLocalTime().format(DateTimeFormatter.ofPattern(TIME_PRINT_FORMAT));
    }

    //@@author A0135812L
    /**
     * This determines the natural ordering of the task.
     *
     * @return type of task
     */
    @Override
    public int compareTo(Time other) {

        LocalDateTime thisStartDateTime = this.startDate;
        LocalDateTime otherStartDateTime = other.startDate;
        LocalDate thisStartDate = thisStartDateTime.toLocalDate();
        LocalDate otherStartDate = otherStartDateTime.toLocalDate();

        // Compare Date first. Unable to compare DateTime directly as some might be untimed
        int cmp = thisStartDate.compareTo(otherStartDate);
        if(cmp!=0){
            return cmp;
        }
        // If it is on the same date but "this" is untimed it will go before the other
        if(this.isUntimed){
            return -1;
        }else if(other.isUntimed){
            // If it is on the same date but other is untimed "this" will go after other
            return 1;
        }

        LocalTime thisStartTime = thisStartDateTime.toLocalTime();
        LocalTime otherStartTime = otherStartDateTime.toLocalTime();
        cmp = thisStartTime.compareTo(otherStartTime);
        if(cmp!=0){
            return cmp;
        }

        if(!this.getEndDate().isPresent()){
            return -1;
        }else if(!other.getEndDate().isPresent()){
            return 1;
        }

        LocalDateTime thisEndDateTime = this.getEndDate().get();
        LocalDateTime otherEndDateTime = other.getEndDate().get();

        cmp = thisEndDateTime.compareTo(otherEndDateTime);
        return cmp;
    }
    //@@author

}
