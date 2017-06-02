package guitests.guihandles;

import seedu.address.TestApp;

/**
 * Provides a handle for the main GUI.
 */
public class MainGuiHandle extends GuiHandle {

    public MainGuiHandle() {
        super(TestApp.APP_TITLE);
    }

    public PersonListPanelHandle getPersonListPanel() {
        return new PersonListPanelHandle();
    }

    public ResultDisplayHandle getResultDisplay() {
        return new ResultDisplayHandle();
    }

    public CommandBoxHandle getCommandBox() {
        return new CommandBoxHandle(TestApp.APP_TITLE);
    }

    public StatusBarFooterHandle getStatusBarFooter() {
        return new StatusBarFooterHandle();
    }

    public MainMenuHandle getMainMenu() {
        return new MainMenuHandle();
    }

    public BrowserPanelHandle getBrowserPanel() {
        return new BrowserPanelHandle();
    }

    public AlertDialogHandle getAlertDialog(String title) {
        return new AlertDialogHandle(title);
    }
}
