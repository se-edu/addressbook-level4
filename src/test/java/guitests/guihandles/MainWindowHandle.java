package guitests.guihandles;

import javafx.stage.Window;

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

        personListPanel = new PersonListPanelHandle(this);
        resultDisplay = new ResultDisplayHandle();
        commandBox = new CommandBoxHandle(this);
        statusBarFooter = new StatusBarFooterHandle();
        mainMenu = new MainMenuHandle(this);
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
