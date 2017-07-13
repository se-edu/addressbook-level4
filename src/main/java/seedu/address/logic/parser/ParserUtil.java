package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
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

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INSUFFICIENT_PARTS = "Number of parts must be more than 1.";

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
     * Returns the parsed {@code name} as an {@code Optional<Name>} if {@code name} is not null.
     * Else, returns {@code Optional.empty()}.
     */
    public static Optional<Name> parseName(String name) throws IllegalValueException {
        return name != null ? Optional.of(new Name(name)) : Optional.empty();
    }

    /**
     * Returns the parsed {@code phone} as an {@code Optional<Phone>} if {@code phone} is not null.
     * Else, returns {@code Optional.empty()}.
     */
    public static Optional<Phone> parsePhone(String phone) throws IllegalValueException {
        return phone != null ? Optional.of(new Phone(phone)) : Optional.empty();
    }

    /**
     * Returns the parsed {@code address} as an {@code Optional<Address>} if {@code address} is not null.
     * Else, returns {@code Optional.empty()}.
     */
    public static Optional<Address> parseAddress(String address) throws IllegalValueException {
        return address != null ? Optional.of(new Address(address)) : Optional.empty();
    }

    /**
     * Returns the parsed {@code email} as an {@code Optional<Email>} if {@code email} is not null.
     * Else, returns {@code Optional.empty()}.
     */
    public static Optional<Email> parseEmail(String email) throws IllegalValueException {
        return email != null ? Optional.of(new Email(email)) : Optional.empty();
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws IllegalValueException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        return tagSet;
    }
}
