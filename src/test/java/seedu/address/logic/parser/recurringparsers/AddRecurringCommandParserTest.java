package seedu.address.logic.parser.recurringparsers;


import org.junit.Test;
import seedu.address.logic.commands.recurringcommands.AddRecurringCommand;
import seedu.address.model.recurring.Recurring;
import seedu.address.testutil.RecurringBuilder;

import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalRecurrings.RECURRING;


public class AddRecurringCommandParserTest {
    private AddRecurringCommandParser parser = new AddRecurringCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Recurring expectedRecurring = new RecurringBuilder(RECURRING).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_RECURRING + AMOUNT_DESC_RECURRING
                        + CATEGORY_DESC_RECURRING + DATE_DESC_RECURRING + FREQUENCY_DESC_RECURRING
                        + OCCURRENCE_DESC_RECURRING + REMARKS_DESC_RECURRING,
                new AddRecurringCommand(expectedRecurring));

        assertParseSuccess(parser, NAME_DESC_RECURRING + AMOUNT_DESC_RECURRING
                        + CATEGORY_DESC_RECURRING + DATE_DESC_RECURRING + FREQUENCY_DESC_RECURRING
                        + OCCURRENCE_DESC_RECURRING + REMARKS_DESC_RECURRING,
                new AddRecurringCommand(expectedRecurring));
    }
}
