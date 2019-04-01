package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.habit.Habit;
import seedu.address.model.habit.HabitTitle;
import seedu.address.model.habit.Progress;
import seedu.address.model.tag.Tag;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Jackson-friendly version of {@link seedu.address.model.habit.Habit}
 */
public class JsonAdaptedHabit {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Habit's %s field is missing!";

    private final String name;
    private final String progress;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedHabit} with the given habit details.
     */
    @JsonCreator
    public JsonAdaptedHabit(@JsonProperty("name") String name, @JsonProperty("progress") String progress,
                               @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.name = name;
        this.progress = progress;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Habit} into this class for Jackson use.
     */
    public JsonAdaptedHabit(Habit source) {
        name = source.getHabitTitle().fullName;
        progress = source.getProgress().value;
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted habit object into the model's {@code Habit} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted purchase.
     */
    public Habit toModelType() throws IllegalValueException {
        final List<Tag> habitTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            habitTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, HabitTitle.class.getSimpleName()));
        }
        if (!HabitTitle.isValidName(name)) {
            throw new IllegalValueException(HabitTitle.MESSAGE_CONSTRAINTS);
        }
        final HabitTitle modelName = new HabitTitle(name);

        if (progress == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Progress.class.getSimpleName()));
        }
        if (!Progress.isValidProgress(progress)) {
            throw new IllegalValueException(Progress.MESSAGE_CONSTRAINTS);
        }
        final Progress modelProgress = new Progress(progress);




        final Set<Tag> modelTags = new HashSet<>(habitTags);
        return new Habit(modelName, modelProgress, modelTags);
    }
}
