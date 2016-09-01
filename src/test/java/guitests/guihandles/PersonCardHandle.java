package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.address.controller.PersonCardController;
import seedu.address.exceptions.IllegalValueException;
import seedu.address.model.Tag;
import seedu.address.model.person.Person;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Provides a handle to a person card in the person list panel.
 */
public class PersonCardHandle extends GuiHandle {
    private static final String NAME_FIELD_ID = "#name";
    private static final String ADDRESS_FIELD_ID = "#address";
    private static final String PHONE_FIELD_ID = "#phone";
    private static final String EMAIL_FIELD_ID = "#email";
    private static final String TAGS_FIELD_ID = "#tags";

    private Node node;

    public PersonCardHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, null);
    }

    public PersonCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node){
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    public String getFullName() {
        return getTextFromLabel(NAME_FIELD_ID);
    }

    public String getAddress() {
        return getTextFromLabel(ADDRESS_FIELD_ID);
    }

    public String getTags() {
        return getTextFromLabel(TAGS_FIELD_ID);
    }

    public boolean isSamePerson(Person person){
        return getFullName().equals(person.getName().fullName)
                && getAddress().equals(person.getAddress().value);
    }

    public Tag[] getTagList() {
        String tags = getTags();

        if (tags.equals("")) {
            return new Tag[]{};
        }

        final String[] split = tags.split(", ");

        final List<Tag> collect = Arrays.asList(split).stream().map(e -> {
            try {
                return new Tag(e.replaceFirst("Tag: ", ""));
            } catch (IllegalValueException e1) {
                assert false; //not possible
                return null;
            }
        }).collect(Collectors.toList());

        return collect.toArray(new Tag[split.length]);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof PersonCardHandle) {
            PersonCardHandle handle = (PersonCardHandle) obj;
            return getFullName().equals(handle.getFullName())
                    && getAddress().equals(handle.getAddress()); //TODO: compare the rest
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getFullName() + " " + getAddress();
    }
}
