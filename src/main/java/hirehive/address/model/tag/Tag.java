package hirehive.address.model.tag;

/**
 * Represents a Tag in the address book.
 */

public enum Tag {
    APPLICANT("Applicant"),
    CANDIDATE("Candidate"),
    INTERVIEWEE("Interviewee"),
    OFFERED("Offered"),
    REJECTED("Rejected");

    public static final String MESSAGE_CONSTRAINTS = "Tag should be one of the following:\n"
            + " 1. Applicant\n"
            + " 2. Candidate\n"
            + " 3. Interviewee\n"
            + " 4. Offered\n"
            + " 5. Rejected\n";

    private static final Tag DEFAULT_TAG = APPLICANT;

    public final String tagName;

    Tag(String tagName) {
        this.tagName = tagName;
    }

    public String getTagName() {
        return tagName;
    }

    public static Tag getDefaultTag() {
        return DEFAULT_TAG;
    }
}
