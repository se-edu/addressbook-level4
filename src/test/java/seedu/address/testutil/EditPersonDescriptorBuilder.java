package seedu.address.testutil;

import java.util.Arrays;
import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditPersonDescriptorBuilder {

    private EditPersonDescriptor descriptor;

    public EditPersonDescriptorBuilder() {
        descriptor = new EditPersonDescriptor();
    }

    public EditPersonDescriptorBuilder(EditPersonDescriptor descriptor) {
        this.descriptor = new EditPersonDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code person}'s details
     */
    public EditPersonDescriptorBuilder(ReadOnlyPerson person) throws IllegalValueException {
        descriptor = new EditPersonDescriptor();
        descriptor.setName(person.getName());
        descriptor.setPhone(person.getPhone());
        descriptor.setEmail(person.getEmail());
        descriptor.setAddress(person.getAddress());
        descriptor.setTags(person.getTags());
    }

    /**
     * Sets the name of the building EditPersonDescriptor.
     * @param name
     * @return the EditPersonDescriptorBuilder.
     * @throws IllegalValueException if given name string is invalid.
     */
    public EditPersonDescriptorBuilder withName(String name) throws IllegalValueException {
        ParserUtil.parseName(Optional.of(name)).ifPresent(descriptor::setName);
        return this;
    }

    /**
     * Sets the phone of the building EditPersonDescriptor.
     * @param phone
     * @return the EditPersonDescriptorBuilder.
     * @throws IllegalValueException if given phone string is invalid.
     */
    public EditPersonDescriptorBuilder withPhone(String phone) throws IllegalValueException {
        ParserUtil.parsePhone(Optional.of(phone)).ifPresent(descriptor::setPhone);
        return this;
    }

    /**
     * Sets the email of the building EditPersonDescriptor.
     * @param email
     * @return the EditPersonDescriptorBuilder.
     * @throws IllegalValueException if given email address string is invalid.
     */
    public EditPersonDescriptorBuilder withEmail(String email) throws IllegalValueException {
        ParserUtil.parseEmail(Optional.of(email)).ifPresent(descriptor::setEmail);
        return this;
    }

    /**
     * Sets the address of the building EditPersonDescriptor.
     * @param address
     * @return the EditPersonDescriptorBuilder.
     * @throws IllegalValueException if given address string is invalid.
     */
    public EditPersonDescriptorBuilder withAddress(String address) throws IllegalValueException {
        ParserUtil.parseAddress(Optional.of(address)).ifPresent(descriptor::setAddress);
        return this;
    }

    /**
     * Sets the tags of the building EditPersonDescriptor.
     * @param tags
     * @return the EditPersonDescriptorBuilder.
     * @throws IllegalValueException if any of the given tag name string is invalid.
     */
    public EditPersonDescriptorBuilder withTags(String... tags) throws IllegalValueException {
        descriptor.setTags(ParserUtil.parseTags(Arrays.asList(tags)));
        return this;
    }

    public EditPersonDescriptor build() {
        return descriptor;
    }
}
