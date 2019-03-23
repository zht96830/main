package seedu.address.ui;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalBudgets.FOOD_BUDGET;
import static seedu.address.testutil.TypicalDebts.AMY;
import static seedu.address.testutil.TypicalExpenses.DUCK_RICE;
import static seedu.address.testutil.TypicalRecurrings.PHONE_BILL;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.BrowserPanelHandle;
import javafx.beans.property.SimpleObjectProperty;

import seedu.address.model.budget.Budget;
import seedu.address.model.debt.Debt;
import seedu.address.model.expense.Expense;
import seedu.address.model.recurring.Recurring;

public class BrowserPanelTest extends GuiUnitTest {
    private SimpleObjectProperty<Expense> selectedExpense = new SimpleObjectProperty<>();
    private SimpleObjectProperty<Debt> selectedDebt = new SimpleObjectProperty<>();
    private SimpleObjectProperty<Budget> selectedBudget = new SimpleObjectProperty<>();
    private SimpleObjectProperty<Recurring> selectedRecurring = new SimpleObjectProperty<>();

    private BrowserPanel browserPanel;
    private BrowserPanelHandle browserPanelHandle;

    @Before
    public void setUp() {
        guiRobot.interact(() -> browserPanel = new BrowserPanel(selectedExpense, selectedDebt, selectedBudget,
                selectedRecurring));
        uiPartRule.setUiPart(browserPanel);

        browserPanelHandle = new BrowserPanelHandle(browserPanel.getRoot());
    }

    @Test
    public void display() throws Exception {
        // default web page
        assertEquals(BrowserPanel.DEFAULT_PAGE, browserPanelHandle.getLoadedUrl());

        // associated web page of an expense
        guiRobot.interact(() -> selectedExpense.set(DUCK_RICE));
        String urlQuery = BrowserPanel.QUESTION_MARK + BrowserPanel.QUERY_NAME + DUCK_RICE.getName().name
                + BrowserPanel.QUERY_CATEGORY + DUCK_RICE.getCategory().toString()
                + BrowserPanel.QUERY_AMOUNT + BrowserPanel.DOLLAR_SIGN + DUCK_RICE.getAmount().toString()
                + BrowserPanel.QUERY_DATE + DUCK_RICE.getDate().toString()
                + BrowserPanel.QUERY_REMARK + DUCK_RICE.getRemarks();

        URL expectedExpenseUrl = new URL(BrowserPanel.EXPENSES_PAGE_URL
                + urlQuery.replaceAll(" ", "%20"));

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedExpenseUrl, browserPanelHandle.getLoadedUrl());

        // associated web page of a debt
        guiRobot.interact(() -> selectedDebt.set(AMY));
        urlQuery = BrowserPanel.QUESTION_MARK + BrowserPanel.QUERY_PERSONOWED + AMY.getPersonOwed().name
                + BrowserPanel.QUERY_CATEGORY + AMY.getCategory().toString()
                + BrowserPanel.QUERY_AMOUNT + BrowserPanel.DOLLAR_SIGN + AMY.getAmount().toString()
                + BrowserPanel.QUERY_DEADLINE + AMY.getDeadline().toString()
                + BrowserPanel.QUERY_REMARK + AMY.getRemarks();

        URL expectedDebtUrl = new URL(BrowserPanel.DEBTS_PAGE_URL
                + urlQuery.replaceAll(" ", "%20"));

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedDebtUrl, browserPanelHandle.getLoadedUrl());

        // associated web page of a debt
        guiRobot.interact(() -> selectedBudget.set(FOOD_BUDGET));
        urlQuery = BrowserPanel.QUESTION_MARK
                + BrowserPanel.QUERY_CATEGORY + FOOD_BUDGET.getCategory().toString()
                + BrowserPanel.QUERY_AMOUNT + BrowserPanel.DOLLAR_SIGN + FOOD_BUDGET.getAmount().toString()
                + BrowserPanel.QUERY_STARTDATE + FOOD_BUDGET.getStartDate().toString()
                + BrowserPanel.QUERY_ENDDATE + FOOD_BUDGET.getEndDate().toString()
                + BrowserPanel.QUERY_REMARK + FOOD_BUDGET.getRemarks();

        URL expectedBudgetUrl = new URL(BrowserPanel.BUDGETS_PAGE_URL
                + urlQuery.replaceAll(" ", "%20"));

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedBudgetUrl, browserPanelHandle.getLoadedUrl());

        // associated web page of a recurring
        guiRobot.interact(() -> selectedRecurring.set(PHONE_BILL));
        urlQuery = BrowserPanel.QUESTION_MARK + BrowserPanel.QUERY_NAME + PHONE_BILL.getName().name
                + BrowserPanel.QUERY_CATEGORY + PHONE_BILL.getCategory().toString()
                + BrowserPanel.QUERY_AMOUNT + BrowserPanel.DOLLAR_SIGN + PHONE_BILL.getAmount().toString()
                + BrowserPanel.QUERY_DATE + PHONE_BILL.getDate().toString()
                + BrowserPanel.QUERY_FREQUENCY + PHONE_BILL.getFrequency()
                + BrowserPanel.QUERY_OCCURENCE + PHONE_BILL.getOccurrence()
                + BrowserPanel.QUERY_REMARK + PHONE_BILL.getRemarks();

        URL expectedRecurringUrl = new URL(BrowserPanel.RECURRINGS_PAGE_URL
                + urlQuery.replaceAll(" ", "%20"));

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedRecurringUrl, browserPanelHandle.getLoadedUrl());
    }
}
