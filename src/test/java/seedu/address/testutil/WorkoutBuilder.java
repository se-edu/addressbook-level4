package seedu.address.testutil;

import seedu.address.model.workout.Exercise;
import seedu.address.model.workout.Reps;
import seedu.address.model.workout.Sets;
import seedu.address.model.workout.Time;
import seedu.address.model.workout.Workout;

/**
 * A utility class to help with building workout objects.
 */

public class WorkoutBuilder {

    public static final String DEFAULT_EXERCISE = "Push ups";
    public static final String DEFAULT_SETS = "5";
    public static final String DEFAULT_REPS = "20";
    public static final String DEFAULT_TIME = "10";

    private Exercise exercise;
    private Sets sets;
    private Reps reps;
    private Time time;

    public WorkoutBuilder() {
        exercise = new Exercise(DEFAULT_EXERCISE);
        sets = new Sets(DEFAULT_SETS);
        reps = new Reps(DEFAULT_REPS);
        time = new Time(DEFAULT_TIME);
    }

    /**
     * Initializes the WorkoutBuilder with the data of {@code workoutToCopy}.
     */
    public WorkoutBuilder(Workout workoutToCopy) {
        exercise = workoutToCopy.getExercise();
        sets = workoutToCopy.getSets();
        reps = workoutToCopy.getReps();
        time = workoutToCopy.getTime();
    }

    /**
     * Sets the {@code WorkoutName} of the {@code workout} that we are building
     */
    public WorkoutBuilder withExercise(String exercise) {
        this.exercise = new Exercise(exercise);
        return this;
    }


    /**
     * Sets the {@code WorkoutSets} of the {@code workout} that we are building
     */
    public WorkoutBuilder withSets(String sets) {
        this.sets = new Sets(sets);
        return this;
    }

    /**
     * Sets the {@code WorkoutReps} of the {@code workout} that we are building
     */
    public WorkoutBuilder withReps(String reps) {
        this.reps = new Reps(reps);
        return this;
    }


    /**
     * Sets the {@code WorkoutTime} of the {@code workout} that we are building
     */
    public WorkoutBuilder withTime(String time) {
        this.time = new Time(time);
        return this;
    }

    public Workout build() {
        return new Workout(exercise, sets, reps, time);
    }
}
