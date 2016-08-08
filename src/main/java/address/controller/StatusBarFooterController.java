package address.controller;

import commons.FxViewUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.StatusBar;

/**
 * A controller for the status bar that is displayed at the footer of the application.
 */
public class StatusBarFooterController extends UiController {

    private static StatusBar syncStatusBar;
    private static StatusBar saveLocStatusBar;

    @FXML
    private AnchorPane saveLocStatusBarPane;
    @FXML
    private AnchorPane syncStatusBarPane;

    public StatusBarFooterController() {
        super();
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
