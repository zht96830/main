package seedu.address.ui;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.expense.Expense;

import java.util.function.Consumer;
import java.util.logging.Logger;

public class ExpenseTable {
    private static final String FXML = "ExpenseTable.fxml";
    private final Logger logger = LogsCenter.getLogger(ExpenseListPanel.class);

    @javafx.fxml.FXML
    private TableView<Expense> expenseTableView;

    public ExpenseTable(ObservableList<Expense> expenseList, ObservableValue<Expense> selectedExpense,
                           Consumer<Expense> onSelectedExpenseChange) {
        expenseTableView.setItems(expenseList);
    }

}
