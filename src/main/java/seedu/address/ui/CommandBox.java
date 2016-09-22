package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import seedu.address.logic.commands.*;
import seedu.address.commons.FxViewUtil;
import seedu.address.logic.Logic;
import seedu.address.model.ModelManager;
import seedu.address.commons.LoggerManager;

import java.util.logging.Logger;

public class CommandBox extends UiPart {
    private final Logger logger = LoggerManager.getLogger(CommandBox.class);
    private static final String FXML = "CommandBox.fxml";

    private AnchorPane placeHolderPane;
    private AnchorPane commandPane;
    private ResultDisplay resultDisplay;

    private Logic logic;

    @FXML
    private TextField commandTextField;
    private CommandResult mostRecentResult;

    public static CommandBox load(Stage primaryStage, AnchorPane commandBoxPlaceholder,
                                  ResultDisplay resultDisplay, ModelManager modelManager) {
        CommandBox commandBox = UiPartLoader.loadUiPart(primaryStage, commandBoxPlaceholder, new CommandBox());
        commandBox.configure(resultDisplay, modelManager);
        commandBox.addToPlaceholder();
        return commandBox;
    }

    public void configure(ResultDisplay resultDisplay, ModelManager modelManager) {
        this.resultDisplay = resultDisplay;
        this.logic = new Logic(modelManager);
    }

    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(commandTextField);
        FxViewUtil.applyAnchorBoundaryParameters(commandPane, 0.0, 0.0, 0.0, 0.0);
        FxViewUtil.applyAnchorBoundaryParameters(commandTextField, 0.0, 0.0, 0.0, 0.0);
    }

    @Override
    public void setNode(Node node) {
        commandPane = (AnchorPane) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    @Override
    public void setPlaceholder(AnchorPane pane) {
        this.placeHolderPane = pane;
    }

    public void processCommandInput(String commandText) {
        mostRecentResult = logic.execute(commandText);
        resultDisplay.postMessage(mostRecentResult.feedbackToUser);
        logger.info("Result: " + mostRecentResult.feedbackToUser);
    }

    @FXML
    private void handleCommandInputChanged() {
        if (commandTextField.getStyleClass().contains("error")) commandTextField.getStyleClass().remove("error");

        processCommandInput(commandTextField.getText());

        if (mostRecentResult instanceof IncorrectCommandResult) {
            commandTextField.getStyleClass().add("error");
            logger.fine("Invalid command: " + commandTextField.getText());
        } else {
            commandTextField.setText("");
        }
    }

}
