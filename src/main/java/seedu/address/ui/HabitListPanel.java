package seedu.address.ui;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.habit.Habit;

/**
 * Panel containing the list of purchases.
 */
public class HabitListPanel extends UiPart<Region>{

    private static final String FXML = "HabitListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(HabitListPanel.class);

    @FXML
    private ListView<Habit> habitListView;

    public HabitListPanel(ObservableList<Habit> habitList, ObservableValue<Habit> selectedHabit,
                             Consumer<Habit> onSelectedHabitChange) {
        super(FXML);
        habitListView.setItems(habitList);
        habitListView.setCellFactory(listView -> new HabitListViewCell());
        habitListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            logger.fine("Selection in habit list panel changed to : '" + newValue + "'");
            onSelectedHabitChange.accept(newValue);
        });
        selectedHabit.addListener((observable, oldValue, newValue) -> {
            logger.fine("Selected habit changed to: " + newValue);

            // Don't modify selection if we are already selecting the selected habit,
            // otherwise we would have an infinite loop.
            if (Objects.equals(habitListView.getSelectionModel().getSelectedItem(), newValue)) {
                return;
            }

            if (newValue == null) {
                habitListView.getSelectionModel().clearSelection();
            } else {
                int index = habitListView.getItems().indexOf(newValue);
                habitListView.scrollTo(index);
                habitListView.getSelectionModel().clearAndSelect(index);
            }
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Habit} using a {@code HabitCard}.
     */
    class HabitListViewCell extends ListCell<Habit> {
        @Override
        protected void updateItem(Habit habit, boolean empty) {
            super.updateItem(habit, empty);

            if (empty || habit == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new HabitCard(habit, getIndex() + 1).getRoot());
            }
        }
    }
}
