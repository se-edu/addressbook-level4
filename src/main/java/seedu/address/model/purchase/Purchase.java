package seedu.address.model.purchase;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Purchase in the expenditure list.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Purchase {
    private final PurchaseName name;
    private final Price price;

    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Purchase(PurchaseName name, Price price, Set<Tag> tags) {
        requireAllNonNull(name, price, tags);
        this.name = name;
        this.price = price;
        this.tags.addAll(tags);
    }

    public PurchaseName getPurchaseName() {
        return name;
    }

    public Price getPrice() {
        return price;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePurchase(Purchase otherPurchase) {
        if (otherPurchase == this) {
            return true;
        }

        return otherPurchase != null
                && otherPurchase.getPurchaseName().equals(getPurchaseName())
                && (otherPurchase.getPrice().equals(getPrice()));
    }


    /**
     * Returns true if both purchases have the same identity and data fields.
     * This defines a stronger notion of equality between two purchases.
     */
 /*   @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Purchase)) {
            return false;
        }

        Purchase otherPurchase = (Purchase) other;
        return otherPurchase.getPurchaseName().equals(getPurchaseName())
                && otherPurchase.getPrice().equals(getPrice())
                && otherPurchase.getTags().equals(getTags());
    }
*/
    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, price, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getPurchaseName())
                .append(" Price: $")
                .append(getPrice())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}
