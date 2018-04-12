package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.tutee.EducationLevel;
import seedu.address.model.tutee.Grade;
import seedu.address.model.tutee.School;
import seedu.address.model.tutee.Subject;
import seedu.address.model.tutee.Tutee;

/**
 * JAXB-friendly version of the Person.
 */
public class XmlAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String phone;
    @XmlElement(required = true)
    private String email;
    @XmlElement(required = true)
    private String address;
    @XmlElement(required = true)
    private String subject;
    @XmlElement(required = true)
    private String grade;
    @XmlElement(required = true)
    private String educationLevel;
    @XmlElement(required = true)
    private String school;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedPerson.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedPerson() {}

    /**
     * Constructs an {@code XmlAdaptedPerson} with given person details.
     */
    public XmlAdaptedPerson(String name, String phone, String email, String address, List<XmlAdaptedTag> tagged) {
        this(name, phone, email, address, null, null, null, null, tagged);
    }

    /**
     * Constructs an {@code XmlAdaptedPerson} with the given tutee details.
     */
    public XmlAdaptedPerson(String name, String phone, String email, String address, String subject, String grade,
                            String educationLevel, String school, List<XmlAdaptedTag> tagged) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.subject = subject;
        this.grade = grade;
        this.educationLevel = educationLevel;
        this.school = school;
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
    }

    /**
     * Converts a given Person into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
        if (source instanceof Tutee) {
            subject = ((Tutee) source).getSubject().subject;
            grade = ((Tutee) source).getGrade().grade;
            educationLevel = ((Tutee) source).getEducationLevel().educationLevel;
            school = ((Tutee) source).getSchool().school;
        }
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Person object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }

        if (this.name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(this.name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name name = new Name(this.name);

        if (this.phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(this.phone)) {
            throw new IllegalValueException(Phone.MESSAGE_PHONE_CONSTRAINTS);
        }
        final Phone phone = new Phone(this.phone);

        if (this.email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(this.email)) {
            throw new IllegalValueException(Email.MESSAGE_EMAIL_CONSTRAINTS);
        }
        final Email email = new Email(this.email);

        if (this.address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(this.address)) {
            throw new IllegalValueException(Address.MESSAGE_ADDRESS_CONSTRAINTS);
        }
        final Address address = new Address(this.address);

        final Set<Tag> tags = new HashSet<>(personTags);

        //@@author yungyung04
        if (isTutee(personTags)) {
            if (this.subject == null) {
                throw new IllegalValueException(
                        String.format(MISSING_FIELD_MESSAGE_FORMAT, Subject.class.getSimpleName()));
            }
            if (!Subject.isValidSubject(this.subject)) {
                throw new IllegalValueException(Subject.MESSAGE_SUBJECT_CONSTRAINTS);
            }
            final Subject subject = new Subject(this.subject);

            if (this.grade == null) {
                throw new IllegalValueException(
                        String.format(MISSING_FIELD_MESSAGE_FORMAT, Grade.class.getSimpleName()));
            }
            if (!Grade.isValidGrade(this.grade)) {
                throw new IllegalValueException(Grade.MESSAGE_GRADE_CONSTRAINTS);
            }
            final Grade grade = new Grade(this.grade);

            if (this.educationLevel == null) {
                throw new IllegalValueException(
                        String.format(MISSING_FIELD_MESSAGE_FORMAT, EducationLevel.class.getSimpleName()));
            }
            if (!EducationLevel.isValidEducationLevel(this.educationLevel)) {
                throw new IllegalValueException(EducationLevel.MESSAGE_EDUCATION_LEVEL_CONSTRAINTS);
            }
            final EducationLevel educationLevel = new EducationLevel(this.educationLevel);

            if (this.school == null) {
                throw new IllegalValueException(
                        String.format(MISSING_FIELD_MESSAGE_FORMAT, School.class.getSimpleName()));
            }
            if (!School.isValidSchool(this.school)) {
                throw new IllegalValueException(School.MESSAGE_SCHOOL_CONSTRAINTS);
            }
            final School school = new School(this.school);

            return new Tutee(name, phone, email, address, subject, grade, educationLevel, school, tags);
        } else {
            return new Person(name, phone, email, address, tags);
        }
    }
    //@@author
    private boolean isTutee(List<Tag> personTags) {
        return personTags.contains(new Tag("Tutee"));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedPerson)) {
            return false;
        }

        XmlAdaptedPerson otherPerson = (XmlAdaptedPerson) other;
        return Objects.equals(name, otherPerson.name)
                && Objects.equals(phone, otherPerson.phone)
                && Objects.equals(email, otherPerson.email)
                && Objects.equals(address, otherPerson.address)
                && tagged.equals(otherPerson.tagged);
    }
}
