package seedu.address.logic.commands.budgetcommands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.budget.Budget;

/**
 * Clears all budgets in Finance Tracker.
 */
public class ClearBudgetCommand extends Command {

    public static final String COMMAND_WORD = "clearbudget";

    public static final String COMMAND_WORD_SHORTCUT = "cb";

    public static final String MESSAGE_SUCCESS = "Budget list has been cleared!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        for (Budget budget : model.getFinanceTracker().getBudgetList()) {
            model.deleteBudget(budget);
        }
        model.commitFinanceTracker();

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
