package seedu.address.model.person;

import java.util.function.Predicate;

/**
* For hiding persons during lock
*/

public class HideAllPersonPredicate implements Predicate<Person> {

    public HideAllPersonPredicate() {}

    @Override
    public boolean test(Person person) {
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return false;
    }

}
