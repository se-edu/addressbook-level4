package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.model.tutee.GradeContainsKeywordsPredicate;
import seedu.address.testutil.TuteeBuilder;

public class GradeContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        GradeContainsKeywordsPredicate firstPredicate =
                new GradeContainsKeywordsPredicate(firstPredicateKeywordList);
        GradeContainsKeywordsPredicate secondPredicate =
                new GradeContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        GradeContainsKeywordsPredicate firstPredicateCopy =
                new GradeContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different education levels -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_gradeContainsKeywords_returnsTrue() {
        // One keyword
        GradeContainsKeywordsPredicate predicate =
                new GradeContainsKeywordsPredicate(Collections.singletonList(VALID_GRADE_AMY));
        assertTrue(predicate.test(new TuteeBuilder().withGrade(VALID_GRADE_AMY).build()));

        // Only one matching keyword
        predicate = new GradeContainsKeywordsPredicate(Arrays.asList(VALID_GRADE_AMY, VALID_GRADE_BOB));
        assertTrue(predicate.test(new TuteeBuilder()
                .withGrade(VALID_GRADE_AMY).build()));

        // Mixed-case keywords
        predicate = new GradeContainsKeywordsPredicate(Arrays.asList("a"));
        assertTrue(predicate.test(new TuteeBuilder().withGrade("A").build()));
    }

    @Test
    public void test_gradeDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        GradeContainsKeywordsPredicate predicate =
                new GradeContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new TuteeBuilder().withGrade(VALID_GRADE_AMY).build()));

        // Non-matching keyword
        predicate = new GradeContainsKeywordsPredicate(Arrays.asList(VALID_GRADE_BOB));
        assertFalse(predicate.test(new TuteeBuilder().withGrade(VALID_GRADE_AMY).build()));

        // Keywords match education level, school and subject, but does not match grade
        predicate = new GradeContainsKeywordsPredicate(Arrays.asList("school", "primary", "mathematics"));
        assertFalse(predicate.test(new TuteeBuilder().withGrade(VALID_GRADE_AMY).withSchool("school")
                .withEducationLevel("primary").withSubject("mathematics").build()));

        // Keywords match email and address, but does not match grade
        predicate = new GradeContainsKeywordsPredicate(Arrays
                .asList(VALID_GRADE_BOB, "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new TuteeBuilder().withGrade(VALID_GRADE_AMY)
                .withEmail("alice@email.com").withAddress("Main Street").build()));

    }
}
