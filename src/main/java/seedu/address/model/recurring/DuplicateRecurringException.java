package seedu.address.model.recurring;

public class DuplicateRecurringException extends RuntimeException {
    public DuplicateRecurringException() {
        super("Operation would result in duplicate recurring");
    }
}
