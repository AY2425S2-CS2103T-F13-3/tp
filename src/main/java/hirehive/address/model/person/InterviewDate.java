package hirehive.address.model.person;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

import hirehive.address.commons.util.AppUtil;

/**
 * Represents a Person's interview date in the address book
 * Can be empty. Represented in the string format of DD/MM/YYYY
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class InterviewDate {
    public static final String MESSAGE_CONSTRAINTS = "Dates are in the DD/MM/YYYY format";
    public static final String DEFAULT_DATE = "01/01/2025";

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public final Optional<LocalDate> value;

    public InterviewDate() {
        value = Optional.empty();
    }

    /**
     * Constructor for new {@link InterviewDate} objects with initialized dates
     * @param date Date value
     */
    public InterviewDate(String date) {
        requireNonNull(date);
        AppUtil.checkArgument(isValidDate(date), MESSAGE_CONSTRAINTS);
        if (!date.isEmpty()) {
            value = Optional.of(LocalDate.parse(date, DATE_TIME_FORMATTER));
        } else {
            value = Optional.empty();
        }
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        if (test.isEmpty()) {
            return true;
        }
        try {
            LocalDate.parse(test, DATE_TIME_FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return value.map(x -> x.format(DATE_TIME_FORMATTER)).orElse("");
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
