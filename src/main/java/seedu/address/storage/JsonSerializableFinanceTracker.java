package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.FinanceTracker;
import seedu.address.model.ReadOnlyFinanceTracker;
import seedu.address.model.expense.Expense;

/**
 * An Immutable FinanceTracker that is serializable to JSON format.
 */
@JsonRootName(value = "financetracker")
class JsonSerializableFinanceTracker {

    private final List<JsonAdaptedExpense> expenses = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableFinanceTracker} with the given expenses.
     */
    @JsonCreator
    public JsonSerializableFinanceTracker(@JsonProperty("expenses") List<JsonAdaptedExpense> expenses) {
        this.expenses.addAll(expenses);
    }

    /**
     * Converts a given {@code ReadOnlyFinanceTracker} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableFinanceTracker}.
     */
    public JsonSerializableFinanceTracker(ReadOnlyFinanceTracker source) {
        expenses.addAll(source.getExpenseList().stream().map(JsonAdaptedExpense::new).collect(Collectors.toList()));
    }

    /**
     * Converts this finance tracker into the model's {@code FinanceTracker} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public FinanceTracker toModelType() throws IllegalValueException {
        FinanceTracker financeTracker = new FinanceTracker();
        for (JsonAdaptedExpense jsonAdaptedExpense : expenses) {
            Expense expense = jsonAdaptedExpense.toModelType();
            financeTracker.addExpense(expense);
        }
        return financeTracker;
    }

}
