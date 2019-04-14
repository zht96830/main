package seedu.address.model.statistics;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalExpenses.DUCK_RICE;

import javafx.collections.transformation.FilteredList;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.attributes.Date;
import seedu.address.model.attributes.Frequency;
import seedu.address.model.expense.Expense;
import seedu.address.model.expense.ExpenseList;
import seedu.address.testutil.Assert;

public class StatisticsTest {

    private ExpenseList expenseList = new ExpenseList();
    FilteredList<Expense> statsExpenses = new FilteredList<Expense>(
            expenseList.asUnmodifiableObservableList());

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () ->
                new Statistics(null));
    }

    @Test
    public void basicStats_parameters_null_throwsNullPointerException() {
        Statistics stats = new Statistics(statsExpenses);
        Date date = new Date("01-01-2019");
        //startDate cannot be null
        Assert.assertThrows(NullPointerException.class, () ->
                stats.calculateStats("stats", null, date, null));
        //endDate cannot be null
        Assert.assertThrows(NullPointerException.class, () ->
                stats.calculateStats("stats", date, null, null));
    }

    @Test
    public void compareStats_parameters_null_throwsNullPointerException() {
        Statistics stats = new Statistics(statsExpenses);
        Date date = new Date("01-01-2019");
        Frequency frequency = new Frequency("M");
        //date1 cannot be null
        Assert.assertThrows(NullPointerException.class, () ->
                stats.calculateStats("compare", null, date  , frequency));
        //date2 cannot be null
        Assert.assertThrows(NullPointerException.class, () ->
                stats.calculateStats("compare", date, null, frequency));
        //date2 cannot be null
        Assert.assertThrows(NullPointerException.class, () ->
                stats.calculateStats("compare", date, date, null));
    }

    @Test
    public void trendStats_parameters_null_throwsNullPointerException() {
        Statistics stats = new Statistics(statsExpenses);
        Date date = new Date("01-01-2019");
        Frequency frequency = new Frequency("M");
        //date1 cannot be null
        Assert.assertThrows(NullPointerException.class, () ->
                stats.calculateStats("trend", null, date  , frequency));
        //date2 cannot be null
        Assert.assertThrows(NullPointerException.class, () ->
                stats.calculateStats("trend", date, null, frequency));
        //date2 cannot be null
        Assert.assertThrows(NullPointerException.class, () ->
                stats.calculateStats("trend", date, date, null));
    }


}
