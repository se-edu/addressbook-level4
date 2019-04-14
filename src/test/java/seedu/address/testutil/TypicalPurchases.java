package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_MOVIE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_PRAWNMEE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PURCHASENAME_MOVIE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PURCHASENAME_PRAWNMEE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_ENTERTAINMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FOOD;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.ExpenditureList;
import seedu.address.model.purchase.Purchase;

/**
 * A utility class containing a list of {@code Purchase} objects to be used in tests.
 */
public class TypicalPurchases {
    public static final Purchase MCFLURRY = new PurchaseBuilder().withPurchaseName("Oreo mcflurry")
            .withPrice("2.30").withTags("snacks").build();
    public static final Purchase SUBWAY = new PurchaseBuilder().withPurchaseName("6inch cold cut trio")
            .withPrice("7.90").withTags("food").build();
    public static final Purchase KARAOKE = new PurchaseBuilder().withPurchaseName("Karaoke at katong")
            .withPrice("18.50").withTags("entertainment").build();

    // Manually added
    public static final Purchase CHICKENRICE = new PurchaseBuilder().withPurchaseName("Deck chicken rice")
            .withPrice("2.50").withTags("food").build();
    public static final Purchase MILKTEA = new PurchaseBuilder().withPurchaseName("Gongcha milk tea")
            .withPrice("3.90").withTags("drink").build();

    // Manually added - Purchase's details found in {@code CommandTestUtil}
    public static final Purchase PRAWNMEE = new PurchaseBuilder().withPurchaseName(VALID_PURCHASENAME_PRAWNMEE)
            .withPrice(VALID_PRICE_PRAWNMEE).withTags(VALID_TAG_FOOD).build();
    public static final Purchase MOVIE = new PurchaseBuilder().withPurchaseName(VALID_PURCHASENAME_MOVIE)
            .withPrice(VALID_PRICE_MOVIE).withTags(VALID_TAG_ENTERTAINMENT).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPurchases() {} // prevents instantiation

    /**
     * Returns an {@code ExpenditureList} with all the typical purchases.
     */
    public static ExpenditureList getTypicalExpenditureList() {
        ExpenditureList expl = new ExpenditureList();
        for (Purchase purchase : getTypicalPurchases()) {
            expl.addPurchase(purchase);
        }
        return expl;
    }

    public static List<Purchase> getTypicalPurchases() {
        return new ArrayList<>(Arrays.asList(MCFLURRY, SUBWAY, KARAOKE));
    }
}

