package guitests.guihandles;

import guitests.GuiRobot;
import javafx.stage.Stage;
import seedu.address.TestApp;

/**
 * Provides a handle for the main GUI.
 */
public class MainGuiHandle extends GuiHandle {

    private PersonListPanelHandle personListPanel;
    private ResultDisplayHandle resultDisplay;
    private CommandBoxHandle commandBox;
    private StatusBarFooterHandle statusBarFooter;
    private MainMenuHandle mainMenu;
    private BrowserPanelHandle browserPanel;

    public MainGuiHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);

        personListPanel = new PersonListPanelHandle(guiRobot, primaryStage);
        resultDisplay = new ResultDisplayHandle(guiRobot, primaryStage);
        commandBox = new CommandBoxHandle(guiRobot, primaryStage, TestApp.APP_TITLE);
        statusBarFooter = new StatusBarFooterHandle(guiRobot, primaryStage);
        mainMenu = new MainMenuHandle(guiRobot, primaryStage);
        browserPanel = new BrowserPanelHandle(guiRobot, primaryStage);
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
