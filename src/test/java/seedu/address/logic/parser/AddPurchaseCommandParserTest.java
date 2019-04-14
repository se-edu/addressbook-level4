package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PRICE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PURCHASENAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PRICE_DESC_MOVIE;
import static seedu.address.logic.commands.CommandTestUtil.PURCHASENAME_DESC_MOVIE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_ENTERTAINMENT;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FAMILY;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FOOD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_MOVIE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PURCHASENAME_MOVIE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_ENTERTAINMENT;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.logic.commands.AddPurchaseCommand;
import seedu.address.model.purchase.Price;
import seedu.address.model.purchase.PurchaseName;
import seedu.address.model.tag.Tag;



public class AddPurchaseCommandParserTest {

    private AddPurchaseCommandParser parser = new AddPurchaseCommandParser();

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
    }
}
