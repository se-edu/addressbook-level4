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
    private static Logger logger = LoggerManager.getLogger(CommandBox.class);
    public static final String FXML = "CommandBox.fxml";

    private AnchorPane placeHolderPane;
    private AnchorPane commandPane;
    private StatusBarHeader statusBarHeader;
    private ModelManager modelManager;
    private Parser parser;

    @FXML
    TextField commandInput;

    public CommandBox() {
        super();
    }

    public static CommandBox load(Stage primaryStage, AnchorPane commandBoxPlaceholder, Parser parser,
                                  StatusBarHeader statusBarHeader, ModelManager modelManager) {
        logger.fine("Loading command box.");
        CommandBox commandBox = UiPartLoader.loadUiPart(primaryStage, commandBoxPlaceholder, new CommandBox());
        commandBox.configure(parser, statusBarHeader, modelManager);
        return commandBox;
    }

    private void configure(Parser parser, StatusBarHeader statusBarHeader, ModelManager modelManager) {
        this.parser = parser;
        this.statusBarHeader = statusBarHeader;
        this.modelManager = modelManager;
        addToPlaceholder();
    }

    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(commandInput);
        FxViewUtil.applyAnchorBoundaryParameters(commandPane, 0.0, 0.0, 0.0, 0.0);
        FxViewUtil.applyAnchorBoundaryParameters(commandInput, 0.0, 0.0, 0.0, 0.0);
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

    @FXML
    private void handleCommandInputChanged() {
        if (commandInput.getStyleClass().contains("error")) commandInput.getStyleClass().remove("error");

        Command command = parser.parseCommand(commandInput.getText());
        command.setData(modelManager);
        CommandResult result = command.execute();

        if (command instanceof IncorrectCommand) {
            commandInput.getStyleClass().add("error");
        } else {
            commandInput.getStyleClass().add("");
        }

        statusBarHeader.postMessage(result.feedbackToUser);

        logger.info("Result: " + command.getClass().getSimpleName());
        logger.info("Result: " + result.feedbackToUser);
        logger.fine("Invalid command: " + commandInput.getText());
    }
}
