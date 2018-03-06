package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class TagContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        TagContainsKeywordsPredicate firstPredicate = new TagContainsKeywordsPredicate(firstPredicateKeywordList);
        TagContainsKeywordsPredicate secondPredicate = new TagContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TagContainsKeywordsPredicate firstPredicateCopy = new TagContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_tagContainsKeywords_returnsTrue() {
        // One keyword
        TagContainsKeywordsPredicate predicate =
                new TagContainsKeywordsPredicate(Collections.singletonList("Friends"));
        assertTrue(predicate.test(new PersonBuilder().withTags("Friends", "Colleagues").build()));

        // Multiple keywords
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("Friends", "Colleagues"));
        assertTrue(predicate.test(new PersonBuilder().withTags("Friends", "Colleagues").build()));

        // Only one matching keyword
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("Friends", "Spouse"));
        assertTrue(predicate.test(new PersonBuilder().withTags("Friends", "Family").build()));

        // Mixed-case keywords
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("fRiends", "colLEagues"));
        assertTrue(predicate.test(new PersonBuilder().withTags("Friends", "Colleagues").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withTags("Friends").build()));

        // Non-matching keyword
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("Friends"));
        assertFalse(predicate.test(new PersonBuilder().withTags("Enemy", "Nemesis").build()));

        // Keywords match name, phone, email and address, but does not match tag
        predicate = new TagContainsKeywordsPredicate(
                Arrays.asList("Alice", "12345", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withTags("Friends").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").withName("Alice").build()));
    }
}
