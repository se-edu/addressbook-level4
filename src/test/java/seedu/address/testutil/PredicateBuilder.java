package seedu.address.testutil;

import java.util.Arrays;
import java.util.function.Predicate;

import seedu.address.model.person.NameContainsKeywordPredicate;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * A utility class to help with building {@code Predicate<ReadOnlyPerson>} objects.
 */
public class PredicateBuilder {

    private Predicate<ReadOnlyPerson> predicate;

    public PredicateBuilder withNamePredicate(String... keywords) {
        Predicate<ReadOnlyPerson> resultantPredicate = Arrays.stream(keywords).map(NameContainsKeywordPredicate::new)
                .map(predicate -> (Predicate<ReadOnlyPerson>) predicate).reduce(Predicate::or).get();

        predicate = (predicate == null) ? resultantPredicate : predicate.or(resultantPredicate);

        return this;
    }

    public Predicate<ReadOnlyPerson> build() {
        return predicate;
    }

}
