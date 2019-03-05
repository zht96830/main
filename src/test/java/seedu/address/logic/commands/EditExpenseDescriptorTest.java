package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_DEBT;
import static seedu.address.logic.commands.CommandTestUtil.DESC_EXPENSE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_DEBT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_DEBT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_DEBT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_DEBT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARKS_EXPENSE;

import org.junit.Test;

import seedu.address.logic.commands.expensecommands.EditCommand;
import seedu.address.logic.commands.expensecommands.EditCommand.EditExpenseDescriptor;
import seedu.address.testutil.EditExpenseDescriptorBuilder;

public class EditExpenseDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditCommand.EditExpenseDescriptor descriptorWithSameValues = new EditExpenseDescriptor(DESC_EXPENSE);
        assertTrue(DESC_EXPENSE.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_EXPENSE.equals(DESC_EXPENSE));

        // null -> returns false
        assertFalse(DESC_EXPENSE.equals(null));

        // different types -> returns false
        assertFalse(DESC_EXPENSE.equals(5));

        // different values -> returns false
        assertFalse(DESC_EXPENSE.equals(DESC_DEBT));

        // different name -> returns false
        EditCommand.EditExpenseDescriptor editedAmy = new EditExpenseDescriptorBuilder(DESC_EXPENSE)
                .withName(VALID_NAME_DEBT).build();
        assertFalse(DESC_EXPENSE.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new EditExpenseDescriptorBuilder(DESC_EXPENSE).withAmount(VALID_AMOUNT_DEBT).build();
        assertFalse(DESC_EXPENSE.equals(editedAmy));

        // different email -> returns false
        editedAmy = new EditExpenseDescriptorBuilder(DESC_EXPENSE).withCategory(VALID_CATEGORY_DEBT).build();
        assertFalse(DESC_EXPENSE.equals(editedAmy));

        // different address -> returns false
        editedAmy = new EditExpenseDescriptorBuilder(DESC_EXPENSE).withDate(VALID_DEADLINE_DEBT).build();
        assertFalse(DESC_EXPENSE.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditExpenseDescriptorBuilder(DESC_EXPENSE).withRemarks(VALID_REMARKS_EXPENSE).build();
        assertFalse(DESC_EXPENSE.equals(editedAmy));
    }
}
