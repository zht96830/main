package seedu.address.model.debt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_DEBT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_DEBT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARKS_DEBT;
import static seedu.address.testutil.TypicalDebts.DOCTOR;
import static seedu.address.testutil.TypicalDebts.DUCK_RICE;
import static seedu.address.testutil.TypicalDebts.TAXI;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.debt.exceptions.DebtNotFoundException;
import seedu.address.testutil.DebtBuilder;

public class DebtListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final DebtList debtList = new DebtList();

    @Test
    public void contains_nullDebt_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        debtList.contains(null);
    }

    @Test
    public void contains_debtNotInList_returnsFalse() {
        assertFalse(debtList.contains(DUCK_RICE));
    }

    @Test
    public void contains_debtInList_returnsTrue() {
        debtList.add(DUCK_RICE);
        assertTrue(debtList.contains(DUCK_RICE));
    }

    @Test
    public void contains_expenseWithSameIdentityFieldsInList_returnsTrue() {
        // expenses just need to have same personOwed and amount to be classified as the same
        debtList.add(DUCK_RICE);
        Debt editedDebt = new DebtBuilder(DUCK_RICE).withCategory(VALID_CATEGORY_DEBT)
                .withDeadline(VALID_DEADLINE_DEBT).withRemarks(VALID_REMARKS_DEBT).build();
        assertTrue(debtList.contains(editedDebt));
    }

    @Test
    public void contains_debtWithSameToStringInList_returnsTrue() {
        // check if the string format of expense in expense list is same as string format of added
        debtList.add(DUCK_RICE);
        debtList.add(TAXI);
        Iterator<Debt> iter = debtList.iterator();
        assertTrue(iter.next().toString().equals(TAXI.toString())
                && iter.next().toString().equals(DUCK_RICE.toString()));
    }

    @Test
    public void add_nullDebt_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        debtList.add(null);
    }

    @Test
    public void setDebt_nullTargetDebt_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        debtList.setDebt(null, DUCK_RICE);
    }

    @Test
    public void setDebt_nullEditedDebt_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        debtList.setDebt(DUCK_RICE, null);
    }

    @Test
    public void setDebt_targetDebtNotInList_throwsDebtNotFoundException() {
        thrown.expect(DebtNotFoundException.class);
        debtList.setDebt(DUCK_RICE, DUCK_RICE);
    }

    @Test
    public void setDebt_editedDebtIsSameDebt_success() {
        debtList.add(DUCK_RICE);
        debtList.setDebt(DUCK_RICE, DUCK_RICE);
        DebtList expectedDebtList = new DebtList();
        expectedDebtList.add(DUCK_RICE);
        assertEquals(expectedDebtList, debtList);
    }

    @Test
    public void setDebt_editedDebtHasSameIdentity_success() {
        debtList.add(DUCK_RICE);
        Debt editedDebt = new DebtBuilder(DUCK_RICE).withCategory(VALID_CATEGORY_DEBT)
                .withDeadline(VALID_DEADLINE_DEBT).withRemarks(VALID_REMARKS_DEBT).build();
        debtList.setDebt(DUCK_RICE, editedDebt);
        DebtList expectedDebtList = new DebtList();
        expectedDebtList.add(editedDebt);
        assertEquals(expectedDebtList, debtList);
    }

    @Test
    public void setDebt_editedDebtHasDifferentIdentity_success() {
        debtList.add(DUCK_RICE);
        debtList.setDebt(DUCK_RICE, DOCTOR);
        DebtList expectedDebtList = new DebtList();
        expectedDebtList.add(DOCTOR);
        assertEquals(expectedDebtList, debtList);
    }

    @Test
    public void setDebts_nullDebtList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        debtList.setDebts((DebtList) null);
    }

    @Test
    public void setDebts_debtList_replacesOwnListWithProvidedDebtList() {
        debtList.add(DUCK_RICE);
        DebtList expectedDebtList = new DebtList();
        expectedDebtList.add(DOCTOR);
        debtList.setDebts(expectedDebtList);
        assertEquals(expectedDebtList, debtList);
    }

    @Test
    public void setDebts_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        debtList.setDebts((List<Debt>) null);
    }

    @Test
    public void setDebts_list_replacesOwnListWithProvidedList() {
        debtList.add(DUCK_RICE);
        List<Debt> debtList = Collections.singletonList(DOCTOR);
        this.debtList.setDebts(debtList);
        DebtList expectedDebtList = new DebtList();
        expectedDebtList.add(DOCTOR);
        assertEquals(expectedDebtList, this.debtList);
    }

    @Test
    public void remove_nullDebt_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        debtList.remove(null);
    }

    @Test
    public void remove_debtDoesNotExist_throwsDebtNotFoundException() {
        thrown.expect(DebtNotFoundException.class);
        debtList.remove(DUCK_RICE);
    }

    @Test
    public void remove_existingDebt_removesDebt() {
        debtList.add(DUCK_RICE);
        debtList.remove(DUCK_RICE);
        DebtList expectedExpenseList = new DebtList();
        assertEquals(expectedExpenseList, debtList);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        debtList.asUnmodifiableObservableList().remove(0);
    }
}
