package seedu.address.controller;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.controlsfx.control.StatusBar;
import seedu.address.commons.FxViewUtil;

/**
 * A controller for the status bar that is displayed at the header of the application.
 */
public class ResultDisplay extends BaseUiPart {
    public static final String HEADER_STATUS_BAR_ID = "resultDisplay";
    private static final String STATUS_BAR_STYLE_SHEET = "result-display";
    private StatusBar headerStatusBar;

    private static final String FXML = "ResultDisplay.fxml";

    private AnchorPane placeHolder;

    private AnchorPane mainPane;

    public static ResultDisplay load(Stage primaryStage, AnchorPane placeHolder) {
        ResultDisplay statusBar = UiPartLoader.loadUiPart(primaryStage, placeHolder, new ResultDisplay());
        statusBar.configure();
        return statusBar;
    }

    public void configure() {
        headerStatusBar = new StatusBar();
        headerStatusBar.setId(HEADER_STATUS_BAR_ID);
        headerStatusBar.getStyleClass().removeAll();
        headerStatusBar.getStyleClass().add(STATUS_BAR_STYLE_SHEET);
        headerStatusBar.setText("");
        FxViewUtil.applyAnchorBoundaryParameters(headerStatusBar, 0.0, 0.0, 0.0, 0.0);
        mainPane.getChildren().add(headerStatusBar);
        FxViewUtil.applyAnchorBoundaryParameters(mainPane, 0.0, 0.0, 0.0, 0.0);
        placeHolder.getChildren().add(mainPane);
    }

    public StatusBar getHeaderStatusBarView() {
        return headerStatusBar;
    }

    @Override
    public void setNode(Node node) {
        mainPane = (AnchorPane) node;
    }

    @Override
    public void setPlaceholder(AnchorPane placeholder) {
        this.placeHolder = placeholder;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    public void postMessage(String message) {
        headerStatusBar.setText(message);
    }
}
