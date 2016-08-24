package seedu.address.storage;

import seedu.address.commons.Utils;
import seedu.address.exceptions.IllegalValueException;
import seedu.address.model.Tag;

import javax.xml.bind.annotation.XmlValue;

/**
 * JAXB-friendly adapted tag data holder class.
 */
public class AdaptedTag {

    @XmlValue
    public String tagName;

    /**
     * No-arg constructor for JAXB use.
     */
    public AdaptedTag() {}

    /**
     * Converts a given Tag into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created AdaptedTag
     */
    public AdaptedTag(Tag source) {
        tagName = source.tagName;
    }

    /**
     * Checks whether any required element is missing.
     *
     * JAXB does not enforce (required = true) without a given XML schema.
     * Since we do most of our validation using the model class constructors, the only extra logic we need
     * is to ensure that every xml element in the document is present. JAXB sets missing elements as null,
     * so we check for that.
     */
    public boolean isAnyRequiredFieldMissing() {
        return Utils.isAnyNull(tagName);
    }

    /**
     * Converts this jaxb-friendly adapted tag object into the model's Tag object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Tag toModelType() throws IllegalValueException {
        return new Tag(tagName);
    }
}
