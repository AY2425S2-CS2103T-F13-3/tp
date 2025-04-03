package hirehive.address.model.person;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import hirehive.address.logic.Messages;

/**
 * Tests that a {@code Person}'s {@code InterviewDate} is within the given amount of days from the current date.
 */
public class UpcomingInterviewPredicate implements PersonPredicate {
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final int days;
    private final LocalDate currDay;

    /**
     * Default constructor for {@code UpcomingInterviewPredicate}
     * @param days Number of days to query for
     */
    public UpcomingInterviewPredicate(int days) {
        currDay = LocalDate.now();
        this.days = days;
    }

    /**
     * Testing constructor for {@code UpcomingInterviewPredicate}, allows setting of current day to given value
     * @param days Number of days to query for
     * @param currDay Initialized date to query from
     */
    public UpcomingInterviewPredicate(int days, String currDay) {
        this.currDay = LocalDate.parse(currDay, DATE_TIME_FORMATTER);
        this.days = days;
    }

    @Override
    public boolean test(Person person) {
        return person.getDate().value.map(date -> ChronoUnit.DAYS.between(currDay, date))
                .map(days -> days <= this.days && days >= 0)
                .orElse(false);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UpcomingInterviewPredicate)) {
            return false;
        }

        UpcomingInterviewPredicate otherUpcomingInterviewPredicate = (UpcomingInterviewPredicate) other;
        return this.days == otherUpcomingInterviewPredicate.days && this.currDay.equals(otherUpcomingInterviewPredicate.currDay);
    }

    @Override
    public String getSuccessString() {
        return String.format(Messages.MESSAGE_FILTER_OVERVIEW_DATE, days);
    }
}
