package seedu.address.ui;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalExpenses.DUCK_RICE;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.BrowserPanelHandle;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.model.expense.Expense;

public class BrowserPanelTest extends GuiUnitTest {
    private SimpleObjectProperty<Expense> selectedExpense = new SimpleObjectProperty<>();
    private BrowserPanel browserPanel;
    private BrowserPanelHandle browserPanelHandle;

    @Before
    public void setUp() {
        guiRobot.interact(() -> browserPanel = new BrowserPanel(selectedExpense));
        uiPartRule.setUiPart(browserPanel);

        browserPanelHandle = new BrowserPanelHandle(browserPanel.getRoot());
    }

    @Test
    public void display() throws Exception {
        // default web page
        assertEquals(BrowserPanel.DEFAULT_PAGE, browserPanelHandle.getLoadedUrl());

        // associated web page of a expense
        guiRobot.interact(() -> selectedExpense.set(DUCK_RICE));
        String urlQuery = BrowserPanel.QUERY_NAME + DUCK_RICE.getName().name + BrowserPanel.QUERY_CATEGORY
                + DUCK_RICE.getCategory().toString() + BrowserPanel.QUERY_AMOUNT + "$"
                + DUCK_RICE.getAmount().toString() + BrowserPanel.QUERY_DATE
                + DUCK_RICE.getDate().toString() + BrowserPanel.QUERY_REMARK + DUCK_RICE.getRemarks();

        URL expectedExpenseUrl = new URL(BrowserPanel.EXPENSES_PAGE_URL
                + urlQuery.replaceAll(" ", "%20"));

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedExpenseUrl, browserPanelHandle.getLoadedUrl());
    }
}
