package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class NameContainsKeywordPredicateTest {

    @Test
    public void equals() {
        List<String> firstKeyword = Collections.singletonList("first");
        List<String> secondKeyword = Arrays.asList("first", "second");

        NameContainsKeywordPredicate firstPredicate = new NameContainsKeywordPredicate(firstKeyword);
        NameContainsKeywordPredicate secondPredicate = new NameContainsKeywordPredicate(secondKeyword);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NameContainsKeywordPredicate findFirstCommandCopy = new NameContainsKeywordPredicate(firstKeyword);
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
        NameContainsKeywordPredicate predicate = new NameContainsKeywordPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() throws Exception {
        NameContainsKeywordPredicate predicate = new NameContainsKeywordPredicate(Collections.singletonList("Bob"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Charlie").build()));
    }
}
