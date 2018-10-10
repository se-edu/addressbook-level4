package seedu.address.model.Events;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class Name {
    public final String ThisName;
    public static final String NAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";
    public static final String MESSAGE_NAME =
            "Names should only contain alphanumeric characters and spaces, and it should not be blank";
    public Name(String name){
        requireNonNull(name);
        checkArgument(CheckValid(name),MESSAGE_NAME);
        ThisName=name;
    }
    public static boolean CheckValid(String name){
        return name.matches(NAME_VALIDATION_REGEX);
    }

    public String toString() {
        return ThisName;
    }
    public static Boolean Checkqual(String name, String other){
        return (name==other);
    }
}
