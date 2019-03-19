package seedu.address.model.attributes;

/**
 * Represents a list view in the finance tracker.
 */
public enum View {
    ALL, DAY, WEEK, MONTH, YEAR, FOOD, TRANSPORT, SHOPPING, WORK, UTILITIES, HEALTHCARE, ENTERTAINMENT, TRAVEL, OTHERS;

    public static final String MESSAGE_CONSTRAINTS =
            "View should only be one of the following:\nALL, DAY, WEEK, MONTH, FOOD, TRANSPORT, SHOPPING, WORK, "
                    + "UTILITIES, HEALTHCARE, ENTERTAINMENT, TRAVEL, OTHERS.";

    /**
     * Method to check if string belongs to one of the possible View enum values.
     *
     * @param viewName is a string containing possible enum value.
     * @return true if string is a valid View enum; false if string is not a valid View enum.
     */
    public static boolean isValidView(String viewName) {
        View[] views = View.values();

        for (View view : views) {
            if (view.toString().equals(viewName.toUpperCase())) {
                return true;
            }
        }
        return false;
    }
}
