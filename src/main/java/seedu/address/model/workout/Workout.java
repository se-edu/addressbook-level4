package seedu.address.model.workout;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Workout Class
 */
public class Workout {

    // Identity fields
    private final Exercise exercise;
    private final Sets sets;
    private final Reps reps;
    private final Time time;


    /**
     * Every field must be present and not null.
     */
    public Workout (Exercise exercise, Sets sets, Reps reps, Time time) {
        requireAllNonNull(exercise, sets, reps, time);
        this.exercise = exercise;
        this.sets = sets;
        this.reps = reps;
        this.time = time;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public Sets getSets() {
        return sets;
    }

    public Reps getReps() {
        return reps;
    }

    public Time getTime() {
        return time;
    }


    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Exercise: ")
                .append(getExercise())
                .append("Sets: ")
                .append(getSets())
                .append("Reps: ")
                .append(getReps())
                .append("Duration: ")
                .append(getTime());
        return builder.toString();
    }

}
