package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.debt.Debt;
import seedu.address.model.expense.Expense;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Expense> PREDICATE_SHOW_ALL_FINANCES = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' finance tracker file path.
     */
    Path getFinanceTrackerFilePath();

    /**
     * Sets the user prefs' finance tracker file path.
     */
    void setFinanceTrackerFilePath(Path financeTrackerFilePath);

    /**
     * Replaces finance tracker data with the data in {@code financeTracker}.
     */
    void setFinanceTracker(ReadOnlyFinanceTracker financeTracker);

    /** Returns the FinanceTracker */
    ReadOnlyFinanceTracker getFinanceTracker();

    /**
     * Returns true if a expense with the same identity as {@code expense} exists in the finance tracker.
     */
    boolean hasExpense(Expense expense);

    /**
     * Deletes the given expense.
     * The expense must exist in the finance tracker.
     */
    void deleteExpense(Expense target);

    /**
     * Adds the given expense.
     * {@code expense} must not already exist in the finance tracker.
     */
    void addExpense(Expense expense);

    /**
     * Replaces the given expense {@code target} with {@code editedExpense}.
     * {@code target} must exist in the finance tracker.
     * The expense identity of {@code editedExpense} must not be the same as another existing expense in the finance tracker.
     */
    void setExpense(Expense target, Expense editedExpense);

    /** Returns an unmodifiable view of the filtered expense list */
    ObservableList<Expense> getFilteredExpenseList();

    /**
     * Updates the filter of the filtered expense list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredExpenseList(Predicate<Expense> predicate);

    /**
     * Returns true if a debt with the same identity as {@code debt} exists in the finance tracker.
     */
    boolean hasDebt(Debt debt);

    /**
     * Deletes the given debt.
     * The debt must exist in the finance tracker.
     */
    void deleteDebt(Debt target);

    /**
     * Adds the given debt.
     */
    void addDebt(Debt debt);

    /**
     * Replaces the given debt {@code target} with {@code editedDebt}.
     * {@code target} must exist in the finance tracker.
     * The expense identity of {@code editedDebt} must not be the same as another existing debt in the finance tracker.
     */
    void setDebt(Debt target, Debt editedDebt);

    /** Returns an unmodifiable view of the filtered debt list */
    ObservableList<Debt> getFilteredDebtList();

    /**
     * Updates the filter of the filtered debt list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredDebtList(Predicate<Debt> predicate);

    /**
     * Returns true if the model has previous finance tracker states to restore.
     */
    boolean canUndoFinanceTracker();

    /**
     * Returns true if the model has undone finance tracker states to restore.
     */
    boolean canRedoFinanceTracker();

    /**
     * Restores the model's finance tracker to its previous state.
     */
    void undoFinanceTracker();

    /**
     * Restores the model's finance tracker to its previously undone state.
     */
    void redoFinanceTracker();

    /**
     * Saves the current finance tracker state for undo/redo.
     */
    void commitFinanceTracker();

    /**
     * Selected expense in the filtered expense list.
     * null if no expense is selected.
     */
    ReadOnlyProperty<Expense> selectedExpenseProperty();

    /**
     * Returns the selected expense in the filtered expense list.
     * null if no expense is selected.
     */
    Expense getSelectedExpense();

    /**
     * Sets the selected expense in the filtered expense list.
     */
    void setSelectedExpense(Expense expense);
}
