package seedu.address.logic.commands.statscommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENDDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTDATE;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.attributes.Category;
import seedu.address.model.attributes.Date;

/**
 * Calculates and displays statistics
 */
public class StatsCommand extends Command {

    public static final String COMMAND_WORD = "stats";

    public static final String COMMAND_WORD_SHORTCUT = "st";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Calculates statistics between the Start Date and End Date "
            + "Parameters: "
            + "[" + PREFIX_STARTDATE + "START DATE] "
            + "[" + PREFIX_ENDDATE + "END DATE] "
            //+ "[" + PREFIX_CATEGORY + "CATEGORY] "
            + "\nExample: " + COMMAND_WORD + " "
            + PREFIX_STARTDATE + "13-01-1996 "
            + PREFIX_ENDDATE + "13-02-1996 ";
            //+ PREFIX_CATEGORY + "food";

    public static final String MESSAGE_SUCCESS = "Statistics Calculated!";

    private final Date startDate;
    private final Date endDate;
    private final Category category;


    public StatsCommand(Date startDate, Date endDate, Category category) {
        requireNonNull(startDate);
        requireNonNull(endDate);

        this.startDate = startDate;
        this.endDate = endDate;
        this.category = category;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        /* Execution */
        model.calculateStatistics(startDate, endDate, category);
        /* End Execution*/

        String categoryString = " Category Specified: ";
        if (category == null){
            categoryString = categoryString + "False";
        }
        else {
            categoryString = categoryString + category;
        }
        String returnMessage = MESSAGE_SUCCESS;
        return new CommandResult(returnMessage);
    }

    @Override
    public boolean equals(Object other){
        return other == this //short circuit if same object
                || (other instanceof StatsCommand // instance of handles nulls
                && startDate.equals(((StatsCommand) other).startDate)
                && endDate.equals(((StatsCommand) other).endDate)
                && category.equals(((StatsCommand) other).category)); /*Have to handle null category*/
    }
}
