package seedu.address.logic.parser.debtparsers;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.debtcommands.ListDebtCommand;
import seedu.address.model.attributes.View;

//@@author jamessspanggg
public class ListDebtCommandParserTest {

    private ListDebtCommandParser parser = new ListDebtCommandParser();

    @Test
    public void parse_validArgs_success() {
        assertParseSuccess(parser, " v/$100", new ListDebtCommand(View.$100));
    }

    @Test
    public void parse_invalidArgs_failure() {
        assertParseFailure(parser, " v/100", View.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidPrefix_failure() {
        assertParseFailure(parser, " ve/$100", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListDebtCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyString_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListDebtCommand.MESSAGE_USAGE));
    }
}
