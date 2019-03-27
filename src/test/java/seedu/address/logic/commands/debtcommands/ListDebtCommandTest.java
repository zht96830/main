package seedu.address.logic.commands.debtcommands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showDebtAtIndex;
import static seedu.address.testutil.TypicalDebts.getTypicalFinanceTrackerWithDebts;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EXPENSE;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.attributes.View;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListDebtCommand.
 */
public class ListDebtCommandTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalFinanceTrackerWithDebts(), new UserPrefs());
        expectedModel = new ModelManager(model.getFinanceTracker(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListDebtCommand(View.ALL), model, commandHistory,
                String.format(ListDebtCommand.MESSAGE_SUCCESS, View.ALL.getMessage()), expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showDebtAtIndex(model, INDEX_FIRST_EXPENSE);
        assertCommandSuccess(new ListDebtCommand(View.ALL), model, commandHistory,
                String.format(ListDebtCommand.MESSAGE_SUCCESS, View.ALL.getMessage()), expectedModel);
    }

    @Test
    public void execute_listIsNotFiltered_showsHealthCareCategory() {
        expectedModel.updateFilteredDebtList(Model.PREDICATE_SHOW_HEALTHCARE_DEBTS);
        assertCommandSuccess(new ListDebtCommand(View.HEALTHCARE), model, commandHistory,
                String.format(ListDebtCommand.MESSAGE_SUCCESS, View.HEALTHCARE.getMessage()), expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsHealthCareCategory() {
        expectedModel.updateFilteredDebtList(Model.PREDICATE_SHOW_HEALTHCARE_DEBTS);
        showDebtAtIndex(model, INDEX_FIRST_EXPENSE);
        assertCommandSuccess(new ListDebtCommand(View.HEALTHCARE), model, commandHistory,
                String.format(ListDebtCommand.MESSAGE_SUCCESS, View.HEALTHCARE.getMessage()), expectedModel);
    }

}
