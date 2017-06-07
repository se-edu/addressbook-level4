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

        this.nameLabel = (Label) getChildNode(NAME_FIELD_ID);
        this.addressLabel = (Label) getChildNode(ADDRESS_FIELD_ID);
        this.phoneLabel = (Label) getChildNode(PHONE_FIELD_ID);
        this.emailLabel = (Label) getChildNode(EMAIL_FIELD_ID);
        this.tagsContainer = (Region) getChildNode(TAGS_FIELD_ID);
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

    private List<String> getTagsFromSelf() {
        return tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(node -> ((Labeled) node).getText())
                .collect(Collectors.toList());
    }

    private List<String> getTagsFromSet(Set<Tag> tags) {
        return tags
                .stream()
                .map(tag -> tag.tagName)
                .collect(Collectors.toList());
    }

    /**
     * Returns true if {@code PersonCard} belongs to {@code person}.
     */
    public boolean belongsTo(ReadOnlyPerson person) {
        return getFullName().equals(person.getName().fullName)
                && getPhone().equals(person.getPhone().value)
                && getEmail().equals(person.getEmail().value)
                && getAddress().equals(person.getAddress().value)
                && getTagsFromSelf().equals(getTagsFromSet(person.getTags()));
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof PersonCardHandle)) {
            return super.equals(obj);
        }

        PersonCardHandle handle = (PersonCardHandle) obj;
        return getFullName().equals(handle.getFullName())
                && getPhone().equals(handle.getPhone())
                && getEmail().equals(handle.getEmail())
                && getAddress().equals(handle.getAddress())
                && getTagsFromSelf().equals(handle.getTagsFromSelf());
    }

    @Override
    public String toString() {
        return getFullName() + " " + getAddress();
    }
}
