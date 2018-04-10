package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.typicaladdressbook.TypicalPersons.BENSON;
import static seedu.address.testutil.typicaladdressbook.TypicalTutees.ALICETUTEE;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tutee.EducationLevel;
import seedu.address.model.tutee.Grade;
import seedu.address.model.tutee.School;
import seedu.address.model.tutee.Subject;
import seedu.address.testutil.Assert;

public class XmlAdaptedPersonTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_SUBJECT = "1201";
    private static final String INVALID_GRADE = "100";
    private static final String INVALID_EDUCATION_LEVEL = "8th grade";
    private static final String INVALID_SCHOOL = "  ";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final List<XmlAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(XmlAdaptedTag::new)
            .collect(Collectors.toList());

    private static final String VALID_TUTEE_NAME = ALICETUTEE.getName().toString();
    private static final String VALID_TUTEE_PHONE = ALICETUTEE.getPhone().toString();
    private static final String VALID_TUTEE_EMAIL = ALICETUTEE.getEmail().toString();
    private static final String VALID_TUTEE_ADDRESS = ALICETUTEE.getAddress().toString();
    private static final String VALID_TUTEE_SUBJECT = ALICETUTEE.getSubject().toString();
    private static final String VALID_TUTEE_GRADE = ALICETUTEE.getGrade().toString();
    private static final String VALID_TUTEE_EDUCATION_LEVEL = ALICETUTEE.getEducationLevel().toString();
    private static final String VALID_TUTEE_SCHOOL = ALICETUTEE.getSchool().toString();
    private static final List<XmlAdaptedTag> VALID_TUTEE_TAGS = ALICETUTEE.getTags().stream()
            .map(XmlAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        XmlAdaptedPerson person = new XmlAdaptedPerson(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = Phone.MESSAGE_PHONE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = Email.MESSAGE_EMAIL_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, null, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS, VALID_TAGS);
        String expectedMessage = Address.MESSAGE_ADDRESS_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, null, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, invalidTags);
        Assert.assertThrows(IllegalValueException.class, person::toModelType);
    }

    //@@author yungyung04
    //=========== Tutee Related Tests =============================================================

    @Test
    public void toModelType_validTuteeDetails_returnsTutee() throws Exception {
        XmlAdaptedPerson tutee = new XmlAdaptedPerson(ALICETUTEE);
        assertEquals(ALICETUTEE, tutee.toModelType());
    }

    @Test
    public void toModelType_invalidTuteeName_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(INVALID_NAME, VALID_TUTEE_PHONE, VALID_TUTEE_EMAIL, VALID_TUTEE_ADDRESS,
                        VALID_TUTEE_SUBJECT, VALID_TUTEE_GRADE, VALID_TUTEE_EDUCATION_LEVEL,
                        VALID_TUTEE_SCHOOL, VALID_TUTEE_TAGS);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullTuteeName_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(null, VALID_TUTEE_PHONE, VALID_TUTEE_EMAIL, VALID_TUTEE_ADDRESS,
                        VALID_TUTEE_SUBJECT, VALID_TUTEE_GRADE, VALID_TUTEE_EDUCATION_LEVEL,
                        VALID_TUTEE_SCHOOL, VALID_TUTEE_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTuteePhone_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_TUTEE_NAME, INVALID_PHONE, VALID_TUTEE_EMAIL, VALID_TUTEE_ADDRESS,
                        VALID_TUTEE_SUBJECT, VALID_TUTEE_GRADE, VALID_TUTEE_EDUCATION_LEVEL,
                        VALID_TUTEE_SCHOOL, VALID_TUTEE_TAGS);
        String expectedMessage = Phone.MESSAGE_PHONE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullTuteePhone_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_TUTEE_NAME, null, VALID_TUTEE_EMAIL, VALID_TUTEE_ADDRESS,
                        VALID_TUTEE_SUBJECT, VALID_TUTEE_GRADE, VALID_TUTEE_EDUCATION_LEVEL,
                        VALID_TUTEE_SCHOOL, VALID_TUTEE_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTuteeEmail_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_TUTEE_NAME, VALID_TUTEE_PHONE, INVALID_EMAIL, VALID_TUTEE_ADDRESS,
                        VALID_TUTEE_SUBJECT, VALID_TUTEE_GRADE, VALID_TUTEE_EDUCATION_LEVEL,
                        VALID_TUTEE_SCHOOL, VALID_TUTEE_TAGS);
        String expectedMessage = Email.MESSAGE_EMAIL_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullTuteeEmail_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_TUTEE_NAME, VALID_TUTEE_PHONE, null, VALID_TUTEE_ADDRESS,
                        VALID_TUTEE_SUBJECT, VALID_TUTEE_GRADE, VALID_TUTEE_EDUCATION_LEVEL,
                        VALID_TUTEE_SCHOOL, VALID_TUTEE_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTuteeAddress_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_TUTEE_NAME, VALID_TUTEE_PHONE, VALID_TUTEE_EMAIL, INVALID_ADDRESS,
                        VALID_TUTEE_SUBJECT, VALID_TUTEE_GRADE, VALID_TUTEE_EDUCATION_LEVEL,
                        VALID_TUTEE_SCHOOL, VALID_TUTEE_TAGS);
        String expectedMessage = Address.MESSAGE_ADDRESS_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullTuteeAddress_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_TUTEE_NAME, VALID_TUTEE_PHONE, VALID_TUTEE_EMAIL, null,
                        VALID_TUTEE_SUBJECT, VALID_TUTEE_GRADE, VALID_TUTEE_EDUCATION_LEVEL,
                        VALID_TUTEE_SCHOOL, VALID_TUTEE_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidSubject_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_TUTEE_NAME, VALID_TUTEE_PHONE, VALID_TUTEE_EMAIL, VALID_TUTEE_ADDRESS,
                        INVALID_SUBJECT, VALID_TUTEE_GRADE, VALID_TUTEE_EDUCATION_LEVEL,
                        VALID_TUTEE_SCHOOL, VALID_TUTEE_TAGS);
        String expectedMessage = Subject.MESSAGE_SUBJECT_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullSubject_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_TUTEE_NAME, VALID_TUTEE_PHONE, VALID_TUTEE_EMAIL, VALID_TUTEE_ADDRESS,
                null, VALID_TUTEE_GRADE, VALID_TUTEE_EDUCATION_LEVEL,
                VALID_TUTEE_SCHOOL, VALID_TUTEE_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Subject.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidGrade_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_TUTEE_NAME, VALID_TUTEE_PHONE, VALID_TUTEE_EMAIL, VALID_TUTEE_ADDRESS,
                        VALID_TUTEE_SUBJECT, INVALID_GRADE, VALID_TUTEE_EDUCATION_LEVEL,
                        VALID_TUTEE_SCHOOL, VALID_TUTEE_TAGS);
        String expectedMessage = Grade.MESSAGE_GRADE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullGrade_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_TUTEE_NAME, VALID_TUTEE_PHONE, VALID_TUTEE_EMAIL, VALID_TUTEE_ADDRESS,
                VALID_TUTEE_SUBJECT, null, VALID_TUTEE_EDUCATION_LEVEL,
                VALID_TUTEE_SCHOOL, VALID_TUTEE_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Grade.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEducationLevel_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_TUTEE_NAME, VALID_TUTEE_PHONE, VALID_TUTEE_EMAIL, VALID_TUTEE_ADDRESS,
                        VALID_TUTEE_SUBJECT, VALID_TUTEE_GRADE, INVALID_EDUCATION_LEVEL,
                        VALID_TUTEE_SCHOOL, VALID_TUTEE_TAGS);
        String expectedMessage = EducationLevel.MESSAGE_EDUCATION_LEVEL_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEducationLevel_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_TUTEE_NAME, VALID_TUTEE_PHONE, VALID_TUTEE_EMAIL, VALID_TUTEE_ADDRESS,
                VALID_TUTEE_SUBJECT, VALID_TUTEE_GRADE, null,
                VALID_TUTEE_SCHOOL, VALID_TUTEE_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, EducationLevel.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidSchool_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_TUTEE_NAME, VALID_TUTEE_PHONE, VALID_TUTEE_EMAIL, VALID_TUTEE_ADDRESS,
                        VALID_TUTEE_SUBJECT, VALID_TUTEE_GRADE, VALID_TUTEE_EDUCATION_LEVEL,
                        INVALID_SCHOOL, VALID_TUTEE_TAGS);
        String expectedMessage = School.MESSAGE_SCHOOL_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullSchool_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_TUTEE_NAME, VALID_TUTEE_PHONE, VALID_TUTEE_EMAIL, VALID_TUTEE_ADDRESS,
                VALID_TUTEE_SUBJECT, VALID_TUTEE_GRADE, VALID_TUTEE_EDUCATION_LEVEL,
                null, VALID_TUTEE_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, School.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_tuteeHasInvalidTags_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TUTEE_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_TUTEE_NAME, VALID_TUTEE_PHONE, VALID_TUTEE_EMAIL, VALID_TUTEE_ADDRESS,
                VALID_TUTEE_SUBJECT, VALID_TUTEE_GRADE, VALID_TUTEE_EDUCATION_LEVEL,
                VALID_TUTEE_SCHOOL, invalidTags);
        Assert.assertThrows(IllegalValueException.class, person::toModelType);
    }
}
