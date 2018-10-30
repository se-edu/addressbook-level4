package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.module.Module;

/**
 * An UI component that displays information of a {@code Module}.
 */
public class ModuleCard2 extends UiPart<Region> {

    private static final String FXML = "ModuleListCard2.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Module module;

    @FXML
    private Label code;
    @FXML
    private Label id;
    @FXML
    private Label credits;
    @FXML
    private Label semester;
    @FXML
    private Label year;
    @FXML
    private Label grade;

    public ModuleCard2(Module module) {
        super(FXML);
        this.module = module;
        id.setText(""); // may be used to index module cards in the UI.
        code.setText(module.getCode().value);
        credits.setText(module.getCredits().value + "");
        semester.setText(module.getSemester().value);
        year.setText(module.getYear().value + "");
        grade.setText(module.getGrade().value);
        //module.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModuleCard2)) {
            return false;
        }

        // state check
        ModuleCard2 card = (ModuleCard2) other;
        return id.getText().equals(card.id.getText())
                && module.equals(card.module);
    }
}
