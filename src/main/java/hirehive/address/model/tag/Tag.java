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

    /**
     * Gets the tag of the hiring stage that is offset from this tag
     * @param offset of the wanted hiring stage tag from this tag
     * @return tag that is offset from this tag
     */
    public Tag offsetBy(int offset) {
        if (offset == 0) {
            return this;
        }
        if (offset > 0) {
            return switch (this) {
            case REJECTED -> Tag.APPLICANT.offsetBy(offset - 1);
            case APPLICANT -> Tag.CANDIDATE.offsetBy(offset - 1);
            case CANDIDATE -> Tag.INTERVIEWEE.offsetBy(offset - 1);
            case INTERVIEWEE -> Tag.OFFERED.offsetBy(offset - 1);
            case OFFERED -> this;
            };
        }
        return switch (this) {
        case REJECTED -> this;
        case APPLICANT -> Tag.REJECTED.offsetBy(offset + 1);
        case CANDIDATE -> Tag.APPLICANT.offsetBy(offset + 1);
        case INTERVIEWEE -> Tag.CANDIDATE.offsetBy(offset + 1);
        case OFFERED -> Tag.INTERVIEWEE.offsetBy(offset + 1);
        };
    }
}
