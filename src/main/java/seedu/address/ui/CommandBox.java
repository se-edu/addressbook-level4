package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.logic.CommandHistoryListIterator;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private final Logic logic;
    private CommandHistoryListIterator historyIterator;

    @FXML
    private TextField commandTextField;

    public CommandBox(Pane commandBoxPlaceholder, Logic logic) {
        super(FXML);
        this.logic = logic;
        historyIterator = logic.getHistoryIterator();
        addToPlaceholder(commandBoxPlaceholder);
        commandTextField.setOnKeyPressed(this::updateText);
    }

    /**
     * Shows the previous input if {@code KeyCode.UP} is pressed,
     * and shows the next input if {@code KeyCode.DOWN} is pressed.
     */
    private void updateText(KeyEvent keyEvent) {
        assert historyIterator != null;

        if (keyEvent.getCode() == KeyCode.UP) {
            keyEvent.consume();
            if (!historyIterator.hasPrevious()) {
                return;
            }

            updateText(historyIterator.previous());
        } else if (keyEvent.getCode() == KeyCode.DOWN) {
            keyEvent.consume();
            if (!historyIterator.hasNext()) {
                commandTextField.setText("");
                return;
            }

            updateText(historyIterator.next());
        }
    }

    /**
     * Sets {@code CommandBox}'s text field with {@code text} and
     * positions the caret to the end of the {@code text}.
     */
    private void updateText(String text) {
        commandTextField.setText(text);
        commandTextField.positionCaret(commandTextField.getText().length());
    }

    private void addToPlaceholder(Pane placeHolderPane) {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(commandTextField);
    }

    @FXML
    private void handleCommandInputChanged() {
        try {
            CommandResult commandResult = logic.execute(commandTextField.getText());

            // process result of the command
            setStyleToIndicateCommandSuccess();
            commandTextField.setText("");
            logger.info("Result: " + commandResult.feedbackToUser);
            raise(new NewResultAvailableEvent(commandResult.feedbackToUser));

        } catch (CommandException | ParseException e) {
            // handle command failure
            setStyleToIndicateCommandFailure();
            logger.info("Invalid command: " + commandTextField.getText());
            raise(new NewResultAvailableEvent(e.getMessage()));
        } finally {
            historyIterator = logic.getHistoryIterator();
        }
    }


    /**
     * Sets the command box style to indicate a successful command.
     */
    private void setStyleToIndicateCommandSuccess() {
        commandTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = commandTextField.getStyleClass();

        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
    }

}
