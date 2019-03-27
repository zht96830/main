package seedu.address.model.util;

import static seedu.address.model.attributes.Category.ENTERTAINMENT;
import static seedu.address.model.attributes.Category.FOOD;
import static seedu.address.model.attributes.Category.HEALTHCARE;
import static seedu.address.model.attributes.Category.SHOPPING;
import static seedu.address.model.attributes.Category.TRANSPORT;
import static seedu.address.model.attributes.Category.TRAVEL;
import static seedu.address.model.attributes.Category.UTILITIES;
import static seedu.address.model.attributes.Category.WORK;

import seedu.address.model.FinanceTracker;
import seedu.address.model.ReadOnlyFinanceTracker;
import seedu.address.model.attributes.Amount;
import seedu.address.model.attributes.Category;
import seedu.address.model.attributes.Date;
import seedu.address.model.attributes.Name;
import seedu.address.model.budget.Budget;
import seedu.address.model.expense.Expense;

/**
 * Contains utility methods for populating {@code FinanceTracker} with sample data.
 */
public class SampleDataUtil {
    public static Expense[] getSampleExpenses() {
        return new Expense[] {
            new Expense(new Name("Chicken Rice"), new Amount("1021"), new Date("11-12-2018"),
                FOOD, "Bishan Chicken Rice"),
            new Expense(new Name("Laksa"), new Amount("520"), new Date("03-02-2019"),
                FOOD, null),
            new Expense(new Name("Japan Ticket"), new Amount("62040"), new Date("21-01-2019"),
                Category.TRAVEL, null),
            new Expense(new Name("Top up EZ link card"), new Amount("2000"), new Date("03-03-2019"),
                TRANSPORT, "Topped up at KR MRT"),
            new Expense(new Name("See doctor"), new Amount("6520"), new Date("26-02-2019"),
                HEALTHCARE, "For fever at UHC"),
            new Expense(new Name("Telephone bills"), new Amount("2510"), new Date("28-02-2019"),
                Category.UTILITIES, "Exceeded limit by 1 GB")
        };
    }

    public static Expense[] getSortedSampleExpenses() {
        return new Expense[] {
            new Expense(new Name("Top up EZ link card"), new Amount("2000"), new Date("03-03-2019"),
                    TRANSPORT, "Topped up at KR MRT"),
            new Expense(new Name("Telephone bills"), new Amount("2510"), new Date("28-02-2019"),
                    Category.UTILITIES, "Exceeded limit by 1 GB"),
            new Expense(new Name("See doctor"), new Amount("6520"), new Date("26-02-2019"),
                    HEALTHCARE, "For fever at UHC"),
            new Expense(new Name("Laksa"), new Amount("520"), new Date("03-02-2019"),
                    FOOD, null),
            new Expense(new Name("Japan Ticket"), new Amount("62040"), new Date("21-01-2019"),
                    Category.TRAVEL, null),
            new Expense(new Name("Chicken Rice"), new Amount("1021"), new Date("11-12-2018"),
                    FOOD, "Bishan Chicken Rice")
        };
    }
    public static Budget[] getSampleBudgets() {
        return new Budget[] {
            new Budget(FOOD, new Amount("600"), new Date("01-10-2019"), new Date("31-10-2019"),
                    "eat less save more", 0, 0),
            new Budget(TRANSPORT, new Amount("300"), new Date("01-07-2019"), new Date("31-12-2019"),
                    "only for school and back", 0, 0),
            new Budget(SHOPPING, new Amount("200"), new Date("01-06-2019"), new Date("30-06-2019"),
                    "only for grocery shopping", 0, 0),
            new Budget(WORK, new Amount("100"), new Date("13-05-2019"), new Date("02-08-2019"),
                    "internship stuff", 0, 0),
            new Budget(UTILITIES, new Amount("450"), new Date("01-05-2019"), new Date("31-05-2019"),
                    "take shorter showers", 0, 0),
            new Budget(HEALTHCARE, new Amount("100"), new Date("01-06-2019"), new Date("15-06-2019"),
                    "stress may make me feel sick", 0, 0),
            new Budget(ENTERTAINMENT, new Amount("900"), new Date("11-05-2019"), new Date("11-08-2019"),
                    "yay summer vacation", 0, 0),
            new Budget(TRAVEL, new Amount("7000"), new Date("01-01-2020"), new Date("31-12-2020"),
                    "grad trip and family trip", 0, 0)};
    }

    public static ReadOnlyFinanceTracker getSampleFinanceTracker() {
        FinanceTracker sampleFt = new FinanceTracker();
        for (Expense sampleExpense : getSampleExpenses()) {
            sampleFt.addExpense(sampleExpense);
        }
        for (Budget sampleBudget : getSampleBudgets()) {
            sampleFt.addBudget(sampleBudget);
        }
        return sampleFt;
    }
}
