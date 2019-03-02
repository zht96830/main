package seedu.address.model.recurring;

import seedu.address.model.attributes.*;
import seedu.address.model.expense.Expense;

import java.util.Objects;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a Recurring in the finance tracker.
 * Guarantees: field values are validated, immutable.
 */
public class Recurring extends Expense {
    // Additional fields
    private Frequency frequency;
    private int occurances;

    /**
     * Initializes a newly created Recurring object that contains only the compulsory fields.
     */
    public Recurring(Name name, Amount amount, Date date, Category category, String remarks, Frequency frequency,
                     int occurances) {
        super(name, amount, date, category, remarks);
        this.frequency = frequency;
        this.occurances = occurances;
    }

    public Frequency getFrequency() {
        return frequency;
    }

    public int getOccurances() {
        return occurances;
    }

    /**
     * Returns true if both recurrings of the same name have the same amount, date, frequency, and occurances.
     * This defines a weaker notion of equality between two recurrings.
     */
    public boolean isSameRecurring(Recurring otherRecurring) {
        if (otherRecurring == this) {
            return true;
        }

        return otherRecurring != null
                && otherRecurring.getName().equals(getName())
                && otherRecurring.getAmount().equals(getAmount())
                && otherRecurring.getDate().equals(getDate())
                && otherRecurring.getFrequency().equals(getFrequency())
                && otherRecurring.getOccurances() == (getOccurances());
    }

    /**
     * Returns true if both recurrings have the same identity and data fields.
     * This defines a stronger notion of equality between two recurrings.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Recurring)) {
            return false;
        }

        Recurring otherRecurring = (Recurring) other;
        return otherRecurring.getName().equals(getName())
                && otherRecurring.getAmount().equals(getAmount())
                && otherRecurring.getDate().equals(getDate())
                && otherRecurring.getCategory().equals(getCategory())
                && otherRecurring.getRemarks().equals(getRemarks())
                && otherRecurring.getFrequency().equals(getFrequency())
                && otherRecurring.getOccurances() == (getOccurances());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, amount, date, category, remarks, frequency, occurances);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Name: ")
                .append(name)
                .append(" Amount: ")
                .append(amount)
                .append(" Category: ")
                .append(category);

        if (date != null) {
            builder.append(" Date: ")
                    .append(date);
        }
        if (remarks != null) {
            builder.append(" Remarks: ")
                    .append(remarks);
        }

        builder.append("Frequency: ")
                .append(frequency)
                .append(" Occurances")
                .append(occurances);

        return builder.toString();
    }
}
