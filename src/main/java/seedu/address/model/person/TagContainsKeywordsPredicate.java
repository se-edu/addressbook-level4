package seedu.address.model.person;

import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s {@code UniqueTagList} matches any of the keywords given.
 */
public class TagContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public TagContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        Iterator tagsSetIterator = person.getTags().iterator();
        StringBuilder sb = new StringBuilder();
        sb.append(tagsSetIterator.next());
        while (tagsSetIterator.hasNext()) {
            sb.append(" " + tagsSetIterator.next());
        }
        String tagStringList = sb.toString()
                .replace("[", "")
                .replace("]", "");
        return keywords.isEmpty()
                ||  keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordsIgnoreCase(tagStringList, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((TagContainsKeywordsPredicate) other).keywords)); // state check
    }

}
