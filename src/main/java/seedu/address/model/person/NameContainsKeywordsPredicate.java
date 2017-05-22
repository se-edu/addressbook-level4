package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that {@code Name} matches the keyword given.
 */
public class NameContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    public final String keyword;

    public NameContainsKeywordsPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword);
    }
}
