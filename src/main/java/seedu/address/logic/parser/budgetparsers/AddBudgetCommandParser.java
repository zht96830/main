package seedu.address.logic.parser.budgetparsers;

import seedu.address.logic.commands.debtcommands.AddDebtCommand;
import seedu.address.logic.parser.*;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.attributes.Amount;
import seedu.address.model.attributes.Category;
import seedu.address.model.attributes.Date;
import seedu.address.model.budget.Budget;

import java.util.stream.Stream;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARKS;

/**
 * Parses input arguments and creates a new AddDebtCommand object
 */
public class AddBudgetCommandParser implements Parser<AddBudgetCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddDebtCommand
     * and returns an AddDebtCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddBudgetCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_AMOUNT, PREFIX_CATEGORY,
                        PREFIX_STARTDATE, PREFIX_ENDDATE);

        if (!arePrefixesPresent(argMultimap, PREFIX_AMOUNT, PREFIX_CATEGORY, PREFIX_STARTDATE, PREFIX_ENDDATE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddDebtCommand.MESSAGE_USAGE));
        }

        Amount amount = ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get());
        Category category = ParserUtil.parseCategory(argMultimap.getValue(PREFIX_CATEGORY).get());
        Date startDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_STARTDATE).get());
        Date endDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_ENDDATE).get());
        String remarks = null;
        if (argMultimap.getValue(PREFIX_REMARKS).isPresent()) {
            remarks = argMultimap.getValue(PREFIX_REMARKS).get();
        }

        Budget budget = new Budget(category, amount, startDate, endDate, remarks);

        return new AddBudgetCommand(budget);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}

