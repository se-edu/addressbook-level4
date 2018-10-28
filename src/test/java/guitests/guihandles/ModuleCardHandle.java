package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;
import seedu.address.model.module.Module;


/**
 * Provides a handle to a person card in the person list panel.
 */
public class ModuleCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String CODE_FIELD_ID = "#code";
    private static final String CREDITS_FIELD_ID = "#credits";
    private static final String SEMESTER_FIELD_ID = "#semester";
    private static final String YEAR_FIELD_ID = "#year";
    private static final String GRADE_FIELD_ID = "#grade";

    private final Label idLabel;
    private final Label codeLabel;
    private final Label creditsLabel;
    private final Label semesterLabel;
    private final Label yearLabel;
    private final Label gradeLabel;

    public ModuleCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        codeLabel = getChildNode(CODE_FIELD_ID);
        creditsLabel = getChildNode(CREDITS_FIELD_ID);
        semesterLabel = getChildNode(SEMESTER_FIELD_ID);
        yearLabel = getChildNode(YEAR_FIELD_ID);
        gradeLabel = getChildNode(GRADE_FIELD_ID);

    }

    public String getId() {
        return idLabel.getText();
    }

    public String getCode() {
        return codeLabel.getText();
    }

    public String getCredits() {
        return creditsLabel.getText();
    }

    public String getSemester() {
        return semesterLabel.getText();
    }

    public String getYear() {
        return yearLabel.getText();
    }

    public String getGrade() {
        return gradeLabel.getText();
    }


    /**
     * Returns true if this handle contains {@code module}.
     */
    public boolean equals(Module module) {
        return getCode().equals(module.getCode().value)
                && getCredits().equals(module.getCredits().value)
                && getSemester().equals(module.getSemester().value)
                && getYear().equals(module.getYear().value)
                && getGrade().equals(module.getGrade().value);
    }
}
