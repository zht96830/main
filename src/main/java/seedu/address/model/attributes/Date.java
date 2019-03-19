package seedu.address.model.attributes;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

/**
 * Represents a date in the finance tracker.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date implements Comparable<Date> {

    public static final String MESSAGE_CONSTRAINTS =
            "Date should only be dd-mm-yyyy format.";
    public static final String MESSAGE_DATE_DOES_NOT_EXIST = "Date does not exist.";
    private static final String VALIDATION_REGEX = "\\d{2}-\\d{2}-\\d{4}";
    private LocalDate localDate;

    /**
     * Constructs a {@code Date}.
     * @param date A valid date number.
     */
    public Date(String date) throws DateTimeParseException {
        requireNonNull(date);
        //checkArgument((isValidDate(date)=="format"), MESSAGE_CONSTRAINTS);
        //checkArgument((isValidDate(date)=="exist"), MESSAGE_DATE_DOES_NOT_EXIST);
        localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-uuuu")
                .withResolverStyle(ResolverStyle.STRICT));
    }

    public LocalDate getLocalDate() {
        return this.localDate;
    }

    public void setLocalDate(String date) {
        this.localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-uuuu")
                .withResolverStyle(ResolverStyle.STRICT));
    }

    /**
     * Returns string that informs if a given string is valid or of the wrong date format or does not exist.
     */
    public static String isValidDate(String test) {
        if (!test.matches(VALIDATION_REGEX)) {
            return "format";
        } else if (!doesDateExist(test)) {
            return "exist";
        }
        return "valid";
    }

    /**
     * Returns true if date exists.
     */
    private static boolean doesDateExist(String test) throws DateTimeParseException {
        try {
            LocalDate localDate = LocalDate.parse(test, DateTimeFormatter.ofPattern("dd-MM-uuuu")
                    .withResolverStyle(ResolverStyle.STRICT));
        } catch (DateTimeParseException dtpe) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format("%02d-%02d-%4d", localDate.getDayOfMonth(), localDate.getMonthValue(),
                localDate.getYear());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Date // instanceof handles nulls
                && localDate.getDayOfMonth() == (((Date) other).localDate.getDayOfMonth()) // state check
                && localDate.getMonthValue() == (((Date) other).localDate.getMonthValue())
                && localDate.getYear() == ((Date) other).localDate.getYear());
    }

    /**
     * Returns -1, 0 and 1 respectively if Date is earlier than, same as, or later than {@param other}
     */
    public int compareTo(Date other) {
        if (this.localDate.isBefore(other.localDate)) {
            return -1;
        }
        if (this.localDate.isAfter(other.localDate)) {
            return 1;
        }
        return 0;
        /*
        if (this.localDate.getYear() < other.localDate.getYear()) {
            return -1;
        }
        if (this.localDate.getYear() > other.localDate.getYear()) {
            return 1;
        }

        // years are the same
        if (this.localDate.getMonthValue() < other.localDate.getMonthValue()) {
            return -1;
        }
        if (this.localDate.getMonthValue() > other.localDate.getMonthValue()) {
            return 1;
        }

        // years and months are the same
        if (this.localDate.getDayOfMonth() < other.localDate.getDayOfMonth()) {
            return -1;
        }
        if (this.localDate.getDayOfMonth() > other.localDate.getDayOfMonth()) {
            return 1;
        }
        return 0;*/
    }


    /**
     * @return true if localDate is the same date as current date
     */
    public boolean isWithinDay() {
        // get today's date
        LocalDate currentDate = LocalDate.now();
        return currentDate.equals(localDate);
    }

    /**
     * @return true if localDate is 1 week within the current date (including today)
     * if today's date is 13-01-1996, the last date that is within the range would be 07-01-1996
     */
    public boolean isWithinWeek() {
        // get today's date
        LocalDate currentDate = LocalDate.now();
        // get date is one week before today's date
        LocalDate lastWeekDate = currentDate.minusWeeks(1);
        return currentDate.equals(localDate) || (localDate.isAfter(lastWeekDate) && localDate.isBefore(currentDate));
    }

    /**
     * @return true if localDate is 1 month within the current date (including today)
     */
    public boolean isWithinMonth() {
        // get today's date
        LocalDate currentDate = LocalDate.now();
        // get date is one month before today's date
        LocalDate lastMonthDate = currentDate.minusMonths(1);
        return currentDate.equals(localDate) || (localDate.isAfter(lastMonthDate) && localDate.isBefore(currentDate));
    }

    /**
     * @return true if localDate is 1 year within the current date (including today)
     */
    public boolean isWithinYear() {
        // get today's date
        LocalDate currentDate = LocalDate.now();
        // get date is one year before today's date
        LocalDate lastYearDate = currentDate.minusYears(1);
        return currentDate.equals(localDate) || (localDate.isAfter(lastYearDate) && localDate.isBefore(currentDate));
    }

    @Override
    public int hashCode() {
        return this.hashCode();
    }

}
