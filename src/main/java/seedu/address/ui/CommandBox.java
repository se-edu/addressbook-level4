package seedu.address.ui;

import java.util.List;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.logic.ListElementPointer;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private final Logic logic;
    private ListElementPointer<String> pointer;

    @FXML
    private TextField commandTextField;

    public CommandBox(Logic logic) {
        super(FXML);
        this.logic = logic;
        pointer = new ListElementPointer<>(logic.getUserInputHistory());
    }

    @FXML
    private void handleKeyPress(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
        case UP:
            // As up and down buttons will alter the position of the caret,
            // consuming it causes the caret's position to remain unchanged
            keyEvent.consume();

            navigateToPreviousInput();
            break;
        case DOWN:
            keyEvent.consume();
            navigateToNextInput();
            break;
        default:
            // let JavaFx handle the keypress
        }
    }

    /**
     * Retrieves the previous input from the current cursor in {@code pointer}
     * and updates the text field with it. Moves the cursor in {@code pointer} backward by 1.
     */
    private void navigateToPreviousInput() {
        assert pointer != null;
        if (!pointer.hasPrevious()) {
            return;
        }

        setText(pointer.previous());
    }

    /**
     * Retrieves the next input from the current cursor in {@code pointer}
     * and updates the text field with it. Moves the cursor in {@code pointer} forward by 1.
     */
    private void navigateToNextInput() {
        assert pointer != null;
        setText(!pointer.hasNext() ? "" : pointer.next());
    }

    /**
     * Sets {@code CommandBox}'s text field with {@code text} and
     * positions the caret to the end of the {@code text}.
     */
    private void setText(String text) {
        commandTextField.setText(text);
        commandTextField.positionCaret(commandTextField.getText().length());
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
            List<String> userInputHistory = logic.getUserInputHistory();
            // causes history to return an empty string upon pressing the down button after the last user input
            // has been returned
            userInputHistory.add("");

            pointer = new ListElementPointer<>(userInputHistory, userInputHistory.size() - 1);
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
