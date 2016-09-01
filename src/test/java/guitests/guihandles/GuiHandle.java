package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.stage.Window;
import seedu.address.TestApp;
import seedu.address.model.person.Person;
import seedu.address.util.AppLogger;
import seedu.address.util.LoggerManager;

import java.lang.reflect.Constructor;

/**
 * Base class for all GUI Handles used in testing.
 */
public class GuiHandle {
    protected final GuiRobot guiRobot;
    protected final Stage primaryStage;
    protected final String stageTitle;

    private final AppLogger logger = LoggerManager.getLogger(this.getClass());

    public GuiHandle(GuiRobot guiRobot, Stage primaryStage, String stageTitle) {
        this.guiRobot = guiRobot;
        this.primaryStage = primaryStage;
        this.stageTitle = stageTitle;
        focusOnSelf();
    }

    /**
     * Creates an object of the specified GuiHandle child class.
     */
    public <T> T as(Class<? extends GuiHandle> clazz) {
        try {
            Constructor<?> ctor = clazz.getConstructor(GuiRobot.class, Stage.class);
            Object object = ctor.newInstance(new Object[] { guiRobot, primaryStage});
            return (T) object;
        } catch (Exception e) {
            throw new RuntimeException("Cannot create gui handle of type " + clazz.getName(), e);
        }
    }

    public void write(String textToWrite) {
        guiRobot.write(textToWrite);
    }

    public void focusOnWindow(String stageTitle) {
        logger.info("Focusing {}", stageTitle);
        java.util.Optional<Window> window = guiRobot.listTargetWindows()
                .stream()
                .filter(w -> w instanceof Stage && ((Stage) w).getTitle().equals(stageTitle)).findAny();

        if (!window.isPresent()) {
            logger.warn("Can't find stage {}, Therefore, aborting focusing", stageTitle);
            return;
        }

        guiRobot.targetWindow(window.get());
        guiRobot.interact(() -> window.get().requestFocus());
        logger.info("Finishing focus {}", stageTitle);
    }

    protected Node getNode(String query) {
        return guiRobot.lookup(query).tryQuery().get();
    }

    protected String getTextFieldText(String filedName) {
        return ((TextField) getNode(filedName)).getText();
    }

    public void moveCursor(Person person) {
        guiRobot.moveTo(person.getName().fullName);
    }

    /**
     * Sets the specified text field directly (as opposed to simulating the user typing).
     * @param textFieldId
     * @param newText
     */
    protected void setTextField(String textFieldId, String newText) {
        TextField textField = (TextField) getNode(textFieldId);
        textField.setText(newText);
    }

    /**
     * Simulates the user typing text in the given text field
     * @param textFieldId
     * @param newText
     */
    protected void typeTextField(String textFieldId, String newText) {
        guiRobot.clickOn(textFieldId);
        guiRobot.push(KeyCode.SHORTCUT, KeyCode.A).eraseText(1)
                .write(newText);
    }

    public void pressEnter() {
        guiRobot.type(KeyCode.ENTER).sleep(500);
    }

    public void pressTab() {
        guiRobot.push(KeyCode.TAB).sleep(500);
    }

    protected void pressEsc() {
        guiRobot.push(KeyCode.ESCAPE);
    }

    /**
     * Presses the button with the name 'Cancel' on it.
     */
    public void clickCancel() {
        guiRobot.clickOn("Cancel");
    }

    /**
     * Presses the button named 'OK'.
     */
    public void clickOk() {
        guiRobot.clickOn("OK");
    }

    public GuiHandle clickOn(String id) {
        guiRobot.clickOn(id);
        return this;
    }

    public GuiHandle rightClickOn(String id) {
        guiRobot.rightClickOn(id);
        return this;
    }

    /**
     * Dismisses the dialog by pressing Esc
     */
    public void dismiss() {
        pressEsc();
    }

    public void dismissErrorMessage(String errorDialogTitle) {
        focusOnWindow(errorDialogTitle);
        clickOk();
        focusOnSelf();
    }

    protected String getTextFromLabel(String fieldId, Node parentNode) {
        return ((Label) guiRobot.from(parentNode).lookup(fieldId).tryQuery().get()).getText();
    }

    public void focusOnSelf() {
        if (stageTitle != null) {
            focusOnWindow(stageTitle);
        }
    }

    public void focusOnMainApp() {
        this.focusOnWindow(TestApp.APP_TITLE);
    }
}
