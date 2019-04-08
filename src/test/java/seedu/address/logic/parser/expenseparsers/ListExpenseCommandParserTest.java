package seedu.address.logic.parser.expenseparsers;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.expensecommands.ListExpenseCommand;
import seedu.address.model.attributes.View;

//@@author jamessspanggg
public class ListExpenseCommandParserTest {

    private ListExpenseCommandParser parser = new ListExpenseCommandParser();

    // Test that involves all
    @Test
    public void parse_validArgs_all_returnsListExpenseCommand() {
        assertParseSuccess(parser, " v/all", new ListExpenseCommand(View.ALL));
    }

    // Test that involves Date
    @Test
    public void parse_validArgs_month_returnsListExpenseCommand() {
        assertParseSuccess(parser, " v/month", new ListExpenseCommand(View.MONTH));
    }

    // Test that involves Amount
    @Test
    public void parse_validArgs_$100_returnsListExpenseCommand() {
        assertParseSuccess(parser, " v/$100", new ListExpenseCommand(View.$100));
    }

    // Test that involves Category
    @Test
    public void parse_validArgs_food_returnsListExpenseCommand() {
        assertParseSuccess(parser, " v/food", new ListExpenseCommand(View.FOOD));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, " v/alla", View.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidPrefix_throwsParseException() {
        assertParseFailure(parser, " ve/all", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListExpenseCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyString_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListExpenseCommand.MESSAGE_USAGE));
    }
}
