package seedu.address.model.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's Date in the SmartyDo.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Time {

    public static final String MESSAGE_DATE_CONSTRAINTS = "Task Dates should be in valid UK-format "
            + "DD/MMM/YYYY or DD/MM/YYYY or DD.MM.YYYY or DD.MMM.YYY or DD-MM-YYYY or DD-MMM-YYYY";
    /**
     * Date validation in UK format, includes checks for valid date during leap years.
     * Supported Formats: dd/mmm/yyyy, dd-mmm-yyyy, dd/mm/yyyy, dd-mm-yyyy, dd.mm.yyyy
     *
     * @author Alok Chaudhary, Filbert(with the help of Java regex converter)
     */
    public static final String DATE_VALIDATION_REGEX = "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]|"
            + "(?:Jan|Mar|May|Jul|Aug|Oct|Dec)))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2]|"
            + "(?:Jan|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec))\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^"
            + "(?:29(\\/|-|\\.)(?:0?2|(?:Feb))\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|"
            + "(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9]|"
            + "(?:Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep))|(?:1[0-2]|(?:Oct|Nov|Dec)))"
            + "\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";
    public static final String[] DATE_PARSE_FORMAT_CHOICE = {"[dd-MM-uuuu]","[dd-MMM-uuuu]",
            "[dd.MM.uuuu]","[dd.MMM.uuuu]", "[dd/MM/uuuu]","[dd/MMM/uuuu]"};

    public final String value; //value to store date in UK format
    public final LocalDate date; //US format by java YYYY-MM-DD

    /**
     * Validates given time number.
     *
     * @throws IllegalValueException if given time string is invalid.
     */

    public Time(String date) throws IllegalValueException {
        assert date != null;
        date = date.trim();
        if (!date.isEmpty()&&!isValidDate(date)) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        }
        DateTimeFormatter formatter = setFormatter();
        this.date = LocalDate.parse(date, formatter);
        value = timeToUkFormat();
    }

    /*
     * Initialize the date formatter for parsing different types of date formats.
     */
    private DateTimeFormatter setFormatter() {
        DateTimeFormatterBuilder formatterBuilder = new DateTimeFormatterBuilder();
        formatterBuilder.append(DateTimeFormatter.ofPattern(""));
        for(String format : DATE_PARSE_FORMAT_CHOICE)
            formatterBuilder.append(DateTimeFormatter.ofPattern(format));
        DateTimeFormatter formatter = formatterBuilder.toFormatter(Locale.UK);
        return formatter;
    }

  //Store date as UK-format string
    private String timeToUkFormat() {
        return date.format(DateTimeFormatter.ofPattern("dd-MMM-uuuu"));

    }

    /**
     * Returns true if a given string is a valid task time.
     */
    public static boolean isValidDate(String test) {
        return test.matches(DATE_VALIDATION_REGEX);

    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMM-uuuu");
        String text = date.format(formatter);
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

}
