package seedu.address.logic.parser;

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

    public static final String MESSAGE_INSUFFICIENT_PARTS = "Number of parts must be more than 1.";

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
    * Splits the {@code string} into {@code numOfParts} parts, using whitespace as a delimiter.
    * Leading and trailing whitespaces will be trimmed.
    * {@code Optional.empty()} objects are appended as 'fillers' if the total number of parts after
    * splitting {@code string} is fewer than {@code numOfParts}.<br>
    * Examples:
    * <pre>
    *     split("  Hello World! ", 2) -> "Hello" and "World!"
    *     split(" Hello    World!", 3) -> "Hello" and "World!" and Optional.empty()
    *     split("Foo bar baz", 2) -> "Foo" and "bar baz" // only 2 fields
    * </pre>
    * @return A list of size {@code numOfParts} containing the resultant parts in the order they
    *         appeared in the input followed by {@code Optional.empty()} objects (if any).
    * @throws IllegalArgumentException if {@code numOfParts} < 2
    */
    public static List<Optional<String>> split(String string, int numOfParts) throws IllegalArgumentException {
        if (numOfParts < 2) {
            throw new IllegalArgumentException(MESSAGE_INSUFFICIENT_PARTS);
        }
        return Arrays.stream(Arrays.copyOf(string.trim().split("\\s+", numOfParts), numOfParts))
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
}
