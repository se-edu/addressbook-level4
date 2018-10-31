package seedu.address.model.capgoal;

//@@author jeremiah-ang
/**
 * Represents Cap Goal
 *
 * Immutable. Value can be null.
 */
public class CapGoal {

    private final double value;
    private final boolean isSet;
    private final boolean isImpossible;

    public CapGoal() {
        value = 0;
        isSet = false;
        isImpossible = false;
    }

    public CapGoal(double value) {
        this(value, false);
    }

    public CapGoal(double value, boolean isImpossible) {
        isSet = (value > 0);
        this.value = value;
        this.isImpossible = isImpossible;
    }

    public double getValue() {
        return value;
    }

    public boolean isSet() {
        return isSet;
    }

    public boolean isImpossible() {
        return isImpossible;
    }

    public CapGoal makeIsImpossible() {
        return new CapGoal(value, true);
    }

    @Override
    public String toString() {
        return "" + getValue();
    }
}
