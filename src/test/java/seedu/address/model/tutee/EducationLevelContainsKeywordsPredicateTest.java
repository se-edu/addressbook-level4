package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EDUCATION_LEVEL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EDUCATION_LEVEL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EDUCATION_LEVEL_ROBERT;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.model.tutee.EducationLevelContainsKeywordsPredicate;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TuteeBuilder;

public class EducationLevelContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        EducationLevelContainsKeywordsPredicate firstPredicate =
                new EducationLevelContainsKeywordsPredicate(firstPredicateKeywordList);
        EducationLevelContainsKeywordsPredicate secondPredicate =
                new EducationLevelContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        EducationLevelContainsKeywordsPredicate firstPredicateCopy =
                new EducationLevelContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different education levels -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_educationLevelContainsKeywords_returnsTrue() {
        // One keyword
        EducationLevelContainsKeywordsPredicate predicate =
                new EducationLevelContainsKeywordsPredicate(Collections.singletonList(VALID_EDUCATION_LEVEL_AMY));
        assertTrue(predicate.test(new TuteeBuilder().withEducationLevel(VALID_EDUCATION_LEVEL_AMY).build()));

        // Multiple keywords
        predicate = new EducationLevelContainsKeywordsPredicate(Arrays
                .asList("junior", "college"));
        assertTrue(predicate.test(new TuteeBuilder()
                .withEducationLevel("junior college").build()));

        // Only one matching keyword
        predicate = new EducationLevelContainsKeywordsPredicate(Arrays.asList("junior"));
        assertTrue(predicate.test(new TuteeBuilder()
                .withEducationLevel("junior college").build()));

        // Mixed-case keywords
        predicate = new EducationLevelContainsKeywordsPredicate(Arrays.asList("JuNiOr", "colLEGE"));
        assertTrue(predicate.test(new TuteeBuilder().withEducationLevel("junior college").build()));
    }

    @Test
    public void test_educationLevelDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        EducationLevelContainsKeywordsPredicate predicate =
                new EducationLevelContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new TuteeBuilder().withEducationLevel(VALID_EDUCATION_LEVEL_AMY).build()));

        // Non-matching keyword
        predicate = new EducationLevelContainsKeywordsPredicate(Arrays.asList(VALID_EDUCATION_LEVEL_ROBERT));
        assertFalse(predicate.test(new TuteeBuilder().withEducationLevel(VALID_EDUCATION_LEVEL_AMY).build()));

        // Keywords match grade, school and subject, but does not match education level
        predicate = new EducationLevelContainsKeywordsPredicate(Arrays.asList("school", "B", "mathematics"));
        assertFalse(predicate.test(new TuteeBuilder().withEducationLevel(VALID_EDUCATION_LEVEL_AMY).withSchool("school")
                .withGrade("B").withSubject("mathematics").build()));

        // Keywords match email and address, but does not match education level
        predicate = new EducationLevelContainsKeywordsPredicate(Arrays
                .asList("Bob", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new TuteeBuilder().withEducationLevel(VALID_EDUCATION_LEVEL_AMY)
                .withEmail("alice@email.com").withAddress("Main Street").build()));

    }
}
