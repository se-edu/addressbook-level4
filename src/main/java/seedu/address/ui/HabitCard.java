package seedu.address.ui;


import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.habit.Habit;
import seedu.address.model.purchase.Purchase;

/**
 * An UI component that displays information of a {@code Habit}.
 */
public class HabitCard extends UiPart<Region> {

    private static final String FXML = "HabitListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Habit habit;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label price;
    @FXML

    private FlowPane tags;

    public HabitCard(Habit habit, int displayedIndex) {
        super(FXML);
        this.habit = habit;
        id.setText(displayedIndex + ". ");
        name.setText(habit.getHabitTitle().fullName);
        price.setText(habit.getProgress().value);
        habit.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof HabitCard)) {
            return false;
        }

        // state check
        HabitCard card = (HabitCard) other;
        return id.getText().equals(card.id.getText())
                && habit.equals(card.habit);
    }
}