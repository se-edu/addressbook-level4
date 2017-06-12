package seedu.address.testutil;

import java.util.Arrays;
import java.util.function.Predicate;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * A utility class to help with building {@code Predicate<ReadOnlyPerson>} objects.
 */
public class PredicateBuilder {

    private Predicate<ReadOnlyPerson> predicate;

    public PredicateBuilder withNamePredicate(String... keywords) throws IllegalValueException {
        Predicate<ReadOnlyPerson> resultantPredicate =
                Arrays.stream(keywords).map(this::nameContains).reduce(Predicate::or).get();

        predicate = (predicate == null) ? resultantPredicate : predicate.or(resultantPredicate);

        return this;
    }

    public Predicate<ReadOnlyPerson> build() {
        return predicate;
    }

    /**
     * Returns a predicate that returns true if the {@code ReadOnlyPerson}'s {@code Name} contains {@code keyword}
     */
    private Predicate<ReadOnlyPerson> nameContains(String keyword) {
        return person -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword);
    }

}
