package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;
import seedu.address.model.budget.Budget;

/**
 * Provides a handle to a budget card in the budget list panel.
 */
public class BudgetCardHandle extends NodeHandle<Node> {
    private static final String AMOUNT_FIELD_ID = "#budgetAmount";
    private static final String CATEGORY_FIELD_ID = "#budgetCategory";
    private static final String DURATION_FIELD_ID = "#budgetDuration";
    private static final String PERCENTAGE_FIELD_ID = "#budgetPercentage";

    private final Label amountLabel;
    private final Label categoryLabel;
    private final Label durationLabel;
    private final Label percentageLabel;

    public BudgetCardHandle(Node cardNode) {
        super(cardNode);

        amountLabel = getChildNode(AMOUNT_FIELD_ID);
        categoryLabel = getChildNode(CATEGORY_FIELD_ID);
        durationLabel = getChildNode(DURATION_FIELD_ID);
        percentageLabel = getChildNode(PERCENTAGE_FIELD_ID);
    }

    public String getAmount() {
        return amountLabel.getText();
    }

    public String getCategory() {
        return categoryLabel.getText();
    }

    public String getDuration() {
        return durationLabel.getText();
    }

    public String getPercentage() {
        return percentageLabel.getText();
    }

    /**
     * Returns true if this handle contains {@code budget}.
     */
    public boolean equals(Budget budget) {
        return getAmount().equals("$" + budget.getAmount().toString())
                && getCategory().equals(budget.getCategory().toString())
                && getDuration().equals(budget.getDuration())
                && getPercentage().equals(budget.getPercentage() + "% spent");
    }
}
