package seedu.address.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import seedu.address.commands.Command;
import seedu.address.commands.CommandResult;
import seedu.address.commands.IncorrectCommand;
import seedu.address.commons.FxViewUtil;
import seedu.address.model.ModelManager;
import seedu.address.parser.Parser;
import seedu.address.util.LoggerManager;

import java.util.logging.Logger;

public class CommandBox extends BaseUiPart {
    private final Logger logger = LoggerManager.getLogger(CommandBox.class);
    private static final String FXML = "CommandBox.fxml";

    private AnchorPane placeHolderPane;
    private AnchorPane commandPane;
    private ResultDisplay resultDisplay;
    private ModelManager modelManager;
    private Parser parser;

    @FXML
    private TextField commandTextField;

    public static CommandBox load(Stage primaryStage, AnchorPane commandBoxPlaceholder, Parser parser,
                                  ResultDisplay resultDisplay, ModelManager modelManager) {
        CommandBox commandBox = UiPartLoader.loadUiPart(primaryStage, commandBoxPlaceholder, new CommandBox());
        commandBox.configure(parser, resultDisplay, modelManager);
        commandBox.addToPlaceholder();
        return commandBox;
    }

    public void configure(Parser parser, ResultDisplay resultDisplay, ModelManager modelManager) {
        this.parser = parser;
        this.resultDisplay = resultDisplay;
        this.modelManager = modelManager;
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
        Command command = parser.parseCommand(commandText);
        command.setData(modelManager);
        CommandResult result = command.execute();

        if (command instanceof IncorrectCommand) {
            commandTextField.getStyleClass().add("error");
        } else {
            commandTextField.setText("");
        }

        resultDisplay.postMessage(result.feedbackToUser);

        logger.info("Result: " + result.feedbackToUser);
        logger.fine("Invalid command: " + commandTextField.getText());
    }

    @FXML
    private void handleCommandInputChanged() {
        if (commandTextField.getStyleClass().contains("error")) commandTextField.getStyleClass().remove("error");
        processCommandInput(commandTextField.getText());
    }
}
