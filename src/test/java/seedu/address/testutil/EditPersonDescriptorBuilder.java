package seedu.address.testutil;

import java.util.Arrays;
import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.parser.ParserUtil;

/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditPersonDescriptorBuilder {

    private EditPersonDescriptor descriptor;

    public EditPersonDescriptorBuilder() {
        descriptor = new EditPersonDescriptor();
    }

    public EditPersonDescriptorBuilder withName(String name) throws IllegalValueException {
        descriptor.setName(ParserUtil.parseName(Optional.of(name)));
        return this;
    }

    public EditPersonDescriptorBuilder withPhone(String phone) throws IllegalValueException {
        descriptor.setPhone(ParserUtil.parsePhone(Optional.of(phone)));
        return this;
    }

    public EditPersonDescriptorBuilder withEmail(String email) throws IllegalValueException {
        descriptor.setEmail(ParserUtil.parseEmail(Optional.of(email)));
        return this;
    }

    public EditPersonDescriptorBuilder withAddress(String address) throws IllegalValueException {
        descriptor.setAddress(ParserUtil.parseAddress(Optional.of(address)));
        return this;
    }

    public EditPersonDescriptorBuilder withTags(String... tags) throws IllegalValueException {
        descriptor.setTags(Optional.of(ParserUtil.parseTags(Arrays.asList(tags))));
        return this;
    }

    public EditPersonDescriptor build() {
        return descriptor;
    }
}
