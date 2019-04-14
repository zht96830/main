package seedu.address.model.statistics;

import java.lang.reflect.Array;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javafx.collections.transformation.FilteredList;
import seedu.address.model.attributes.Category;
import seedu.address.model.attributes.Date;
import seedu.address.model.attributes.Frequency;
import seedu.address.model.budget.Budget;
import seedu.address.model.debt.Debt;
import seedu.address.model.expense.Expense;
/**
 * Statistics
 */
public class Statistics {

    public static final String MESSAGE_CONSTRAINTS_END_DATE =
            "Start date has to be before end date.";
    public static final int FOOD = 0;
    public static final int TRANSPORT = 1;
    public static final int SHOPPING = 2;
    public static final int WORK = 3;
    public static final int UTILITIES = 4;
    public static final int HEALTHCARE = 5;
    public static final int ENTERTAINMENT = 6;
    public static final int TRAVEL = 7;
    public static final int OTHERS = 8;
    public static final int ALL = 9;


    protected Category category;
    protected String html;
    protected FilteredList<Expense> statsExpenses;

    /**
     * statsExpense must be present and not null.
     */
    public Statistics(FilteredList<Expense> statsExpenses) {
        this.statsExpenses = statsExpenses;
    }


    /**
     * This function returns the HTML code specifying the results of statistics commands
     * @return
     */
    public String getHtml() {
        return html;
    }

    /**
     * Calculates Statistics with data from model
     */
    public void calculateStats(String command, Date d1, Date d2, Frequency frequency) {
        switch (command) {
            case "stats":
                basicStats(d1, d2);
            case "compare":
                compareStats(d1, d2, frequency);
            case "trend":
                trendStats(d1, d2, frequency);
            default:
        }
    }

    private void basicStats(Date startDate, Date endDate){

        ArrayList<ArrayList<Expense>> data = extractRelevantExpenses(startDate, endDate);
        ArrayList<ArrayList<Object>> tableData = generateSummary(data);
        tableData = trimTable(tableData);

        this.html = "Statistics Summary <br>\n"
                + "From: " + startDate.toString() + " To: " + endDate.toString() + "\n";

        ArrayList<ArrayList<String>> dataInString = convertDataToString(tableData);

        ArrayList<String> header = new ArrayList<>();
        header.add("Categories");
        header.add("Amount Spent");
        header.add("Entry Counts");
        header.add("Percentage");

        String table = htmlTableBuilder(header, dataInString);
        this.html = html + table;

    }

    private ArrayList<ArrayList<String>> convertDataToString(ArrayList<ArrayList<Object>> tableData) {
        ArrayList<ArrayList<String>> dataInString = new ArrayList<>();
        for (ArrayList<Object> list : tableData) {
            ArrayList<String> row = new ArrayList<>();
            for (Object data : list) {
                if (data instanceof Double) {
                    row.add(String.format("%.2f", (Double) data));
                }
                else if (data instanceof String){
                    row.add((String) data);
                }
                else if (data instanceof Integer) {
                    row.add(((Integer) data).toString());
                }
            }
            dataInString.add(row);
        }

        return dataInString;
    }

    private ArrayList<ArrayList<Object>> trimTable(ArrayList<ArrayList<Object>> data) {
        ArrayList<ArrayList<Object>> tableData = new ArrayList<>();
        for (ArrayList<Object> row : data) {
            if (!row.get(2).equals(0)) {
                tableData.add(row);
            }
        }
        return tableData;
    }

    private ArrayList<ArrayList<Object>> generateSummary(ArrayList<ArrayList<Expense>> data) {

        ArrayList<ArrayList<Object>> tableData = new ArrayList<>();

        for (int i = 0; i <= ALL; i++) {
            ArrayList<Expense> list = data.get(i);
            double categoryTotal = 0;
            int categoryCount = 0;
            String categoryString = convertIntegerCategoryToString(i);
            if (!list.isEmpty()) {
                for (Expense e : list) {
                    categoryTotal += e.getAmount().value / 100;
                    categoryCount++;
                }
            }
            ArrayList<Object> rowData = new ArrayList<>();
            rowData.add(categoryString);
            rowData.add(categoryTotal);
            rowData.add(categoryCount);
            tableData.add(rowData);
        }

        for (int i = 0; i <= ALL; i++) {
            double categoryPercentage = (Double) tableData.get(i).get(1)
                    / (Double) tableData.get(ALL).get(1) * 100;
            categoryPercentage = Math.floor(categoryPercentage * 100) / 100;
            tableData.get(i).add(categoryPercentage);
        }

        return tableData;
    }

    private ArrayList<ArrayList<Expense>> extractRelevantExpenses(Date startDate, Date endDate) {
        ArrayList<ArrayList<Expense>> data = new ArrayList<>();
        for (int i = 0; i <= ALL; i++) {
            data.add(new ArrayList<>());
        }

        for (Expense expense : statsExpenses) {
            Date date = expense.getDate();
            if (date.compareTo(startDate) != -1 && date.compareTo(endDate) != 1) {
                data.get(ALL).add(expense);
                int categoryInInteger = convertCategoryToInteger(expense.getCategory().toString());
                data.get(categoryInInteger).add(expense);
            }
        }
        return data;
    }

    /**
     * Converts String Category into Numerical Category
     */
    protected int convertCategoryToInteger(String categoryInString) {
        switch (categoryInString) {
        case "FOOD":
            return FOOD;
        case "TRANSPORT":
            return TRANSPORT;
        case "SHOPPING":
            return SHOPPING;
        case "WORK":
            return WORK;
        case "UTILITIES":
            return UTILITIES;
        case "HEALTHCARE":
            return HEALTHCARE;
        case "ENTERTAINMENT":
            return ENTERTAINMENT;
        case "TRAVEL":
            return TRAVEL;
        case "OTHERS":
            return OTHERS;
        case "ALL":
            return ALL;
        default:
        }
        return -1;
    }

    protected String convertIntegerCategoryToString(int categoryInInteger) {
        switch (categoryInInteger) {
            case FOOD:
                return "FOOD";
            case TRANSPORT:
                return "TRANSPORT";
            case SHOPPING:
                return "SHOPPING";
            case WORK:
                return "WORK";
            case UTILITIES:
                return "UTILITIES";
            case HEALTHCARE:
                return "HEALTHCARE";
            case ENTERTAINMENT:
                return "ENTERTAINMENT";
            case TRAVEL:
                return "TRAVEL";
            case OTHERS:
                return "OTHERS";
            case ALL:
                return "ALL";
            default:
        }
        return "";
    }

    /**
     * Builds table for display
     */
    private String htmlTableBuilder(ArrayList<String> header, ArrayList<ArrayList<String>> data) {
        String table;
        table = "<table style=\"width:100%\">\n";

        table = table + "  <tr>\n";
        for (String column : header) {
            table = table + "    <th>" + column + "</th>\n";
        }
        table = table + "  </tr>\n";

        for (ArrayList<String> row : data) {
            table = table + "  <tr>\n";
            for (String column : row) {
                table = table + "    <th>" + column + "</th>\n";
            }
            table = table + "  </tr>\n";
        }

        table = table + "</table>";

        return table;
    }

    private void compareStats(Date date1, Date date2, Frequency frequency){ Date startDate1 = date1;
        Date startDate2 = date2;

        Date endDate1;
        Date endDate2;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        switch (frequency.toString()){
            case "D":
                endDate1 = new Date(dtf.format(startDate1.getLocalDate().plusDays(1)));
                endDate2 = new Date(dtf.format(startDate2.getLocalDate().plusDays(1)));
                break;
            case "W":
                endDate1 = new Date(dtf.format(startDate1.getLocalDate().plusWeeks(1)));
                endDate2 = new Date(dtf.format(startDate2.getLocalDate().plusWeeks(1)));
                break;
            case "Y":
                endDate1 = new Date(dtf.format(startDate1.getLocalDate().plusYears(1)));
                endDate2 = new Date(dtf.format(startDate2.getLocalDate().plusYears(1)));
                break;
            case "M":
                endDate1 = new Date(dtf.format(startDate1.getLocalDate().plusMonths(1)));
                endDate2 = new Date(dtf.format(startDate2.getLocalDate().plusMonths(1)));
                break;
            default:
                endDate1 = null;
                endDate2 = null;
        }

        this.html = "Statistics Compare <br>\n"
                + "From: " + startDate1.toString() + " To: " + endDate1.toString() + "\n";

        ArrayList<String> header = new ArrayList<>();
        header.add("Categories");
        header.add("Amount Spent");
        header.add("Entry Counts");
        header.add("Percentage");

        // Time Range 1
        ArrayList<ArrayList<Expense>> data = extractRelevantExpenses(startDate1, endDate1);
        ArrayList<ArrayList<Object>> tableData = generateSummary(data);
        tableData = trimTable(tableData);

        ArrayList<ArrayList<String>> dataInString = convertDataToString(tableData);

        String table = htmlTableBuilder(header, dataInString);
        this.html = html + table;

        // Time Range 2
        this.html = html + "<br>";
        this.html = html + "From: " + startDate2.toString() + " To: " + endDate2.toString() + "\n";

        ArrayList<ArrayList<Expense>> datac = extractRelevantExpenses(startDate2, endDate2);
        ArrayList<ArrayList<Object>> tableDatac = generateSummary(datac);
        tableDatac = trimTable(tableDatac);

        ArrayList<ArrayList<String>> dataInStringc = convertDataToString(tableDatac);

        String tablec = htmlTableBuilder(header, dataInStringc);
        this.html = html + tablec;

    }
    private void trendStats(Date startDate, Date endDate, Frequency frequency){

    }

}

