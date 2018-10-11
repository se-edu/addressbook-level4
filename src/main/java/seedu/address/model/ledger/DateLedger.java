package seedu.address.model.ledger;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a date in the Ledger
 */

public class DateLedger {

    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Dates should be in the format DD/MM, and it should not be blank";
    public static final String MESSAGE_VALIDATION_REGEX =
            "^([0-2][0-9]||3[0-1])/(0[0-9]||1[0-2])$";

    private static String value;

    public DateLedger(String date) {
        requireNonNull(date);
        checkArgument(isValidDateLedger(date), MESSAGE_DATE_CONSTRAINTS);
        value = date;
    }

    public static boolean isValidDateLedger(String test) {
        return (test.matches(MESSAGE_VALIDATION_REGEX));
    }
}
