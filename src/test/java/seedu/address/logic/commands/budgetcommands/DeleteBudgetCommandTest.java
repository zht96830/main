package seedu.address.logic.commands.budgetcommands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.model.attributes.Category.FOOD;
import static seedu.address.model.attributes.Category.OTHERS;
import static seedu.address.model.attributes.Category.UTILITIES;
import static seedu.address.model.attributes.Category.WORK;
import static seedu.address.testutil.TypicalBudgets.getTypicalFinanceTrackerWithBudgets;

import org.junit.Test;
import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.generalcommands.RedoCommand;
import seedu.address.logic.commands.generalcommands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.budget.Budget;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteBudgetCommand}.
 */
public class DeleteBudgetCommandTest {
    private Model model = new ModelManager(getTypicalFinanceTrackerWithBudgets(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validCategoryFilteredList_success() {
        int index = -1;
        for (Budget budget : model.getFilteredBudgetList()) {
            if (budget.getCategory() == FOOD) {
                index = model.getFilteredBudgetList().indexOf(budget);
            }
        }
        Budget budgetToDelete = model.getFilteredBudgetList().get(index);
        DeleteBudgetCommand deleteBudgetCommand = new DeleteBudgetCommand(FOOD);

        String expectedMessage = String.format(DeleteBudgetCommand.MESSAGE_DELETE_BUDGET_SUCCESS, budgetToDelete);

        ModelManager expectedModel = new ModelManager(model.getFinanceTracker(), new UserPrefs());
        expectedModel.deleteBudget(budgetToDelete);
        expectedModel.commitFinanceTracker();

        assertCommandSuccess(deleteBudgetCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidCategoryFilteredList_throwsCommandException() {
        int index = -1;
        for (Budget budget : model.getFilteredBudgetList()) {
            if (budget.getCategory() == OTHERS) {
                index = model.getFilteredBudgetList().indexOf(budget);
            }
        }
        DeleteBudgetCommand deleteBudgetCommand = new DeleteBudgetCommand(OTHERS);

        assertCommandFailure(deleteBudgetCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_BUDGET_CATEGORY);
    }

    @Test
    public void executeUndoRedo_validCategoryUnfilteredList_success() throws Exception {
        int index = -1;
        for (Budget budget : model.getFilteredBudgetList()) {
            if (budget.getCategory() == FOOD) {
                index = model.getFilteredBudgetList().indexOf(budget);
            }
        }
        Budget budgetToDelete = model.getFilteredBudgetList().get(index);
        DeleteBudgetCommand deleteBudgetCommand = new DeleteBudgetCommand(FOOD);

        Model expectedModel = new ModelManager(model.getFinanceTracker(), new UserPrefs());
        expectedModel.deleteBudget(budgetToDelete);
        expectedModel.commitFinanceTracker();

        // delete -> food budget deleted
        deleteBudgetCommand.execute(model, commandHistory);

        // undo -> reverts finance tracker back to previous state and filtered expense list to show all budgets
        expectedModel.undoFinanceTracker();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same budget (food) deleted again
        expectedModel.redoFinanceTracker();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidCategoryUnfilteredList_failure() {
        DeleteBudgetCommand deleteBudgetCommand = new DeleteBudgetCommand(OTHERS);

        // execution failed -> finance tracker state not added into model
        assertCommandFailure(deleteBudgetCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_BUDGET_CATEGORY);

        // single finance tracker state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Deletes a {@code Budget} from a filtered list.
     * 2. Undo the deletion.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously deleted expense in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the deletion. This ensures {@code RedoCommand} deletes the expense object regardless of indexing.
     */
    /*@Test
    public void executeUndoRedo_validIndexFilteredList_samePersonDeleted() throws Exception {
        DeleteExpenseCommand deleteExpenseCommand = new DeleteExpenseCommand(INDEX_FIRST_EXPENSE);
        Model expectedModel = new ModelManager(model.getFinanceTracker(), new UserPrefs());

        showPersonAtIndex(model, INDEX_SECOND_EXPENSE);
        Expense expenseToDelete = model.getFilteredExpenseList().get(INDEX_FIRST_EXPENSE.getZeroBased());
        expectedModel.deleteExpense(expenseToDelete);
        expectedModel.commitFinanceTracker();

        // delete -> deletes second expense in unfiltered expense list / first expense in filtered expense list
        deleteExpenseCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered expense list to show all persons
        expectedModel.undoFinanceTracker();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(expenseToDelete, model.getFilteredExpenseList().get(INDEX_FIRST_EXPENSE.getZeroBased()));
        // redo -> deletes same second expense in unfiltered expense list
        expectedModel.redoFinanceTracker();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }*/

    @Test
    public void equals() {
        DeleteBudgetCommand deleteUtilitiesCommand = new DeleteBudgetCommand(UTILITIES);
        DeleteBudgetCommand deleteWorkCommand = new DeleteBudgetCommand(WORK);

        // same object -> returns true
        assertTrue(deleteWorkCommand.equals(deleteWorkCommand));

        // different types -> returns false
        assertFalse(deleteWorkCommand.equals(1));

        // null -> returns false
        assertFalse(deleteWorkCommand.equals(null));

        // different expense -> returns false
        assertFalse(deleteWorkCommand.equals(deleteUtilitiesCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show nothing.
     */
    private void showNothing(Model model) {
        model.updateFilteredBudgetList(p -> false);

        assertTrue(model.getFilteredBudgetList().isEmpty());
    }
}
