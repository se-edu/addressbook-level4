package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TO_CLASS;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes
 */
public class ParserUtil {

    public static final String MESSAGE_MULTIPLE_VALUES_WARNING = "Warning: Multiple %1$s values entered. "
            + "Only the last instance of %1$s has been stored.";

    /**
     * Parses {@code index} into an integer and returns it. Leading and trailing whitespaces will be trimmed.
     * @throws IllegalValueException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static int parseIndex(String index) throws IllegalValueException {
        String trimmedIndex = index.trim();
        if (!StringUtil.isUnsignedInteger(trimmedIndex)) {
            throw new IllegalValueException("Index is not a non-zero unsigned integer.");
        }
        return Integer.parseInt(trimmedIndex);

    }

    /**
    * Splits a preamble string into ordered fields.
    * @return A list of size {@code numFields} where the ith element is the ith field value if specified in
    *         the input, {@code Optional.empty()} otherwise.
    */
    public static List<Optional<String>> splitPreamble(String preamble, int numFields) {
        return Arrays.stream(Arrays.copyOf(preamble.split("\\s+", numFields), numFields))
                .map(Optional::ofNullable)
                .collect(Collectors.toList());
    }

    /**
     * Parses a {@code Optional<String> name} into an {@code Optional<Name>} if {@code name} is present.
     */
    public static Optional<Name> parseName(Optional<String> name) throws IllegalValueException {
        assert name != null;
        return name.isPresent() ? Optional.of(new Name(name.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> phone} into an {@code Optional<Phone>} if {@code phone} is present.
     */
    public static Optional<Phone> parsePhone(Optional<String> phone) throws IllegalValueException {
        assert phone != null;
        return phone.isPresent() ? Optional.of(new Phone(phone.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> address} into an {@code Optional<Address>} if {@code address} is present.
     */
    public static Optional<Address> parseAddress(Optional<String> address) throws IllegalValueException {
        assert address != null;
        return address.isPresent() ? Optional.of(new Address(address.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> email} into an {@code Optional<Email>} if {@code email} is present.
     */
    public static Optional<Email> parseEmail(Optional<String> email) throws IllegalValueException {
        assert email != null;
        return email.isPresent() ? Optional.of(new Email(email.get())) : Optional.empty();
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws IllegalValueException {
        assert tags != null;
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        return tagSet;
    }

    /**
     * Generates a warning message for each field (represented by {@code prefixes}) that appears multiple times in
     * {@code argMultimap}. Returns {@code Optional.empty()} if there are no fields that appears multiple times.
     */
    public static Optional<String> getWarningMessage(ArgumentMultimap argMultimap, Prefix... prefixes) {
        requireNonNull(argMultimap);
        requireNonNull(prefixes);

        List<String> fieldsWithMultipleValues = getFieldsWithMultipleValues(argMultimap, prefixes);
        if (fieldsWithMultipleValues.isEmpty()) {
            return Optional.empty();
        }

        String joinedFields = (fieldsWithMultipleValues.size() == 1) ? fieldsWithMultipleValues.get(0)
                : StringUtil.joinStrings(fieldsWithMultipleValues);
        return Optional.of(String.format(MESSAGE_MULTIPLE_VALUES_WARNING, joinedFields));
    }

    /**
     * Returns a {@code List<String>} of fields (represented by {@code prefixes}) that appears multiple times in
     * {@code argMultimap}.
     */
    private static List<String> getFieldsWithMultipleValues(ArgumentMultimap argMultimap, Prefix... prefixes) {
        assert argMultimap != null;
        assert prefixes != null;

        return Arrays.stream(prefixes).filter(prefix -> argMultimap.getAllValues(prefix).size() > 1)
                .map(PREFIX_TO_CLASS::get).collect(Collectors.toList());
    }
}
