package hirehive.address.model.person;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.function.Predicate;

import hirehive.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s {@code InterviewDate} is within the given amount of days from the current date.
 */
public class UpcomingInterviewPredicate implements Predicate<Person> {
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
        return person.getDate().value.map(date -> ChronoUnit.DAYS.between(currDay, date) <= this.days)
                .orElse(false);
    }
}
