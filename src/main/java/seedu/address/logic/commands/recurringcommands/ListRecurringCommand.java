package seedu.address.logic.commands.recurringcommands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VIEW;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_RECURRING;


/**
 * Lists all recurring in the Finance Tracker to the user.
 */
public class ListRecurringCommand extends Command {

    public static final String COMMAND_WORD = "listrecurring";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an recurring to the Finance Tracker. "
            + "Parameters: "
            + "[" + PREFIX_VIEW + "VIEW]";

    public static final String MESSAGE_SUCCESS = "Listed recurring";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {

        requireNonNull(model);
        model.updateFilteredRecurringList(PREDICATE_SHOW_ALL_RECURRING);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
