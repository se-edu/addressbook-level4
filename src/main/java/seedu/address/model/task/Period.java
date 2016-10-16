package seedu.address.model.task;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

import seedu.address.commons.exceptions.IllegalValueException;

public class Period {

    public static final String TIME_DATE_CONSTRAINTS = "Time should be in valid format";

    /**
     * Time validation regex: 1st main group = 12Hour format with AM/PM required
     *                        2nd main group = 24Hour format without AM/PM
     *
     * @author Filbert
     */
    public static final String TIME_VALIDATION_REGEX = "((1[012]|0?[1-9])[:.]?[0-5][0-9]([aApP][mM]))|"
            + "(([01]\\d|2[0-3])[:.]?([0-5]\\d))";

    public static final String TIME_PARSE_FORMAT_CHOICE_12HR = "[h:mma]" + "[h.mma]" + "[hmma]";
    public static final String TIME_PARSE_FORMAT_CHOICE_24HR = "[k:mm]" + "[k.mm]" + "[kkmm]";

    public final String value;
    public final LocalTime time;

    /**
     * Validates given Time.
     *
     * @throws IllegalValueException if given Time string is invalid.
     */
    public Period(String time) throws IllegalValueException {
        time = time.trim().toUpperCase();
        if (!time.isEmpty()&&!isValidDate(time)) {
            throw new IllegalValueException(TIME_DATE_CONSTRAINTS);
        }
        DateTimeFormatter formatter= setFormatter();
        this.time = LocalTime.parse(time, formatter);
        value = toMyFormat();
    }

    private String toMyFormat() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mma");
        String text = time.format(formatter);
        return text;
    }

    private DateTimeFormatter setFormatter() {
        DateTimeFormatterBuilder formatterBuilder = new DateTimeFormatterBuilder();
        formatterBuilder.append(DateTimeFormatter.ofPattern(TIME_PARSE_FORMAT_CHOICE_12HR));
        formatterBuilder.append(DateTimeFormatter.ofPattern(TIME_PARSE_FORMAT_CHOICE_24HR));
        DateTimeFormatter formatter = formatterBuilder.toFormatter();
        return formatter;
    }

    /**
     * Returns true if a given string is a valid task time.
     */
    public static boolean isValidDate(String test) {
        return test.matches(TIME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Period // instanceof handles nulls
                && this.value.equals(((Period) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
