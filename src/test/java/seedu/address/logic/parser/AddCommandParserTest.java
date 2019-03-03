package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_EXPENSE;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_DEBT;
import static seedu.address.logic.commands.CommandTestUtil.CATEGORY_DESC_EXPENSE;
import static seedu.address.logic.commands.CommandTestUtil.CATEGORY_DESC_DEBT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CATEGORY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DEADLINE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_EXPENSE;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_DEBT;
import static seedu.address.logic.commands.CommandTestUtil.AMOUNT_DESC_EXPENSE;
import static seedu.address.logic.commands.CommandTestUtil.AMOUNT_DESC_DEBT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.REMARKS_DESC_EXPENSE;
import static seedu.address.logic.commands.CommandTestUtil.REMARKS_DESC_DEBT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_DEBT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_DEBT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_DEBT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_DEBT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARKS_DEBT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARKS_EXPENSE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalExpenses.EXPENSE;
import static seedu.address.testutil.TypicalExpenses.BOB;

import org.junit.Test;

import seedu.address.logic.commands.expensecommands.AddCommand;
import seedu.address.logic.parser.expenseparsers.AddCommandParser;
import seedu.address.model.attributes.Address;
import seedu.address.model.attributes.Amount;
import seedu.address.model.attributes.Email;
import seedu.address.model.expense.Expense;
import seedu.address.model.attributes.Name;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.ExpenseBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Expense expectedExpense = new ExpenseBuilder(BOB).withTags(VALID_REMARKS_DEBT).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_DEBT + AMOUNT_DESC_DEBT + CATEGORY_DESC_DEBT
                + DEADLINE_DESC_DEBT + REMARKS_DESC_EXPENSE, new AddCommand(expectedExpense));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_EXPENSE + NAME_DESC_DEBT + AMOUNT_DESC_DEBT + CATEGORY_DESC_DEBT
                + DEADLINE_DESC_DEBT + REMARKS_DESC_EXPENSE, new AddCommand(expectedExpense));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, NAME_DESC_DEBT + AMOUNT_DESC_EXPENSE + AMOUNT_DESC_DEBT + CATEGORY_DESC_DEBT
                + DEADLINE_DESC_DEBT + REMARKS_DESC_EXPENSE, new AddCommand(expectedExpense));

        // multiple emails - last email accepted
        assertParseSuccess(parser, NAME_DESC_DEBT + AMOUNT_DESC_DEBT + CATEGORY_DESC_EXPENSE + CATEGORY_DESC_DEBT
                + DEADLINE_DESC_DEBT + REMARKS_DESC_EXPENSE, new AddCommand(expectedExpense));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, NAME_DESC_DEBT + AMOUNT_DESC_DEBT + CATEGORY_DESC_DEBT + DATE_DESC_EXPENSE
                + DEADLINE_DESC_DEBT + REMARKS_DESC_EXPENSE, new AddCommand(expectedExpense));

        // multiple tags - all accepted
        Expense expectedExpenseMultipleTags = new ExpenseBuilder(BOB).withTags(VALID_REMARKS_DEBT, VALID_REMARKS_EXPENSE)
                .build();
        assertParseSuccess(parser, NAME_DESC_DEBT + AMOUNT_DESC_DEBT + CATEGORY_DESC_DEBT + DEADLINE_DESC_DEBT
                + REMARKS_DESC_DEBT + REMARKS_DESC_EXPENSE, new AddCommand(expectedExpenseMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Expense expectedExpense = new ExpenseBuilder(EXPENSE).withTags().build();
        assertParseSuccess(parser, NAME_DESC_EXPENSE + AMOUNT_DESC_EXPENSE + CATEGORY_DESC_EXPENSE + DATE_DESC_EXPENSE,
                new AddCommand(expectedExpense));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_DEBT + AMOUNT_DESC_DEBT + CATEGORY_DESC_DEBT + DEADLINE_DESC_DEBT,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_DEBT + VALID_AMOUNT_DEBT + CATEGORY_DESC_DEBT + DEADLINE_DESC_DEBT,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_DEBT + AMOUNT_DESC_DEBT + VALID_CATEGORY_DEBT + DEADLINE_DESC_DEBT,
                expectedMessage);

        // missing address prefix
        assertParseFailure(parser, NAME_DESC_DEBT + AMOUNT_DESC_DEBT + CATEGORY_DESC_DEBT + VALID_DEADLINE_DEBT,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_DEBT + VALID_AMOUNT_DEBT + VALID_CATEGORY_DEBT + VALID_DEADLINE_DEBT,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + AMOUNT_DESC_DEBT + CATEGORY_DESC_DEBT + DEADLINE_DESC_DEBT
                + REMARKS_DESC_DEBT + REMARKS_DESC_EXPENSE, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_DEBT + INVALID_AMOUNT_DESC + CATEGORY_DESC_DEBT + DEADLINE_DESC_DEBT
                + REMARKS_DESC_DEBT + REMARKS_DESC_EXPENSE, Amount.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_DEBT + AMOUNT_DESC_DEBT + INVALID_CATEGORY_DESC + DEADLINE_DESC_DEBT
                + REMARKS_DESC_DEBT + REMARKS_DESC_EXPENSE, Email.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_DEBT + AMOUNT_DESC_DEBT + CATEGORY_DESC_DEBT + INVALID_DATE_DESC
                + REMARKS_DESC_DEBT + REMARKS_DESC_EXPENSE, Address.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_DEBT + AMOUNT_DESC_DEBT + CATEGORY_DESC_DEBT + DEADLINE_DESC_DEBT
                + INVALID_DEADLINE_DESC + VALID_REMARKS_DEBT, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + AMOUNT_DESC_DEBT + CATEGORY_DESC_DEBT + INVALID_DATE_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_DEBT + AMOUNT_DESC_DEBT + CATEGORY_DESC_DEBT
                + DEADLINE_DESC_DEBT + REMARKS_DESC_DEBT + REMARKS_DESC_EXPENSE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
