package seedu.address.exceptions;

import seedu.address.model.Tag;

public class DuplicateTagException extends DuplicateDataException {

    private final Tag offender;

    public DuplicateTagException(Tag tag) {
        super("\"Duplicate tag not allowed: \" + offender + \" already exists!\"");
        offender = tag;
    }

    @Override
    public String toString() {
        return "Duplicate tag not allowed: " + offender + " already exists!";
    }
}
