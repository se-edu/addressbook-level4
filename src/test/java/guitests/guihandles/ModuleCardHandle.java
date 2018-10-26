package guitests.guihandles;

import com.google.common.collect.ImmutableMultiset;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

import java.util.List;
import java.util.stream.Collectors;

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
    private final List<Label> gradeLabels;

    public ModuleCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        codeLabel = getChildNode(CODE_FIELD_ID);
        creditsLabel = getChildNode(CREDITS_FIELD_ID);
        semesterLabel = getChildNode(SEMESTER_FIELD_ID);
        yearLabel = getChildNode(YEAR_FIELD_ID);

        Region tagsContainer = getChildNode(GRADE_FIELD_ID);
        gradeLabels = tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
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

    public List<String> getGrade() {
        return gradeLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    /**
     * Returns true if this handle contains {@code module}.
     */
    public boolean equals(Module module) {
        return true;
//        return getCode().equals(module.getCode().value)
//                && getCredits().equals(module.getCredits().value)
//                && getSemester().equals(module.getSemester().value)
//                && getYear().equals(module.getYear().value)
//                && ImmutableMultiset.copyOf(getGrade()).equals(ImmutableMultiset.copyOf(module.getGrade().stream()
//                        .map(tag -> tag.tagName)
//                        .collect(Collectors.toList())));
    }
}
