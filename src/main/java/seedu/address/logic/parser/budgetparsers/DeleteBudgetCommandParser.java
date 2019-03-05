package seedu.address.logic.parser.budgetparsers;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.budgetcommands.DeleteBudgetCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.attributes.Category;

/**
 * Parses input arguments and creates a new DeleteBudgetCommand object
 */
public class DeleteBudgetCommandParser implements Parser<DeleteBudgetCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteBudgetCommand
     * and returns a DeleteBudgetCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteBudgetCommand parse(String args) throws ParseException {
        try {
            Category category = ParserUtil.parseCategory(args);
            return new DeleteBudgetCommand(category);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteBudgetCommand.MESSAGE_USAGE), pe);
        }
    }
}
