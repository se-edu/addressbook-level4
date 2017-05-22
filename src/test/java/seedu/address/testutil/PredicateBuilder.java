package seedu.address.testutil;

import java.util.function.Predicate;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * A utility class to help with building {@code Predicate<ReadOnlyPerson>} objects.
 */
public class PredicateBuilder {

    private Predicate<ReadOnlyPerson> predicate;

    public PredicateBuilder() {
        this.predicate = (person -> false);
    }

    public PredicateBuilder withNamePredicate(String... keywords) throws IllegalValueException {
        for (String keyword : keywords) {
            this.predicate = this.predicate.or(person -> StringUtil.containsWordIgnoreCase(
                                                        person.getName().fullName, keyword));
        }

        return this;
    }

    public Predicate<ReadOnlyPerson> build() {
        return this.predicate;
    }

}
