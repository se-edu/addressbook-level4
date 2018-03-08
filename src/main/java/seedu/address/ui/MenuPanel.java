package seedu.address.ui;

import java.util.logging.Logger;

import javax.swing.plaf.synth.Region;

import com.google.common.eventbus.Subscribe;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ExitAppRequestEvent;
import seedu.address.commons.events.ui.ShowHelpRequestEvent;


/**
 * Panel containing menu buttons.
 */
public class MenuPanel extends UiPart<Region> {

    private static final String FXML = "MenuPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private VBox menuVbox;

    @FXML
    private JFXButton helpMenuItem;

    @FXML
    private JFXButton closeMenuButton;

    public MenuPanel(JFXDrawer drawer) {
        super(FXML);
        // setAccelerators();
        setcloseMenuButton(drawer);
        registerAsAnEventHandler(this);
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(JFXButton menuItem, KeyCombination keyCombination) {
        menuItem.getScene().getAccelerators().put(keyCombination, () -> helpMenuItem.fire());
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        raise(new ExitAppRequestEvent());
    }

    @Subscribe
    private void handleShowHelpEvent(ShowHelpRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleHelp();
    }

    /**
     * Opens the help window.
     */
    @FXML
    public void handleHelp() {
        HelpWindow helpWindow = new HelpWindow();
        helpWindow.show();
    }

    /**
     * close the menu panel.
     */
    public void setcloseMenuButton(JFXDrawer drawer) {
        closeMenuButton.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                drawer.close();
            }
        });

    }

    VBox returnMenuVBox() {
        return menuVbox;
    }
}
