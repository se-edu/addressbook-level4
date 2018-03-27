package seedu.address.model.tutee;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Tutee}'s {@code Subject} matches any of the keywords given.
 */
public class SubjectContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public SubjectContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        if (!(person instanceof Tutee)) {
            return false;
        } else {
            return keywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase((
                            (Tutee) person).getSubject().toString(), keyword));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SubjectContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((SubjectContainsKeywordsPredicate) other).keywords)); // state check
    }
}
