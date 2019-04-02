

package seedu.address.logic.commands.recurringcommands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.expense.Expense;
import seedu.address.model.recurring.Recurring;

/**
 * Adds outstanding expenses from all Recurring Expenses into expenses in the Finance Tracker.
 */
public class ConvertRecurringToExpenseCommand extends Command {

    public static final String COMMAND_WORD = "convertrecurring";

    public static final String COMMAND_WORD_SHORTCUT = "cre";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds outstanding expenses from all Recurring into "
            + "expenses in the Finance Tracker.";

    public static final String MESSAGE_CONVERT_RECURRING_SUCCESS = "All outstanding expenses from Recurring Expenses"
            + " converted into expenses successfully.\n"
            + "Number of expenses added: ";

    public ConvertRecurringToExpenseCommand() {
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Recurring> lastShownRecurringList = model.getFilteredRecurringList();
        int numAdded = 0;

        for (int i = 0; i < lastShownRecurringList.size(); i++) {
            Recurring recurring = lastShownRecurringList.get(i);
            LocalDate lastConvertedDate = recurring.getLastConvertedDate();
            // If the recurring's lastConvertedDate is before today, check if it has outstanding expenses to be added.
            if (lastConvertedDate.isBefore(LocalDate.now())) {
                ArrayList<Expense> recurringListOfExpenses = recurring.getRecurringListOfExpenses();
                for (int j = 0; j < recurringListOfExpenses.size(); j++) {
                    LocalDate date = recurringListOfExpenses.get(j).getDate().getLocalDate();
                    if ((date.isBefore(LocalDate.now()) || date.isEqual(LocalDate.now()))
                            && date.isAfter(lastConvertedDate)) {
                        model.addExpense(recurringListOfExpenses.get(j));
                        model.commitFinanceTracker();
                        numAdded++;
                    }
                }
            }
            recurring.setLastConvertedDate(LocalDate.now());
        }

        return new CommandResult(String.format(MESSAGE_CONVERT_RECURRING_SUCCESS + numAdded + "."));
    }
}
