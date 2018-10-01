package seedu.address.model;

/**
 * API for entity in Model such as Person and Group.
 */
public abstract class Entity {
    /**Returns true if both entity has the same identity fields.*/
    public abstract boolean isSame(Object other);
}
