package seedu.address.ui;

import javafx.animation.FadeTransition;
import javafx.animation.Status;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import seedu.address.commons.util.FxViewUtil;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.commons.core.LogsCenter;

import java.util.logging.Logger;

/**
 * Controller for a help page
 */
public class TaskWindow extends UiPart {

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String ICON = "/images/address_book_32.png";
    private static final String FXML = "TaskWindow.fxml";
    
    private static final String TITLE = "Task";

    private VBox mainPane;

    private Stage dialogStage;
    
    private BrowserPanel browserPanel;
    
    @FXML
    private AnchorPane browserPlaceholder;
    
    @FXML
    private MenuItem closeMenuItem;

    public static TaskWindow load(Stage primaryStage, UserPrefs prefs) {
        logger.fine("Showing help page about the application.");
        TaskWindow taskWindow = UiPartLoader.loadUiPart(primaryStage, new TaskWindow());
        taskWindow.configure(prefs);
        return taskWindow;
    }

    @Override
    public void setNode(Node node) {
        mainPane = (VBox) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    private void configure(UserPrefs prefs){
        Scene scene = new Scene(mainPane);
        scene.setFill(Color.TRANSPARENT);
        
        //Null passed as the parent stage to make it non-modal.
        dialogStage = createDialogStage(TITLE, null, scene);
        
        dialogStage.initStyle(StageStyle.TRANSPARENT);
        
        setIcon(dialogStage, ICON);
        setWindowDefaultSize(prefs);

        browserPanel = BrowserPanel.load(browserPlaceholder);
    }
    
    protected void setWindowDefaultSize(UserPrefs prefs) {
        dialogStage.setHeight(300);
        dialogStage.setWidth(300);
        
        if (prefs.getGuiSettings().getWindowCoordinates() != null) {
        	dialogStage.setX(prefs.getGuiSettings().getWindowCoordinates().getX() - dialogStage.getWidth());
        	dialogStage.setY(prefs.getGuiSettings().getWindowCoordinates().getY());
        }
    }
    
    public void loadTaskPage(ReadOnlyTask task) {
    	browserPanel.loadTaskPage(task);
    }
    
    public void loadTaskCard(ReadOnlyTask task) {
    	browserPanel.loadTaskCard(task);
    }

    public void show() {
    	FadeTransition ft = new FadeTransition();
    	ft.setDuration(Duration.millis(500));
    	ft.setNode(dialogStage.getScene().getRoot());

    	ft.setFromValue(0.0);
    	ft.setToValue(1.0);
    	
    	ft.play();
    	dialogStage.show();
    }
    
    public void hide() {
    	FadeTransition ft = new FadeTransition();
    	ft.setDuration(Duration.millis(500));
    	ft.setNode(dialogStage.getScene().getRoot());
    	
    	ft.setFromValue(1.0);
    	ft.setToValue(0.0);
    	
    	ft.play();
    	ft.setOnFinished((ae) -> dialogStage.hide());
    }
}
