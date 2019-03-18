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
import seedu.address.model.expense.Expense;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final URL DEFAULT_PAGE =
            requireNonNull(MainApp.class.getResource(FXML_FILE_FOLDER + "default.html"));

    public static final String SEARCH_PAGE_URL = "https://se-education.org/dummy-search-page/";

    public static final URL EXPENSES_PAGE_URL =
            requireNonNull(MainApp.class.getResource(FXML_FILE_FOLDER + "selectedexpense.html"));

    public static final String QUERY_NAME = "?name=";
    public static final String QUERY_CATEGORY = "&category=";
    public static final String QUERY_AMOUNT = "&amount=";
    public static final String QUERY_DATE = "&date=";
    public static final String QUERY_REMARK = "&remarks=";

    private static final String FXML = "BrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private WebView browser;

    public BrowserPanel(ObservableValue<Expense> selectedPerson) {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        // Load expense page when selected expense changes.
        selectedPerson.addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                loadDefaultPage();
                return;
            }
            loadExpensePage(newValue);
        });

        loadDefaultPage();
    }

    private void loadExpensePage(Expense expense) {
        loadPage(EXPENSES_PAGE_URL.toExternalForm() + QUERY_NAME + expense.getName().name + QUERY_CATEGORY
                + expense.getCategory().toString() + QUERY_AMOUNT + "$" + expense.getAmount().toString() + QUERY_DATE
                + expense.getDate().toString() + QUERY_REMARK + expense.getRemarks());
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
