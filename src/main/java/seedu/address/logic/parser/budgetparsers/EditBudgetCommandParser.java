package seedu.address.logic.parser.budgetparsers;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.budgetcommands.EditBudgetCommand;
import seedu.address.logic.commands.expensecommands.EditCommand;
import seedu.address.logic.parser.*;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.attributes.Category;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARKS;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditBudgetCommandParser implements Parser<EditBudgetCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditBudgetCommand
     * and returns an EditBudgetCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditBudgetCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_AMOUNT, PREFIX_CATEGORY,
                        PREFIX_STARTDATE, PREFIX_ENDDATE,PREFIX_REMARKS);

        Category category;

        try {
            category = ParserUtil.parseCategory(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditBudgetCommand.MESSAGE_USAGE), pe);
        }

        EditBudgetCommand.EditBudgetDescriptor editBudgetDescriptor = new EditBudgetCommand.EditBudgetDescriptor();
        if (argMultimap.getValue(PREFIX_AMOUNT).isPresent()) {
            editBudgetDescriptor.setAmount(ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get()));
        }

        if (argMultimap.getValue(PREFIX_STARTDATE).isPresent()) {
            editBudgetDescriptor.setStartDate(ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get()));
        }

        if (argMultimap.getValue(PREFIX_ENDDATE).isPresent()) {
            editBudgetDescriptor.setEndDate(ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get()));
        }

        if (argMultimap.getValue(PREFIX_REMARKS).isPresent()) {
            editBudgetDescriptor.setRemarks(argMultimap.getValue(PREFIX_REMARKS).get());
        }


        if (!editBudgetDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditBudgetCommand.MESSAGE_NOT_EDITED);
        }

        return new EditBudgetCommand(category, editBudgetDescriptor);
    }
}
