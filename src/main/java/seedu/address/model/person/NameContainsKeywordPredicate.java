package seedu.address.model.person;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name} matches the keyword given.
 */
public class NameContainsKeywordPredicate implements Predicate<ReadOnlyPerson> {
    private final String keyword;

    public NameContainsKeywordPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword);
    }

    /**
     * Creates a {@code Predicate<ReadOnlyPerson>} using {@code keywords} for {@code function}
     */
    public static Predicate<ReadOnlyPerson> createPredicate(String[] keywords,
                    Function<String, ? extends Predicate<ReadOnlyPerson>> function) {
        return Arrays.stream(keywords).map(function).map(predicate -> (Predicate<ReadOnlyPerson>) predicate)
                .reduce(Predicate::or).get();
    }
}
