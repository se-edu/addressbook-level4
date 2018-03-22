package seedu.address.model.tutee;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Tutee}'s {@code School} matches any of the keywords given.
 */
public class SchoolContainsKeywordsPredicate implements Predicate<Tutee> {
    private final List<String> keywords;

    public SchoolContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Tutee tutee) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(tutee.getSchool().toString(), keyword)) == true;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SchoolContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((SchoolContainsKeywordsPredicate) other).keywords)); // state check
    }
}
