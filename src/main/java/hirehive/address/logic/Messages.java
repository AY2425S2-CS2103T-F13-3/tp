package hirehive.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import hirehive.address.logic.parser.Prefix;
import hirehive.address.model.person.Person;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The provided index is invalid. ";
    public static final String MESSAGE_INDEX_OUT_OF_BOUNDS = "The provided index is out of bounds."
            + "\n Please enter a number between 1 and %d";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_NO_SUCH_PERSON = "No such person exists.";

    public static final String MESSAGE_MULTIPLE_PEOPLE_QUERIED = "There are multiple contacts containing the given name."
            + "\n Please enter the full name of the person";

    public static final String MESSAGE_FILTER_OVERVIEW_TAG = "Showing all %s entries.";
    public static final String MESSAGE_FIlTER_OVERVIEW_DATE = "Showing entries with interviews in %1$d days.";
    public static final String MESSAGE_FIlTER_OVERVIEW_NAME = "Showing entries with keywords %s in name.";
    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code person} for display to the user.
     */
    public static String format(Person person) {
        final StringBuilder builder = new StringBuilder();
        builder.append(person.getName())
                .append("; Phone: ")
                .append(person.getPhone())
                .append("; Email: ")
                .append(person.getEmail())
                .append("; Address: ")
                .append(person.getAddress())
                .append("; Role: ")
                .append(person.getRole())
                .append("; Note: ")
                .append(person.getNote())
                .append("; Date: ")
                .append(person.getDate())
                .append("; Tag: ");
        person.getTags().forEach(builder::append);
        return builder.toString();
    }

}
