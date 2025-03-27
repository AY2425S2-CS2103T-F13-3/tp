package hirehive.address.model.tag;

import static java.util.Objects.requireNonNull;

import hirehive.address.commons.util.AppUtil;

/**
 * Represents a Tag in the address book.
 */

public enum Tag {
    APPLICANT ("Applicant"),
    CANDIDATE ("Candidate"),
    INTERVIEWEE ("Interviewee"),
    OFFERED ("Offered"),
    REJECTED ("Rejected");

    public final String tagName;
    private static final Tag DEFAULT_TAG = APPLICANT;

    public static final String MESSAGE_CONSTRAINTS = "Tag should be one of the following:\n"
            + " 1. Applicant\n"
            + " 2. Candidate\n"
            + " 3. Interviewee\n"
            + " 4. Offered\n"
            + " 5. Rejected\n";

    Tag(String tagName) {
        this.tagName = tagName;
    }

    public static Tag getDefaultTag() {
        return DEFAULT_TAG;
    }
}

//public class Tag {
//
//    public static final String MESSAGE_CONSTRAINTS = "Tags names should be alphanumeric";
//    public static final String VALIDATION_REGEX = "\\p{Alnum}+";
//    public static final String DEFAULT_TAG = "Applicant";
//
//    public final String tagName;
//
//    /**
//     * Constructs a {@code Tag}.
//     *
//     * @param tagName A valid tag name.
//     */
//    public Tag(String tagName) {
//        requireNonNull(tagName);
//        AppUtil.checkArgument(isValidTagName(tagName), MESSAGE_CONSTRAINTS);
//        this.tagName = tagName;
//    }
//
//    /**
//     * Returns true if a given string is a valid tag name.
//     */
//    public static boolean isValidTagName(String test) {
//        return test.matches(VALIDATION_REGEX);
//    }
//
//    /**
//     * Returns a default tag for new persons.
//     */
//    public static Tag getDefaultTag() {
//        return new Tag(DEFAULT_TAG);
//    }
//
//    @Override
//    public boolean equals(Object other) {
//        if (other == this) {
//            return true;
//        }
//
//        // instanceof handles nulls
//        if (!(other instanceof Tag)) {
//            return false;
//        }
//
//        Tag otherTag = (Tag) other;
//        return tagName.equalsIgnoreCase(otherTag.tagName);
//    }
//
//    @Override
//    public int hashCode() {
//        return tagName.hashCode();
//    }
//
//    /**
//     * Format state as text for viewing.
//     */
//    public String toString() {
//        return '[' + tagName + ']';
//    }
//
//}
