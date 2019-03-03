package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

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
import seedu.address.model.expense.Expense;
import seedu.address.model.person.exceptions.DuplicateExpenseException;
import seedu.address.testutil.PersonBuilder;

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
        FinanceTracker newData = getTypicalAddressBook();
        financeTracker.resetData(newData);
        assertEquals(newData, financeTracker);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two expenses with the same identity fields
        Expense editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Expense> newExpenses = Arrays.asList(ALICE, editedAlice);
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
        assertFalse(financeTracker.hasExpense(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        financeTracker.addExpense(ALICE);
        assertTrue(financeTracker.hasExpense(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        financeTracker.addExpense(ALICE);
        Expense editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
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
        financeTracker.addExpense(ALICE);
        assertEquals(1, counter.get());
    }

    @Test
    public void removeListener_withInvalidationListener_listenerRemoved() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        financeTracker.addListener(listener);
        financeTracker.removeListener(listener);
        financeTracker.addExpense(ALICE);
        assertEquals(0, counter.get());
    }

    /**
     * A stub ReadOnlyFinanceTracker whose expenses list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyFinanceTracker {
        private final ObservableList<Expense> expenses = FXCollections.observableArrayList();

        AddressBookStub(Collection<Expense> expenses) {
            this.expenses.setAll(expenses);
        }

        @Override
        public ObservableList<Expense> getExpenseList() {
            return expenses;
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
