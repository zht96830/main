package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_DEBT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARKS_EXPENSE;
import static seedu.address.testutil.TypicalExpenses.DUCK_RICE;
import static seedu.address.testutil.TypicalExpenses.getTypicalFinanceTrackerWithExpenses;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.budget.Budget;
import seedu.address.model.debt.Debt;
import seedu.address.model.expense.Expense;
import seedu.address.model.person.exceptions.DuplicateExpenseException;
import seedu.address.model.recurring.Recurring;
import seedu.address.testutil.ExpenseBuilder;

public class FinanceTrackerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final FinanceTracker financeTracker = new FinanceTracker();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), financeTracker.getExpenseList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        financeTracker.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        FinanceTracker newData = getTypicalFinanceTrackerWithExpenses();
        financeTracker.resetData(newData);
        assertEquals(newData, financeTracker);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two expenses with the same identity fields
        Expense editedAlice = new ExpenseBuilder(DUCK_RICE).withCategory(VALID_DEADLINE_DEBT).withRemarks(
                VALID_REMARKS_EXPENSE).build();
        List<Expense> newExpenses = Arrays.asList(DUCK_RICE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newExpenses);

        thrown.expect(DuplicateExpenseException.class);
        financeTracker.resetData(newData);
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        financeTracker.hasExpense(null);
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(financeTracker.hasExpense(DUCK_RICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        financeTracker.addExpense(DUCK_RICE);
        assertTrue(financeTracker.hasExpense(DUCK_RICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        financeTracker.addExpense(DUCK_RICE);
        Expense editedAlice = new ExpenseBuilder(DUCK_RICE).withCategory(VALID_DEADLINE_DEBT).withRemarks(
                VALID_REMARKS_EXPENSE).build();
        assertTrue(financeTracker.hasExpense(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        financeTracker.getExpenseList().remove(0);
    }

    @Test
    public void addListener_withInvalidationListener_listenerAdded() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        financeTracker.addListener(listener);
        financeTracker.addExpense(DUCK_RICE);
        assertEquals(1, counter.get());
    }

    @Test
    public void removeListener_withInvalidationListener_listenerRemoved() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        financeTracker.addListener(listener);
        financeTracker.removeListener(listener);
        financeTracker.addExpense(DUCK_RICE);
        assertEquals(0, counter.get());
    }

    /**
     * A stub ReadOnlyFinanceTracker whose expenses list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyFinanceTracker {
        private final ObservableList<Expense> expenses = FXCollections.observableArrayList();
        private final ObservableList<Debt> debts = FXCollections.observableArrayList();
        private final ObservableList<Budget> budgets = FXCollections.observableArrayList();
        private final ObservableList<Recurring> recurrings = FXCollections.observableArrayList();

        AddressBookStub(Collection<Expense> expenses) {
            this.expenses.setAll(expenses);
        }

        @Override
        public ObservableList<Expense> getExpenseList() {
            return expenses;
        }

        @Override
        public ObservableList<Debt> getDebtList() {
            return debts;
        }

        @Override
        public ObservableList<Budget> getBudgetList() {
            return budgets;
        }

        @Override
        public ObservableList<Recurring> getRecurringList() {
            return recurrings;
        }

        @Override
        public void addListener(InvalidationListener listener) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeListener(InvalidationListener listener) {
            throw new AssertionError("This method should not be called.");
        }
    }

}
