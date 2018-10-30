package seedu.address.storage;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ledger.Account;
import seedu.address.model.ledger.DateLedger;
import seedu.address.model.ledger.Ledger;

import javax.xml.bind.annotation.XmlElement;
import java.util.Objects;

/**
 * JAXB-friendly version of the Ledger.
 */
public class XmlAdaptedLedger {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Ledger's %s field is missing!";

    @XmlElement(required = true)
    private String ledgerDate;
    @XmlElement(required = true)
    private String ledgerBalance;



    /**
     * Constructs an XmlAdaptedLedger.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedLedger() {}

    /**
     * Constructs an {@code XmlAdaptedLedger} with the given ledger details.
     */
    public XmlAdaptedLedger(String ledgerDate, String ledgerBalance) {
        this.ledgerDate = ledgerDate;
        this.ledgerBalance = ledgerBalance;
    }

    /**
     * Converts a given Ledger into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedLedger
     */
    public XmlAdaptedLedger(Ledger source) {
        ledgerDate = source.getDateLedger().getDate();
        ledgerBalance = source.getAccount().getBalance();
    }

    /**
     * Converts this jaxb-friendly adapted ledger object into the model's Ledger object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted ledger
     */
    public Ledger toModelType() throws IllegalValueException {
        if (ledgerDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    DateLedger.class.getSimpleName()));
        }
        if (!DateLedger.isValidDateLedger(ledgerDate)) {
            throw new IllegalValueException(DateLedger.MESSAGE_DATE_CONSTRAINTS);
        }
        final DateLedger modelDateLedger = new DateLedger(ledgerDate);

        if (ledgerBalance == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Account.class.getSimpleName()));
        }
        if (!Account.isValidBalance(ledgerBalance)) {
            throw new IllegalValueException(Account.MESSAGE_BALANCE_CONSTRAINTS);
        }
        final Account modelAccount = new Account(Double.parseDouble(ledgerBalance));

        return new Ledger(modelDateLedger, modelAccount);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedLedger)) {
            return false;
        }

        XmlAdaptedLedger otherLedger = (XmlAdaptedLedger) other;
        return Objects.equals(ledgerDate, otherLedger.ledgerDate)
                && Objects.equals(ledgerBalance, otherLedger.ledgerBalance);
    }
}
