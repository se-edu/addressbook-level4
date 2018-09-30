package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMultiset;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;

/**
 * Provides a handle to a person card in the person list panel.
 */
public class PersonCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String EMPLOYEEID_FIELD_ID = "#employeeId";
    private static final String NAME_FIELD_ID = "#name";
    private static final String ADDRESS_FIELD_ID = "#address";
    private static final String PHONE_FIELD_ID = "#phone";
    private static final String EMAIL_FIELD_ID = "#email";
    private static final String DATEOFBIRTH_FIELD_ID = "#dateOfBirth";
    private static final String DEPARTMENT_FIELD_ID = "#department";
    private static final String POSITION_FIELD_ID = "#position";
    private static final String SALARY_FIELD_ID = "#salary";
    private static final String BONUS_FIELD_ID = "#bonus";
    private static final String TAGS_FIELD_ID = "#tags";

    private final Label idLabel;
    private final Label employeeIdLabel;
    private final Label nameLabel;
    private final Label addressLabel;
    private final Label phoneLabel;
    private final Label emailLabel;
    private final Label dateOfBirthLabel;
    private final Label departmentLabel;
    private final Label positionLabel;
    private final Label salaryLabel;
    private final Label bonusLabel;
    private final List<Label> tagLabels;

    public PersonCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        employeeIdLabel = getChildNode(EMPLOYEEID_FIELD_ID);
        nameLabel = getChildNode(NAME_FIELD_ID);
        addressLabel = getChildNode(ADDRESS_FIELD_ID);
        phoneLabel = getChildNode(PHONE_FIELD_ID);
        emailLabel = getChildNode(EMAIL_FIELD_ID);
        dateOfBirthLabel = getChildNode(DATEOFBIRTH_FIELD_ID);
        departmentLabel = getChildNode(DEPARTMENT_FIELD_ID);
        positionLabel = getChildNode(POSITION_FIELD_ID);
        salaryLabel = getChildNode(SALARY_FIELD_ID);
        bonusLabel = getChildNode(BONUS_FIELD_ID);


        Region tagsContainer = getChildNode(TAGS_FIELD_ID);
        tagLabels = tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getEmployeeId() {
        return employeeIdLabel.getText();
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getAddress() {
        return addressLabel.getText();
    }

    public String getPhone() {
        return phoneLabel.getText();
    }

    public String getEmail() {
        return emailLabel.getText();
    }

    public String getDateOfBirth() {
        return dateOfBirthLabel.getText();
    }

    public String getDepartment() {
        return departmentLabel.getText();
    }

    public String getPosition() {
        return positionLabel.getText();
    }

    public String getSalary() {
        return salaryLabel.getText();
    }

    public String getBonus() {
        return bonusLabel.getText();
    }

    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    /**
     * Returns true if this handle contains {@code person}.
     */
    public boolean equals(Person person) {
        return getName().equals(person.getName().fullName)
                && getAddress().equals(person.getAddress().value)
                && getPhone().equals(person.getPhone().value)
                && getEmail().equals(person.getEmail().value)
                && ImmutableMultiset.copyOf(getTags()).equals(ImmutableMultiset.copyOf(person.getTags().stream()
                        .map(tag -> tag.tagName)
                        .collect(Collectors.toList())));
    }
}
