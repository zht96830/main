package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;

import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.budget.Budget;
import seedu.address.model.debt.Debt;
import seedu.address.model.expense.Expense;
import seedu.address.model.recurring.Recurring;



/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final URL DEFAULT_PAGE =
            requireNonNull(MainApp.class.getResource(FXML_FILE_FOLDER + "default.html"));

    public static final String SEARCH_PAGE_URL = "https://se-education.org/dummy-search-page/";

    public static final URL EXPENSES_PAGE_URL =
            requireNonNull(MainApp.class.getResource(FXML_FILE_FOLDER + "selectedexpense.html"));
    public static final URL DEBTS_PAGE_URL =
            requireNonNull(MainApp.class.getResource(FXML_FILE_FOLDER + "selecteddebt.html"));
    public static final URL BUDGETS_PAGE_URL =
            requireNonNull(MainApp.class.getResource(FXML_FILE_FOLDER + "selectedbudget.html"));
    public static final URL RECURRINGS_PAGE_URL =
            requireNonNull(MainApp.class.getResource(FXML_FILE_FOLDER + "selectedrecurring.html"));

    // Page titles for testing purposes
    public static final String EXPENSE_PAGE_TITLE = "Selected Expense Page";
    public static final String DEBT_PAGE_TITLE = "Selected Debt Page";
    public static final String BUDGET_PAGE_TITLE = "Selected Budget Page";
    public static final String RECURRING_PAGE_TITLE = "Selected Recurring Page";
    public static final String DEFAULT_PAGE_TITLE = "Default Page";

    public static final String QUERY_NAME = "name=";
    public static final String QUERY_CATEGORY = "category=";
    public static final String QUERY_AMOUNT = "amount=";
    public static final String QUERY_DATE = "date=";
    public static final String QUERY_STARTDATE = "startdate=";
    public static final String QUERY_PERSONOWED = "personowed=";
    public static final String QUERY_DEADLINE = "deadline=";
    public static final String QUERY_ENDDATE = "enddate=";
    public static final String QUERY_REMARK = "remarks=";
    public static final String QUERY_FREQUENCY = "frequency=";
    public static final String QUERY_OCCURENCE = "occurrence=";
    public static final String QUESTION_MARK = "?";
    public static final String AND = "&";
    public static final String DOLLAR_SIGN = "$";

    private static final String FXML = "BrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private WebView browser;

    public BrowserPanel(ObservableValue<Expense> selectedExpense, ObservableValue<Debt> selectedDebt,
                        ObservableValue<Budget> selectedBudget, ObservableValue<Recurring> selectedRecurring) {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        // Load expense page when selected expense changes.
        selectedExpense.addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                loadDefaultPage();
                return;
            }
            loadObjectPage(newValue);
        });

        // Load debt page when selected debt changes.
        selectedDebt.addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                loadDefaultPage();
                return;
            }
            loadObjectPage(newValue);
        });

        // Load budget page when selected budget changes.
        selectedBudget.addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                loadDefaultPage();
                return;
            }
            loadObjectPage(newValue);
        });

        // Load recurring page when selected recurring changes.
        selectedRecurring.addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                loadDefaultPage();
                return;
            }
            loadObjectPage(newValue);
        });

        loadDefaultPage();
    }

    /**
     * Runs load page with url that is set based on object
     */
    private void loadObjectPage(Object object) {
        String html = "";

        if (object instanceof Budget) {

            BufferedInputStream bis = new BufferedInputStream(convertUrlToInputStream(BUDGETS_PAGE_URL));
            html = convertInputStreamToString(bis);

            html = html.replace("$category", ((Budget) object).getCategory().toString());
            html = html.replace("$amount", ((Budget) object).getAmount().toString());
            html = html.replace("$duration", ((Budget) object).getDuration());
            html = html.replace("$totalspent", Double.toString(((Budget) object).getTotalSpent()));
            html = html.replace("$percentage", Double.toString(((Budget) object).getPercentage()));
            html = html.replace("$remarks", ((Budget) object).getRemarks());
        } else if (object instanceof Recurring) {

            BufferedInputStream bis = new BufferedInputStream(convertUrlToInputStream(RECURRINGS_PAGE_URL));

            html = convertInputStreamToString(bis);

            html = html.replace("$name", ((Recurring) object).getName().name);
            html = html.replace("$category", ((Recurring) object).getCategory().toString());
            html = html.replace("$amount", ((Recurring) object).getAmount().toString());
            html = html.replace("$date", ((Recurring) object).getDate().toString());
            html = html.replace("$frequency", ((Recurring) object).getFrequency().toString());
            html = html.replace("$occurrence", ((Recurring) object).getOccurrence().toString());
            html = html.replace("$remarks", ((Recurring) object).getRemarks());
        } else if (object instanceof Debt) {

            BufferedInputStream bis = new BufferedInputStream(convertUrlToInputStream(DEBTS_PAGE_URL));
            html = convertInputStreamToString(bis);

            html = html.replace("$personowed", ((Debt) object).getPersonOwed().name);
            html = html.replace("$category", ((Debt) object).getCategory().toString());
            html = html.replace("$amount", ((Debt) object).getAmount().toString());
            html = html.replace("$deadline", ((Debt) object).getDeadline().toString());
            html = html.replace("$remarks", ((Debt) object).getRemarks());
        } else {

            BufferedInputStream bis = new BufferedInputStream(convertUrlToInputStream(EXPENSES_PAGE_URL));

            html = convertInputStreamToString(bis);

            html = html.replace("$name", ((Expense) object).getName().name);
            html = html.replace("$category", ((Expense) object).getCategory().toString());
            html = html.replace("$amount", ((Expense) object).getAmount().toString());
            html = html.replace("$date", ((Expense) object).getDate().toString());
            html = html.replace("$remarks", ((Expense) object).getRemarks());
        }
        loadPage(html);
    }

    /**
     * Loads an HTML file in terms of string format
     * @param html represents the html content in string format
     */
    public void loadPage(String html) {
        Platform.runLater(() -> browser.getEngine().loadContent(html));
    }

    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    private void loadDefaultPage() {
        Platform.runLater(() -> browser.getEngine().load(DEFAULT_PAGE.toExternalForm()));
    }

    /**
     * Converts the content of buffered input stream to string format
     */
    private String convertInputStreamToString(BufferedInputStream bis) {
        StringBuilder sb = new StringBuilder();
        try {
            byte[] contents = new byte[4096];
            int bytesRead = 0;
            while ((bytesRead = bis.read(contents)) != -1) {
                sb.append(new String(contents, 0, bytesRead));
            }
            bis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * Converts the url to input stream format
     */
    private InputStream convertUrlToInputStream(URL url) {
        try {
            return url.openStream();
        } catch (IOException e) {
            System.out.println(e);
        }
        return null;
    }
}
