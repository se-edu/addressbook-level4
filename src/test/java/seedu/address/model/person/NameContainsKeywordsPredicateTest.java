package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class NameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstKeyword = Collections.singletonList("first");
        List<String> secondKeyword = Arrays.asList("first", "second");

        NameContainsKeywordsPredicate firstPredicate = new NameContainsKeywordsPredicate(firstKeyword);
        NameContainsKeywordsPredicate secondPredicate = new NameContainsKeywordsPredicate(secondKeyword);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NameContainsKeywordsPredicate findFirstCommandCopy = new NameContainsKeywordsPredicate(firstKeyword);
        assertTrue(firstPredicate.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() throws Exception {
        // Name matches all keywords
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Arrays.asList("Bob", "Alice"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new NameContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Extra non-matching keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob", "Carol"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Mixed-case keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Repeated matching keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Alice", "Bob"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Repeated non-matching keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob", "Bob"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() throws Exception {
        // Non-matching keywords
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Invalid names as keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("$%", "***"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }
}
