package seedu.address.logic.parser.statsparsers;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENDDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTDATE;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import seedu.address.logic.commands.statscommands.StatsCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.attributes.Date;

/**
 * Parses input arguments and creates a new StatsCommand object
 */
public class StatsCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the StatsCommand
     * and returns a StatsCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */

    public StatsCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_STARTDATE, PREFIX_ENDDATE, PREFIX_CATEGORY);
        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatsCommand.MESSAGE_USAGE));
        }

        Date startDate;
        Date endDate;

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        if (argMultimap.getValue(PREFIX_ENDDATE).isPresent()) {
            endDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_ENDDATE).get());
            if (argMultimap.getValue(PREFIX_STARTDATE).isPresent()) {
                startDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_STARTDATE).get());
            } else {
                startDate = new Date(dtf.format(endDate.getLocalDate().minusMonths(1)));
            }
        } else {
            if (argMultimap.getValue(PREFIX_STARTDATE).isPresent()) {
                startDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_STARTDATE).get());
                endDate = new Date(dtf.format(startDate.getLocalDate().plusMonths(1)));
            } else {
                endDate = new Date(dtf.format(LocalDate.now()));
                startDate = new Date(dtf.format(endDate.getLocalDate().minusMonths(1)));
            }
        }

        return new StatsCommand(startDate, endDate);
    }
}
