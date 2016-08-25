package seedu.address.parser.expr;


import seedu.address.model.person.ReadOnlyPerson;

public class NotExpr implements Expr {
    Expr expr;

    public NotExpr(Expr expr) {
        this.expr = expr;
    }

    @Override
    public boolean satisfies(ReadOnlyPerson person) {
        return !expr.satisfies(person);
    }

    @Override
    public String toString() {
        return "NOT(" + expr + ")";
    }
}
