package seedu.address.ui;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import seedu.address.commons.util.FxViewUtil;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.commons.core.LogsCenter;

import java.util.logging.Logger;

/**
 * Controller for a help page
 */
public class TaskWindow extends UiPart {

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String ICON = "/images/help_icon.png";
    private static final String FXML = "TaskWindow.fxml";
    private static final String TITLE = "Task";
    
    //private static String url;

    private AnchorPane mainPane;

    private Stage dialogStage;
    
    private WebView browser;

    public static TaskWindow load(Stage primaryStage) {
        logger.fine("Showing help page about the application.");
        TaskWindow taskWindow = UiPartLoader.loadUiPart(primaryStage, new TaskWindow());
        taskWindow.configure();
        return taskWindow;
    }

    @Override
    public void setNode(Node node) {
        mainPane = (AnchorPane) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    private void configure(){
        Scene scene = new Scene(mainPane);
        //Null passed as the parent stage to make it non-modal.
        dialogStage = createDialogStage(TITLE, null, scene);
        dialogStage.setMaximized(true); //TODO: set a more appropriate initial size
        setIcon(dialogStage, ICON);

        browser = new WebView();
        
        FxViewUtil.applyAnchorBoundaryParameters(browser, 0.0, 0.0, 0.0, 0.0);
        mainPane.getChildren().add(browser);
    }
    
    public void loadTaskPage(ReadOnlyTask task) {
        browser.getEngine().load("https://www.google.com.sg/#safe=off&q=" + task.getName().taskName.replaceAll(" ", "+"));
    }
    
    public void loadTaskCard(ReadOnlyTask task) {
        browser.getEngine().loadContent(task.getAsHTML());
    }

    public void show() {
        //dialogStage.showAndWait();
    	dialogStage.show();
    }
}
