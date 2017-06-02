package guitests.guihandles;

import javafx.stage.Stage;
import seedu.address.TestApp;

/**
 * Provides a handle for the main GUI.
 */
public class MainGuiHandle extends GuiHandle {

    public MainGuiHandle(Stage primaryStage) {
        super(primaryStage, TestApp.APP_TITLE);
    }

    public PersonListPanelHandle getPersonListPanel() {
        return new PersonListPanelHandle(primaryStage);
    }

    public ResultDisplayHandle getResultDisplay() {
        return new ResultDisplayHandle(primaryStage);
    }

    public CommandBoxHandle getCommandBox() {
        return new CommandBoxHandle(primaryStage, TestApp.APP_TITLE);
    }

    public StatusBarFooterHandle getStatusBarFooter() {
        return new StatusBarFooterHandle(primaryStage);
    }

    public MainMenuHandle getMainMenu() {
        return new MainMenuHandle(primaryStage);
    }

    public BrowserPanelHandle getBrowserPanel() {
        return new BrowserPanelHandle(primaryStage);
    }

    public AlertDialogHandle getAlertDialog(String title) {
        return new AlertDialogHandle(primaryStage, title);
    }
}
