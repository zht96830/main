package seedu.address.ui;

import static java.util.Objects.requireNonNull;

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
import seedu.address.model.statistics.Statistics;
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
    public static final URL STATISTICS_PAGE_URL =
            requireNonNull(MainApp.class.getResource(FXML_FILE_FOLDER + "statistics.html"));

    public static final String QUERY_NAME = "name=";
    public static final String QUERY_CATEGORY = "&category=";
    public static final String QUERY_AMOUNT = "&amount=";
    public static final String QUERY_DATE = "&date=";
    public static final String QUERY_STARTDATE = "&startdate=";
    public static final String QUERY_PERSONOWED = "&personowed=";
    public static final String QUERY_DEADLINE = "&deadline=";
    public static final String QUERY_ENDDATE = "&enddate=";
    public static final String QUERY_REMARK = "&remarks=";
    public static final String QUERY_FREQUENCY = "&frequency=";
    public static final String QUERY_OCCURENCE = "&occurrence=";
    public static final String QUESTION_MARK = "?";
    public static final String DOLLAR_SIGN = "$";


    private static final String FXML = "BrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private WebView browser;

    public BrowserPanel(ObservableValue<Expense> selectedExpense, ObservableValue<Debt> selectedDebt,
                        ObservableValue<Budget> selectedBudget, ObservableValue<Recurring> selectedRecurring,
                        ObservableValue<Statistics> selectedStatistic) {
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

        String url;
        if (object instanceof Recurring) {
            url = RECURRINGS_PAGE_URL.toExternalForm()
                    + QUESTION_MARK
                    + QUERY_NAME + ((Recurring) object).getName().name
                    + QUERY_CATEGORY + ((Recurring) object).getCategory().toString()
                    + QUERY_AMOUNT + DOLLAR_SIGN + ((Recurring) object).getAmount().toString()
                    + QUERY_DATE + ((Recurring) object).getDate().toString()
                    + QUERY_FREQUENCY + ((Recurring) object).getFrequency().toString()
                    + QUERY_OCCURENCE + ((Recurring) object).getOccurrence().toString()
                    + QUERY_REMARK + ((Recurring) object).getRemarks();
        } else if (object instanceof Budget) {
            url = BUDGETS_PAGE_URL.toExternalForm()
                    + QUESTION_MARK
                    + QUERY_CATEGORY + ((Budget) object).getCategory().toString()
                    + QUERY_AMOUNT + DOLLAR_SIGN + ((Budget) object).getAmount().toString()
                    + QUERY_STARTDATE + ((Budget) object).getStartDate().toString()
                    + QUERY_ENDDATE + ((Budget) object).getEndDate().toString()
                    + QUERY_REMARK + ((Budget) object).getRemarks();
        } else if (object instanceof Debt) {
            url = DEBTS_PAGE_URL.toExternalForm()
                    + QUESTION_MARK
                    + QUERY_PERSONOWED + ((Debt) object).getPersonOwed().name
                    + QUERY_CATEGORY + ((Debt) object).getCategory().toString()
                    + QUERY_AMOUNT + DOLLAR_SIGN + ((Debt) object).getAmount().toString()
                    + QUERY_DEADLINE + ((Debt) object).getDeadline().toString()
                    + QUERY_REMARK + ((Debt) object).getRemarks();
        } else {
            url = EXPENSES_PAGE_URL.toExternalForm()
                    + QUESTION_MARK
                    + QUERY_NAME + ((Expense) object).getName().name
                    + QUERY_CATEGORY + ((Expense) object).getCategory().toString()
                    + QUERY_AMOUNT + DOLLAR_SIGN + ((Expense) object).getAmount().toString()
                    + QUERY_DATE + ((Expense) object).getDate().toString()
                    + QUERY_REMARK + ((Expense) object).getRemarks();
        }

        loadPage(url);
    }

    public void loadPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(url));
    }

    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    private void loadDefaultPage() {
        loadPage(DEFAULT_PAGE.toExternalForm());
    }

}
