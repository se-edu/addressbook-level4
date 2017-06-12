package guitests.guihandles;

import javafx.stage.Window;

/**
 * Provides a handle for the main GUI.
 */
public class MainWindowHandle extends WindowHandle {

    private MainMenuHandle mainMenu;
    private PersonListPanelHandle personListPanel;
    private ResultDisplayHandle resultDisplay;
    private CommandBoxHandle commandBox;
    private BrowserPanelHandle browserPanel;
    private StatusBarFooterHandle statusBarFooter;

    public MainWindowHandle(Window window) {
        super(window);

        mainMenu = new MainMenuHandle(this);
        personListPanel = new PersonListPanelHandle(this);
        resultDisplay = new ResultDisplayHandle(this);
        commandBox = new CommandBoxHandle(this);
        browserPanel = new BrowserPanelHandle(this);
        statusBarFooter = new StatusBarFooterHandle(this);
    }

    public MainMenuHandle getMainMenu() {
        return mainMenu;
    }

    public PersonListPanelHandle getPersonListPanel() {
        return personListPanel;
    }

    public ResultDisplayHandle getResultDisplay() {
        return resultDisplay;
    }

    public CommandBoxHandle getCommandBox() {
        return commandBox;
    }

    public BrowserPanelHandle getBrowserPanel() {
        return browserPanel;
    }

    public StatusBarFooterHandle getStatusBarFooter() {
        return statusBarFooter;
    }
}
