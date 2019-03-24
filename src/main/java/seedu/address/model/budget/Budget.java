package seedu.address.model.budget;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.model.attributes.Amount;
import seedu.address.model.attributes.Category;
import seedu.address.model.attributes.Date;

/**
 * Represents a Budget in the finance tracker.
 */
public class Budget {

    private int index;
    private Category category;
    private Amount amount;
    private Date startDate;
    private Date endDate;
    private String remarks;
    private int totalSpent; // in cents
    private double percentage;

    private boolean isAboutToExceed; // when percentage reaches 90

    public static final String MESSAGE_CONSTRAINTS_START_DATE =
            "Start date has to be today or later.";
    public static final String MESSAGE_CONSTRAINTS_END_DATE =
            "Start date has to be before end date.";

    // constructor
    public Budget(Category category, Amount amount, Date startDate, Date endDate, String remarks) {
        requireAllNonNull(category, amount, endDate);
        this.category = category;
        this.amount = amount;
        //checkArgument(startDate.isEqualOrAfterToday(), MESSAGE_CONSTRAINTS_START_DATE);
        this.startDate = startDate;
        //checkArgument(startDate.getLocalDate().isBefore(endDate.getLocalDate()));
        this.endDate = endDate;
        if (remarks == null) {
            this.remarks = "";
        } else {
            this.remarks = remarks;
        }
        totalSpent = 0;
        percentage = (((double) totalSpent) / amount.value) * 100;
        isAboutToExceed = false;
    }
    public Budget(Budget toCopy) {
        requireNonNull(toCopy);
        this.category = toCopy.category;
        this.amount = toCopy.amount;
        this.startDate = toCopy.startDate;
        this.endDate = toCopy.endDate;
        if (toCopy.remarks == null) {
            this.remarks = "";
        } else {
            this.remarks = toCopy.remarks;
        }
        totalSpent = toCopy.totalSpent;
        percentage = (((double) totalSpent) / amount.value) * 100;
        isAboutToExceed = toCopy.isAboutToExceed;
    }

    public Category getCategory() {
        return category;
    }

    public Amount getAmount() {
        return amount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public double getTotalSpent() {
        return totalSpent;
    }

    public double getPercentage() {
        return percentage;
    }

    public boolean getIsAboutToExceed() {
        return isAboutToExceed;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public void setTotalSpent(int totalSpent) {
        this.totalSpent = totalSpent;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public void changeIsAboutToExceed() {
        if (isAboutToExceed == true) {
            isAboutToExceed = false;
        } else {
            isAboutToExceed = true;
        }
    }

    public void updateTotalSpent(double difference) {
        this.totalSpent += difference;
    }

    public void updatePercentage() {
        this.percentage = (((double) totalSpent) / amount.value) * 100;
    }

    /**
     * Checks if budget overlaps in terms of date with another budget.
     * @return true if budget overlaps with {@code other}, false if otherwise
     */
    public boolean overlaps(Budget other) {
        if (this.category != other.category) {
            return false;
        }
        if (((this.startDate.compareTo(other.startDate) < 0) && (this.endDate.compareTo(other.startDate) < 0))
                || ((other.startDate.compareTo(this.startDate) < 0)
                && (other.startDate.compareTo(this.startDate) < 0))) {
            return false;
        }
        return true;
    }

    /**
     * Check on whether the two budgets are the same based on their variables.
     * They need not be the same instance variable.
     * @return true if the two budgets have the same attributes.
     */
    public boolean isSameBudget(Budget otherBudget) {
        if (otherBudget == this) {
            return true;
        }

        return otherBudget != null
                && otherBudget.getCategory().equals(getCategory())
                && otherBudget.getAmount().equals(getAmount())
                && otherBudget.getStartDate().equals(getStartDate())
                && otherBudget.getEndDate().equals(getEndDate());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Category: ")
                .append(category)
                .append(" Amount: ")
                .append(amount);
        if (startDate != null) {
            builder.append(" Start date: ").append(startDate);
        }
        builder.append(" End date: ").append(endDate);
        if (remarks != null) {
            builder.append(" Remarks: ").append(remarks);
        }
        return builder.toString();
    }

}
