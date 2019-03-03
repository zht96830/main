package seedu.address.model.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_DEBT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARKS_EXPENSE;
import static seedu.address.testutil.TypicalExpenses.DUCK_RICE;
import static seedu.address.testutil.TypicalExpenses.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.expense.Expense;
import seedu.address.model.person.exceptions.DuplicateExpenseException;
import seedu.address.model.person.exceptions.ExpenseNotFoundException;
import seedu.address.testutil.ExpenseBuilder;

public class UniqueExpenseListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueExpenseList uniqueExpenseList = new UniqueExpenseList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueExpenseList.contains(null);
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniqueExpenseList.contains(DUCK_RICE));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniqueExpenseList.add(DUCK_RICE);
        assertTrue(uniqueExpenseList.contains(DUCK_RICE));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniqueExpenseList.add(DUCK_RICE);
        Expense editedAlice = new ExpenseBuilder(DUCK_RICE).withCategory(VALID_DEADLINE_DEBT).withTags(VALID_REMARKS_EXPENSE)
                .build();
        assertTrue(uniqueExpenseList.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueExpenseList.add(null);
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniqueExpenseList.add(DUCK_RICE);
        thrown.expect(DuplicateExpenseException.class);
        uniqueExpenseList.add(DUCK_RICE);
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueExpenseList.setExpense(null, DUCK_RICE);
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueExpenseList.setExpense(DUCK_RICE, null);
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        thrown.expect(ExpenseNotFoundException.class);
        uniqueExpenseList.setExpense(DUCK_RICE, DUCK_RICE);
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniqueExpenseList.add(DUCK_RICE);
        uniqueExpenseList.setExpense(DUCK_RICE, DUCK_RICE);
        UniqueExpenseList expectedUniqueExpenseList = new UniqueExpenseList();
        expectedUniqueExpenseList.add(DUCK_RICE);
        assertEquals(expectedUniqueExpenseList, uniqueExpenseList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniqueExpenseList.add(DUCK_RICE);
        Expense editedAlice = new ExpenseBuilder(DUCK_RICE).withCategory(VALID_DEADLINE_DEBT).withTags(VALID_REMARKS_EXPENSE)
                .build();
        uniqueExpenseList.setExpense(DUCK_RICE, editedAlice);
        UniqueExpenseList expectedUniqueExpenseList = new UniqueExpenseList();
        expectedUniqueExpenseList.add(editedAlice);
        assertEquals(expectedUniqueExpenseList, uniqueExpenseList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniqueExpenseList.add(DUCK_RICE);
        uniqueExpenseList.setExpense(DUCK_RICE, BOB);
        UniqueExpenseList expectedUniqueExpenseList = new UniqueExpenseList();
        expectedUniqueExpenseList.add(BOB);
        assertEquals(expectedUniqueExpenseList, uniqueExpenseList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniqueExpenseList.add(DUCK_RICE);
        uniqueExpenseList.add(BOB);
        thrown.expect(DuplicateExpenseException.class);
        uniqueExpenseList.setExpense(DUCK_RICE, BOB);
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueExpenseList.remove(null);
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        thrown.expect(ExpenseNotFoundException.class);
        uniqueExpenseList.remove(DUCK_RICE);
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniqueExpenseList.add(DUCK_RICE);
        uniqueExpenseList.remove(DUCK_RICE);
        UniqueExpenseList expectedUniqueExpenseList = new UniqueExpenseList();
        assertEquals(expectedUniqueExpenseList, uniqueExpenseList);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueExpenseList.setExpenses((UniqueExpenseList) null);
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        uniqueExpenseList.add(DUCK_RICE);
        UniqueExpenseList expectedUniqueExpenseList = new UniqueExpenseList();
        expectedUniqueExpenseList.add(BOB);
        uniqueExpenseList.setExpenses(expectedUniqueExpenseList);
        assertEquals(expectedUniqueExpenseList, uniqueExpenseList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueExpenseList.setExpenses((List<Expense>) null);
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniqueExpenseList.add(DUCK_RICE);
        List<Expense> expenseList = Collections.singletonList(BOB);
        uniqueExpenseList.setExpenses(expenseList);
        UniqueExpenseList expectedUniqueExpenseList = new UniqueExpenseList();
        expectedUniqueExpenseList.add(BOB);
        assertEquals(expectedUniqueExpenseList, uniqueExpenseList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Expense> listWithDuplicateExpenses = Arrays.asList(DUCK_RICE, DUCK_RICE);
        thrown.expect(DuplicateExpenseException.class);
        uniqueExpenseList.setExpenses(listWithDuplicateExpenses);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueExpenseList.asUnmodifiableObservableList().remove(0);
    }
}
