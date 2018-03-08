package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class Age {
    public static final String AGE_CONSTRAINTS =
            "Persons age must be above 0 years old and under 150.";

    public final Integer age;

    public Age(Integer age) {
        requireNonNull(age);
        checkArgument(isValidAge(age), AGE_CONSTRAINTS);
        this.age = age;
    }

    public static boolean isValidAge(Integer age) {
        return age >= 0 && age < 150;
    }

    @Override
    public String toString() {
        return age.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Age
                && this.age == ((Age) other).age);
    }

}
