package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.address.controller.PersonCardController;
import seedu.address.model.datatypes.person.Person;
import seedu.address.model.datatypes.tag.Tag;
import seedu.address.testutil.PersonBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Provides a handle to a person card in the person list panel.
 */
public class PersonCardHandle extends GuiHandle {
    private static final String FIRST_NAME_FIELD_ID = "#firstName";
    private static final String LAST_NAME_FIELD_ID = "#lastName";
    private static final String ADDRESS_FIELD_ID = "#address";
    private static final String BIRTHDAY_FIELD_ID = "#birthday";
    private static final String TAGS_FIELD_ID = "#tags";
    private static final String PENDING_STATE_LABEL_FIELD_ID = "#commandTypeLabel";
    private static final String PENDING_STATE_PROGRESS_INDICATOR_FIELD_ID = "#remoteRequestOngoingIndicator";
    private static final String PENDING_STATE_ROOT_FIELD_ID = "#commandStateDisplayRootNode";
    private static final String PENDING_STATE_GRACE_PERIOD_FIELD_ID = "#commandStateInfoLabel";

    private Node node;

    public PersonCardHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, null);
    }

    public PersonCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node){
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    public String getLastName(){
        return getTextFromLabel(LAST_NAME_FIELD_ID);
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    public String getFirstName() {
        return getTextFromLabel(FIRST_NAME_FIELD_ID);
    }

    public String getAddress() {
        return getTextFromLabel(ADDRESS_FIELD_ID);
    }

    public String getBirthday() {
        return getTextFromLabel(BIRTHDAY_FIELD_ID);
    }

    public String getTags() {
        return getTextFromLabel(TAGS_FIELD_ID);
    }

    public boolean isSamePerson(Person person){
        return getFirstName().equals(person.getFirstName())
                && getLastName().equals(person.getLastName())
                && getAddress().equals(PersonCardController.getAddressString(person.getStreet(),
                                                                person.getCity(), person.getPostalCode()));
    }

    public Tag[] getTagList() {
        String tags = getTags();

        if (tags.equals("")) {
            return new Tag[]{};
        }

        final String[] split = tags.split(", ");

        final List<Tag> collect = Arrays.asList(split).stream().map(e -> new Tag(e.replaceFirst("Tag: ", ""))).collect(Collectors.toList());

        return collect.toArray(new Tag[split.length]);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof PersonCardHandle) {
            PersonCardHandle handle = (PersonCardHandle) obj;
            return getFirstName().equals(handle.getFirstName()) && getLastName().equals(handle.getLastName())
                    && getAddress().equals(handle.getAddress()) && getBirthday().equals(handle.getBirthday());
        }
        return super.equals(obj);
    }

    private int getRemainingGracePeriod() {
        return Integer.valueOf(getTextFromLabel(PENDING_STATE_GRACE_PERIOD_FIELD_ID));
    }

    public boolean isGracePeriodFrozen() {
        int gracePeriod = getRemainingGracePeriod();
        guiRobot.sleep(2, TimeUnit.SECONDS);
        return gracePeriod == getRemainingGracePeriod();
    }

    /**
     * Gets the representation of this card in a person form.
     * @param id The id of the person
     * @param githubUsername The github username of the person
     * @return
     */
    public Person mockPerson(int id, String githubUsername) {
        String address = getAddress();
        final String[] split = address.split(System.lineSeparator());
        String street = "", city = "", postalcode = "";
        for(int i = 0; i < split.length; i++) {
            if (i == 0) {
                street = split[i];
            } else if (i == 1) {
                city = split[i];
            } else if (i == 2) {
                postalcode = split[i];
            }
        }
        return new PersonBuilder(getFirstName(), getLastName(), id).withStreet(street).withCity(city)
                                 .withPostalCode(postalcode).withBirthday(getBirthday()).withGithubUsername(githubUsername)
                                 .withTags(getTagList()).build();
    }

    @Override
    public String toString() {
        return getFirstName() + " " + getLastName() + " " + getAddress() + " " + getBirthday();
    }
}
