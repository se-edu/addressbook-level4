package seedu.address.model.person;

import java.util.Comparator;

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

    public Comparator<Person> getComparator(String sortCategory) {
        Comparator<Person> comparator = null;

        switch (sortCategory) {
        case CATEGORY_NAME:
                comparator = getNameComparator();

        }
        return comparator;
    }

    private Comparator<Person> getNameComparator() {
        return new Comparator<Person>() {
            @Override
            public int compare(Person person1, Person person2) {
                String personName1 = person1.getName().toString();
                String personName2 = person2.getName().toString();

                return personName1.compareToIgnoreCase(personName2);
            }
        };
    }
}
