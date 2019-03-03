package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARKS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_FINANCES;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.attributes.Category;
import seedu.address.model.debt.Debt;
import seedu.address.model.expense.Expense;
import seedu.address.model.attributes.Name;
import seedu.address.model.attributes.Amount;
import seedu.address.model.attributes.Date;

/**
 * Edits the details of an existing expense in the Finance Tracker.
 */
public class EditDebtCommand extends Command {

    public static final String COMMAND_WORD = "editdebt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the debt identified "
            + "by the index number used in the displayed debt list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_AMOUNT + "AMOUNT] "
            + "[" + PREFIX_CATEGORY + "CATEGORY] "
            + "[" + PREFIX_DATE + "DATE] "
            + "[" + PREFIX_REMARKS + "REMARKS]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_NAME + "Hatyai "
            + PREFIX_AMOUNT + "400 "
            + PREFIX_CATEGORY + "travel";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited debt: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    private final Index index;
    private final EditDebtDescriptor editDebtDescriptor;

    /**
     * @param index of the expense in the filtered expense list to edit
     * @param editDebtDescriptor details to edit the expense with
     */
    public EditDebtCommand(Index index, EditDebtDescriptor editDebtDescriptor) {
        requireNonNull(index);
        requireNonNull(editDebtDescriptor);

        this.index = index;
        this.editDebtDescriptor = new EditDebtDescriptor(editDebtDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Debt> lastShownList = model.getFilteredDebtList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EXPENSE_DISPLAYED_INDEX);
        }

        Debt debtToEdit = lastShownList.get(index.getZeroBased());
        Debt editeddebt = createEditedDebt(debtToEdit, editDebtDescriptor);

        model.setDebts(debtToEdit, editeddebt);
        model.updateFilteredExpenseList(PREDICATE_SHOW_ALL_FINANCES);
        model.commitFinanceTracker();
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, editeddebt));
    }

    /**
     * Creates and returns a {@code Debt} with the details of {@code debtToEdit}
     * edited with {@code editDebtDescriptor}.
     */
    private static Debt createEditedDebt(Debt debtToEdit, EditDebtDescriptor editPersonDescriptor) {
        assert debtToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(debtToEdit.getPersonOwed());
        Amount updatedAmount = editPersonDescriptor.getAmount().orElse(debtToEdit.getAmount());
        Category updatedCategory = editPersonDescriptor.getCategory().orElse(debtToEdit.getCategory());
        Date updatedDate = editPersonDescriptor.getDate().orElse(debtToEdit.getDeadline());
        String updatedRemarks = editPersonDescriptor.getRemarks().orElse(debtToEdit.getRemarks());

        return new Debt(updatedName, updatedAmount, updatedDate, updatedCategory, updatedRemarks);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditDebtCommand e = (EditDebtCommand) other;
        return index.equals(e.index)
                && editDebtDescriptor.equals(e.editDebtDescriptor);
    }

    /**
     * Stores the details to edit the expense with. Each non-empty field value will replace the
     * corresponding field value of the expense.
     */
    public static class EditDebtDescriptor {
        private Name name;
        private Amount amount;
        private Date date;
        private Category category;
        private String remarks;

        public EditDebtDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditDebtDescriptor(EditDebtDescriptor toCopy) {
            setName(toCopy.name);
            setAmount(toCopy.amount);
            setDate(toCopy.date);
            setCategory(toCopy.category);
            setRemarks(toCopy.remarks);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, amount, date, category, remarks);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setAmount(Amount amount) {
            this.amount = amount;
        }

        public Optional<Amount> getAmount() {
            return Optional.ofNullable(amount);
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public Optional<Date> getDate() {
            return Optional.ofNullable(date);
        }

        public void setCategory(Category category) { this.category = category; }

        public Optional<Category> getCategory() { return Optional.ofNullable(category); }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public Optional<String> getRemarks() { return Optional.ofNullable(remarks); }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditDebtDescriptor)) {
                return false;
            }

            // state check
            EditDebtDescriptor e = (EditDebtDescriptor) other;

            return getName().equals(e.getName())
                    && getAmount().equals(e.getAmount())
                    && getDate().equals(e.getDate())
                    && getCategory().equals(e.getCategory())
                    && getRemarks().equals(e.getRemarks());
        }
    }
}
