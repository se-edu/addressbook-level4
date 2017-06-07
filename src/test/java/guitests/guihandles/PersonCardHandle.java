package guitests.guihandles;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.layout.Region;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.Tag;

/**
 * Provides a handle to a person card in the person list panel.
 */
public class PersonCardHandle extends NodeHandle {
    private static final String NAME_FIELD_ID = "#name";
    private static final String ADDRESS_FIELD_ID = "#address";
    private static final String PHONE_FIELD_ID = "#phone";
    private static final String EMAIL_FIELD_ID = "#email";
    private static final String TAGS_FIELD_ID = "#tags";

    private Label nameLabel;
    private Label addressLabel;
    private Label phoneLabel;
    private Label emailLabel;
    private Region tagsContainer;

    public PersonCardHandle(Node cardNode) {
        super(cardNode);

        this.nameLabel = (Label) getNode(NAME_FIELD_ID);
        this.addressLabel = (Label) getNode(ADDRESS_FIELD_ID);
        this.phoneLabel = (Label) getNode(PHONE_FIELD_ID);
        this.emailLabel = (Label) getNode(EMAIL_FIELD_ID);
        this.tagsContainer = (Region) getNode(TAGS_FIELD_ID);
    }

    public String getFullName() {
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

    private List<String> getTags() {
        return tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(node -> ((Labeled) node).getText())
                .collect(Collectors.toList());
    }

    private List<String> getTags(Set<Tag> tags) {
        return tags
                .stream()
                .map(tag -> tag.tagName)
                .collect(Collectors.toList());
    }

    public boolean isSamePerson(ReadOnlyPerson person) {
        return getFullName().equals(person.getName().fullName)
                && getPhone().equals(person.getPhone().value)
                && getEmail().equals(person.getEmail().value)
                && getAddress().equals(person.getAddress().value)
                && getTags().equals(getTags(person.getTags()));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PersonCardHandle) {
            PersonCardHandle handle = (PersonCardHandle) obj;
            return getFullName().equals(handle.getFullName())
                    && getPhone().equals(handle.getPhone())
                    && getEmail().equals(handle.getEmail())
                    && getAddress().equals(handle.getAddress())
                    && getTags().equals(handle.getTags());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getFullName() + " " + getAddress();
    }
}
