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
    public static final String MESSAGE_DEADLINE_CONSTRAINTS =
            "Deadline must not be a date that has already passed.";
    public static final String MESSAGE_DATE_DOES_NOT_EXIST = "Date does not exist.";
    private static final String VALIDATION_REGEX = "\\d{2}-\\d{2}-\\d{4}";
    private LocalDate date;

    /**
     * Constructs a {@code Date}.
     * @param date A valid date number.
     */
    public Date(String date) throws DateTimeParseException {
        requireNonNull(date);
        //checkArgument((isValidDate(date)=="format"), MESSAGE_CONSTRAINTS);
        //checkArgument((isValidDate(date)=="exist"), MESSAGE_DATE_DOES_NOT_EXIST);
        this.date = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-uuuu")
                .withResolverStyle(ResolverStyle.STRICT));
    }

    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-uuuu")
                .withResolverStyle(ResolverStyle.STRICT));
    }

    public void setDate(Date date) {
        this.date = date.getDate();
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
            LocalDate date = LocalDate.parse(test, DateTimeFormatter.ofPattern("dd-MM-uuuu")
                    .withResolverStyle(ResolverStyle.STRICT));
        } catch (DateTimeParseException dtpe) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format("%02d-%02d-%4d", date.getDayOfMonth(), date.getMonthValue(),
                date.getYear());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Date // instanceof handles nulls
                && date.getDayOfMonth() == (((Date) other).date.getDayOfMonth()) // state check
                && date.getMonthValue() == (((Date) other).date.getMonthValue())
                && date.getYear() == ((Date) other).date.getYear());
    }

    /**
     * Returns -1, 0 and 1 respectively if Date is earlier than, same as, or later than {@param other}
     */
    @Override
    public int compareTo(Date other) {
        if (this.date.isBefore(other.date)) {
            return -1;
        }
        if (this.date.isAfter(other.date)) {
            return 1;
        }
        return 0;
        /*
        if (this.date.getYear() < other.date.getYear()) {
            return -1;
        }
        if (this.date.getYear() > other.date.getYear()) {
            return 1;
        }

        // years are the same
        if (this.date.getMonthValue() < other.date.getMonthValue()) {
            return -1;
        }
        if (this.date.getMonthValue() > other.date.getMonthValue()) {
            return 1;
        }

        // years and months are the same
        if (this.date.getDayOfMonth() < other.date.getDayOfMonth()) {
            return -1;
        }
        if (this.date.getDayOfMonth() > other.date.getDayOfMonth()) {
            return 1;
        }
        return 0;*/
    }

    /**
     * Checks against operating system's date and check whether {@code date} is equal or after this date.
     *
     * @return true if date is the same as the system's current date or if it falls on a date after the
     * system's current date, else it returns false
     */
    public boolean isEqualOrAfterToday() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        Date currentDate = new Date(dtf.format(LocalDate.now()));

        if (compareTo(currentDate) == -1) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public int hashCode() {
        return this.hashCode();
    }

}
