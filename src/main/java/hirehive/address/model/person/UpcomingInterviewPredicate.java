package hirehive.address.model.person;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.function.Predicate;

import hirehive.address.commons.util.StringUtil;

public class UpcomingInterviewPredicate implements Predicate<Person> {
    private final int days;
    private final LocalDate currDay;

    public static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public UpcomingInterviewPredicate(int days) {
        currDay = LocalDate.now();
        this.days = days;
    }

    public UpcomingInterviewPredicate(int days, String currDay) { //testing constructor
        this.currDay = LocalDate.parse(currDay, DATE_TIME_FORMATTER);
        this.days = days;
    }

    @Override
    public boolean test(Person person) {
        return person.getDate().value.map(date -> ChronoUnit.DAYS.between(currDay, date) <= this.days)
                .orElse(false);
    }
}
