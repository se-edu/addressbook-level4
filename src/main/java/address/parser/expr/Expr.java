package address.parser.expr;

import address.model.datatypes.person.ReadOnlyPerson;

public interface Expr {
    boolean satisfies(ReadOnlyPerson person);
    String toString();
}
