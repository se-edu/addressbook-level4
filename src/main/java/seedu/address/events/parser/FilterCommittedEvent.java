package seedu.address.events.parser;

import seedu.address.events.BaseEvent;
import seedu.address.parser.expr.Expr;

/**
 * Indicates that a filter was committed by the user.
 */
public class FilterCommittedEvent extends BaseEvent {

    public final Expr filterExpression;

    public FilterCommittedEvent(Expr filterExpression) {
        this.filterExpression = filterExpression;
    }

    @Override
    public String toString() {
        return filterExpression.toString();
    }
}
