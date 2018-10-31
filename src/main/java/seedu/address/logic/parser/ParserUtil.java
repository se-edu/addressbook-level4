package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.module.Code;
import seedu.address.model.module.Credit;
import seedu.address.model.module.Grade;
import seedu.address.model.module.Semester;
import seedu.address.model.module.Year;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    public static final String PREFIX = "-";

    //@@author alexkmj
    /**
     * Tokenizes args into an array of args. Checks if args is null and trims leading and trailing
     * whitespaces.
     *
     * @param args the target args that would be tokenize
     * @return array of args
     */
    public static String[] tokenize(String args) {
        requireNonNull(args);
        String trimmedArgs = args.trim();
        return trimmedArgs.split("\\s+");
    }

    //@@author alexkmj
    /**
     * Validates the number of arguments. If number of arguments does not
     * equal to {@code required}, {@code ParseException} will be thrown.
     *
     * @throws ParseException if the number of arguments is invalid
     */
    public static void validateNumOfArgs(Object[] args, int required)
            throws ParseException {
        requireNonNull(args);

        validateNumOfArgs(args, required, required);
    }

    //@@author alexkmj
    /**
     * Validates the number of arguments. If number of arguments is not within
     * the bounds, {@code ParseException} will be thrown.
     *
     * @throws ParseException if the number of arguments is invalid
     */
    public static void validateNumOfArgs(Object[] args, int min, int max)
            throws ParseException {
        requireNonNull(args);

        if (args.length < min || args.length > max) {
            throw new ParseException("Invalid number of arguments!"
                    + "Number of arguments should be more than or equal to "
                    + min
                    + " and less than or equal to "
                    + max);
        }
    }

    //@@author alexkmj
    /**
     * Validates the number of arguments. If number of arguments is not within
     * the {@code setOfAllowedNumOfArgs}, {@code ParseException} is thrown.
     *
     * @throws ParseException if the number of arguments is not within the
     * {@code setOfAllowedNumOfArgs}
     */
    public static void validateNumOfArgs(Object[] args,
            HashSet<Integer> setOfAllowedNumOfArgs) throws ParseException {
        requireNonNull(args);

        if (!setOfAllowedNumOfArgs.contains(args.length)) {
            String allowedNumOfArgs = setOfAllowedNumOfArgs.stream()
                    .map(Objects::toString)
                    .collect(Collectors.joining(", "));

            throw new ParseException("Invalid number of arguments!"
                    + "Number of arguments should be "
                    + allowedNumOfArgs);
        }
    }

    //@@author alexkmj
    /**
     * Maps arguments according to their prefixes.
     */
    public static Map<String, String> mapArgs(String[] args) {
        Map<String, String> argsMap = new HashMap<>();

        for (int index = 0; index < args.length; index++) {
            if (args[index].startsWith(PREFIX)
                    && (index + 1 < args.length)) {
                String key = args[index].substring(1);
                String value = args[++index];

                argsMap.put(key, value);
            }
        }

        return argsMap;
    }

    //@@author alexkmj
    /**
     * Parses a {@code String code} into a {@code Code}. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the given {@code code} is invalid.
     */
    public static Code parseCode(String args) throws ParseException {
        requireNonNull(args);
        String trimmedCode = args.trim();
        if (!Code.isValidCode(trimmedCode)) {
            throw new ParseException(Code.MESSAGE_CODE_CONSTRAINTS);
        }
        return new Code(trimmedCode);
    }

    //@@author alexkmj
    /**
     * Parses a {@code String year} into a {@code Year}. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the given {@code year} is invalid.
     */
    public static Year parseYear(String args) throws ParseException {
        requireNonNull(args);
        String trimmedYear = args.trim();
        if (!Year.isValidYear(trimmedYear)) {
            throw new ParseException(Year.MESSAGE_YEAR_CONSTRAINTS);
        }
        return new Year(trimmedYear);
    }

    //@@author alexkmj
    /**
     * Parses a {@code String semester} into a {@code Semester}. Leading and trailing whitespaces
     * will be trimmed.
     *
     * @throws ParseException if the given {@code semester} is invalid.
     */
    public static Semester parseSemester(String args) throws ParseException {
        requireNonNull(args);
        String trimmedSemester = args.trim();
        if (!Semester.isValidSemester(trimmedSemester)) {
            throw new ParseException(Semester.MESSAGE_SEMESTER_CONSTRAINTS);
        }
        return new Semester(trimmedSemester);
    }

    //@@author alexkmj
    /**
     * Parses a {@code String credit} into a {@code Credit}. Leading and trailing whitespaces will
     * be trimmed.
     *
     * @throws ParseException if the given {@code credit} is invalid.
     */
    public static Credit parseCredit(String args) throws ParseException {
        requireNonNull(args);
        String trimmedCredit = args.trim();
        int intCredit = Integer.parseInt(trimmedCredit);
        if (!Credit.isValidCredit(intCredit)) {
            throw new ParseException(Credit.MESSAGE_CREDIT_CONSTRAINTS);
        }
        return new Credit(intCredit);
    }

    //@@author alexkmj
    /**
     * Parses a {@code String grade} into a {@code Grade}. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the given {@code grade} is invalid.
     */
    public static Grade parseGrade(String args) throws ParseException {
        requireNonNull(args);
        String trimmedGrade = args.trim();
        if (!Grade.isValidGrade(trimmedGrade)) {
            throw new ParseException(Grade.MESSAGE_GRADE_CONSTRAINTS);
        }
        return new Grade(trimmedGrade);
    }

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing
     * whitespaces will be trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_PHONE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}. Leading and trailing whitespaces
     * will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_ADDRESS_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_EMAIL_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_TAG_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }
}
