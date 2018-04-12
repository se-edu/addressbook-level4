package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.model.tutee.SubjectContainsKeywordsPredicate;
import seedu.address.testutil.TuteeBuilder;

//@@author yungyung04
public class SubjectContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        SubjectContainsKeywordsPredicate firstPredicate =
                new SubjectContainsKeywordsPredicate(firstPredicateKeywordList);
        SubjectContainsKeywordsPredicate secondPredicate =
                new SubjectContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        SubjectContainsKeywordsPredicate firstPredicateCopy =
                new SubjectContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different subjects -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_subjectContainsKeywords_returnsTrue() {
        // One keyword
        SubjectContainsKeywordsPredicate predicate =
                new SubjectContainsKeywordsPredicate(Collections.singletonList(VALID_SUBJECT_AMY));
        assertTrue(predicate.test(new TuteeBuilder().withSubject(VALID_SUBJECT_AMY).build()));

        // Only one matching keyword
        predicate = new SubjectContainsKeywordsPredicate(Arrays.asList(VALID_SUBJECT_AMY, VALID_SUBJECT_BOB));
        assertTrue(predicate.test(new TuteeBuilder().withSubject(VALID_SUBJECT_AMY).build()));

        // Mixed-case keywords
        predicate = new SubjectContainsKeywordsPredicate(Arrays.asList("MatheMAtics"));
        assertTrue(predicate.test(new TuteeBuilder().withSubject("mathematics").build()));
    }

    @Test
    public void test_subjectDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        SubjectContainsKeywordsPredicate predicate =
                new SubjectContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new TuteeBuilder().withSubject(VALID_SUBJECT_AMY).build()));

        // Non-matching keyword
        predicate = new SubjectContainsKeywordsPredicate(Arrays.asList(VALID_SUBJECT_BOB));
        assertFalse(predicate.test(new TuteeBuilder().withSubject(VALID_SUBJECT_AMY).build()));

        // Keywords match education level, grade and school, but does not match subject
        predicate = new SubjectContainsKeywordsPredicate(Arrays.asList("B", "primary", "school"));
        assertFalse(predicate.test(new TuteeBuilder().withSubject(VALID_SUBJECT_AMY).withGrade("B")
                .withEducationLevel("primary").withSchool("school").build()));

        // Keywords match email and address, but does not match subject
        predicate = new SubjectContainsKeywordsPredicate(Arrays
                .asList(VALID_SUBJECT_BOB, "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new TuteeBuilder().withSubject(VALID_SUBJECT_AMY)
                .withEmail("alice@email.com").withAddress("Main Street").build()));

    }
}
