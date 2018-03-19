package seedu.address.testutil;

import java.util.HashSet;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.tutee.EducationLevel;
import seedu.address.model.tutee.Grade;
import seedu.address.model.tutee.School;
import seedu.address.model.tutee.Subject;
import seedu.address.model.tutee.Tutee;
import seedu.address.model.util.SampleDataUtil;


/**
 * A utility class to help with building Tutee objects.
 */
public class TuteeBuilder extends PersonBuilder {
    public static final String DEFAULT_SUBJECT = "social studies";
    public static final String DEFAULT_GRADE = "B-";
    public static final String DEFAULT_EDUCATION_LEVEL = "primary";
    public static final String DEFAULT_SCHOOL = "fengshan primary school";

    private Subject subject;
    private Grade grade;
    private EducationLevel educationLevel;
    private School school;

    public TuteeBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        subject = new Subject(DEFAULT_SUBJECT);
        grade = new Grade(DEFAULT_GRADE);
        educationLevel = new EducationLevel(DEFAULT_EDUCATION_LEVEL);
        school = new School(DEFAULT_SCHOOL);
        tags = SampleDataUtil.getTagSet(DEFAULT_TAGS);
        tags.add(new Tag("Tutee"));
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public TuteeBuilder(Tutee tuteeToCopy) {
        name = tuteeToCopy.getName();
        phone = tuteeToCopy.getPhone();
        email = tuteeToCopy.getEmail();
        address = tuteeToCopy.getAddress();
        subject = tuteeToCopy.getSubject();
        grade = tuteeToCopy.getGrade();
        educationLevel = tuteeToCopy.getEducationLevel();
        school = tuteeToCopy.getSchool();
        tags = new HashSet<>(tuteeToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Tutee} that we are building.
     */
    public TuteeBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Tutee} that we are building.
     */
    public TuteeBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        this.tags.add(new Tag("Tutee"));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Tutee} that we are building.
     */
    public TuteeBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Tutee} that we are building.
     */
    public TuteeBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Tutee} that we are building.
     */
    public TuteeBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Subject} of the {@code Tutee} that we are building.
     */
    public TuteeBuilder withSubject(String subject) {
        this.subject = new Subject(subject);
        return this;
    }
    /**
     * Sets the {@code Grade} of the {@code Tutee} that we are building.
     */
    public TuteeBuilder withGrade(String grade) {
        this.grade = new Grade(grade);
        return this;
    }
    /**
     * Sets the {@code EducationLevel} of the {@code Tutee} that we are building.
     */
    public TuteeBuilder withEducationLevel(String educationLevel) {
        this.educationLevel = new EducationLevel(educationLevel);
        return this;
    }
    /**
     * Sets the {@code School} of the {@code Tutee} that we are building.
     */
    public TuteeBuilder withSchool(String school) {
        this.school = new School(school);
        return this;
    }


    public Tutee build() {
        return new Tutee(name, phone, email, address, subject, grade, educationLevel, school, tags);
    }

}
