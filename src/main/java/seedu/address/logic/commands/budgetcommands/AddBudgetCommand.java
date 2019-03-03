package seedu.address.logic.commands.budgetcommands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.expense.Expense;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARKS;

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
            + PREFIX_NAME + "Char Kuey Teow "
            + PREFIX_AMOUNT + "300 "
            + PREFIX_CATEGORY + "food "
            + PREFIX_DATE + "13-01-1996 "
            + PREFIX_REMARKS + "My lunch for tuesday";

    public static final String MESSAGE_SUCCESS = "New expense added:\n%1$s";

    private final Expense toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Expense}
     */
    public AddCommand(Expense expense) {
        requireNonNull(expense);
        toAdd = expense;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        model.addExpense(toAdd);
        model.commitFinanceTracker();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.address.logic.commands.expensecommands.AddCommand // instanceof handles nulls
                && toAdd.equals(((seedu.address.logic.commands.expensecommands.AddCommand) other).toAdd));
    }
}
