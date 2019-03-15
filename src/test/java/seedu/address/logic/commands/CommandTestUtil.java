package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DUE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARKS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.debtcommands.EditDebtCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.expensecommands.EditCommand;
import seedu.address.model.FinanceTracker;
import seedu.address.model.Model;
import seedu.address.model.expense.Expense;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.testutil.EditDebtDescriptorBuilder;
import seedu.address.testutil.EditExpenseDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_EXPENSE = "Chicken Rice";
    public static final String VALID_NAME_DEBT = "Bob Choo";
    public static final String VALID_AMOUNT_EXPENSE = "11111111";
    public static final String VALID_AMOUNT_DEBT = "12345";
    public static final String VALID_CATEGORY_EXPENSE = "food";
    public static final String VALID_CATEGORY_EXPENSE_2 = "transport";
    public static final String VALID_CATEGORY_DEBT = "shopping";
    public static final String VALID_DATE_EXPENSE = "03-03-2019";
    public static final String VALID_DATE_EXPENSE_2 = "12-03-2019";
    public static final String VALID_DEADLINE_DEBT = "05-05-2019";
    public static final String VALID_REMARKS_EXPENSE = "Bishan chicken rice";
    public static final String VALID_REMARKS_DEBT = "fan";

    public static final String NAME_DESC_EXPENSE = " " + PREFIX_NAME + VALID_NAME_EXPENSE;
    public static final String NAME_DESC_DEBT = " " + PREFIX_NAME + VALID_NAME_DEBT;
    public static final String AMOUNT_DESC_EXPENSE = " " + PREFIX_AMOUNT + VALID_AMOUNT_EXPENSE;
    public static final String AMOUNT_DESC_DEBT = " " + PREFIX_AMOUNT + VALID_AMOUNT_DEBT;
    public static final String CATEGORY_DESC_EXPENSE = " " + PREFIX_CATEGORY + VALID_CATEGORY_EXPENSE;
    public static final String CATEGORY_DESC_DEBT = " " + PREFIX_CATEGORY + VALID_CATEGORY_DEBT;
    public static final String DATE_DESC_EXPENSE = " " + PREFIX_DATE + VALID_DATE_EXPENSE;
    public static final String DATE_DESC_EXPENSE_2 = " " + PREFIX_DATE + VALID_DATE_EXPENSE_2;
    public static final String DEADLINE_DESC_DEBT = " " + PREFIX_DUE + VALID_DEADLINE_DEBT;
    public static final String REMARKS_DESC_EXPENSE = " " + PREFIX_REMARKS + VALID_REMARKS_EXPENSE;
    public static final String REMARKS_DESC_DEBT = " " + PREFIX_REMARKS + VALID_REMARKS_DEBT;


    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_AMOUNT_DESC = " " + PREFIX_AMOUNT + "911a"; // 'a' not allowed in phones
    public static final String INVALID_CATEGORY_DESC = " " + PREFIX_CATEGORY + "fod"; // not one of enum values
    public static final String INVALID_DATE_DESC = " " + PREFIX_DATE + "142-121-012"; // must be dd-MM-yyyy
    public static final String INVALID_DEADLINE_DESC = " " + PREFIX_DUE + "12-1213-01"; // must be dd-MM-yyyy

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditExpenseDescriptor DESC_EXPENSE;
    public static final EditDebtCommand.EditDebtDescriptor DESC_DEBT;

    static {
        DESC_EXPENSE = new EditExpenseDescriptorBuilder().withName(VALID_NAME_EXPENSE)
                .withAmount(VALID_AMOUNT_EXPENSE).withCategory(VALID_CATEGORY_EXPENSE).withDate(VALID_DATE_EXPENSE)
                .withRemarks(VALID_REMARKS_DEBT).build();
        DESC_DEBT = new EditDebtDescriptorBuilder().withPersonOwed(VALID_NAME_DEBT)
                .withAmount(VALID_AMOUNT_DEBT).withCategory(VALID_CATEGORY_DEBT).withDeadline(VALID_DEADLINE_DEBT)
                .withRemarks(VALID_REMARKS_DEBT).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel} <br>
     * - the {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
            CommandResult expectedCommandResult, Model expectedModel) {
        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
        try {
            CommandResult result = command.execute(actualModel, actualCommandHistory);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
            assertEquals(expectedCommandHistory, actualCommandHistory);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandHistory, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage, Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, actualCommandHistory, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered expense list and selected expense in {@code actualModel} remain unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandFailure(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        FinanceTracker expectedFinanceTracker = new FinanceTracker(actualModel.getFinanceTracker());
        List<Expense> expectedFilteredList = new ArrayList<>(actualModel.getFilteredExpenseList());
        Expense expectedSelectedExpense = actualModel.getSelectedExpense();

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedFinanceTracker, actualModel.getFinanceTracker());
            assertEquals(expectedFilteredList, actualModel.getFilteredExpenseList());
            assertEquals(expectedSelectedExpense, actualModel.getSelectedExpense());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the expense at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredExpenseList().size());

        Expense expense = model.getFilteredExpenseList().get(targetIndex.getZeroBased());
        final String[] splitName = expense.getName().name.split("\\s+");
        model.updateFilteredExpenseList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredExpenseList().size());
    }

    /**
     * Deletes the first expense in {@code model}'s filtered list from {@code model}'s address book.
     */
    public static void deleteFirstPerson(Model model) {
        Expense firstExpense = model.getFilteredExpenseList().get(0);
        model.deleteExpense(firstExpense);
        model.commitFinanceTracker();
    }

}
