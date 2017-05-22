package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class NameContainsKeywordsPredicateTest {
    private NameContainsKeywordsPredicate predicate;

    @Test
    public void test_nameContainsKeywords_returnsTrue() throws Exception {
        predicate = new NameContainsKeywordsPredicate("Hans");
        assertTrue(predicate.test(new PersonBuilder().withName("Hans Anderson").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() throws Exception {
        predicate = new NameContainsKeywordsPredicate("Owen");
        assertFalse(predicate.test(new PersonBuilder().withName("Christopher Low").build()));
    }
}
