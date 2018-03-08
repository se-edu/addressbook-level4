package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class ContainKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");
        List<String> thirdPredicateKeywordList = Collections.emptyList();

        ContainKeywordsPredicate firstPredicate = new ContainKeywordsPredicate(
                firstPredicateKeywordList, thirdPredicateKeywordList);
        ContainKeywordsPredicate secondPredicate = new ContainKeywordsPredicate(
                secondPredicateKeywordList, thirdPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        ContainKeywordsPredicate firstPredicateCopy = new ContainKeywordsPredicate(
                firstPredicateKeywordList, thirdPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_containKeywords_returnsTrue() {
        // Both zero keyword
        ContainKeywordsPredicate predicate = new ContainKeywordsPredicate(
                Collections.emptyList(), Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").withTags("Friends").build()));

        // Both one keyword
        predicate = new ContainKeywordsPredicate(
                Collections.singletonList("Alice"), Collections.singletonList("Friends"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").withTags("Friends").build()));

        // Name one keyword
        predicate = new ContainKeywordsPredicate(
                Collections.singletonList("Alice"), Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").withTags("Friends").build()));

        // Tag one keyword
        predicate = new ContainKeywordsPredicate(
                Collections.emptyList(), Collections.singletonList("Friends"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").withTags("Friends").build()));

        // Both multiple keywords, but only one matches
        predicate = new ContainKeywordsPredicate(
                Arrays.asList("Alice", "Carol"), Arrays.asList("Friends", "Enemy"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").withTags("Friends").build()));

        // Both mixed-case keyword
        predicate = new ContainKeywordsPredicate(
                Collections.singletonList("aLIce"), Collections.singletonList("fRIeNDs"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").withTags("Friends").build()));
    }

    @Test
    public void test_notContainKeywords_returnsFalse() {
        // Non-matching keyword
        ContainKeywordsPredicate predicate = new ContainKeywordsPredicate(
                Collections.singletonList("Carol"), Collections.singletonList("Enemy"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").withTags("Friends").build()));

        // Keyword match phone, email, address, and tags, but does not match name
        predicate = new ContainKeywordsPredicate(
                Arrays.asList("12345 alice@email.com Main Street Friends"), Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withTags("Friends").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));

        // Keyword match phone, email, address, and tags, but does not match tag
        predicate = new ContainKeywordsPredicate(
                Collections.emptyList(), Arrays.asList("Alice 12345 alice@email.com Main Street"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }
}
