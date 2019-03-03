package seedu.address.model.budget;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Iterator;
import java.util.List;

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
     * Returns true if the list contains an equivalent budget as the given argument
     */
    public boolean contains(Budget toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameBudget);
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
        switch (toAdd.getCategory()) {
            case FOOD:
                internalList.add(0, toAdd);
                break;
            case TRANSPORT:
                internalList.add(1, toAdd);
                break;
            case SHOPPING:
                internalList.add(2, toAdd);
                break;
            case WORK:
                internalList.add(3, toAdd);
                break;
            case UTILITIES:
                internalList.add(4, toAdd);
                break;
            case HEALTHCARE:
                internalList.add(5, toAdd);
                break;
            case ENTERTAINMENT:
                internalList.add(6, toAdd);
                break;
            case TRAVEL:
                internalList.add(7, toAdd);
                break;
            case OTHERS:
                internalList.add(8, toAdd);
                break;
        }
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
     * Replaces entire budget list {@code internalList} with new budget list {@code replacement}
     */
    public void setBudgets(BudgetList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code budgets}
     */
    public void setBudgets(List<Budget> budgets) {
        requireAllNonNull(budgets);
        internalList.setAll(budgets);
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
