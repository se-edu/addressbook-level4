package seedu.address.model.task;

import java.time.LocalDateTime;
import java.util.Comparator;

import seedu.address.model.Task;

//@@author yungyung04
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
