package seedu.address.logic.commands.expensecommands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.FinanceTracker;
import seedu.address.model.Model;

/**
 * Clears all expenses in Finance Tracker.
 */
public class ClearExpenseCommand extends Command {

    public static final String COMMAND_WORD = "clearexpense";

    public static final String COMMAND_WORD_SHORTCUT = "ce";

    public static final String MESSAGE_SUCCESS = "Expense list has been cleared!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.setFinanceTracker(new FinanceTracker());
        model.commitFinanceTracker();

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
