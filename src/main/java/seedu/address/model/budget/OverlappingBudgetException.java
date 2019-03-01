package seedu.address.model.budget;

public class OverlappingBudgetException extends RuntimeException {

    public OverlappingBudgetException() {
        super("Operation would result in overlapping budgets");
    }
}
