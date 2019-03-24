package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ExpenditureList;
import seedu.address.model.ReadOnlyExpenditureList;
import seedu.address.model.purchase.Purchase;

/**
 * An Immutable ExpenditureList that is serializable to JSON format.
 */
@JsonRootName(value = "expenditurelist")
public class JsonSerializableExpenditureList {
    private final List<JsonAdaptedPurchase> purchases = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableExpenditureList} with the given purchases.
     */
    @JsonCreator
    public JsonSerializableExpenditureList(@JsonProperty("purchases") List<JsonAdaptedPurchase> purchases) {
        this.purchases.addAll(purchases);
    }

    /**
     * Converts a given {@code ReadOnlyExpenditureList} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableExpenditureList}.
     */
    public JsonSerializableExpenditureList(ReadOnlyExpenditureList source) {
        purchases.addAll(source.getPurchaseList().stream().map(JsonAdaptedPurchase::new).collect(Collectors.toList()));
    }

    /**
     * Converts this expenditure list into the model's {@code ExpenditureList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public ExpenditureList toModelType() throws IllegalValueException {
        ExpenditureList expenditureList = new ExpenditureList();
        for (JsonAdaptedPurchase jsonAdaptedPurchase : purchases) {
            Purchase purchase= jsonAdaptedPurchase.toModelType();
            expenditureList.addPurchase(purchase);
        }
        return expenditureList;
    }
}
