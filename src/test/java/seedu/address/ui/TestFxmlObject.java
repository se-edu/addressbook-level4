package seedu.address.ui;

import javafx.beans.DefaultProperty;

/**
 * A test object which can be constructed via an FXML file.
 * Unlike other JavaFX classes, this class can be constructed without the JavaFX toolkit being initialized.
 */
@DefaultProperty("text")
public class TestFxmlObject {

    private String text;

    public TestFxmlObject() {}

    public TestFxmlObject(String text) {
        setText(text);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TestFxmlObject // instanceof handles nulls
                        && text.equals(((TestFxmlObject) other).getText()));
    }

}
