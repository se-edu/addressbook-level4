package seedu.address.model.capGoal;

/**
 * Represents Cap Goal
 *
 * Immutable. Value can be null.
 */
public class CapGoal {

    private double capGoal;
    private boolean isSet = true;
    private final String MESSAGE_IS_NULL = "NIL";

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
