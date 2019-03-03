package seedu.address.model.attributes;

import org.junit.Test;
import seedu.address.testutil.Assert;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AmountTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Amount(null));
    }

    @Test
    public void constructor_invalidAmount_emptyString_throwsIllegalArgumentException() {
        String invalidAmount = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Amount(invalidAmount));
    }

    @Test
    public void isValidAmount() {
        // null amount
        Assert.assertThrows(NullPointerException.class, () -> Amount.isValidAmount(null));

        // invalid amount numbers
        assertFalse(Amount.isValidAmount("")); // empty string
        assertFalse(Amount.isValidAmount(" ")); // spaces only
        assertFalse(Amount.isValidAmount("amount")); // non-numeric
        assertFalse(Amount.isValidAmount("91p041")); // alphabets within digits

        // valid amount numbers
        assertTrue(Amount.isValidAmount("1")); // exactly 1 number
        assertTrue(Amount.isValidAmount("12345"));
        assertTrue(Amount.isValidAmount("124293842033123")); // long amount numbers
    }
}
