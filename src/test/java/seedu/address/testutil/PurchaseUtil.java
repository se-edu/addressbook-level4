package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PURCHASENAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

//import java.util.Set;

import seedu.address.logic.commands.AddPurchaseCommand;
import seedu.address.model.purchase.Purchase;
//import seedu.address.model.tag.Tag;

/**
 * A utility class for Purchase.
 */
public class PurchaseUtil {

    /**
     * Returns an add purchase command string for adding the {@code purchase}.
     */
    public static String getAddPurchaseCommand(Purchase purchase) {
        return AddPurchaseCommand.COMMAND_WORD + " " + getPurchaseDetails(purchase);
    }

    /**
     * Returns the part of command string for the given {@code purchase}'s details.
     */
    public static String getPurchaseDetails(Purchase purchase) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_PURCHASENAME + purchase.getPurchaseName().fullName + " ");
        sb.append(PREFIX_PRICE + purchase.getPrice().value + " ");
        purchase.getTags().stream().forEach(
                s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }


}
