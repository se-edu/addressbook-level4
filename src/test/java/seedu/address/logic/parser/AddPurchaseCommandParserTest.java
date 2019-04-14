package seedu.address.logic.parser;

/*import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PRICE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PURCHASENAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.PRICE_DESC_MOVIE;
import static seedu.address.logic.commands.CommandTestUtil.PRICE_DESC_PRAWNMEE;
import static seedu.address.logic.commands.CommandTestUtil.PURCHASENAME_DESC_MOVIE;
import static seedu.address.logic.commands.CommandTestUtil.PURCHASENAME_DESC_PRAWNMEE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_ENTERTAINMENT;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FAMILY;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FOOD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_MOVIE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PURCHASENAME_MOVIE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_ENTERTAINMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FAMILY;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPurchases.MOVIE;
import static seedu.address.testutil.TypicalPurchases.PRAWNMEE;

import org.junit.Test;

import seedu.address.logic.commands.AddPurchaseCommand;
import seedu.address.model.purchase.Price;
import seedu.address.model.purchase.Purchase;
import seedu.address.model.purchase.PurchaseName;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PurchaseBuilder;

*/

public class AddPurchaseCommandParserTest {
   /*
    private AddPurchaseCommandParser parser = new AddPurchaseCommandParser();

 /*
    @Test
    public void parse_allFieldsPresent_success() {
        Purchase expectedPurchase = new PurchaseBuilder(MOVIE).withTags(VALID_TAG_ENTERTAINMENT).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + PURCHASENAME_DESC_MOVIE + PRICE_DESC_MOVIE
                + TAG_DESC_ENTERTAINMENT, new AddPurchaseCommand(expectedPurchase));

        // multiple purchasenames - last name accepted
        assertParseSuccess(parser, PURCHASENAME_DESC_PRAWNMEE + PURCHASENAME_DESC_MOVIE + PRICE_DESC_MOVIE
                + TAG_DESC_ENTERTAINMENT, new AddPurchaseCommand(expectedPurchase));

        // multiple prices - last price accepted
        assertParseSuccess(parser, PURCHASENAME_DESC_MOVIE + PRICE_DESC_PRAWNMEE + PRICE_DESC_MOVIE
                + TAG_DESC_ENTERTAINMENT, new AddPurchaseCommand(expectedPurchase));

        // multiple tags - all accepted
        Purchase expectedPurchaseMultipleTags = new PurchaseBuilder(MOVIE)
                .withTags(VALID_TAG_ENTERTAINMENT, VALID_TAG_FAMILY).build();
        assertParseSuccess(parser, PURCHASENAME_DESC_MOVIE + PRICE_DESC_MOVIE
                + TAG_DESC_ENTERTAINMENT + TAG_DESC_FAMILY, new AddPurchaseCommand(expectedPurchaseMultipleTags));
    }
*/

    /*
    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Purchase expectedPurchase = new PurchaseBuilder(PRAWNMEE).withTags().build();
        assertParseSuccess(parser, PURCHASENAME_DESC_PRAWNMEE + PRICE_DESC_PRAWNMEE,
                new AddPurchaseCommand(expectedPurchase));
    }
    */

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPurchaseCommand.MESSAGE_USAGE);

        // missing purchasename prefix
        assertParseFailure(parser, VALID_PURCHASENAME_MOVIE + PRICE_DESC_MOVIE,
                expectedMessage);

        // missing price prefix
        assertParseFailure(parser, PURCHASENAME_DESC_MOVIE + VALID_PRICE_MOVIE,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_PURCHASENAME_MOVIE + VALID_PRICE_MOVIE,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid purchasename
        assertParseFailure(parser, INVALID_PURCHASENAME_DESC + PRICE_DESC_MOVIE
                + TAG_DESC_ENTERTAINMENT + TAG_DESC_FAMILY, PurchaseName.MESSAGE_CONSTRAINTS);

        // invalid price
        assertParseFailure(parser, PURCHASENAME_DESC_MOVIE + INVALID_PRICE_DESC
                + TAG_DESC_FAMILY + TAG_DESC_ENTERTAINMENT, Price.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, PURCHASENAME_DESC_MOVIE + PRICE_DESC_MOVIE
                + INVALID_TAG_DESC + VALID_TAG_ENTERTAINMENT, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_PURCHASENAME_DESC + INVALID_PRICE_DESC,
                PurchaseName.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + PURCHASENAME_DESC_MOVIE + PRICE_DESC_MOVIE
                        + TAG_DESC_ENTERTAINMENT + TAG_DESC_FOOD,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPurchaseCommand.MESSAGE_USAGE));
    }*/
}
