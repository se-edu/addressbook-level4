package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class NameContainsKeywordPredicateTest {

    @Test
    public void test_nameContainsKeywords_returnsTrue() throws Exception {
        NameContainsKeywordPredicate predicate = new NameContainsKeywordPredicate("Hans");
        assertTrue(predicate.test(new PersonBuilder().withName("Hans Anderson").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() throws Exception {
        NameContainsKeywordPredicate predicate = new NameContainsKeywordPredicate("Owen");
        assertFalse(predicate.test(new PersonBuilder().withName("Christopher Low").build()));
    }
}
