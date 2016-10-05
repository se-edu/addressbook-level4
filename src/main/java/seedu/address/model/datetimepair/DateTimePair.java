package seedu.address.model.datetimepair;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;

import java.util.Objects;

/**
 * Created by haliq on 5/10/16.
 */
public class DateTimePair implements ReadOnlyDateTimePair {
    /** complete explicit definition formatter for datetime */
    private static DateTimeFormatter longDTFormat = DateTimeFormat.forPattern("dd/MM/yyyy:HHmm");

    private UniqueTagList tags;

    /** unique identifier */
    private int id;
    /** open datetime t */
    private DateTime open;
    /** close datetime t' */
    private DateTime close;
    /** name of pair */
    private String name;
    /** flag to determine if floating */
    private boolean flt;

    /**
     * Create a datetime pair object
     * @param openArg   open datetime of dd/MM/yyyy:HHmm
     * @param closeArg  close datetime of dd/MM/yyyy:HHmm
     * @param nameArg   name of object
     * @param floatArg  flag to determine if floating
     */
    public DateTimePair(String openArg, String closeArg, String nameArg, boolean floatArg, UniqueTagList tags)
    throws IllegalArgumentException {
        assert !CollectionUtil.isAnyNull(openArg, closeArg, nameArg, floatArg, tags);
        this.open = longDTFormat.parseDateTime(openArg);
        this.close = longDTFormat.parseDateTime(closeArg);
        this.name = nameArg;
        this.flt = floatArg;
        this.tags = new UniqueTagList(tags);
    }

    /**
     * Copy constructor
     * @param source datetime pair object to copy from
     */
    public DateTimePair(ReadOnlyDateTimePair source) {
        this(
                longDTFormat.print(source.getOpen()),
                longDTFormat.print(source.getClose()),
                source.getName(),
                source.getFloat(),
                source.getTags());
    }


    @Override
    public DateTime getOpen() {
        return open;
    }

    @Override
    public DateTime getClose() {
        return close;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean getFloat() {
        return flt;
    }

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }

    /**
     * Replaces this person's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyDateTimePair // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyDateTimePair) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(open,close,name,flt);
    }

    @Override
    public String toString() {
        return getAsText();
    }

    public static String dateTimeToString(DateTime dt) {
        return longDTFormat.print(dt);
    }
}
