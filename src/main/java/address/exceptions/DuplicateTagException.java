package address.exceptions;

import address.model.datatypes.tag.Tag;

public class DuplicateTagException extends DuplicateDataException {

    private final Tag offender;

    public DuplicateTagException(Tag tag) {
        offender = tag;
    }

    @Override
    public String toString() {
        return "Duplicate tag not allowed: " + offender + " already exists!";
    }
}
