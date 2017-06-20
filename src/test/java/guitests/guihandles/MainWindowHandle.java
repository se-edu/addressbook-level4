package guitests.guihandles;

import javafx.stage.Stage;
import seedu.address.TestApp;

/**
 * Provides a handle for {@code MainWindow}.
 */
public class MainWindowHandle extends GuiHandle {

    private final PersonListPanelHandle personListPanel;
    private final ResultDisplayHandle resultDisplay;
    private final CommandBoxHandle commandBox;
    private final StatusBarFooterHandle statusBarFooter;
    private final MainMenuHandle mainMenu;
    private final BrowserPanelHandle browserPanel;

    public MainWindowHandle(Stage primaryStage) {
        super(primaryStage, TestApp.APP_TITLE);

        personListPanel = new PersonListPanelHandle(primaryStage);
        resultDisplay = new ResultDisplayHandle(primaryStage);
        commandBox = new CommandBoxHandle(primaryStage, TestApp.APP_TITLE);
        statusBarFooter = new StatusBarFooterHandle(primaryStage);
        mainMenu = new MainMenuHandle(primaryStage);
        browserPanel = new BrowserPanelHandle(primaryStage);
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
