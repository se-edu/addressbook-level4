package guitests.guihandles;

import guitests.GuiRobot;
import javafx.stage.Window;
import seedu.address.TestApp;

/**
 * Provides a handle for {@code MainWindow}.
 */
public class MainWindowHandle extends WindowHandle {

    private final PersonListPanelHandle personListPanel;
    private final ResultDisplayHandle resultDisplay;
    private final CommandBoxHandle commandBox;
    private final StatusBarFooterHandle statusBarFooter;
    private final MainMenuHandle mainMenu;
    private final BrowserPanelHandle browserPanel;

    public MainWindowHandle(Window window) {
        super(window);

        personListPanel = new PersonListPanelHandle(new GuiRobot(), null);
        resultDisplay = new ResultDisplayHandle(new GuiRobot(), null);
        commandBox = new CommandBoxHandle(new GuiRobot(), null, TestApp.APP_TITLE);
        statusBarFooter = new StatusBarFooterHandle(new GuiRobot(), null);
        mainMenu = new MainMenuHandle(new GuiRobot(), null);
        browserPanel = new BrowserPanelHandle(this);
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

    public StatusBarFooterHandle getStatusBarFooter() {
        return statusBarFooter;
    }

    public MainMenuHandle getMainMenu() {
        return mainMenu;
    }

    public BrowserPanelHandle getBrowserPanel() {
        return browserPanel;
    }
}
