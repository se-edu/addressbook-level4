package seedu.address.parser.expr;


import seedu.address.model.person.ReadOnlyPerson;

public class AndExpr implements Expr {

    private final Expr left;
    private final Expr right;

    public AndExpr(Expr left, Expr right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean satisfies(ReadOnlyPerson person) {
        return left.satisfies(person) && right.satisfies(person);
    }

    @Override
    public String toString() {
        return "(" + left + ") AND (" + right + ")";
    }
}
