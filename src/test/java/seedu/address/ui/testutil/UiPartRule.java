package seedu.address.ui.testutil;

import java.util.concurrent.TimeoutException;

import org.testfx.api.FxToolkit;

import javafx.scene.Parent;
import javafx.scene.Scene;
import seedu.address.ui.UiPart;

/**
 * Provides an isolated stage to test an individual {@code UiPart}.
 */
public class UiPartRule extends StageRule {
    private static final String[] CSS_FILES = {"view/DarkTheme.css", "view/Extensions.css"};

    // This is a workaround to solve headless test failure on Windows OS
    // Refer to https://github.com/javafxports/openjdk-jfx/issues/66 for more details.
    static {
        if (System.getProperty("os.name").toLowerCase().startsWith("win")) {
            System.loadLibrary("WindowsCodecs");
        }
    }

    public void setUiPart(final UiPart<? extends Parent> uiPart) {
        try {
            FxToolkit.setupScene(() -> {
                Scene scene = new Scene(uiPart.getRoot());
                scene.getStylesheets().setAll(CSS_FILES);
                return scene;
            });
            FxToolkit.showStage();
        } catch (TimeoutException te) {
            throw new AssertionError("Timeout should not happen.", te);
        }
    }
}
