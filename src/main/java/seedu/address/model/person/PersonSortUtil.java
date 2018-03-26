package seedu.address.model.person;

import java.util.Comparator;

import seedu.address.model.tutee.Tutee;

/**
 * Provides utilities for sorting a list of Tutees.
 */
public class PersonSortUtil {
    public static final String CATEGORY_NAME = "name";
    public static final String CATEGORY_MONTH = "month";
    public static final String CATEGORY_EDUCATION_LEVEL = "level";
    public static final String CATEGORY_GRADE = "grade";
    public static final String CATEGORY_SCHOOL = "school";
    public static final String CATEGORY_SUBJECT = "subject";
    public static final int NEGATIVE_DIGIT = -1;
    public static final int POSITIVE_DIGIT = 1;

    public Comparator<Person> getComparator(String sortCategory) {
        Comparator<Person> comparator = null;

        switch (sortCategory) {
        case CATEGORY_NAME:
            comparator = getNameComparator();
            break;
        case CATEGORY_SUBJECT:
            comparator = getSubjectComparator();

        }
        return comparator;
    }

    private Comparator<Person> getSubjectComparator() {
        return new Comparator<Person>() {
            @Override
            public int compare(Person person1, Person person2) {
                int result = 0; //value will be replaced
                if (areBothTutees(person1, person2)) {

                    String personSubject1 = ((Tutee) person1).getSubject().toString();
                    String personSubject2 = ((Tutee) person2).getSubject().toString();

                    result = personSubject1.compareToIgnoreCase(personSubject2);
                } else if (isFirstTutee(person1, person2)) {
                    result = NEGATIVE_DIGIT;
                } else if (isSecondTutee(person1, person2)) {
                    result = POSITIVE_DIGIT;
                } else if (areNotTutees(person1, person2)) {
                    result = compareNameLexicographically(person1, person2);
                } else {
                    assert (false); //should never reach this statement
                }
                return result;
            }
        };
    }

    private Comparator<Person> getNameComparator() {
        return new Comparator<Person>() {
            @Override
            public int compare(Person person1, Person person2) {
                return compareNameLexicographically(person1, person2);
            }
        };
    }

    private boolean areNotTutees(Person person1, Person person2) {
        return !(person1 instanceof Tutee || person2 instanceof Tutee);
    }

    private boolean isSecondTutee(Person person1, Person person2) {
        return !(person1 instanceof Tutee) && person2 instanceof Tutee;
    }

    private boolean isFirstTutee(Person person1, Person person2) {
        return person1 instanceof Tutee && !(person2 instanceof Tutee);
    }

    private boolean areBothTutees(Person person1, Person person2) {
        return person1 instanceof Tutee && person2 instanceof Tutee;
    }

    private int compareNameLexicographically(Person person1, Person person2) {
        String personName1 = person1.getName().toString();
        String personName2 = person2.getName().toString();

        return personName1.compareToIgnoreCase(personName2);
    }
}
