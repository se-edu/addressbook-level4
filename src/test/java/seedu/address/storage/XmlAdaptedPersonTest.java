package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.testutil.Assert;

public class XmlAdaptedPersonTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();

    private final List<XmlAdaptedTag> validTags = BENSON.getTags().stream()
            .map(tag -> new XmlAdaptedTag(tag))
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        XmlAdaptedPerson person = new XmlAdaptedPerson(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, validTags);
        Assert.assertThrows(IllegalValueException.class, () -> person.toModelType());
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS, validTags);
        Assert.assertThrows(IllegalValueException.class, () -> person.toModelType());
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS, validTags);
        Assert.assertThrows(IllegalValueException.class, () -> person.toModelType());
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS, validTags);
        Assert.assertThrows(IllegalValueException.class, () -> person.toModelType());
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(validTags);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, invalidTags);
        Assert.assertThrows(IllegalValueException.class, () -> person.toModelType());
    }

}
