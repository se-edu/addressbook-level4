package guitests.guihandles;

import javafx.stage.Stage;

/**
 * A handle to the {@code HelpWindow} of the application.
 */
public class HelpWindowHandle extends StageHandle {

    public static final String HELP_WINDOW_TITLE = "Help";

    public HelpWindowHandle(Stage helpWindowStage) {
        super(helpWindowStage);
    }
}
