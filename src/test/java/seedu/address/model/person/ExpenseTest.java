package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_DEBT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_DEBT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_DEBT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_DEBT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARKS_EXPENSE;
import static seedu.address.testutil.TypicalExpenses.DUCK_RICE;
import static seedu.address.testutil.TypicalExpenses.BOB;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.expense.Expense;
import seedu.address.testutil.ExpenseBuilder;

public class ExpenseTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Expense expense = new ExpenseBuilder().build();
        thrown.expect(UnsupportedOperationException.class);
        expense.getTags().remove(0);
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(DUCK_RICE.isSameExpense(DUCK_RICE));

        // null -> returns false
        assertFalse(DUCK_RICE.isSameExpense(null));

        // different phone and email -> returns false
        Expense editedAlice = new ExpenseBuilder(DUCK_RICE).withAmount(VALID_AMOUNT_DEBT).withDate(VALID_CATEGORY_DEBT).build();
        assertFalse(DUCK_RICE.isSameExpense(editedAlice));

        // different name -> returns false
        editedAlice = new ExpenseBuilder(DUCK_RICE).withName(VALID_NAME_DEBT).build();
        assertFalse(DUCK_RICE.isSameExpense(editedAlice));

        // same name, same phone, different attributes -> returns true
        editedAlice = new ExpenseBuilder(DUCK_RICE).withDate(VALID_CATEGORY_DEBT).withCategory(VALID_DEADLINE_DEBT)
                .withTags(VALID_REMARKS_EXPENSE).build();
        assertTrue(DUCK_RICE.isSameExpense(editedAlice));

        // same name, same email, different attributes -> returns true
        editedAlice = new ExpenseBuilder(DUCK_RICE).withAmount(VALID_AMOUNT_DEBT).withCategory(VALID_DEADLINE_DEBT)
                .withTags(VALID_REMARKS_EXPENSE).build();
        assertTrue(DUCK_RICE.isSameExpense(editedAlice));

        // same name, same phone, same email, different attributes -> returns true
        editedAlice = new ExpenseBuilder(DUCK_RICE).withCategory(VALID_DEADLINE_DEBT).withTags(VALID_REMARKS_EXPENSE).build();
        assertTrue(DUCK_RICE.isSameExpense(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Expense aliceCopy = new ExpenseBuilder(DUCK_RICE).build();
        assertTrue(DUCK_RICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(DUCK_RICE.equals(DUCK_RICE));

        // null -> returns false
        assertFalse(DUCK_RICE.equals(null));

        // different type -> returns false
        assertFalse(DUCK_RICE.equals(5));

        // different expense -> returns false
        assertFalse(DUCK_RICE.equals(BOB));

        // different name -> returns false
        Expense editedAlice = new ExpenseBuilder(DUCK_RICE).withName(VALID_NAME_DEBT).build();
        assertFalse(DUCK_RICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new ExpenseBuilder(DUCK_RICE).withAmount(VALID_AMOUNT_DEBT).build();
        assertFalse(DUCK_RICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new ExpenseBuilder(DUCK_RICE).withDate(VALID_CATEGORY_DEBT).build();
        assertFalse(DUCK_RICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new ExpenseBuilder(DUCK_RICE).withCategory(VALID_DEADLINE_DEBT).build();
        assertFalse(DUCK_RICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new ExpenseBuilder(DUCK_RICE).withTags(VALID_REMARKS_EXPENSE).build();
        assertFalse(DUCK_RICE.equals(editedAlice));
    }
}
