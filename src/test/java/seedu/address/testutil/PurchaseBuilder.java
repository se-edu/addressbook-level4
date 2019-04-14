package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.purchase.Price;
import seedu.address.model.purchase.Purchase;
import seedu.address.model.purchase.PurchaseName;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Purchase objects.
 */
public class PurchaseBuilder {
    public static final String DEFAULT_PURCHASENAME = "Oreo mcflurry";
    public static final String DEFAULT_PRICE = "2.30";
    private PurchaseName name;
    private Price price;
    private Set<Tag> tags;

    public PurchaseBuilder() {
        name = new PurchaseName(DEFAULT_PURCHASENAME);
        price = new Price(DEFAULT_PRICE);
        tags = new HashSet<>();
    }

    /**
     * Initializes the PurchaseBuilder with the data of {@code purchaseToCopy}.
     */
    public PurchaseBuilder(Purchase purchaseToCopy) {
        name = purchaseToCopy.getPurchaseName();
        price = purchaseToCopy.getPrice();
        tags = new HashSet<>(purchaseToCopy.getTags());
    }

    /**
     * Sets the {@code PurchaseName} of the {@code Purchase} that we are building.
     */
    public PurchaseBuilder withPurchaseName(String name) {
        this.name = new PurchaseName(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Purchase} that we are building.
     */
    public PurchaseBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Price} of the {@code Purchase} that we are building.
     */
    public PurchaseBuilder withPrice(String price) {
        this.price = new Price(price);
        return this;
    }

    public Purchase build() {
        return new Purchase(name, price, tags);
    }

}
