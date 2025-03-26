package hirehive.address.model.person;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import hirehive.address.commons.util.AppUtil;

/**
 * Represents a Person's interview date in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class InterviewDate {
    public static final String MESSAGE_CONSTRAINTS = "Dates are in the DD/MM/YYYY format";
    public static final String DEFAULT_DATE = "01/01/2025";

    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public final LocalDate value;

    public InterviewDate(String dateTime) {
        requireNonNull(dateTime);
        AppUtil.checkArgument(isValidDate(dateTime), MESSAGE_CONSTRAINTS);
        value = LocalDate.parse(dateTime, formatter);
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        try {
            LocalDate.parse(test, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return value.format(formatter);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof InterviewDate)) {
            return false;
        }

        InterviewDate otherDate = (InterviewDate) other;
        return value.equals(otherDate.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
