package seedu.address.model.person;

import java.util.Comparator;

import seedu.address.model.tutee.Tutee;

/**
 * Provides utilities for sorting a list of Persons.
 */
public class PersonSortUtil {
    public static final String CATEGORY_NAME = "name";
    public static final String CATEGORY_EDUCATION_LEVEL = "edu";
    public static final String CATEGORY_GRADE = "grade";
    public static final String CATEGORY_SCHOOL = "school";
    public static final String CATEGORY_SUBJECT = "subject";
    public static final int NEGATIVE_DIGIT = -1;
    public static final int POSITIVE_DIGIT = 1;

    /**
     * Returns the apppropriate Person comparator given the sorting category
     */
    public Comparator<Person> getComparator(String sortCategory) {
        Comparator<Person> comparator = null;

        switch (sortCategory) {
        case CATEGORY_NAME:
            comparator = getNameComparator();
            break;
        case CATEGORY_EDUCATION_LEVEL:
            comparator = getEducationLevelComparator();
            break;
        case CATEGORY_GRADE:
            comparator = getGradeComparator();
            break;
        case CATEGORY_SCHOOL:
            comparator = getSchoolComparator();
            break;
        case CATEGORY_SUBJECT:
            comparator = getSubjectComparator();
            break;
        default:
            assert (false); //invalid sortCategory should be identified in parser.
        }
        return comparator;
    }

    private Comparator<Person> getEducationLevelComparator() {
        return new Comparator<Person>() {
            @Override
            public int compare(Person person1, Person person2) {
                int result = 0; //value will be replaced
                if (areBothTutees(person1, person2)) {

                    String personEducationLevel1 = ((Tutee) person1).getEducationLevel().toString();
                    String personEducationLevel2 = ((Tutee) person2).getEducationLevel().toString();

                    result = personEducationLevel1.compareToIgnoreCase(personEducationLevel2);
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

    private Comparator<Person> getGradeComparator() {
        return new Comparator<Person>() {
            @Override
            public int compare(Person person1, Person person2) {
                int result = 0; //value will be replaced
                if (areBothTutees(person1, person2)) {

                    String personGrade1 = ((Tutee) person1).getGrade().toString();
                    String personGrade2 = ((Tutee) person2).getGrade().toString();

                    result = personGrade1.compareToIgnoreCase(personGrade2);
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

    private Comparator<Person> getSchoolComparator() {
        return new Comparator<Person>() {
            @Override
            public int compare(Person person1, Person person2) {
                int result = 0; //value will be replaced
                if (areBothTutees(person1, person2)) {

                    String personSchool1 = ((Tutee) person1).getSchool().toString();
                    String personSchool2 = ((Tutee) person2).getSchool().toString();

                    result = personSchool1.compareToIgnoreCase(personSchool2);
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

    /**
     * Returns true if both the given {@code Person} are subclass of {@code Tutee}
     */
    private boolean areNotTutees(Person person1, Person person2) {
        return !(person1 instanceof Tutee || person2 instanceof Tutee);
    }

    /**
     * Returns true if the given {@code person1} is the only subclass of {@code Tutee}
     */
    private boolean isSecondTutee(Person person1, Person person2) {
        return !(person1 instanceof Tutee) && person2 instanceof Tutee;
    }

    /**
     * Returns true if the given {@code person2} is the only subclass of {@code Tutee}
     */
    private boolean isFirstTutee(Person person1, Person person2) {
        return person1 instanceof Tutee && !(person2 instanceof Tutee);
    }

    /**
     * Returns true if both the given {@code Person} are not subclass of {@code Tutee}
     */
    private boolean areBothTutees(Person person1, Person person2) {
        return person1 instanceof Tutee && person2 instanceof Tutee;
    }

    /**
     * Compares the name of 2 given person and returns an integer according to their lexicographical relationn
     * Behaves the same as {@code compareTo} in Java.lang.String
     *
     * @param person1 first person to be compared
     * @param person2 second person to be compared
     */
    private int compareNameLexicographically(Person person1, Person person2) {
        String personName1 = person1.getName().toString();
        String personName2 = person2.getName().toString();

        return personName1.compareToIgnoreCase(personName2);
    }
}
