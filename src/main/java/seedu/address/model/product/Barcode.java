package seedu.address.model.product;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a product's barcode number.
 * Guarantees: immutable; is valid as declared in {@link #isBarcodeValid(String)}
 */
public class Barcode {

    private static final int BARCODE_LENGTH = 13;
    public static final String MESSAGE_BARCODE_CONSTRAINTS = "Barcode numbers should be of the format "
            + "<Country code><Manufacturer code><Product Item code><Check Digit> "
            + "and adhere to the following constraints:\n"
            + "1. Each character of the barcode number should be an integer from 0 - 9.\n"
            + "2. The Country code should be a 2 digit positive integer.\n"
            + "3. The Manufacturer code should be a 5 digit positive integer.\n"
            + "4. The Product Item code should be a 5 digit positive integer.\n"
            + "5. The Check Digit verifies if the previous fields are all correct. "
            + "This should be a single digit positive integer.\n";
    private final String value;

    /**
     * Constructs a {@code Barcode}
     * @param barcodeNumber unique identification number of this product
     */
    public Barcode(String barcodeNumber) {
        requireNonNull(barcodeNumber);
        checkArgument(isBarcodeValid(barcodeNumber), MESSAGE_BARCODE_CONSTRAINTS);
        value = barcodeNumber;
    }

    /**
     * Returns if a given barcode number is valid.
     * @param barcodeNumber
     * @return
     */
    private boolean isBarcodeValid(String barcodeNumber) {
        return isCorrectFormat(barcodeNumber) ? verifyDigits(barcodeNumber, getCheckDigit(barcodeNumber)) : false;
    }

    /**
     * Returns if a given barcode number is of the correct format.
     * @param barcodeNumber
     * @return
     */
    private boolean isCorrectFormat(String barcodeNumber) {
        return barcodeNumber.length() == BARCODE_LENGTH;
    }

    /**
     * Returns the check digit in the barcode.
     * @param barcodeNumber
     * @return
     */
    private int getCheckDigit(String barcodeNumber) {
        return barcodeNumber.charAt(barcodeNumber.length() - 1);
    }

    /**
     * Returns if the individual digits of the barcode are valid by checking against
     * the check digit.
     * @param barcodeNumber
     * @param checkDigit
     * @return
     */
    private boolean verifyDigits(String barcodeNumber, int checkDigit) {
        final int MULT = 3;
        int firstSum = 0, secondSum = 0;
        int idx = BARCODE_LENGTH - 1;
        while (idx >= 0) {
            int digit = barcodeNumber.charAt(idx);
            if (idx % 2 == 0) {
                firstSum += digit;
            } else {
                secondSum += digit;
            }
            idx--;
        }
        firstSum *= MULT;
        int sum = firstSum + secondSum;
        int formMultipleOfTen = formMultipleOfTen(sum);
        return checkDigit == formMultipleOfTen;
    }

    /**
     * Returns the smallest positive integer such that adding this integer
     * to {@code number} will give a number divisible by 10.
     * @param number
     * @return
     */
    private int formMultipleOfTen(int number) {
        number %= 10;
        if (number > 5) {
            number = 10 - number;
        }
        return number;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Barcode // instanceof handles nulls
                && value.equals(((Barcode) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
