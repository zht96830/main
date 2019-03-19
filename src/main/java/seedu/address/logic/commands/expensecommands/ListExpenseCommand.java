package seedu.address.logic.commands.expensecommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VIEW;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EXPENSES;
import static seedu.address.model.Model.PREDICATE_SHOW_DAY_EXPENSES;
import static seedu.address.model.Model.PREDICATE_SHOW_ENT_EXPENSES;
import static seedu.address.model.Model.PREDICATE_SHOW_FOOD_EXPENSES;
import static seedu.address.model.Model.PREDICATE_SHOW_HEALTHCARE_EXPENSES;
import static seedu.address.model.Model.PREDICATE_SHOW_MONTH_EXPENSES;
import static seedu.address.model.Model.PREDICATE_SHOW_OTHERS_EXPENSES;
import static seedu.address.model.Model.PREDICATE_SHOW_SHOPPING_EXPENSES;
import static seedu.address.model.Model.PREDICATE_SHOW_TRANSPORT_EXPENSES;
import static seedu.address.model.Model.PREDICATE_SHOW_TRAVEL_EXPENSES;
import static seedu.address.model.Model.PREDICATE_SHOW_UTIL_EXPENSES;
import static seedu.address.model.Model.PREDICATE_SHOW_WEEK_EXPENSES;
import static seedu.address.model.Model.PREDICATE_SHOW_WORK_EXPENSES;
import static seedu.address.model.Model.PREDICATE_SHOW_YEAR_EXPENSES;

import java.util.function.Predicate;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.attributes.View;
import seedu.address.model.expense.Expense;


/**
 * Lists all expenses in the Finance Tracker to the user.
 */
public class ListExpenseCommand extends Command {

    public static final String COMMAND_WORD = "listexpense";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an expense to the Finance Tracker. "
            + "Parameters: "
            + PREFIX_VIEW + "VIEW\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_VIEW + "food";

    public static final String MESSAGE_SUCCESS = "Listed expenses under:\n%1$s";

    private final View view;

    public ListExpenseCommand(View view) {
        requireNonNull(view);
        this.view = view;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredExpenseList(this.getPredicate(this.view));
        return new CommandResult(String.format(MESSAGE_SUCCESS, this.view.toString()));
    }

    public Predicate<Expense> getPredicate(View view) {
        switch (view) {
        case DAY:
            return PREDICATE_SHOW_DAY_EXPENSES;
        case WEEK:
            return PREDICATE_SHOW_WEEK_EXPENSES;
        case MONTH:
            return PREDICATE_SHOW_MONTH_EXPENSES;
        case YEAR:
            return PREDICATE_SHOW_YEAR_EXPENSES;
        case FOOD:
            return PREDICATE_SHOW_FOOD_EXPENSES;
        case WORK:
            return PREDICATE_SHOW_WORK_EXPENSES;
        case TRAVEL:
            return PREDICATE_SHOW_TRAVEL_EXPENSES;
        case OTHERS:
            return PREDICATE_SHOW_OTHERS_EXPENSES;
        case ENTERTAINMENT:
            return PREDICATE_SHOW_ENT_EXPENSES;
        case SHOPPING:
            return PREDICATE_SHOW_SHOPPING_EXPENSES;
        case TRANSPORT:
            return PREDICATE_SHOW_TRANSPORT_EXPENSES;
        case UTILITIES:
            return PREDICATE_SHOW_UTIL_EXPENSES;
        case HEALTHCARE:
            return PREDICATE_SHOW_HEALTHCARE_EXPENSES;
        default:
            return PREDICATE_SHOW_ALL_EXPENSES;
        }
    }
}
