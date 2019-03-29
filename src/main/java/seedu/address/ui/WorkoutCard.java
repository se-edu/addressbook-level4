package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.workout.Workout;

import java.util.Comparator;
import java.util.logging.Logger;

public class WorkoutCard extends UiPart<Region> {
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);
    private static final String FXML = "WorkoutListCard.fxml";

    public final Workout workout;

    @FXML
    private HBox cardPane2;
    @FXML
    private Label exercise;
    @FXML
    private Label sets;
    @FXML
    private Label reps;
    @FXML
    private Label time;
    @FXML
    private Label id;

    public WorkoutCard(Workout workout, int displayedIndex) {
        super(FXML);
        this.workout = workout;
        id.setText(displayedIndex + ". ");
//        logger.info("TASK NAME IS " + task.getDeadlineDate());
        exercise.setText(workout.getExercise().toString());
        sets.setText(workout.getSets().toString());
        reps.setText(workout.getReps().toString());
        time.setText(workout.getTime().toString());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof WorkoutCard)) {
            return false;
        }

        // state check
        WorkoutCard card = (WorkoutCard) other;
        return id.getText().equals(card.id.getText())
                && workout.equals(card.workout);
    }


}
