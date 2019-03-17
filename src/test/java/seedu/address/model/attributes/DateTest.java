package seedu.address.model.attributes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class DateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Date(null));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        String invalidDate = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Date(invalidDate));
    }

    @Test
    public void isValidDate() {
        // null date
        Assert.assertThrows(NullPointerException.class, () -> Date.isValidDate(null));

        // invalid dates
        assertFalse(Date.isValidDate("")=="format"); // empty string
        assertFalse(Date.isValidDate(" ")=="format"); // spaces only
        assertFalse(Date.isValidDate("01-01")=="format"); // must be dd-mm-yyyy
        assertFalse(Date.isValidDate("01-01-18")=="format"); // need 4 numbers for year, e.g. 2018
        assertFalse(Date.isValidDate("01-01-2018a")=="format"); // alphabets not allowed
        assertFalse(Date.isValidDate("date")=="format"); // non-numeric

        // valid dates
        assertTrue(Date.isValidDate("01-01-2019")=="valid");
        assertTrue(Date.isValidDate("31-12-1999")=="valid");
    }

    @Test
    public void compareTo() {
        // initialize dates in chronological order
        Date date1 = new Date("01-01-2000");
        Date date2 = new Date("31-01-2000");
        Date date3 = new Date("10-12-2000");
        Date date4 = new Date("01-01-2015");

        assertEquals(date1.compareTo(date1), 0);
        assertEquals(date1.compareTo(date2), -1);
        assertEquals(date1.compareTo(date3), -1);
        assertEquals(date1.compareTo(date4), -1);

        assertEquals(date2.compareTo(date1), 1);
        assertEquals(date2.compareTo(date2), 0);
        assertEquals(date2.compareTo(date3), -1);
        assertEquals(date2.compareTo(date4), -1);

        assertEquals(date3.compareTo(date1), 1);
        assertEquals(date3.compareTo(date2), 1);
        assertEquals(date3.compareTo(date3), 0);
        assertEquals(date3.compareTo(date4), -1);

        assertEquals(date4.compareTo(date1), 1);
        assertEquals(date4.compareTo(date2), 1);
        assertEquals(date4.compareTo(date3), 1);
        assertEquals(date4.compareTo(date4), 0);
    }
}
