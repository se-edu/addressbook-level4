package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.workout.Exercise;
import seedu.address.model.workout.Reps;
import seedu.address.model.workout.Sets;
import seedu.address.model.workout.Time;
import seedu.address.model.workout.Workout;


class JsonAdaptedWorkout {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Workout's %s field is missing!";

    private final String exercise;
    private final String sets;
    private final String reps;
    private final String time;


    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedWorkout(@JsonProperty("exercise") String exercise, @JsonProperty("sets") String sets,
                             @JsonProperty("reps") String reps, @JsonProperty("time") String time) {
        this.exercise = exercise;
        this.sets = sets;
        this.reps = reps;
        this.time = time;
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedWorkout(Workout source) {
        exercise = source.getExercise().exerciseName;
        sets = source.getSets().value;
        reps = source.getReps().value;
        time = source.getTime().value;
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Workout toModelType() throws IllegalValueException {

        if (exercise == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Exercise.isValidExercise(exercise)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Exercise modelExercise = new Exercise(exercise);

        if (sets == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Sets.isValidSets(sets)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Sets modelSets = new Sets(sets);

        if (reps == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Reps.isValidReps(reps)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Reps modelReps = new Reps(reps);

        if (time == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Time.isValidTime(time)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Time modelTime = new Time(time);

        return new Workout(modelExercise, modelSets, modelReps, modelTime);
    }

}
