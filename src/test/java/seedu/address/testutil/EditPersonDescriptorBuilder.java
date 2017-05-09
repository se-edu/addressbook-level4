package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Phone;

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

    public EditPersonDescriptorBuilder withName(String name) throws IllegalValueException {
        descriptor.setName(ParserUtil.parseName(Optional.of(name)));
        return this;
    }

    public EditPersonDescriptorBuilder withPhones(String... phones) throws IllegalValueException {
        List<Phone> phoneList = new ArrayList<>();
        for (String phone : phones) {
            phoneList.add(new Phone(phone));
        }
        descriptor.setPhones(phoneList);
        return this;
    }

    public EditPersonDescriptorBuilder withEmails(String... emails) throws IllegalValueException {
        List<Email> emailList = new ArrayList<>();
        for (String email : emails) {
            emailList.add(new Email(email));
        }
        descriptor.setEmails(emailList);
        return this;
    }

    public EditPersonDescriptorBuilder withAddresses(String... addresses) throws IllegalValueException {
        List<Address> addressList = new ArrayList<>();
        for (String address : addresses) {
            addressList.add(new Address(address));
        }
        descriptor.setAddresses(addressList);
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
