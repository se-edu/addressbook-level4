package seedu.address.model.tutee;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Tutee}'s {@code Grade} matches any of the keywords given.
 */
public class GradeContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public GradeContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        if (!(person instanceof Tutee)) {
            return false;
        } else {
            return keywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase((
                            (Tutee) person).getGrade().toString(), keyword));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GradeContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((GradeContainsKeywordsPredicate) other).keywords)); // state check
    }
}
