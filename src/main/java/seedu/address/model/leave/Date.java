package seedu.address.model.leave;

import java.time.DateTimeException;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class Date {

    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Wrong date format;";

    public final String date;



    public Date (String date){
        requireNonNull(date);
        checkArgument(isValidDate(date),MESSAGE_DATE_CONSTRAINTS);
        this.date = date;
    }


    public static boolean isValidDate(String date) throws DateTimeException, NumberFormatException {

        String[] dateComponents = date.split("/");
        int day = Integer.parseInt(dateComponents[0]);
        int month = Integer.parseInt(dateComponents[1]);
        int year = Integer.parseInt(dateComponents[2]);

        if (date.length() != 10) {
            return false;
        }

        if (dateComponents.length != 3) {
            return false;
        }

        if (month < 1 || month > 12)
            return false;
        if (day < 1 || day> 31)
            return false;

        return true;
    }

    @Override
    public String toString() {
        return date;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Date // instanceof handles nulls
                && date.equals(((Date) other).date)); // state check
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }

}
