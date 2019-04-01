package seedu.address.storage;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import seedu.address.commons.exceptions.IllegalValueException;

import seedu.address.model.HabitTrackerList;
import seedu.address.model.ReadOnlyHabitTrackerList;
import seedu.address.model.habit.Habit;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An Immutable HabitTracker that is serializable to JSON format.
 */
@JsonRootName(value = "habittrackerlist")
public class JsonSerializableHabitTrackerList {
    private final List<JsonAdaptedHabit> habits = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableHabitTrackerList} with the given habits.
     */
    @JsonCreator
    public JsonSerializableHabitTrackerList(@JsonProperty("habits") List<JsonAdaptedHabit> habits) {
        this.habits.addAll(habits);
    }

    /**
     * Converts a given {@code ReadOnlyHabitTrackerList} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableHabitTrackerList}.
     */
    public JsonSerializableHabitTrackerList(ReadOnlyHabitTrackerList source) {
        habits.addAll(source.getHabitList().stream().map(JsonAdaptedHabit::new).collect(Collectors.toList()));
    }

    /**
     * Converts this habit tracker list into the model's {@code HabitTrackerList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public HabitTrackerList toModelType() throws IllegalValueException {
        HabitTrackerList habitTrackerList = new HabitTrackerList();
        for (JsonAdaptedHabit jsonAdaptedHabit : habits) {
            Habit habit= jsonAdaptedHabit.toModelType();
            habitTrackerList.addHabit(habit);
        }
        return habitTrackerList;
    }
}

