package seedu.address.ui;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.expense.Expense;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.logging.Logger;

public class ExpenseTable {
    private static final String FXML = "ExpenseTable.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @javafx.fxml.FXML
    private TableView<Expense> expenseTableView;

    public ExpenseTable(ObservableList<Expense> expenseList, ObservableValue<Expense> selectedExpense,
                           Consumer<Expense> onSelectedExpenseChange) {
        super(FXML);
        expenseTableView.setItems(expenseList);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Expense} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Expense> {
        @Override
        protected void updateItem(Expense expense, boolean empty) {
            super.updateItem(expense, empty);

            if (empty || expense == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PersonCard(expense, getIndex() + 1).getRoot());
            }
        }
    }
}
