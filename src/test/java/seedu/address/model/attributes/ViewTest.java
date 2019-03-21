package seedu.address.model.attributes;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import seedu.address.testutil.Assert;

public class ViewTest {

    @Test
    public void isValidView() {
        // null amount
        Assert.assertThrows(NullPointerException.class, () -> View.isValidView(null));

        // invalid View
        assertFalse(View.isValidView("")); // empty string
        assertFalse(View.isValidView(" ")); // spaces only
        assertFalse(View.isValidView("transpot")); // spelling error
        assertFalse(View.isValidView("91p041")); // invalid category

        // valid View
        assertTrue(View.isValidView("food")); // in lower case
        assertTrue(View.isValidView("SHOPPING")); // in upper case
        assertTrue(View.isValidView("HeAlThCaRe")); // mixed case
        assertTrue(View.isValidView("TRAvel")); // mixed case
    }
}
