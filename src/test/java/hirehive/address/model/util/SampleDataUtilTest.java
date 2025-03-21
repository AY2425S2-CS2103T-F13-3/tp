package hirehive.address.model.util;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import hirehive.address.model.ReadOnlyAddressBook;
import hirehive.address.model.person.Person;

public class SampleDataUtilTest {

    private final Person[] samplePersons = SampleDataUtil.getSamplePersons();
    private final ReadOnlyAddressBook sampleAddressBook = SampleDataUtil.getSampleAddressBook();

    @Test
    public void getSampleAddressBook_containsAllSamplePersons() {
        for (Person person : samplePersons) {
            assertTrue(sampleAddressBook.getPersonList().contains(person),
                    "AddressBook should contain: " + person);
        }
    }

}
