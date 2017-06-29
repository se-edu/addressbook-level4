package seedu.address.ui.testutil;

import java.util.concurrent.TimeoutException;

import org.testfx.api.FxToolkit;

import javafx.scene.Parent;
import javafx.scene.Scene;
import seedu.address.ui.UiPart;

/**
 * Provides an isolated scene to test an individual {@code UiPart}.
 */
public class UiPartRule extends TestFxRule {
    private static final String[] CSS_FILES = {"view/DarkTheme.css", "view/Extensions.css"};

    public void setUiPart(final UiPart<? extends Parent> uiPart) throws TimeoutException {
        FxToolkit.setupScene(() -> {
            Scene scene = new Scene(uiPart.getRoot());
            scene.getStylesheets().setAll(CSS_FILES);
            return scene;
        });
        FxToolkit.showStage();
    }
}
