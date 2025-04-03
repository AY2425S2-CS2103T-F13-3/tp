package hirehive.address.testutil;

import static hirehive.address.logic.commands.CommandTestUtil.DEFAULT_TAG_APPLICANT;
import static hirehive.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static hirehive.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static hirehive.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static hirehive.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static hirehive.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static hirehive.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static hirehive.address.logic.commands.CommandTestUtil.VALID_NOTE_AMY;
import static hirehive.address.logic.commands.CommandTestUtil.VALID_NOTE_BOB;
import static hirehive.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static hirehive.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static hirehive.address.logic.commands.CommandTestUtil.VALID_ROLE_AMY;
import static hirehive.address.logic.commands.CommandTestUtil.VALID_ROLE_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hirehive.address.model.AddressBook;
import hirehive.address.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("94351253").withRole("Software Engineer")
            .withTag("Applicant").withNote("Shy").withDate("").build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432").withRole("Software Engineer")
            .withTag("Rejected").withNote("6 foot tall").withDate("01/02/2025").build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("wall street").withRole("Software Engineer")
            .withTag("Applicant").withNote("Only 18 years old").withDate("").build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street").withRole("Software Engineer")
            .withTag("Applicant").withNote("Funny").withDate("").build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("94822243")
            .withEmail("werner@example.com").withAddress("michegan ave").withRole("Software Engineer")
            .withTag("Rejected").withNote("Cheerful").withDate("01/03/2025").build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("94824273")
            .withEmail("lydia@example.com").withAddress("little tokyo").withRole("Software Engineer")
            .withTag("Rejected").withNote("Scary").withDate("01/03/2025").build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best").withPhone("94824421")
            .withEmail("anna@example.com").withAddress("4th street").withRole("Software Engineer")
            .withTag("Applicant").withNote("Fat").withDate("").build();
    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withPhone("84824246")
            .withEmail("stefan@example.com").withAddress("little india").withRole("Software Engineer")
            .withNote("Skinny").build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withPhone("84821312")
            .withEmail("hans@example.com").withAddress("chicago ave").withRole("Software Engineer")
            .withNote("Has criminal record").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withRole(VALID_ROLE_AMY)
            .withTag(DEFAULT_TAG_APPLICANT).withNote(VALID_NOTE_AMY).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withRole(VALID_ROLE_BOB)
            .withTag(DEFAULT_TAG_APPLICANT).withNote(VALID_NOTE_BOB).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
