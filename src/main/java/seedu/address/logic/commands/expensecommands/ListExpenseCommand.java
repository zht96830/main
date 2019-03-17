package seedu.address.logic.commands.expensecommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VIEW;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EXPENSES;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;



/**
 * Lists all expenses in the Finance Tracker to the user.
 */
public class ListExpenseCommand extends Command {

    public static final String COMMAND_WORD = "listexpense";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an expense to the Finance Tracker. "
            + "Parameters: "
            + "[" + PREFIX_VIEW + "VIEW]";

    public static final String MESSAGE_SUCCESS = "Listed expenses";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {

        requireNonNull(model);
        model.updateFilteredExpenseList(PREDICATE_SHOW_ALL_EXPENSES);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
