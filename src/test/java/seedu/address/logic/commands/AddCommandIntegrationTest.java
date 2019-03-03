package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalExpenses.getTypicalFinanceTrackerWithExpenses;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.expensecommands.AddCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.expense.Expense;
import seedu.address.testutil.ExpenseBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalFinanceTrackerWithExpenses(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        Expense validExpense = new ExpenseBuilder().build();

        Model expectedModel = new ModelManager(model.getFinanceTracker(), new UserPrefs());
        expectedModel.addExpense(validExpense);
        expectedModel.commitFinanceTracker();

        assertCommandSuccess(new AddCommand(validExpense), model, commandHistory,
                String.format(AddCommand.MESSAGE_SUCCESS, validExpense), expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Expense expenseInList = model.getFinanceTracker().getExpenseList().get(0);
        assertCommandFailure(new AddCommand(expenseInList), model, commandHistory,
                AddCommand.MESSAGE_DUPLICATE_PERSON);
    }

}
