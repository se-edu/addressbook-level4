package seedu.address.testutil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import guitests.guihandles.PersonCardHandle;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import junit.framework.AssertionFailedError;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.UniqueTagList;

/**
 * A utility class for test cases.
 */
public class TestUtil {

    /**
     * Returns true if {@code person} is the same as the person on {@code card}.
     * The persons are considered to be the same if they have the same name, phone, email,
     * address, and tags.
     * Returns false otherwise.
     */
    public static boolean isPersonSameAsPersonOnCard(PersonCardHandle card, ReadOnlyPerson person) {
        return card.isSamePerson(person);
    }

}
