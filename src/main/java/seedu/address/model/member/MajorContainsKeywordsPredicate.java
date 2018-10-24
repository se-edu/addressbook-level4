package seedu.address.model.member;

import seedu.address.commons.util.StringUtil;

import java.util.List;
import java.util.function.Predicate;


public class MajorContainsKeywordsPredicate implements Predicate<Person> {

    private final List<String> keywords;

    public MajorContainsKeywordsPredicate (List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getMajor().Course, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MajorContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((MajorContainsKeywordsPredicate) other).keywords)); // state check
    }

}
