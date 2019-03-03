package seedu.address.model.budget;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Iterator;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class BudgetList implements Iterable<Budget> {

    private final ObservableList<Budget> internalList = FXCollections.observableArrayList();
    private final ObservableList<Budget> internalUnmodifiableList = FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if list contains a budget that overlaps with given argument
     */
    public boolean hasOverlap(Budget toCheck) {
        requireNonNull(toCheck);
        for (Budget budget : internalList) {
            if (toCheck.overlaps(budget)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a budget to the list.
     * Budget cannot be overlapping with another existing budget.
     */
    public void addBudget(Budget toAdd) {
        requireNonNull(toAdd);
        if (hasOverlap(toAdd)) {
            throw new OverlappingBudgetException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the budget {@code target} in the list with {@code editedBudget}.
     * {@code target} must exist in the list.
     */
    public void setBudget(Budget target, Budget editedBudget) {
        requireAllNonNull(target, editedBudget);
        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new BudgetNotFoundException();
        }
        internalList.set(index, editedBudget);
    }

    /**
     * Removes the equivalent budget from the list.
     * The budget must exist in the list.
     */
    public void removeBudget(Budget toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new BudgetNotFoundException();
        }
    }

    /**
     * Returns backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Budget> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Budget> iterator() {
        return internalList.iterator();
    }
}
