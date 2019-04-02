package seedu.address.model.statistics;

import javafx.collections.transformation.FilteredList;
import seedu.address.model.attributes.Category;
import seedu.address.model.attributes.Date;
import seedu.address.model.budget.Budget;
import seedu.address.model.debt.Debt;
import seedu.address.model.expense.Expense;

import java.util.ArrayList;

public class Statistics {

    protected Date startDate;
    protected Date endDate;
    protected Category category;

    public Statistics(Date startDate, Date endDate, Category category) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.category = category;
        System.out.println("Created");
    }

    public Date getStartDate() {
        System.out.println("getStartDate method called");
        return startDate;
    }
    public Date getEndDate() {
        return endDate;
    }

    public void calculateStats(FilteredList<Expense> statsExpenses, FilteredList<Debt> statsDebts
            , FilteredList<Budget> statsBudgets){

        boolean isCategoryNull = (this.category == null);

        ArrayList<Expense> foodExpenses = new ArrayList<>();
        ArrayList<Expense> transportExpenses = new ArrayList<>();
        ArrayList<Expense> shoppingExpenses = new ArrayList<>();
        ArrayList<Expense> workExpenses = new ArrayList<>();
        ArrayList<Expense> utilitiesExpenses = new ArrayList<>();
        ArrayList<Expense> healthcareExpenses = new ArrayList<>();
        ArrayList<Expense> entertainmentExpenses = new ArrayList<>();
        ArrayList<Expense> travelExpenses = new ArrayList<>();
        ArrayList<Expense> othersExpenses = new ArrayList<>();
        ArrayList<Expense> consideredExpenses = new ArrayList<>();

        for (Expense expense : statsExpenses) {
            Date date = expense.getDate();
            if (date.compareTo(startDate) != -1 && date.compareTo(endDate) != 1){
                consideredExpenses.add(expense);
                switch (expense.getCategory().toString()) {
                    case "FOOD":
                        foodExpenses.add(expense);
                        break;
                    case "TRANSPORT":
                        transportExpenses.add(expense);
                        break;
                    case "SHOPPING":
                        shoppingExpenses.add(expense);
                        break;
                    case "WORK":
                        workExpenses.add(expense);
                        break;
                    case "UTILITIES":
                        utilitiesExpenses.add(expense);
                        break;
                    case "HEALTHCARE":
                        healthcareExpenses.add(expense);
                        break;
                    case "ENTERTAINMENT":
                        entertainmentExpenses.add(expense);
                        break;
                    case "TRAVEL":
                        travelExpenses.add(expense);
                        break;
                    case "OTHERS":
                        othersExpenses.add(expense);
                        break;
                }
            }
        }

        //System.out.println("Considered List:");
        for (Expense expense: consideredExpenses) {
            System.out.println(expense.toString());
        }
    }

    private static int totalExpense(ArrayList<Expense> expenses){
        int total = 0;
        for (Expense expense : expenses){
            total += expense.getAmount().value;
        }
        return total;
    }


    private static int totalCounts(ArrayList<Expense> expenses){
        int count = 0;
        for (Expense expense : expenses){
            count ++;
        }
        return count;
    }

}
