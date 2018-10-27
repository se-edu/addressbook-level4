package seedu.address.storage;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyTranscript;
import seedu.address.model.Transcript;
import seedu.address.model.module.Module;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An Immutable Transcript that is serializable to XML format
 */
@XmlRootElement(name = "transcript")
public class XmlSerializableTranscript {

    public static final String MESSAGE_DUPLICATE_MODULE = "Module list contains duplicate module(s).";

    @XmlElement
    private List<XmlAdaptedModule> modules;

    /**
     * Creates an empty XmlSerializableAddressBook.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableTranscript() {
        modules = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableTranscript(ReadOnlyTranscript src) {
        this();
        modules.addAll(src.getModuleList().stream().map(XmlModulePerson::new).collect(Collectors.toList()));
    }

    /**
     * Converts this transcript into the model's {@code Transcript} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedPerson}.
     */
    public Transcript toModelType() throws IllegalValueException {
        Transcript tra = new Transcript();
        for (XmlAdaptedModule m : modules) {
            Module module = m.toModelType();
            if (tra.hasModule(module)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_MODULE);
            }
            tra.addModule(module);
        }
        return tra;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableTranscript)) {
            return false;
        }
        return modules.equals(((XmlSerializableTranscript) other).modules);
    }
}
