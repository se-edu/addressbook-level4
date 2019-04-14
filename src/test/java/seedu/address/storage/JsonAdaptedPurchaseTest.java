package seedu.address.storage;

import static seedu.address.storage.JsonAdaptedPurchase.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalPurchases.SUBWAY;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.purchase.Price;
import seedu.address.model.purchase.PurchaseName;
import seedu.address.testutil.Assert;

public class JsonAdaptedPurchaseTest {
    private static final String INVALID_PURCHASENAME = "Ice cream!";
    private static final String INVALID_PRICE = "$1.50";
    private static final String INVALID_TAG = "snacks!";

    private static final String VALID_PURCHASENAME = SUBWAY.getPurchaseName().toString();
    private static final String VALID_PRICE = SUBWAY.getPrice().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = SUBWAY.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());



    @Test
    public void toModelType_invalidPurchaseName_throwsIllegalValueException() {
        JsonAdaptedPurchase purchase =
                new JsonAdaptedPurchase(INVALID_PURCHASENAME, VALID_PRICE, VALID_TAGS);
        String expectedMessage = PurchaseName.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, purchase::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPurchase purchase = new JsonAdaptedPurchase(null, VALID_PRICE, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, PurchaseName.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, purchase::toModelType);
    }

    @Test
    public void toModelType_invalidPrice_throwsIllegalValueException() {
        JsonAdaptedPurchase purchase =
                new JsonAdaptedPurchase(VALID_PURCHASENAME, INVALID_PRICE, VALID_TAGS);
        String expectedMessage = Price.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, purchase::toModelType);
    }

    @Test
    public void toModelType_nullPrice_throwsIllegalValueException() {
        JsonAdaptedPurchase purchase = new JsonAdaptedPurchase(VALID_PURCHASENAME, null, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Price.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, purchase::toModelType);
    }


    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedPurchase purchase =
                new JsonAdaptedPurchase(VALID_PURCHASENAME, VALID_PRICE, invalidTags);
        Assert.assertThrows(IllegalValueException.class, purchase::toModelType);
    }
}
