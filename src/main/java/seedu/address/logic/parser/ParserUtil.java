package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DURATION;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.ChangeCommand;
import seedu.address.logic.parser.exceptions.SameTimeUnitException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.exceptions.DurationParseException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tutee.EducationLevel;
import seedu.address.model.tutee.Grade;
import seedu.address.model.tutee.School;
import seedu.address.model.tutee.Subject;

import java.time.format.DateTimeParseException;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 * {@code ParserUtil} contains methods that take in {@code Optional} as parameters. However, it goes against Java's
 * convention (see https://stackoverflow.com/a/39005452) as {@code Optional} should only be used a return type.
 * Justification: The methods in concern receive {@code Optional} return values from other methods as parameters and
 * return {@code Optional} values based on whether the parameters were present. Therefore, it is redundant to unwrap the
 * initial {@code Optional} before passing to {@code ParserUtil} as a parameter and then re-wrap it into an
 * {@code Optional} return value inside {@code ParserUtil} methods.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INSUFFICIENT_PARTS = "Number of parts must be more than 1.";
    public static final int INDEX_DIFFERENCE = 1;

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws IllegalValueException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws IllegalValueException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new IllegalValueException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws IllegalValueException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code Optional<String> name} into an {@code Optional<Name>} if {@code name} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Name> parseName(Optional<String> name) throws IllegalValueException {
        requireNonNull(name);
        return name.isPresent() ? Optional.of(parseName(name.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws IllegalValueException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new IllegalValueException(Phone.MESSAGE_PHONE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code Optional<String> phone} into an {@code Optional<Phone>} if {@code phone} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Phone> parsePhone(Optional<String> phone) throws IllegalValueException {
        requireNonNull(phone);
        return phone.isPresent() ? Optional.of(parsePhone(phone.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws IllegalValueException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new IllegalValueException(Address.MESSAGE_ADDRESS_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code Optional<String> address} into an {@code Optional<Address>} if {@code address} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Address> parseAddress(Optional<String> address) throws IllegalValueException {
        requireNonNull(address);
        return address.isPresent() ? Optional.of(parseAddress(address.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws IllegalValueException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new IllegalValueException(Email.MESSAGE_EMAIL_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code Optional<String> email} into an {@code Optional<Email>} if {@code email} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Email> parseEmail(Optional<String> email) throws IllegalValueException {
        requireNonNull(email);
        return email.isPresent() ? Optional.of(parseEmail(email.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws IllegalValueException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new IllegalValueException(Tag.MESSAGE_TAG_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws IllegalValueException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    //@@author ChoChihTun
    /**
     * Parses a {@code String subject} into an {@code Subject}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code subject} is invalid.
     */
    public static Subject parseSubject(String subject) throws IllegalValueException {
        requireNonNull(subject);
        String trimmedSubject = subject.trim();
        if (!Subject.isValidSubject(trimmedSubject)) {
            throw new IllegalValueException(Subject.MESSAGE_SUBJECT_CONSTRAINTS);
        }
        return new Subject(trimmedSubject);
    }

    /**
     * Parses a {@code Optional<String> subject} into an {@code Optional<Subject>} if {@code subject} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Subject> parseSubject(Optional<String> subject) throws IllegalValueException {
        requireNonNull(subject);
        return subject.isPresent() ? Optional.of(parseSubject(subject.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String educationLevel} into an {@code EducationLevel}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code educationLevel} is invalid.
     */
    public static EducationLevel parseEducationLevel(String educationLevel) throws IllegalValueException {
        requireNonNull(educationLevel);
        String trimmedEducationLevel = educationLevel.trim();
        if (!EducationLevel.isValidEducationLevel(trimmedEducationLevel)) {
            throw new IllegalValueException(EducationLevel.MESSAGE_EDUCATION_LEVEL_CONSTRAINTS);
        }
        return new EducationLevel(trimmedEducationLevel);
    }

    /**
     * Parses a {@code Optional<String> educationLevel} into an {@code Optional<EducationLevel>}
     * if {@code educationLevel} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<EducationLevel> parseEducationLevel(Optional<String> educationLevel)
            throws IllegalValueException {
        requireNonNull(educationLevel);
        return educationLevel.isPresent() ? Optional.of(parseEducationLevel(educationLevel.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String school} into an {@code School}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code school} is invalid.
     */
    public static School parseSchool(String school) throws IllegalValueException {
        requireNonNull(school);
        String trimmedSchool = school.trim();
        if (!School.isValidSchool(trimmedSchool)) {
            throw new IllegalValueException(School.MESSAGE_SCHOOL_CONSTRAINTS);
        }
        return new School(trimmedSchool);
    }

    /**
     * Parses a {@code Optional<String> school} into an {@code Optional<School>} if {@code school} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<School> parseSchool(Optional<String> school) throws IllegalValueException {
        requireNonNull(school);
        return school.isPresent() ? Optional.of(parseSchool(school.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String grade} into an {@code Grade}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code grade} is invalid.
     */
    public static Grade parseGrade(String grade) throws IllegalValueException {
        requireNonNull(grade);
        String trimmedGrade = grade.trim();
        if (!Grade.isValidGrade(trimmedGrade)) {
            throw new IllegalValueException(Grade.MESSAGE_GRADE_CONSTRAINTS);
        }
        return new Grade(trimmedGrade);
    }

    /**
     * Parses a {@code Optional<String> grade} into an {@code Optional<Grade>} if {@code grade} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Grade> parseGrade(Optional<String> grade) throws IllegalValueException {
        requireNonNull(grade);
        return grade.isPresent() ? Optional.of(parseGrade(grade.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String timeUnit} into an {@code String} and returns it.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code timeUnit} is invalid.
     */
    public static String parseTimeUnit(String timeUnit) throws IllegalValueException, SameTimeUnitException {
        requireNonNull(timeUnit);
        String trimmedTimeUnit = timeUnit.trim();
        if (!ChangeCommandParser.isValidTimeUnit(trimmedTimeUnit)) {
            throw new IllegalValueException(ChangeCommand.MESSAGE_CONSTRAINT);
        }
        if (ChangeCommandParser.isTimeUnitClash(trimmedTimeUnit)) {
            throw new SameTimeUnitException(ChangeCommand.MESSAGE_SAME_VIEW);
        }
        return trimmedTimeUnit;
    }

    /**
     * Parses a {@code String dateTime} into an {@code LocalDateTime}.
     *
     * @throws DateTimeParseException if the given {@code stringDateTime} is invalid.
     */
    public static LocalDateTime parseDateTime(String stringDateTime) throws DateTimeParseException {
        requireNonNull(stringDateTime);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu HH:mm")
                .withResolverStyle(ResolverStyle.STRICT);
        return LocalDateTime.parse(stringDateTime, formatter);
    }

    /**
     * Checks if the given duration is valid.
     *
     * @throws DurationParseException if the given {@code duration} is invalid.
     */
    public static String parseDuration(String duration) throws DurationParseException {
        requireNonNull(duration);
        String durationValidationRegex = "([0-9]|1[0-9]|2[0-3])h([0-5][0-9]|[0-9])m";
        if (!duration.matches(durationValidationRegex)) {
            throw new DurationParseException(MESSAGE_INVALID_DURATION);
        }
        return duration;
    }

    /**
     * Returns the description if it exists in the user input.
     * Returns empty string otherwise.
     */
    public static String parseDescription(String[] userInputs, int maximumParametersGiven) {
        if (isEmptyDescription(userInputs, maximumParametersGiven)) {
            return "";
        } else {
            int descriptionIndex = maximumParametersGiven - INDEX_DIFFERENCE;
            String description = userInputs[descriptionIndex];
            return description;
        }
    }

    /**
     * Returns true if a given task contains a description.
     */
    private static boolean isEmptyDescription(String[] arguments, int maximumParameterssGiven) {
        return arguments.length < maximumParameterssGiven;
    }
}
