package seedu.address.logic.commands.budgetcommands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.budget.Budget;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;

/**
 * Adds a budget to the Finance Tracker.
 */
public class AddBudgetCommand extends Command {

    public static final String COMMAND_WORD = "addbudget";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a budget to the Finance Tracker. "
            + "Parameters: "
            + PREFIX_AMOUNT + "AMOUNT "
            + PREFIX_CATEGORY + "CATEGORY "
            + PREFIX_STARTDATE + "START_DATE "
            + PREFIX_ENDDATE + "END_DATE\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_AMOUNT + "300 "
            + PREFIX_CATEGORY + "food "
            + PREFIX_STARTDATE + "01-02-2019 "
            + PREFIX_ENDDATE + "28-02-2019";

    public static final String MESSAGE_SUCCESS = "New expense added:\n%1$s";

    private final Budget toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Expense}
     */
    public AddBudgetCommand(Budget budget) {
        requireNonNull(budget   );
        toAdd = budget;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        model.addBudget(toAdd);
        model.commitFinanceTracker();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddBudgetCommand // instanceof handles nulls
                && toAdd.equals(((AddBudgetCommand) other).toAdd));
    }
}
