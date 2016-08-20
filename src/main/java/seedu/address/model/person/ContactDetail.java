package seedu.address.model.person;

/**
 * Represents a person's contact detail. Can be marked private for hiding self from display.
 */
public abstract class ContactDetail {

    private boolean isPrivate;

    public ContactDetail(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public boolean isVisible() {
        return !isPrivate;
    }

    public void makePrivate() {
        isPrivate = true;
    }

    public void makeVisible() {
        isPrivate = false;
    }
}
