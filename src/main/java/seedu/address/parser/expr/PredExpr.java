package seedu.address.parser.expr;


import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.parser.qualifier.Qualifier;
import seedu.address.parser.qualifier.TrueQualifier;

public class PredExpr implements Expr {
    public static final PredExpr TRUE = new PredExpr(new TrueQualifier());

    private final Qualifier qualifier;

    public PredExpr(Qualifier qualifier) {
        this.qualifier = qualifier;
    }

    @Override
    public boolean satisfies(ReadOnlyPerson person) {
        return qualifier.run(person);
    }

    @Override
    public String toString() {
        return qualifier.toString();
    }
}
