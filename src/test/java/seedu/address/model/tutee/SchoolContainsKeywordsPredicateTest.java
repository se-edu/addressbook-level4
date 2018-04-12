package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SCHOOL_AMY;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.model.tutee.SchoolContainsKeywordsPredicate;
import seedu.address.testutil.TuteeBuilder;

//@@author yungyung04
public class SchoolContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        SchoolContainsKeywordsPredicate firstPredicate =
                new SchoolContainsKeywordsPredicate(firstPredicateKeywordList);
        SchoolContainsKeywordsPredicate secondPredicate =
                new SchoolContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        SchoolContainsKeywordsPredicate firstPredicateCopy =
                new SchoolContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different schools -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_schoolContainsKeywords_returnsTrue() {
        // One keyword
        SchoolContainsKeywordsPredicate predicate =
                new SchoolContainsKeywordsPredicate(Collections.singletonList("nan"));
        assertTrue(predicate.test(new TuteeBuilder().withSchool("nan hua high school").build()));

        // Only one matching keyword
        predicate = new SchoolContainsKeywordsPredicate(Arrays.asList("nan", "victoria"));
        assertTrue(predicate.test(new TuteeBuilder().withSchool("victoria").build()));

        // Mixed-case keywords
        predicate = new SchoolContainsKeywordsPredicate(Arrays.asList("nan"));
        assertTrue(predicate.test(new TuteeBuilder().withSchool("NAN").build()));
    }

    @Test
    public void test_schoolDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        SchoolContainsKeywordsPredicate predicate =
                new SchoolContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new TuteeBuilder().withSchool(VALID_SCHOOL_AMY).build()));

        // Non-matching keyword
        predicate = new SchoolContainsKeywordsPredicate(Arrays.asList("victoria"));
        assertFalse(predicate.test(new TuteeBuilder().withSchool("nan hua high school").build()));

        // Keywords match education level, grade and subject, but does not match school
        predicate = new SchoolContainsKeywordsPredicate(Arrays.asList("B", "primary", "mathematics"));
        assertFalse(predicate.test(new TuteeBuilder().withSchool(VALID_SCHOOL_AMY).withGrade("B")
                .withEducationLevel("primary").withSubject("mathematics").build()));

        // Keywords match email and address, but does not match school
        predicate = new SchoolContainsKeywordsPredicate(Arrays
                .asList("victoria", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new TuteeBuilder().withSchool("nan hua high school")
                .withEmail("alice@email.com").withAddress("Main Street").build()));

    }
}
