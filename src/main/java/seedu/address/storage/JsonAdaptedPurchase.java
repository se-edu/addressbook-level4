package seedu.address.storage;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.purchase.PurchaseName;
import seedu.address.model.purchase.Purchase;
import seedu.address.model.purchase.Price;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Purchase}.
 */

public class JsonAdaptedPurchase {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Purchase's %s field is missing!";

    private final String name;
    private final String price;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPurchase} with the given purchase details.
     */
    @JsonCreator
    public JsonAdaptedPurchase(@JsonProperty("name") String name, @JsonProperty("price") String price,
                             @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.name = name;
        this.price = price;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Purchase} into this class for Jackson use.
     */
    public JsonAdaptedPurchase(Purchase source) {
        name = source.getPurchaseName().fullName;
        price = source.getPrice().value;
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted purchase object into the model's {@code Purchase} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted purchase.
     */
    public Purchase toModelType() throws IllegalValueException {
        final List<Tag> purchaseTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            purchaseTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, PurchaseName.class.getSimpleName()));
        }
        if (!PurchaseName.isValidName(name)) {
            throw new IllegalValueException(PurchaseName.MESSAGE_CONSTRAINTS);
        }
        final PurchaseName modelName = new PurchaseName(name);

        if (price == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Price.class.getSimpleName()));
        }
        if (!Price.isValidPrice(price)) {
            throw new IllegalValueException(Price.MESSAGE_CONSTRAINTS);
        }
        final Price modelPrice = new Price(price);




        final Set<Tag> modelTags = new HashSet<>(purchaseTags);
        return new Purchase(modelName, modelPrice, modelTags);
    }

}
