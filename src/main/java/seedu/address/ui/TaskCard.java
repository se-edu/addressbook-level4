package seedu.address.ui;

import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.Task;
import seedu.address.model.tutee.TuitionTask;

/**
 * An UI component that displays information of a {@code Task}.
 */
//@@author a-shakra
public class TaskCard extends UiPart<Region> {
    private static final String FXML = "TaskListCard.fxml";
    public final Task task;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu HH:mm")
            .withResolverStyle(ResolverStyle.STRICT);

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label taskDateAndTime;
    @FXML
    private Label duration;
    @FXML
    private Label description;

    public TaskCard(Task task, int displayedIndex) {
        super(FXML);
        this.task = task;
        id.setText(displayedIndex + ". ");
        if (task instanceof TuitionTask) {
            description.setText(((TuitionTask) task).getTuitionTitle());
        } else {
            description.setText(task.getDescription());
        }
        duration.setText(task.getDuration());
        taskDateAndTime.setText(task.getTaskDateTime().format(formatter));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TaskCard)) {
            return false;
        }

        // state check
        TaskCard card = (TaskCard) other;
        return id.getText().equals(card.id.getText())
                && task.equals(card.task);
    }

}

