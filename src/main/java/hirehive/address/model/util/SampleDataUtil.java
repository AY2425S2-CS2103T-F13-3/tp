package hirehive.address.model.util;

import java.util.HashSet;
import java.util.Set;

import hirehive.address.logic.parser.ParserUtil;
import hirehive.address.logic.parser.exceptions.ParseException;
import hirehive.address.model.AddressBook;
import hirehive.address.model.ReadOnlyAddressBook;
import hirehive.address.model.person.Address;
import hirehive.address.model.person.Email;
import hirehive.address.model.person.InterviewDate;
import hirehive.address.model.person.Name;
import hirehive.address.model.person.Note;
import hirehive.address.model.person.Person;
import hirehive.address.model.person.Phone;
import hirehive.address.model.person.Role;
import hirehive.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alice Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"), new Role("UI designer"),
                Tag.APPLICANT, new Note("20 years old"), new InterviewDate()),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new Role("HR manager"),
                Tag.INTERVIEWEE, new Note(""), new InterviewDate("01/03/2025")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new Role("HR manager"),
                Tag.CANDIDATE, new Note(""), new InterviewDate()),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new Role("Software Engineer"),
                Tag.OFFERED, new Note(""), new InterviewDate("06/07/2025")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"), new Role("Software Engineer"),
                Tag.REJECTED, new Note("30 years old"), new InterviewDate("06/03/2025")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"), new Role("Cybersecurity specialist"),
                Tag.APPLICANT, new Note(""), new InterviewDate())
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }
}
