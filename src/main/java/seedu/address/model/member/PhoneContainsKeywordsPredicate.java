package seedu.address.model.member;

import seedu.address.commons.util.StringUtil;

import java.util.List;
import java.util.function.Predicate;

public class PhoneContainsKeywordsPredicate implements Predicate<Person> {

    private final List<String> keywords;

    public PhoneContainsKeywordsPredicate (List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getPhone().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PhoneContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((PhoneContainsKeywordsPredicate) other).keywords)); // state check
    }

}
