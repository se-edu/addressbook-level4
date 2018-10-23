package seedu.address.model.capgoal;

//@@author jeremiah-ang
/**
 * Represents Cap Goal
 *
 * Immutable. Value can be null.
 */
public class CapGoal {

    private static final String MESSAGE_IS_NULL = "NIL";

    public final double capGoal;
    public final boolean isSet;
    public final boolean isImpossible;

    public CapGoal() {
        capGoal = 0;
        isSet = false;
        isImpossible = false;
    }

    public CapGoal(double capGoal) {
        this(capGoal, false);
    }

    public CapGoal(double capGoal, boolean isImpossible) {
        isSet = true;
        this.capGoal = capGoal;
        this.isImpossible = isImpossible;
    }

    /**
     * Returns the cap goal
     * @return
     */
    public double getCapGoal() {
        return capGoal;
    }

    public boolean isSet() {
        return isSet;
    }

    public CapGoal isImpossible() {
        return new CapGoal(capGoal, true);
    }


    @Override
    public String toString() {
        if (isSet) {
            return "" + getCapGoal();
        }
        return MESSAGE_IS_NULL;

    }
}
