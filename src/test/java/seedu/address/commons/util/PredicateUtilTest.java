package seedu.address.commons.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.person.NameContainsKeywordPredicate;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.PersonBuilder;

public class PredicateUtilTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void createPredicate_emptyArray_throwsIllegalArgumentException() throws IllegalArgumentException {
        thrown.expect(IllegalArgumentException.class);
        PredicateUtil.createPredicate(NameContainsKeywordPredicate::new, new String[0]);
    }

    @Test
    public void createPredicate_nameDoesNotMatchKeyword_predicateRejectsPerson() throws Exception {
        // One keyword
        assertNamePredicateRejectsPerson(createNamePredicate("Alice"), "Alibaba Babu");

        // Multiple keywords
        assertNamePredicateRejectsPerson(createNamePredicate("Alice", "Bob", "Charlie"), "Alibaba Babu");

        // Invalid name as keywords
        assertNamePredicateRejectsPerson(createNamePredicate("$%", "***"), "Alice Bob");
    }

    @Test
    public void createPredicate_nameMatchesKeyword_predicateAcceptsPerson() throws Exception {
        // One keyword
        assertNamePredicateAcceptsPerson(createNamePredicate("Alice"), "Alice Bob");

        // Repeated keywords
        assertNamePredicateAcceptsPerson(createNamePredicate("Alice", "Bob", "Bob"), "Alice");

        // Multiple keywords
        assertNamePredicateAcceptsPerson(createNamePredicate("Alice", "Bob"), "Alice Bob");
        assertNamePredicateAcceptsPerson(createNamePredicate("Alice", "Bob"), "Charlie Bob");
        assertNamePredicateAcceptsPerson(createNamePredicate("Alice", "Bob", "Charlie", "Dick"), "Alice Charlie");
    }

    /**
     * Asserts that {@code predicate} accepts a {@code Person} with the given {@code name}
     */
    private void assertNamePredicateAcceptsPerson(Predicate<ReadOnlyPerson> predicate, String name) throws Exception {
        assertTrue(predicate.test(new PersonBuilder().withName(name).build()));
    }

    /**
     * Asserts that {@code predicate} rejects a {@code Person} with the given {@code name}
     */
    private void assertNamePredicateRejectsPerson(Predicate<ReadOnlyPerson> predicate, String name) throws Exception {
        assertFalse(predicate.test(new PersonBuilder().withName(name).build()));
    }

    /**
     * Creates a {@code Predicate<ReadOnlyPerson>} that returns true if the {@code ReadOnlyPerson}'s {@code Name}
     * contains any of the {@code keywords}
     */
    private Predicate<ReadOnlyPerson> createNamePredicate(String... keywords) {
        return PredicateUtil.createPredicate(NameContainsKeywordPredicate::new, keywords);
    }
}
