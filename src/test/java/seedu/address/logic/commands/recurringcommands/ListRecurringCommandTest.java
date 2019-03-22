package seedu.address.logic.commands.recurringcommands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showRecurringAtIndex;
import static seedu.address.testutil.TypicalRecurrings.getTypicalFinanceTrackerWithRecurrings;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EXPENSE;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.attributes.View;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListRecurringCommand.
 */
public class ListRecurringCommandTest {
    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalFinanceTrackerWithRecurrings(), new UserPrefs());
        expectedModel = new ModelManager(model.getFinanceTracker(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListRecurringCommand(View.ALL), model, commandHistory,
                String.format(ListRecurringCommand.MESSAGE_SUCCESS, View.ALL), expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showRecurringAtIndex(model, INDEX_FIRST_EXPENSE);
        assertCommandSuccess(new ListRecurringCommand(View.ALL), model, commandHistory,
                String.format(ListRecurringCommand.MESSAGE_SUCCESS, View.ALL), expectedModel);
    }

    @Test
    public void execute_listIsNotFiltered_showsHealthCareCategory() {
        expectedModel.updateFilteredRecurringList(Model.PREDICATE_SHOW_HEALTHCARE_RECURRING);
        assertCommandSuccess(new ListRecurringCommand(View.HEALTHCARE), model, commandHistory,
                String.format(ListRecurringCommand.MESSAGE_SUCCESS, View.HEALTHCARE), expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsHealthCareCategory() {
        expectedModel.updateFilteredRecurringList(Model.PREDICATE_SHOW_HEALTHCARE_RECURRING);
        showRecurringAtIndex(model, INDEX_FIRST_EXPENSE);
        assertCommandSuccess(new ListRecurringCommand(View.HEALTHCARE), model, commandHistory,
                String.format(ListRecurringCommand.MESSAGE_SUCCESS, View.HEALTHCARE), expectedModel);
    }
}
