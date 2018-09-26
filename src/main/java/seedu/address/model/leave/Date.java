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
        //checkArgument(isValidDate(date),MESSAGE_DATE_CONSTRAINTS);
        this.date = date;
    }


    public static boolean isValidDate(String date) throws DateTimeException, NumberFormatException {

        if (date.length() != 10) {
            return false;
        }

        String[] dateComponents = date.split("/");

        if (dateComponents.length != 3) {
            return false;
        }

        int day = Integer.parseInt(dateComponents[0]);
        int month = Integer.parseInt(dateComponents[1]);
        int year = Integer.parseInt(dateComponents[2]);

        //LocalDate localdate;
        //localdate = LocalDate.of(year, month, day);
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
