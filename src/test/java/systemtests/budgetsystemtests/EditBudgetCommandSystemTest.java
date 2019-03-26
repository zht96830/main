package systemtests.budgetsystemtests;

import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.AMOUNT_DESC_BUDGET;
import static seedu.address.logic.commands.CommandTestUtil.CATEGORY_DESC_BUDGET;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CATEGORY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ENDDATE_DESC_EXIST;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ENDDATE_DESC_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_STARTDATE_DESC_EXIST;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_STARTDATE_DESC_FORMAT;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.budgetcommands.EditBudgetCommand;
import seedu.address.model.Model;
import seedu.address.model.attributes.Amount;
import seedu.address.model.attributes.Category;
import seedu.address.model.attributes.Date;
import systemtests.FinanceTrackerSystemTest;

public class EditBudgetCommandSystemTest extends FinanceTrackerSystemTest {

    @Test
    public void edit() {
        Model model = getModel();

        /* ----------------- Performing edit operation while an unfiltered list is being shown ---------------------- */

        /* Case: edit all fields, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> edited
         */
        /*Category category = Category.valueOf(VALID_CATEGORY_BUDGET.toUpperCase());
        String command = " " + EditBudgetCommand.COMMAND_WORD + "  " + category + "  " + AMOUNT_DESC_BUDGET + "    "
                + STARTDATE_DESC_BUDGET + "  " + ENDDATE_DESC_BUDGET + " "
                + REMARKS_DESC_BUDGET + "   ";
        Budget editedBudget = new BudgetBuilder(BUDGET).build();
        assertCommandSuccess(command, category, editedBudget);
*/
        /* Case: undo editing the last budget in the list -> last budget restored */
        /*command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);
*/
        /* Case: redo editing the last budget in the list -> last budget edited again */
        /*command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        int index = -1;
        for (Budget budget : model.getFilteredBudgetList()) {
            if (budget.getCategory() == category) {
                index = model.getFilteredBudgetList().indexOf(budget);
            }
        }
        model.setBudget(model.getFilteredBudgetList().get(index), editedBudget);
        assertCommandSuccess(command, model, expectedResultMessage);
*/
        /* Case: edit a budget with new values same as existing values -> edited */
        /*command = EditBudgetCommand.COMMAND_WORD + " " + category.toString() + AMOUNT_DESC_BUDGET
                + STARTDATE_DESC_BUDGET + ENDDATE_DESC_BUDGET + REMARKS_DESC_BUDGET;
        assertCommandSuccess(command, category, BUDGET);
*/
        /* Case: edit a budget with new values same as another budget's values -> edited
         * In this case, test edits travel budget with values to be the same as food budget except for the category
         */
        /*assertTrue(getModel().getFinanceTracker().getExpenseList().contains(BUDGET));
        category = TRAVEL;
        assertNotEquals(getModel().getFilteredBudgetList().get(index), BUDGET);
        index = -1;
        for (Budget budget : model.getFilteredBudgetList()) {
            if (budget.getCategory() == category) {
                index = model.getFilteredBudgetList().indexOf(budget);
            }
        }
        command = EditBudgetCommand.COMMAND_WORD + " " + category.toString() + AMOUNT_DESC_BUDGET
                + STARTDATE_DESC_BUDGET + ENDDATE_DESC_BUDGET + REMARKS_DESC_BUDGET;
        editedBudget = new BudgetBuilder(BUDGET).withCategory(category.toString()).build();
        assertCommandSuccess(command, category, editedBudget);
*/
        /* Case: edit a budget with new values same as another budget's values but with different amount
         * -> edited
         */
        /*assertTrue(getModel().getFinanceTracker().getExpenseList().contains(BUDGET));
        category = TRAVEL;
        assertNotEquals(getModel().getFilteredBudgetList().get(index), BUDGET);
        index = -1;
        for (Budget budget : model.getFilteredBudgetList()) {
            if (budget.getCategory() == category) {
                index = model.getFilteredBudgetList().indexOf(budget);
            }
        }
        command = EditBudgetCommand.COMMAND_WORD + " " + category.toString() + AMOUNT_DESC_EXPENSE
                + STARTDATE_DESC_BUDGET + ENDDATE_DESC_BUDGET + REMARKS_DESC_BUDGET;
        editedBudget = new BudgetBuilder(BUDGET).withCategory(category.toString()).withAmount(VALID_AMOUNT_EXPENSE)
                .build();
        assertCommandSuccess(command, category, editedBudget);
*/
        /* --------------------- Performing edit operation while an expense card is selected -------------------------*/
        // implement listbudget and selectbudget first
        /* Case: selects first card in the budget list, edit a budget -> edited, card selection remains unchanged but
         * browser url changes
         */

        /*showAllExpenses();
        index = INDEX_FIRST_EXPENSE;

        selectExpense(index);

        command = EditExpenseCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_EXPENSE + AMOUNT_DESC_EXPENSE
                + CATEGORY_DESC_EXPENSE + DATE_DESC_EXPENSE + REMARKS_DESC_EXPENSE;
        // this can be misleading: card selection actually remains unchanged but the
        // browser's url is updated to reflect the new expense's name
        assertCommandSuccess(command, index, EXPENSE, index);
        */

        /* --------------------------------- Performing invalid edit operation -------------------------------------- */

        /* Case: invalid category -> rejected */
        assertCommandFailure(EditBudgetCommand.COMMAND_WORD + INVALID_CATEGORY_DESC + AMOUNT_DESC_BUDGET,
                             Category.MESSAGE_CONSTRAINTS);

        /* Case: invalid category (budget does not exist) -> rejected */
        /*assertCommandFailure(EditBudgetCommand.COMMAND_WORD + " c/others" + AMOUNT_DESC_BUDGET,
                Messages.MESSAGE_BUDGET_DOES_NOT_EXIST_FOR_CATEGORY);*/

        /* Case: missing category -> rejected */
        assertCommandFailure(EditBudgetCommand.COMMAND_WORD + AMOUNT_DESC_BUDGET,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditBudgetCommand.MESSAGE_USAGE));

        /* Case: missing all fields -> rejected */
        assertCommandFailure(EditBudgetCommand.COMMAND_WORD + CATEGORY_DESC_BUDGET,
                EditBudgetCommand.MESSAGE_NOT_EDITED);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("EdITbudGet" + CATEGORY_DESC_BUDGET + AMOUNT_DESC_BUDGET, MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid amount -> rejected */
        assertCommandFailure(EditBudgetCommand.COMMAND_WORD + CATEGORY_DESC_BUDGET
                + INVALID_AMOUNT_DESC, Amount.MESSAGE_CONSTRAINTS);

        /* Case: invalid start date format -> rejected */
        assertCommandFailure(EditBudgetCommand.COMMAND_WORD + CATEGORY_DESC_BUDGET
                + INVALID_STARTDATE_DESC_FORMAT, Date.MESSAGE_CONSTRAINTS);

        /* Case: invalid start date -> rejected */
        assertCommandFailure(EditBudgetCommand.COMMAND_WORD + CATEGORY_DESC_BUDGET
                + INVALID_STARTDATE_DESC_EXIST, Date.MESSAGE_DATE_DOES_NOT_EXIST);

        /* Case: invalid end date format -> rejected */
        assertCommandFailure(EditBudgetCommand.COMMAND_WORD + CATEGORY_DESC_BUDGET
                + INVALID_ENDDATE_DESC_FORMAT, Date.MESSAGE_CONSTRAINTS);

        /* Case: invalid end date -> rejected */
        assertCommandFailure(EditBudgetCommand.COMMAND_WORD + CATEGORY_DESC_BUDGET
                + INVALID_ENDDATE_DESC_EXIST, Date.MESSAGE_DATE_DOES_NOT_EXIST);
        /* Case: start date is before today -> rejected */
        /* Case: end date is before start date -> rejected */
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Category, Budget, Category)} except that
     * the browser url and selected card remain unchanged.
     * @see EditBudgetCommandSystemTest#assertCommandSuccess(String, Category, Budget, Category)
     */
    /*private void assertCommandSuccess(String command, Category categoryToEdit, Budget editedBudget) {
        assertCommandSuccess(command, categoryToEdit, editedBudget, null);
    }
*/
    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,<br>
     * 1. Asserts that result display box displays the success message of executing {@code EditBudgetCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the budget of category {@code categoryToEdit}
     * being updated to values specified {@code editedBudget}.<br>
     * @see EditBudgetCommandSystemTest#assertCommandSuccess(String, Model, String, Category)
     */
    /*private void assertCommandSuccess(String command, Category categoryToEdit, Budget editedBudget,
                                      Category expectedSelectedBudgetCardCategory) {
        Model expectedModel = getModel();
        int index = -1;
        for (Budget budget : expectedModel.getFilteredBudgetList()) {
            if (budget.getCategory() == categoryToEdit) {
                index = expectedModel.getFilteredBudgetList().indexOf(budget);
            }
        }
        expectedModel.setBudget(expectedModel.getFilteredBudgetList().get(index), editedBudget);
        expectedModel.updateFilteredBudgetList(PREDICATE_SHOW_ALL_BUDGETS);

        assertCommandSuccess(command, expectedModel, String.format(EditBudgetCommand.MESSAGE_EDIT_BUDGET_SUCCESS,
                editedBudget), expectedSelectedBudgetCardCategory);
    }
*/

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Category)} except that the
     * browser url and selected card remain unchanged.
     * @see EditBudgetCommandSystemTest#assertCommandSuccess(String, Model, String, Category)
     */
    /*private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }
*/
    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url and selected card update accordingly depending on the card at
     * {@code expectedSelectedCardIndex}.<br>
     * 4. Asserts that the status bar's sync status changes.<br>
     * 5. Asserts that the command box has the default style class.<br>
     * Verifications 1 and 2 are performed by
     * {@code FinanceTrackerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see FinanceTrackerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see FinanceTrackerSystemTest#assertSelectedCardChanged(Index)
     */
    /*private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Category expectedSelectedCardCategory) {
        executeCommand(command);
        expectedModel.updateFilteredBudgetList(PREDICATE_SHOW_ALL_BUDGETS);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        if (expectedSelectedCardCategory != null) {
            assertSelectedCardChanged(expectedSelectedCardCategory);
        } else {
            assertSelectedCardUnchanged();
        }
        assertStatusBarUnchangedExceptSyncStatus();
    }*/

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 4. Asserts that the command box has the error style.<br>
     * Verifications 1 and 2 are performed by
     * {@code FinanceTrackerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see FinanceTrackerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
