package hirehive.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import hirehive.address.commons.util.ToStringBuilder;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** The application should exit. */
    private final boolean exit;

    /** Note window should be displayed to the user. */
    private final boolean showNote;

    /** Saved message should appear to the user */
    private final boolean isChange;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit, boolean showNote, boolean isChange) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.showNote = showNote;
        this.isChange = isChange;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, false, false);
    }

    public CommandResult(String feedbackToUser, boolean isChange) {
        this(feedbackToUser, false, false, false, isChange);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isExit() {
        return exit;
    }

    public boolean isShowNote() {
        return showNote;
    }

    public boolean isChange() {
        return isChange;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && showHelp == otherCommandResult.showHelp
                && exit == otherCommandResult.exit
                && showNote == otherCommandResult.showNote
                && isChange == otherCommandResult.isChange;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("feedbackToUser", feedbackToUser)
                .add("showHelp", showHelp)
                .add("exit", exit)
                .add("showNote", showNote)
                .add("isChange", isChange)
                .toString();
    }

}
