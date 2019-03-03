package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.*;
import seedu.address.logic.commands.budgetcommands.AddBudgetCommand;
import seedu.address.logic.commands.budgetcommands.DeleteBudgetCommand;
import seedu.address.logic.commands.budgetcommands.EditBudgetCommand;
import seedu.address.logic.commands.debtcommands.AddDebtCommand;
import seedu.address.logic.commands.debtcommands.DeleteDebtCommand;
import seedu.address.logic.commands.debtcommands.EditDebtCommand;
import seedu.address.logic.commands.expensecommands.AddCommand;
import seedu.address.logic.commands.expensecommands.DeleteCommand;
import seedu.address.logic.commands.expensecommands.EditCommand;
import seedu.address.logic.commands.generalcommands.*;
import seedu.address.logic.commands.recurringcommands.AddRecurringCommand;
import seedu.address.logic.commands.recurringcommands.DeleteRecurringCommand;
import seedu.address.logic.commands.recurringcommands.EditRecurringCommand;
import seedu.address.logic.parser.budgetparsers.AddBudgetCommandParser;
import seedu.address.logic.parser.budgetparsers.EditBudgetCommandParser;
import seedu.address.logic.parser.debtparsers.AddDebtCommandParser;
import seedu.address.logic.parser.debtparsers.DeleteDebtCommandParser;
import seedu.address.logic.parser.debtparsers.EditDebtCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.expenseparsers.AddCommandParser;
import seedu.address.logic.parser.expenseparsers.DeleteCommandParser;
import seedu.address.logic.parser.expenseparsers.EditCommandParser;
import seedu.address.logic.parser.recurringparsers.AddRecurringCommandParser;
import seedu.address.logic.parser.recurringparsers.DeleteRecurringCommandParser;
import seedu.address.logic.parser.recurringparsers.EditRecurringCommandParser;

/**
 * Parses user input.
 */
public class FinanceTrackerParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case AddDebtCommand.COMMAND_WORD:
            return new AddDebtCommandParser().parse(arguments);

        case AddRecurringCommand.COMMAND_WORD:
            return new AddRecurringCommandParser().parse(arguments);

        case AddBudgetCommand.COMMAND_WORD:
            return new AddBudgetCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case EditDebtCommand.COMMAND_WORD:
            return new EditDebtCommandParser().parse(arguments);

        case EditRecurringCommand.COMMAND_WORD:
            return new EditRecurringCommandParser().parse(arguments);

        case EditBudgetCommand.COMMAND_WORD:
            return new EditBudgetCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD:
            return new SelectCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case DeleteDebtCommand.COMMAND_WORD:
            return new DeleteDebtCommandParser().parse(arguments);

        case DeleteBudgetCommand.COMMAND_WORD:
            return new DeleteBudgetCommandParser().parse(arguments);

        case DeleteRecurringCommand.COMMAND_WORD:
            return new DeleteRecurringCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
