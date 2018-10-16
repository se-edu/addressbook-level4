package seedu.address.model.capgoal;

//@@author jeremiah-ang
/**
 * Represents Cap Goal
 *
 * Immutable. Value can be null.
 */
public class CapGoal {

    private static final String MESSAGE_IS_NULL = "NIL";

    private double capGoal;
    private boolean isSet = true;

    public CapGoal() {

    }

    public CapGoal(double capGoal) {
        isSet = false;
        this.capGoal = capGoal;
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

    @Override
    public String toString() {
        if (isSet) {
            return MESSAGE_IS_NULL;
        }
        return "" + getCapGoal();
    }
}
