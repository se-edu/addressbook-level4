package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
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

    /**
     * Returns the specified index in the {@code command}.
     * @throws IllegalValueException if the specified index is invalid (not positive unsigned integer).
     */
    public static int parseIndex(String command) throws IllegalValueException {
        String index = command.trim();
        if (!StringUtil.isUnsignedInteger(index)) {
            throw new IllegalValueException("Index is not a positive unsigned integer.");
        }
        return Integer.parseInt(index);

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
     * Parses a {@code List<String> phones} into a {@code List<Phone>}.
     */
    public static List<Phone> parsePhones(List<String> phones) throws IllegalValueException {
        requireNonNull(phones);
        final List<Phone> phoneList = new ArrayList<>();
        for (String phone : phones) {
            phoneList.add(new Phone(phone));
        }
        return phoneList;
    }

    /**
     * Parses a {@code Optional<String> address} into an {@code Optional<Address>} if {@code address} is present.
     */
    public static Optional<Address> parseAddress(Optional<String> address) throws IllegalValueException {
        assert address != null;
        return address.isPresent() ? Optional.of(new Address(address.get())) : Optional.empty();
    }

    /**
     * Parses a {@code List<String> addresses} into a {@code List<Address>}.
     */
    public static List<Address> parseAddresses(List<String> addresses) throws IllegalValueException {
        requireNonNull(addresses);
        final List<Address> addressList = new ArrayList<>();
        for (String address : addresses) {
            addressList.add(new Address(address));
        }
        return addressList;
    }

    /**
     * Parses a {@code Optional<String> email} into an {@code Optional<Email>} if {@code email} is present.
     */
    public static Optional<Email> parseEmail(Optional<String> email) throws IllegalValueException {
        assert email != null;
        return email.isPresent() ? Optional.of(new Email(email.get())) : Optional.empty();
    }

    /**
     * Parses a {@code List<String> emails} into a {@code List<Email>}.
     */
    public static List<Email> parseEmails(List<String> emails) throws IllegalValueException {
        requireNonNull(emails);
        final List<Email> emailList = new ArrayList<>();
        for (String email : emails) {
            emailList.add(new Email(email));
        }
        return emailList;
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
