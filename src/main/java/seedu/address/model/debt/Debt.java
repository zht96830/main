package seedu.address.model.debt;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.model.attributes.Amount;
import seedu.address.model.attributes.Category;
import seedu.address.model.attributes.Date;
import seedu.address.model.attributes.Name;

import java.util.Objects;

/**
 * Represents a Debt in the finance tracker.
 * Guarantees: field values are validated, immutable.
 */
public class Debt {
    // Compulsory fields
    private final Name personOwed;
    private final Amount amount;
    private final Category category;

    // Optional fields
    private final Date deadline;
    private final String remarks;

    /**
     * Initializes a newly created Debt object that contains only the compulsory fields.
     */
    public Debt(Name personOwed, Amount amount, Category category) {
        requireAllNonNull(personOwed, amount, category);
        this.personOwed = personOwed;
        this.amount = amount;
        this.category = category;
        this.deadline = null;
        this.remarks = null;
    }

    /**
     * Initializes a newly created Debt object that contains the compulsory fields and a deadline.
     */
    public Debt(Name personOwed, Amount amount, Category category, Date deadline) {
        requireAllNonNull(personOwed, amount, category, deadline);
        this.personOwed = personOwed;
        this.amount = amount;
        this.category = category;
        this.deadline = deadline;
        this.remarks = null;
    }

    /**
     * Initializes a newly created Debt object that contains the compulsory fields and remarks.
     */
    public Debt(Name personOwed, Amount amount, Category category, String remarks) {
        requireAllNonNull(personOwed, amount, category, remarks);
        this.personOwed = personOwed;
        this.amount = amount;
        this.category = category;
        this.deadline = null;
        this.remarks = remarks;
    }

    /**
     * Initializes a newly created Debt object that contains all fields.
     */
    public Debt(Name personOwed, Amount amount, Date deadline, Category category, String remarks) {
        requireAllNonNull(personOwed, amount, category);
        this.personOwed = personOwed;
        this.amount = amount;
        this.category = category;
        this.deadline = deadline;
        this.remarks = remarks;
    }

    public Name getPersonOwed() {
        return personOwed;
    }

    public Amount getAmount() {
        return amount;
    }

    public Category getCategory() {
        return category;
    }

    public Date getDeadline() {
        return deadline;
    }

    public String getRemarks() {
        return remarks;
    }

    /**
     * Returns true if both debts have the same personOwed and amount.
     * This defines a weaker notion of equality between two debts.
     */
    public boolean isSameDebt(Debt otherDebt) {
        if (otherDebt == this) {
            return true;
        }

        return otherDebt != null
                && otherDebt.getPersonOwed().equals(this.personOwed)
                && otherDebt.getAmount().equals(this.amount);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(personOwed, amount, category, deadline, remarks);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Person owed: ")
                .append(personOwed)
                .append(" Amount owed: ")
                .append(amount)
                .append(" Category: ")
                .append(category);

        if (deadline != null) {
            builder.append(" Deadline: ").append(deadline);
        }
        if (remarks != null) {
            builder.append(" Remarks: ").append(remarks);
        }
        return builder.toString();
    }

}
