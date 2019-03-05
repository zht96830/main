package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_DEBT;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EXPENSES;
import static seedu.address.testutil.TypicalExpenses.DOCTOR;
import static seedu.address.testutil.TypicalExpenses.DUCK_RICE;
import static seedu.address.testutil.TypicalExpenses.TAXI;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.expense.Expense;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.exceptions.ExpenseNotFoundException;
import seedu.address.testutil.FinanceTrackerBuilder;
import seedu.address.testutil.ExpenseBuilder;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new FinanceTracker(), new FinanceTracker(modelManager.getFinanceTracker()));
        assertEquals(null, modelManager.getSelectedExpense());
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setUserPrefs(null);
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setFinanceTrackerFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setFinanceTrackerFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setGuiSettings(null);
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setFinanceTrackerFilePath(null);
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setFinanceTrackerFilePath(path);
        assertEquals(path, modelManager.getFinanceTrackerFilePath());
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasExpense(null);
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasExpense(DUCK_RICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        modelManager.addExpense(DUCK_RICE);
        assertTrue(modelManager.hasExpense(DUCK_RICE));
    }

    @Test
    public void deletePerson_personIsSelectedAndFirstPersonInFilteredPersonList_selectionCleared() {
        modelManager.addExpense(DUCK_RICE);
        modelManager.setSelectedExpense(DUCK_RICE);
        modelManager.deleteExpense(DUCK_RICE);
        assertEquals(null, modelManager.getSelectedExpense());
    }

    @Test
    public void deletePerson_personIsSelectedAndSecondPersonInFilteredPersonList_firstPersonSelected() {
        modelManager.addExpense(DUCK_RICE);
        modelManager.addExpense(DOCTOR);
        assertEquals(Arrays.asList(DUCK_RICE, DOCTOR), modelManager.getFilteredExpenseList());
        modelManager.setSelectedExpense(DOCTOR);
        modelManager.deleteExpense(DOCTOR);
        assertEquals(DUCK_RICE, modelManager.getSelectedExpense());
    }

    @Test
    public void setPerson_personIsSelected_selectedPersonUpdated() {
        modelManager.addExpense(DUCK_RICE);
        modelManager.setSelectedExpense(DUCK_RICE);
        Expense updatedAlice = new ExpenseBuilder(DUCK_RICE).withDate(VALID_CATEGORY_DEBT).build();
        modelManager.setExpense(DUCK_RICE, updatedAlice);
        assertEquals(updatedAlice, modelManager.getSelectedExpense());
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredExpenseList().remove(0);
    }

    @Test
    public void setSelectedPerson_personNotInFilteredPersonList_throwsPersonNotFoundException() {
        thrown.expect(ExpenseNotFoundException.class);
        modelManager.setSelectedExpense(DUCK_RICE);
    }

    @Test
    public void setSelectedPerson_personInFilteredPersonList_setsSelectedPerson() {
        modelManager.addExpense(DUCK_RICE);
        assertEquals(Collections.singletonList(DUCK_RICE), modelManager.getFilteredExpenseList());
        modelManager.setSelectedExpense(DUCK_RICE);
        assertEquals(DUCK_RICE, modelManager.getSelectedExpense());
    }

    @Test
    public void equals() {
        FinanceTracker financeTracker = new FinanceTrackerBuilder().withExpense(DUCK_RICE).withExpense(TAXI).build();
        FinanceTracker differentFinanceTracker = new FinanceTracker();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(financeTracker, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(financeTracker, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different financeTracker -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentFinanceTracker, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = DUCK_RICE.getName().name.split("\\s+");
        modelManager.updateFilteredExpenseList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(financeTracker, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredExpenseList(PREDICATE_SHOW_ALL_EXPENSES);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setFinanceTrackerFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(financeTracker, differentUserPrefs)));
    }
}
