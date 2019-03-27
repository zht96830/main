package seedu.address.logic.parser.debtparsers;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.debtcommands.ListDebtCommand;
import seedu.address.model.attributes.View;

public class ListDebtCommandParserTest {

    private ListDebtCommandParser parser = new ListDebtCommandParser();

    @Test
    public void parse_validArgs_success() {
        assertParseSuccess(parser, " v/all", new ListDebtCommand(View.ALL));
    }

    @Test
    public void parse_invalidArgs_failure() {
        assertParseFailure(parser, " v/alla", View.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidPrefix_failure() {
        assertParseFailure(parser, " ve/alla", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListDebtCommand.MESSAGE_USAGE));
    }
}
