package seedu.address.model.person;

import seedu.address.commons.util.StringUtil;

import java.util.List;
import java.util.function.Predicate;

import javax.swing.*;

public class HideAllPersonPredicate implements Predicate<Person>{

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
