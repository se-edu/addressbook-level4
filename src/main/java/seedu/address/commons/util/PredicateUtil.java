package seedu.address.commons.util;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Predicate;

import seedu.address.model.person.ReadOnlyPerson;

/**
 * Utility methods related to creating predicates
 */
public class PredicateUtil {

    /**
     * Creates a {@code Predicate} using {@code constructor} with parameters {@code keywords}
     */
    public static Predicate<ReadOnlyPerson> createPredicate(
            Function<String, ? extends Predicate<ReadOnlyPerson>> constructor, String... keywords) {
        requireAllNonNull(keywords, constructor);
        AppUtil.checkArgument(keywords.length > 0);
        return Arrays.stream(keywords).map(constructor).map(predicate -> (Predicate<ReadOnlyPerson>) predicate)
                .reduce(Predicate::or).get();
    }
}
