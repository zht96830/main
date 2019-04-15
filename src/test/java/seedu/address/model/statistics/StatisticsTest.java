package seedu.address.model.statistics;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalExpenses.DUCK_RICE;
import static seedu.address.testutil.TypicalStatistics.DuckRiceTableHtmlCompare;
import static seedu.address.testutil.TypicalStatistics.DuckRiceTableHtmlStats;
import static seedu.address.testutil.TypicalStatistics.DuckRiceTableHtmlTrend;
import static seedu.address.testutil.TypicalStatistics.blankTableHtmlCompare;
import static seedu.address.testutil.TypicalStatistics.blankTableHtmlStats;
import static seedu.address.testutil.TypicalStatistics.emptyTableHtmlTrend;

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
        //frequency cannot be null
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
        //frequency cannot be null
        Assert.assertThrows(NullPointerException.class, () ->
                stats.calculateStats("trend", date, date, null));
    }

    @Test
    public void startDate_after_endDate_produces_emptyTable() {
        Statistics stats = new Statistics(statsExpenses);
        Date date1 = new Date("01-02-2019");
        Date date2 = new Date("01-01-2019");
        Frequency frequency = new Frequency("M");

        //stats
        stats.calculateStats("stats", date1, date2, null);
        String outputHtmlStats = stats.getHtml();
        assertTrue(outputHtmlStats.equals( "Statistics Summary <br>\n" +
                "From: 01-02-2019 To: 01-01-2019\n" + blankTableHtmlStats));

        //statscompare
        stats.calculateStats("trend", date1, date2, frequency);
        String outputHtmlTrend = stats.getHtml();
        assertTrue(outputHtmlTrend.equals( "Statistics Trend <br>\n" +
                "From: 01-02-2019 To: 01-01-2019 Period Length: M<br>\n" + emptyTableHtmlTrend));

    }

    @Test
    public void statsCommand_CorrectResults() {
        ExpenseList expenseListWithDuckRice = new ExpenseList();
        expenseListWithDuckRice.add(DUCK_RICE);
        FilteredList<Expense> statsExpensesWithDuckRice = new FilteredList<>(
                expenseListWithDuckRice.asUnmodifiableObservableList());

        Statistics stats = new Statistics(statsExpensesWithDuckRice);
        Date startDate = new Date("01-01-2018");
        Date endDate = new Date("02-01-2020");

        //stats with duck rice in range
        stats.calculateStats("stats", startDate, endDate, null);
        String outputHtmlStats_InRange = stats.getHtml();
        assertTrue(outputHtmlStats_InRange.equals( "Statistics Summary <br>\n" +
                "From: 01-01-2018 To: 02-01-2020\n" + DuckRiceTableHtmlStats));

        startDate = new Date("02-01-2020");
        //stats with duck rice out of range
        stats.calculateStats("stats", startDate, endDate, null);
        String outputHtmlStats_OutOfRange = stats.getHtml();
        assertFalse(outputHtmlStats_OutOfRange.equals( "Statistics Summary <br>\n" +
                "From: 02-01-2020 To: 01-01-2020\n" + DuckRiceTableHtmlStats));
        assertTrue(outputHtmlStats_OutOfRange.equals( "Statistics Summary <br>\n" +
                "From: 02-01-2020 To: 02-01-2020\n" + blankTableHtmlStats));
    }

    @Test
    public void statsCompareCommand_CorrectResults() {
        ExpenseList expenseListWithDuckRice = new ExpenseList();
        expenseListWithDuckRice.add(DUCK_RICE);
        FilteredList<Expense> statsExpensesWithDuckRice = new FilteredList<>(
                expenseListWithDuckRice.asUnmodifiableObservableList());

        Statistics stats = new Statistics(statsExpensesWithDuckRice);
        Date date1 = new Date("01-01-2019");
        Date date2 = new Date("01-01-2019");
        Frequency frequency = new Frequency("M");

        //statsCompare with duck rice in range
        stats.calculateStats("compare", date1, date2, frequency);
        String outputHtmlStatsCompare_InRange = stats.getHtml();
        assertTrue(outputHtmlStatsCompare_InRange.equals( "Statistics Compare <br>\n" +
                "From: 01-01-2019 To: 01-02-2019\n" + DuckRiceTableHtmlCompare
                + "<br>From: 01-01-2019 To: 01-02-2019\n" + DuckRiceTableHtmlCompare));

        date1 = new Date("02-01-2020");
        date2 = new Date("02-01-2020");
        //statsCompare with duck rice out of range
        stats.calculateStats("compare", date1, date2, frequency);
        String outputHtmlStatsCompare_OutOfRange = stats.getHtml();
        assertFalse(outputHtmlStatsCompare_OutOfRange.equals( "Statistics Compare <br>\n" +
                "From: 02-01-2020 To: 02-02-2020\n" + DuckRiceTableHtmlCompare
                + "<br>From: 02-01-2020 To: 02-02-2020\n" + DuckRiceTableHtmlCompare));
        assertTrue(outputHtmlStatsCompare_OutOfRange.equals( "Statistics Compare <br>\n" +
               "From: 02-01-2020 To: 02-02-2020\n" + blankTableHtmlCompare
                + "<br>From: 02-01-2020 To: 02-02-2020\n" + blankTableHtmlCompare));
    }

    @Test
    public void statsTrendCommand_CorrectResults() {
        ExpenseList expenseListWithDuckRice = new ExpenseList();
        expenseListWithDuckRice.add(DUCK_RICE);
        FilteredList<Expense> statsExpensesWithDuckRice = new FilteredList<>(
                expenseListWithDuckRice.asUnmodifiableObservableList());

        Statistics stats = new Statistics(statsExpensesWithDuckRice);
        Date startDate = new Date("01-01-2019");
        Date endDate = new Date("02-01-2019");
        Frequency frequency = new Frequency("M");

        //statsCompare with duck rice in range
        stats.calculateStats("trend", startDate, endDate, frequency);
        String outputHtmlStatsCompare_InRange = stats.getHtml();
        //System.out.println(outputHtmlStatsCompare_InRange);
        assertTrue(outputHtmlStatsCompare_InRange.equals("Statistics Trend <br>\n" +
            "From: 01-01-2019 To: 02-01-2019 Period Length: M<br>\n" + DuckRiceTableHtmlTrend));

        startDate = new Date("02-01-2019");
        //statsCompare with duck rice out of range
        stats.calculateStats("trend", startDate, endDate, frequency);
        String outputHtmlStatsCompare_OutOfRange = stats.getHtml();
        assertFalse(outputHtmlStatsCompare_OutOfRange.equals("Statistics Trend <br>\n" +
                "From: 02-01-2019 To: 02-01-2019 Period Length: M<br>\n" + DuckRiceTableHtmlTrend));

        assertTrue(outputHtmlStatsCompare_OutOfRange.equals("Statistics Trend <br>\n" +
                "From: 02-01-2019 To: 02-01-2019 Period Length: M<br>\n" + emptyTableHtmlTrend));
    }


}
