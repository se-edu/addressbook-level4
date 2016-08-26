package seedu.address.parser.expr;

import seedu.address.model.person.ReadOnlyPerson;

public interface Expr {
    boolean satisfies(ReadOnlyPerson person);
    String toString();
}
