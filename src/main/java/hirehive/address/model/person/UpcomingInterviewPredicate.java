package hirehive.address.model.person;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.function.Predicate;

import hirehive.address.commons.util.StringUtil;

public class UpcomingInterviewPredicate implements Predicate<Person> {
    private final int days;
    private final LocalDate currDay;

    public UpcomingInterviewPredicate(int days) {
        currDay = LocalDate.now();
        this.days = days;
    }

    @Override
    public boolean test(Person person) {
        return person.getDate().value.map(date -> Math.abs(ChronoUnit.DAYS.between(currDay, date)) >= this.days)
                .orElse(false);
    }
}
