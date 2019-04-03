package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.WorkoutBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyWorkoutBook;
import seedu.address.model.person.Person;
import seedu.address.model.workout.Workout;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "workoutbook")
class JsonSerializableWorkoutBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";

    private final List<JsonAdaptedWorkout> workouts = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableWorkoutBook(@JsonProperty("workouts") List<JsonAdaptedWorkout> workouts) {
        this.workouts.addAll(workouts);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableWorkoutBook(ReadOnlyWorkoutBook source) {
        workouts.addAll(source.getWorkoutList().stream().map(JsonAdaptedWorkout::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public WorkoutBook toModelType() throws IllegalValueException {

        WorkoutBook workoutBook = new WorkoutBook();
        for (JsonAdaptedWorkout jsonAdaptedWorkout : workouts) {
            Workout workout = jsonAdaptedWorkout.toModelType();
            // if (workoutBook.hasWorkout(workout)) {
            //   throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            //}
            workoutBook.addWorkout(workout);
        }
        return workoutBook;
    }

}
