package seedu.address.model.budget;

import seedu.address.model.attributes.Amount;
import seedu.address.model.attributes.Category;
import seedu.address.model.attributes.Date;

public class Budget {

    private Category category;
    private Amount amount;
    private Date startDate;
    private Date endDate;
    private String remarks;
    private int totalSpent; // in cents
    private double percentage;

    public boolean hasBudget;
    public boolean isAboutToExceed; // when percentage reaches 90

    // constructor
    public Budget(Category category, Amount amount, Date startDate, Date endDate, String remarks) {
        this.category = category;
        this.amount = amount;
        this.startDate = startDate;
        this.endDate = endDate;
        if (remarks == null) {
            this.remarks = null;
        } else {
            this.remarks = remarks;
        }
        totalSpent = 0;
        percentage = (((double)totalSpent)/Integer.parseInt(amount.toString()))*100;
        hasBudget = true;
        isAboutToExceed = false;
    }

    public Category getCategory() { return category; }

    public Amount getAmount() { return amount; }

    public Date getStartDate() { return startDate; }

    public Date getEndDate() { return endDate; }

    public String getRemarks() { return remarks; }

    public double getTotalSpent() { return totalSpent; }

    public double getPercentage() { return percentage; }

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
        this. remarks = remarks;
    }

    public void setTotalSpent(int totalSpent) {
        this.totalSpent = totalSpent;
    }

    public boolean overlaps(Budget other) {
        if (this.category != other.category) {
            return false;
        }

        if ((this.startDate.compareTo(other.startDate) && this.endDate.compareTo(other.startDate))
        || (other.startDate.compareTo(this.startDate) && other.startDate.compareTo(this.startDate))) {
            return false;
        }
        return true;
    }
}
