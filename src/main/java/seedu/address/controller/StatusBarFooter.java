package seedu.address.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.controlsfx.control.StatusBar;
import seedu.address.commons.FxViewUtil;

/**
 * A controller for the status bar that is displayed at the footer of the application.
 */
public class StatusBarFooter extends BaseUiPart {
    private static StatusBar syncStatusBar;
    private static StatusBar saveLocStatusBar;

    private GridPane mainPane;

    @FXML
    private AnchorPane saveLocStatusBarPane;
    @FXML
    private AnchorPane syncStatusBarPane;

    private AnchorPane placeHolder;

    private static final String FXML = "StatusBarFooter.fxml";

    public static StatusBarFooter load(Stage stage, AnchorPane placeHolder, String addressBookName) {
        StatusBarFooter statusBarFooter = UiPartLoader.loadUiPart(stage, placeHolder, new StatusBarFooter());
        statusBarFooter.configure(addressBookName);
        return statusBarFooter;
    }

    public void configure(String addressBookName) {
        this.init(addressBookName);
        FxViewUtil.applyAnchorBoundaryParameters(mainPane, 0.0, 0.0, 0.0, 0.0);
        placeHolder.getChildren().add(mainPane);
    }

    @Override
    public void setNode(Node node) {
        mainPane = (GridPane) node;
    }

    @Override
    public void setPlaceholder(AnchorPane placeholder) {
        this.placeHolder = placeholder;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    /**
     * Initializes the status bar
     * @param addressBookName name of the active address book
     */
    public void init(String addressBookName) {
        initStatusBar();
        initAddressBookLabel(addressBookName);
    }

    private void initAddressBookLabel(String addressBookName) {
        updateSaveLocationDisplay(addressBookName);
        setTooltip(saveLocStatusBar);
    }

    private void initStatusBar() {
        this.syncStatusBar = new StatusBar();
        this.saveLocStatusBar = new StatusBar();
        this.syncStatusBar.setText("");
        this.saveLocStatusBar.setText("");
        FxViewUtil.applyAnchorBoundaryParameters(syncStatusBar, 0.0, 0.0, 0.0, 0.0);
        FxViewUtil.applyAnchorBoundaryParameters(saveLocStatusBar, 0.0, 0.0, 0.0, 0.0);
        syncStatusBarPane.getChildren().add(syncStatusBar);
        saveLocStatusBarPane.getChildren().add(saveLocStatusBar);
    }

    private void setTooltip(StatusBar statusBar) {
        Tooltip tp = new Tooltip();
        tp.textProperty().bind(statusBar.textProperty());
        statusBar.setTooltip(tp);
    }

    private void updateSaveLocationDisplay(String addressBookName) {
        saveLocStatusBar.setText(addressBookName);
    }
}
