package seedu.address.storage;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.module.*;
import seedu.address.model.module.Module;
import seedu.address.model.person.*;

import javax.xml.bind.annotation.XmlElement;
import java.util.*;

/**
 * JAXB-friendly version of the Module.
 */
public class XmlAdaptedModule {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Module's %s field is missing!";

    @XmlElement(required = true)
    private Code code;
    @XmlElement(required = true)
    private Year year;
    @XmlElement(required = true)
    private Semester semester;
    @XmlElement(required = true)
    private Credit credit;
    @XmlElement(required = true)
    private Grade grade;

    /**
     * Constructs an XmlAdaptedPerson.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedModule() {
    }

    /**
     * Constructs an {@code XmlAdaptedPerson} with the given person details.
     */
    public XmlAdaptedModule(Code code, Year year, Semester semester, Credit credit, Grade grade) {
        this.code = code;
        this.year = year;
        this.semester = semester;
        this.credit = credit;
        this.grade = grade;
    }

    /**
     * Converts a given Person into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedModule(Module source) {
        code = source.getCode();
        year = source.getYear();
        semester = source.getSemester();
        credit = source.getCredits();
        grade = source.getGrade();
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Person object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Module toModelType() throws IllegalValueException {

        if (code == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Code.class.getSimpleName()));
        }
        if (!Code.isValidCode(code.value)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Code modelCode = new Code(code.value);

        if (year == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Year.class.getSimpleName()));
        }
        if (!Year.isValidYear(year.value)) {
            throw new IllegalValueException(Year.MESSAGE_YEAR_CONSTRAINTS);
        }
        final Year modelYear = new Year(year.value);

        if (semester == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Semester.class.getSimpleName()));
        }
        if (!Semester.isValidSemester(semester.value)) {
            throw new IllegalValueException(Semester.MESSAGE_SEMESTER_CONSTRAINTS);
        }
        final Semester modelSemester = new Semester(semester.value);

        if (credit == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Credit.class.getSimpleName()));
        }
        if (!Credit.isValidCredit(credit.value)) {
            throw new IllegalValueException(Credit.MESSAGE_CREDIT_CONSTRAINTS);
        }
        final Credit modelCredit = new Credit(credit.value);

        if (grade == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Grade.class.getSimpleName()));
        }
        if (!Grade.isValidGrade(grade.value)) {
            throw new IllegalValueException(Credit.MESSAGE_CREDIT_CONSTRAINTS);
        }
        final Grade modelGrade = new Grade(grade.value);

        return new Module(modelCode, modelYear, modelSemester, modelCredit, modelGrade);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedModule)) {
            return false;
        }

        XmlAdaptedModule otherModule = (XmlAdaptedModule) other;
        return Objects.equals(code, otherModule.code)
                && Objects.equals(year, otherModule.year)
                && Objects.equals(semester, otherModule.semester)
                && Objects.equals(credit, otherModule.credit)
                && grade.equals(otherModule.grade);
    }
}
